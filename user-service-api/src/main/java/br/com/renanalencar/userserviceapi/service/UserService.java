package br.com.renanalencar.userserviceapi.service;

import br.com.renanalencar.userserviceapi.mapper.UserMapper;
import br.com.renanalencar.userserviceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse findById(final String id) {
        return userMapper.fromEntity(
                userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                        "Objeto não encontrado. Id: " + id + ", Tipo: " + UserResponse.class.getSimpleName())));
    }

    public void save(CreateUserRequest createUserRequest) {
        verifyIfEmailExists(createUserRequest.email(), null);
        userRepository.save(userMapper.fromRequest(createUserRequest));
    }

    private void verifyIfEmailExists(final String email, final String id) {
        userRepository.findByEmail(email)
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new DataIntegrityViolationException("Email [ " + email + " ] já cadastrado.");
                });
    }
}
