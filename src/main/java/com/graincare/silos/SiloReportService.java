package com.graincare.silos;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graincare.exceptions.SiloNotFoundException;
import com.graincare.user.LoggedUser;
import com.graincare.user.User;

@Service
public class SiloReportService {
	
	@Autowired
	private LoggedUser loggedUser;
	@Autowired
	private SiloRepository siloRepository;
	@Autowired
	private SiloHistoryRepository siloHistoryRepository;
	@Autowired
	private SiloReportGenerator siloReportGenerator;
	
	
	public SiloReportDTO generateReport(Long siloId, Date startDate, Date endDate) {
		User user = loggedUser.get();
		Optional<Silo> silo = siloRepository.findByIdAndFarmUserId(siloId, user.getId());
		if(!silo.isPresent()) {
			throw new SiloNotFoundException();
		}
		
		List<Object[]> results = siloHistoryRepository.generateReportFor(siloId, startDate, endDate);
		SiloReportDTO siloReportDTO = siloReportGenerator.generateFor(silo.get(), results, startDate, endDate);
		
		return siloReportDTO;
	}
	
}
