package com.graincare.graos;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrainBagPriceDAO {
	private final static Logger LOGGER = LoggerFactory.getLogger(GrainBagPriceDAO.class);

	private static final String BASE_ENDPOINT = "http://www.agrolink.com.br/cotacoes/historico";
	@Autowired
	private Client client;
	private WebTarget baseTarget;

	@PostConstruct
	public void init() {
		baseTarget = client.target(BASE_ENDPOINT);
	}

	public Double getPriceFor(String grain, Calendar date) {
		String response = requestTo(getUriFor(grain));

		Elements trs = Jsoup.parse(response).select("#ctl00_cphConteudo_gvHistorico tr");
		if (trs.size() > 0) {
			trs.remove(0);
		}

		String year = String.valueOf(date.get(Calendar.YEAR));
		String month = String.valueOf(date.get(Calendar.MONTH) + 1);
		for (Element tr : trs) {
			Elements tds = tr.select("td");
			if (year.equals(tds.get(0).text().trim()) && month.equals(tds.get(1).text().trim())) {
				return Double.valueOf(tds.get(2).text().trim().replace(",", "."));
			}
		}

		return 0.0;
	}

	private String getUriFor(String grain) {
		if (GrainType.SORGO.getType().equals(grain)) {
			return "/ms/sorgo-sc-60kg";
		} else if (GrainType.MILHO.getType().equals(grain)) {
			return "/sp/milho-seco-sc-60kg";
		} else {
			return "/sp/soja-em-grao-sc-60kg";
		}
	}

	private String requestTo(String url) {
		try {
			WebTarget target = baseTarget.path(url);
			return target.request().get(String.class);
		} catch (Exception e) {
			LOGGER.error("Fail to do request for: {}", url, e);
			return "";
		}
	}
}
