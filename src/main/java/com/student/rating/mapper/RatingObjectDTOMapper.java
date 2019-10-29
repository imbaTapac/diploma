package com.student.rating.mapper;

import com.student.rating.dto.ResultRatingDTO;
import com.student.rating.entity.Rating;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapping class of {@link com.student.rating.entity.Rating} in {@link com.student.rating.dto.ResultRatingDTO} and backward
 * @since 1.0
 */
@Component
public class RatingObjectDTOMapper implements AbstractDTOMapper<Rating, ResultRatingDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public RatingObjectDTOMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public ResultRatingDTO toDto(Rating entity) {
        return modelMapper.map(entity,ResultRatingDTO.class);
    }
}
