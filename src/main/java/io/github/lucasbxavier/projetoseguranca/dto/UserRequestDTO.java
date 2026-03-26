package io.github.lucasbxavier.projetoseguranca.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank
    @NotNull
    public String username;
    @NotBlank
    @NotNull
    @Email(message = "Email inválido")
    public String email;
    @NotBlank
    @NotNull
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres, incluindo letras, números e caracteres especiais")
    public String password;
    @NotBlank
    @NotNull
    public String name;
}
