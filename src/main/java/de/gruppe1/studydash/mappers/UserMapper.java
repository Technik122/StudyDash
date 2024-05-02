package de.gruppe1.studydash.mappers;

import de.gruppe1.studydash.dtos.SignUpDto;
import de.gruppe1.studydash.dtos.UserDto;
import de.gruppe1.studydash.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);
}
