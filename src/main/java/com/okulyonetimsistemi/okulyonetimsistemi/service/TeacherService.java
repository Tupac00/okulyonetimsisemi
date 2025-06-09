package com.okulyonetimsistemi.okulyonetimsistemi.service;

import com.okulyonetimsistemi.okulyonetimsistemi.entity.Teacher;
import com.okulyonetimsistemi.okulyonetimsistemi.exception.ResourceNotFoundException;
import com.okulyonetimsistemi.okulyonetimsistemi.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id " + id));
    }

    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher updateTeacher(Long id, Teacher updatedData) {
        Teacher existing = getTeacherById(id);
        existing.setFirstName(updatedData.getFirstName());
        existing.setLastName(updatedData.getLastName());
        existing.setSubject(updatedData.getSubject());
        return teacherRepository.save(existing);
    }

    public void deleteTeacher(Long id) {
        Teacher existing = getTeacherById(id);
        teacherRepository.delete(existing);
    }
}