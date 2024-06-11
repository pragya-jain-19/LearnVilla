package com.cts.learnvilla.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enrollments")
public class Enrollment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enrollmentId;

	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime enrolledDate = LocalDateTime.now();

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime completedDate = null;

	private Double progress = 0.0;

	private Boolean isProgressLeft = true;

}
