package io.github.lucasbxavier.projetoseguranca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String username;
    private String email;
    private String name;
    private LocalDateTime createdAt;

}