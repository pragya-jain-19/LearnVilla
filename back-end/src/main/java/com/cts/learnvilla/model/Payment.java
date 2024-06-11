package com.cts.learnvilla.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "payments")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer paymentId;

	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;

	@ManyToMany
	@JoinTable(name = "payment_courses", joinColumns = @JoinColumn(name = "payment_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> courses;

	private Double amount;

	private Boolean status = true;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime paymentTime = LocalDateTime.now();

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime statusUpdatedTime = null;

}
