package de.gruppe1.studydash.mappers;

import de.gruppe1.studydash.dtos.CourseDto;
import de.gruppe1.studydash.entities.Course;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDto toCourseDto(Course course);

    Course dtoToCourse(CourseDto courseDto);

    List<CourseDto> toCourseDtos(List<Course> courses);
}
