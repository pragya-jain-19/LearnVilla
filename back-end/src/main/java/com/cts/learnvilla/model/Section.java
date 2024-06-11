package com.cts.learnvilla.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sections")
public class Section {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer sectionId;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="course_id")
	private Course course;

	@Column(nullable=false)
	private Integer sectionNumber = 1;

	@Column(nullable=false)
	private String sectionName;

	@Column(length = 500)
	private String sectionContent;

	private String videoUrl;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate = LocalDateTime.now();

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedDate = LocalDateTime.now();
}
