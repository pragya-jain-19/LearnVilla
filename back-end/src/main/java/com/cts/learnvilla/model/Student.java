package com.cts.learnvilla.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer studentId;

	@Column(nullable=false)
	private String firstName;

	private String lastName;

	@Column(unique = true, length=15)
	private String mobile;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
//	@JsonIgnore
	private String password;

	@Column(nullable = false)
//	@JsonIgnore
	private String confirmPassword;

	@OneToMany(mappedBy = "student")
	@JsonIgnore
	private List<Enrollment> enrollments = new ArrayList<>();

	@OneToOne(mappedBy = "student")
	@JsonIgnore
	private Wishlist wishlist;
	
	@Column(nullable = false)
	private Boolean isActive = true;

	@JsonIgnore
	private String role = "STUDENT";

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role));
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

