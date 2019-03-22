package com.student.rating.mapper;

import com.student.rating.dto.ResultRatingDTO;
import com.student.rating.entity.Rating;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RatingObjectDTOMapper implements AbstractDTOMapper<Rating, ResultRatingDTO> {

    private ModelMapper modelMapper;

    @Autowired
    public RatingObjectDTOMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public ResultRatingDTO toDto(Rating entity) {
        return modelMapper.map(entity,ResultRatingDTO.class);
    }
}
