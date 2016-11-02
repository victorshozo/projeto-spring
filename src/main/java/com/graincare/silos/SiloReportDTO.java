package com.graincare.silos;

import java.util.Calendar;
import java.util.List;

public class SiloReportDTO {
	private Long siloId;
	private Calendar reportStart;
	private Calendar reportEnd;
	private Double siloCapacityUse;
	private List<SiloReport> data;

	public Long getSiloId() {
		return siloId;
	}

	public Calendar getReportStart() {
		return reportStart;
	}

	public Calendar getReportEnd() {
		return reportEnd;
	}

	public Double getSiloCapacityUse() {
		return siloCapacityUse;
	}

	public List<SiloReport> getData() {
		return data;
	}

	public void setSiloId(Long siloId) {
		this.siloId = siloId;
	}

	public void setReportStart(Calendar reportStart) {
		this.reportStart = reportStart;
	}

	public void setReportEnd(Calendar reportEnd) {
		this.reportEnd = reportEnd;
	}

	public void setSiloCapacityUse(Double siloCapacityUse) {
		this.siloCapacityUse = siloCapacityUse;
	}

	public void setData(List<SiloReport> data) {
		this.data = data;
	}

}
