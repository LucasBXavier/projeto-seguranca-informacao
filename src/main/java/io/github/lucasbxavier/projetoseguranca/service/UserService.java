package io.github.lucasbxavier.projetoseguranca.service;

import io.github.lucasbxavier.projetoseguranca.dto.UserRequestDTO;
import io.github.lucasbxavier.projetoseguranca.dto.UserResponseDTO;
import io.github.lucasbxavier.projetoseguranca.mapper.UserMapper;
import io.github.lucasbxavier.projetoseguranca.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository repository;
    private UserMapper mapper;

    public void createUser(UserRequestDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado");
        }
        if (repository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username já cadastrado");
        } else {
            var passwordHash = passwordEncoder.encode(dto.getPassword());
            var user = UserMapper.toEntity(dto, passwordHash);
            repository.save(user);
        }
    }

    public UserResponseDTO getUserInfo(String email) {
        var user = repository.findByEmail(email);
        if (user.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }
        return UserMapper.toResponse(user.get());
    }}
