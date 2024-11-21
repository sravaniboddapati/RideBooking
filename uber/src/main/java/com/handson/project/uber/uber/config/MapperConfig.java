package com.handson.project.uber.uber.config;

import com.handson.project.uber.uber.dto.PointDto;
import com.handson.project.uber.uber.util.GeometryUtil;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
   public ModelMapper mapper() {
    ModelMapper mapper = new ModelMapper();
        mapper.typeMap(PointDto.class, Point.class).setConverter(context -> {
            PointDto pointDto = context.getSource();
            return GeometryUtil.createPoint(pointDto);
        });
    mapper.typeMap(Point.class, PointDto.class).setConverter(converter ->{
        Point point = converter.getSource();
        double coordinates[] = {
                point.getX(),
                point.getY()
        };
        return new PointDto(coordinates);
    });
    return mapper;
    }

}
