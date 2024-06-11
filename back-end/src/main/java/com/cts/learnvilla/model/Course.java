package com.cts.learnvilla.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "courses")
public class Course {

	@Id
	@Column(length = 6)
	private String courseId;

	@Column(nullable = false)
	private String courseName;

	@Column(nullable = false)
	private String description;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	private Double duration;

	@Column(nullable = false)
	private Double price;

	private Integer studentsEnrolled = 0;

	private Double rating = 0.0;

	@Column(length = 500)
	private String courseImage;

	@Column(nullable = false)
	private String language = "English";

	@JsonIgnore
	@Column(nullable = false)
	@OneToMany(mappedBy = "course")
	private List<Section> sections = new ArrayList<>();

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate = LocalDateTime.now();

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedDate = LocalDateTime.now();

	@OneToMany(mappedBy = "course")
	@JsonIgnore
	private List<Enrollment> enrollments = new ArrayList<>();

}
