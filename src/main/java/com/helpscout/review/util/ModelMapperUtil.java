package com.helpscout.review.util;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;


public class ModelMapperUtil {

    private static final ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
    }

    public static <S, T> List<T> convertDtoToEntity(List<S> sourceDto, Class<T> targetEntity) {
        return sourceDto.stream()
                .map(element -> modelMapper.map(element, targetEntity))
                .collect(Collectors.toList());
    }

    public static <S, T>  T convertDtoToEntity( S sourceDto, Class<T> targetEntity) {
        return  modelMapper.map(sourceDto, targetEntity);

    }
}
