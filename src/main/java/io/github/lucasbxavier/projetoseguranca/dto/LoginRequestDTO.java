package io.github.lucasbxavier.projetoseguranca.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequestDTO {

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    @NotNull
    private String email;
    @NotNull
    @NotBlank(message = "Senha obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres, incluindo letras, números e caracteres especiais")
    private String password;
}
