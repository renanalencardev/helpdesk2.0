package br.com.renanalencar.userserviceapi.mapper;

import br.com.renanalencar.userserviceapi.entity.User;
import models.responses.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = IGNORE,
    nullValueCheckStrategy = ALWAYS
)
public interface UserMapper {

    UserResponse fromEntity(final User entity);
}
