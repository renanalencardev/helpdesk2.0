package br.com.renanalencar.userserviceapi.service;

import br.com.renanalencar.userserviceapi.entity.User;
import br.com.renanalencar.userserviceapi.mapper.UserMapper;
import br.com.renanalencar.userserviceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse findById(final String id) {
        return userMapper.fromEntity(find(id));
    }

    public void save(CreateUserRequest createUserRequest) {
        verifyIfEmailExists(createUserRequest.email(), null);
        userRepository.save(userMapper.fromRequest(createUserRequest));
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream().map(userMapper::fromEntity)
                .toList();
    }

    public UserResponse update(final String id, final UpdateUserRequest updateUserRequest) {
        User entity = find(id);
        verifyIfEmailExists(updateUserRequest.email(), id);
        final var newEntity = userRepository.save(userMapper.update(updateUserRequest, entity));
        return userMapper.fromEntity(newEntity);
    }

    private User find(final String id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Objeto não encontrado. Id: " + id + ", Tipo: " + UserResponse.class.getSimpleName()));
    }

    private void verifyIfEmailExists(final String email, final String id) {
        userRepository.findByEmail(email)
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new DataIntegrityViolationException("Email [ " + email + " ] já cadastrado.");
                });
    }
}
