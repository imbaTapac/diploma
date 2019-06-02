package com.student.rating.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.rating.dto.StudentRatingDTO;
import com.student.rating.entity.Rating;

@Component
public class StudentRatingDTOMapper implements AbstractDTOMapper<Rating, StudentRatingDTO> {
	private ModelMapper modelMapper;

	@Autowired
	public StudentRatingDTOMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public StudentRatingDTO toDto(Rating entity) {
		return modelMapper.map(entity, StudentRatingDTO.class);
	}
}
