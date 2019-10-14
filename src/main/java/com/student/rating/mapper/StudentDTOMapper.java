package com.student.rating.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.rating.dto.StudentDTO;
import com.student.rating.entity.Student;

/**
 * Mapping class of {@link com.student.rating.entity.Student} in {@link com.student.rating.dto.StudentDTO} and backward
 * @since 1.0
 */
@Component
public class StudentDTOMapper implements AbstractDTOMapper<Student, StudentDTO> {
	private final ModelMapper modelMapper;

	@Autowired
	public StudentDTOMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public StudentDTO toDto(Student entity) {
		return modelMapper.map(entity, StudentDTO.class);
	}
}
