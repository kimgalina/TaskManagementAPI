package kg.kim.TaskManagementAPI.mapper;

import kg.kim.TaskManagementAPI.entity.User;
import kg.kim.TaskManagementAPI.payload.user.UserCreateRequest;
import kg.kim.TaskManagementAPI.payload.user.UserCreateResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserCreateRequest request);
    UserCreateResponse toUserCreateResponse(User user);
}
