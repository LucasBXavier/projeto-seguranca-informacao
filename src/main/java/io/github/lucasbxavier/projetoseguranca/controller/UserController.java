package io.github.lucasbxavier.projetoseguranca.controller;

import io.github.lucasbxavier.projetoseguranca.dto.LoginRequestDTO;
import io.github.lucasbxavier.projetoseguranca.dto.UserRequestDTO;
import io.github.lucasbxavier.projetoseguranca.dto.UserResponseDTO;
import io.github.lucasbxavier.projetoseguranca.service.LoginService;
import io.github.lucasbxavier.projetoseguranca.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:5500", allowCredentials = "true")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private LoginService loginService;


    @Operation(
            summary = "Criar usuário",
            description = "Cria um novo usuário para acessar o sistema."
    )
    @PostMapping("/criarUsuario")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserRequestDTO user) {
        userService.createUser(user);
        return ResponseEntity.ok().body("Usuário criado com sucesso");
    }

    @Operation(
            summary = "Login de usuário",
            description = "Autentica um usuário com email e senha para acessar o sistema."
    )
    @PostMapping
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO user, HttpServletRequest request) {
        loginService.login(user, request);
        return ResponseEntity.ok(Map.of("message", "Login realizado com sucesso"));
    }

    @PostMapping("/buscarUser/{email}")
    public ResponseEntity<UserResponseDTO> buscarUser(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserInfo(email));
    }
}
