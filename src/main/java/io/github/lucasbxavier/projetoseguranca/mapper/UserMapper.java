package io.github.lucasbxavier.projetoseguranca.mapper;

import io.github.lucasbxavier.projetoseguranca.dto.UserRequestDTO;
import io.github.lucasbxavier.projetoseguranca.dto.UserResponseDTO;
import io.github.lucasbxavier.projetoseguranca.entities.User;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class UserMapper {

    public static User toEntity(UserRequestDTO dto, String passwordHash) {
        User user = new User();

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordHash);
        user.setName(dto.getName());

        return user;
    }

    public static UserResponseDTO toResponse(User entity) {
        UserResponseDTO dto = new UserResponseDTO();

        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setName(entity.getName());
        dto.setCreatedAt(
                entity.getCreatedAt()
                        .atZoneSameInstant(ZoneId.of("America/Sao_Paulo"))
                        .toLocalDateTime()
        );
        return dto;
    }}
