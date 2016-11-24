package com.graincare.user;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;
import static org.apache.commons.codec.binary.Base64.decodeBase64;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.graincare.farm.Farm;


@Entity
@Table(name = "user")
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1077812710316721906L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Email
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "role", nullable = false)
	private String role;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
	private List<Farm> farms = new ArrayList<>();
	
	public User(String email, String password) {
		this.password = password;
		this.email = email;
		this.role = "ROLE_USER";
	}
	
	@Deprecated
	User() {} //Hibernate eyes only
	
	@PrePersist
	@PreUpdate
	void hashPassword() {
		this.password = password != null ? Base64.encodeBase64String(password.getBytes()) : password;
	}
	
	public String getDecryptedpassword(){
		return new String(decodeBase64(password), UTF_8);
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	public List<Farm> getFarms() {
		return farms;
	}
	
	public void setFarms(List<Farm> farms) {
		this.farms = farms;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return asList(new SimpleGrantedAuthority(this.role));
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}