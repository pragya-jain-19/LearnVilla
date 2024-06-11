package com.cts.learnvilla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.learnvilla.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

}
