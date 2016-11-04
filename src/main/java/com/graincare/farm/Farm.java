package com.graincare.farm;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.graincare.user.User;

@Entity
@Table(name = "farm")
public class Farm {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "name", nullable = false)
	private String name;
	@NotNull
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = false)
	private User user;
	
	@Deprecated // hibernate eyes only
	Farm(){}
	
	public Farm(String name, User user) {
		this.name = name;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
