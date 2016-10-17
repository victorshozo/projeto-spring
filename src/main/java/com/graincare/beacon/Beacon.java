package com.graincare.beacon;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "beacon")
public class Beacon {
	@Id
	@GeneratedValue
	private Long id;

	@Deprecated
	Beacon() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Beacon))
			return false;

		Beacon other = (Beacon) obj;
		return Objects.equals(this.id, other.id);
	}
}