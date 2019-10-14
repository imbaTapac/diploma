package com.student.rating.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.rating.dto.StudentProfileDTO;
import com.student.rating.entity.Student;

/**
 * Mapping class of {@link com.student.rating.entity.Student} in {@link com.student.rating.dto.StudentProfileDTO} and backward
 *
 * @since 1.0
 */

@Component
public class StudentProfileDTOMapper implements AbstractDTOMapper<Student, StudentProfileDTO> {

	private final ModelMapper modelMapper;

	@Autowired
	public StudentProfileDTOMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public StudentProfileDTO toDto(Student entity) {
		return modelMapper.map(entity, StudentProfileDTO.class);
	}
}
