package com.graincare.beacon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.graincare.exceptions.BeaconHistoryNotFoundException;
import com.graincare.exceptions.InconsistentBeaconOnDatabaseException;
import com.graincare.exceptions.SiloHistoryNotFoundException;
import com.graincare.mail.SmtpEmailSender;
import com.graincare.silos.SiloHistory;
import com.graincare.silos.SiloHistoryRepository;
import com.graincare.user.LoggedUser;

@RestController
public class BeaconController {

	@Autowired
	private BeaconRepository beaconRepository;
	@Autowired
	private BeaconHistoryRepository beaconHistoryRepository;
	@Autowired
	private SiloHistoryRepository siloHistoryRepository;
	@Autowired
	private LoggedUser loggedUser;
	@Autowired
	private SmtpEmailSender emailSender;

	@RequestMapping(path = "/beacons/history", produces = "application/json", method = RequestMethod.GET)
	public List<BeaconHistory> getBeaconHistory() {
		return beaconHistoryRepository.findByBeaconFarmUserId(loggedUser.get().getId());
	}

	@RequestMapping(path = "/beacons", produces = "application/json", method = RequestMethod.GET)
	public List<Beacon> getBeacon() {
		return beaconRepository.findByFarmUserId(loggedUser.get().getId());
	}

	@RequestMapping(path = "/beacons/silo/{siloId}", produces = "application/json", method = RequestMethod.GET)
	public List<BeaconHistory> getBeaconsFor(@PathVariable Long siloId) {
		Long userId = loggedUser.get().getId();
		Optional<SiloHistory> siloHistory = siloHistoryRepository
				.findBySiloIdAndOpenFalseAndSiloFarmUserId(siloId, userId);
		if (!siloHistory.isPresent()) {
			throw new SiloHistoryNotFoundException();
		}

		List<BeaconHistory> beaconsHistories = siloHistory.get().getBeaconsHistory()
							.stream()
							.filter(b -> b.getTemperature() != null && b.getHumidity() != null)
							.collect(Collectors.toList());
		
		List<Long> beaconsIds = new ArrayList<>();
		beaconsHistories.forEach(b -> {
			if(!beaconsIds.contains(b.getBeacon().getId())){
				beaconsIds.add(b.getBeacon().getId());
			}
		});
				
		List<BeaconHistory> result = new ArrayList<>();
		beaconsIds.forEach(beaconId -> {
			result.add(beaconHistoryRepository.findTopByBeaconFarmUserIdAndBeaconIdOrderByUpdatedAtDesc(userId, beaconId).get());
		});
		
		return result;
	}

	@RequestMapping(path = "/beacons/available", produces = "application/json", method = RequestMethod.GET)
	public List<Beacon> getAvailableBeacons() {
		Long userId = loggedUser.get().getId();
		List<Beacon> allBeacons = beaconRepository.findByFarmUserId(userId);

		List<Beacon> unavailableBeacons = new ArrayList<>();
		siloHistoryRepository.findByOpenFalseAndSiloFarmUserId(userId).forEach(s -> {
			s.getBeaconsHistory().forEach(b -> unavailableBeacons.add(b.getBeacon()));
		});

		List<Beacon> beacons = new ArrayList<>();
		allBeacons.stream().forEach(beacon -> {
			if (!unavailableBeacons.contains(beacon)) {
				beacons.add(beacon);
			}
		});

		return beacons;
	}

	@RequestMapping(path = "/beacon/history", method = RequestMethod.POST)
	public void update(@RequestBody BeaconHistoryDTO dto) {
		List<BeaconHistory> beaconsHistories = beaconHistoryRepository.findByBeaconId(dto.getBeaconId());

		if (beaconsHistories.size() == 0) {
			throw new BeaconHistoryNotFoundException();
		}

		List<BeaconHistory> openBeaconsHistories = beaconsHistories.stream().filter(beaconHistory -> {
			return beaconHistory.getSiloHistory().getOpen() == false;
		}).collect(Collectors.toList());

		if (openBeaconsHistories.isEmpty()) {
			throw new InconsistentBeaconOnDatabaseException();
		}

		SiloHistory siloHistory = openBeaconsHistories.get(0).getSiloHistory();
		Beacon beacon = openBeaconsHistories.get(0).getBeacon();

		BeaconHistory beaconHistory = new BeaconHistory();
		beaconHistory.setSiloHistory(siloHistory);
		beaconHistory.setBeacon(beacon);
		beaconHistory.setHumidity(dto.getHumidity());
		beaconHistory.setDistance(dto.getDistance());
		beaconHistory.setTemperature(dto.getTemperature());
		beaconHistoryRepository.save(beaconHistory);

		Double grainMaxTemperature = beaconHistory.getSiloHistory().getGrao().getMaxTemperature();
		if (dto.getTemperature() > grainMaxTemperature) {
			String region = siloHistory.getSilo().getRegion();
			String grao = siloHistory.getGrao().getType();
			String email = siloHistory.getSilo().getFarm().getUser().getEmail();
			String farmName = siloHistory.getSilo().getFarm().getName();

			StringBuilder message = new StringBuilder();
			message.append("<span style='font-size:2em'>");
			message.append("Atenção!<br/>O silo que está com <b>" + grao + "</b> ");
			message.append("na região: <b>" + region + "</b>, na fazenda: <b>" + farmName + "</b>");
			message.append(", está com a temperatura de " + dto.getTemperature() + " graus");
			message.append(", sendo que sua temperatura máxima é de: " + grainMaxTemperature + " graus.");
			message.append("</span>");

			emailSender.to(email)
					.withSubject("ATENÇÃO, silo com temperatura acima do normal!")
					.withMessage(message.toString())
					.send();
		}
	}
}
