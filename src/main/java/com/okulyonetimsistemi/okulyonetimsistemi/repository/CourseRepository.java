package com.okulyonetimsistemi.okulyonetimsistemi.repository;

import com.okulyonetimsistemi.okulyonetimsistemi.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByName(String name);
}
