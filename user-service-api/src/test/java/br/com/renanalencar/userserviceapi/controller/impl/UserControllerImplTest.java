package br.com.renanalencar.userserviceapi.controller.impl;

import br.com.renanalencar.userserviceapi.entity.User;
import br.com.renanalencar.userserviceapi.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.requests.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static br.com.renanalencar.userserviceapi.creator.CreatorUtils.generateMock;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerImplTest {
    public static final String BASE_URI = "/api/users";
    //A classe MockMvc permite testar endpoints de controllers Spring MVC sem iniciar um servidor HTTP real. Ela simula requisições HTTP e verifica respostas, facilitando testes automatizados de APIs REST.
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByIdWithSucess() throws Exception {
        final var entity = generateMock(User.class);
        final var userId = userRepository.save(entity).getId();

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value(entity.getName()))
                .andExpect(jsonPath("$.email").value(entity.getEmail()))
                .andExpect(jsonPath("$.password").value(entity.getPassword()))
                .andExpect(jsonPath("$.profiles").isArray());

        userRepository.deleteById(userId);
    }

    @Test
    void testFindByIdWithNotFoundException() throws Exception {
        mockMvc.perform(get("/api/users/{id}", "123"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Objeto não encontrado. Id: 123, Tipo: UserResponse"))
                .andExpect(jsonPath("$.error").value(NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.path").value("/api/users/123"))
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void testFindAllWithSucess() throws Exception {
        final var entity1 = generateMock(User.class);
        final var entity2 = generateMock(User.class);

        userRepository.saveAll(List.of(entity1, entity2));

        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").isNotEmpty())
                .andExpect(jsonPath("$[1]").isNotEmpty())
                .andExpect(jsonPath("$[0].profiles").isArray())
                .andExpect(jsonPath("$[1].profiles").isArray());

        userRepository.deleteAll(List.of(entity1, entity2));
    }

    @Test
    void testSaveUserWithSuccess() throws Exception {
        final var validEmail = "asdfasdf@mail.com";
        final var request = generateMock(CreateUserRequest.class).withEmail(validEmail);

        mockMvc.perform(
                post(BASE_URI)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(toJson(request)))
                .andExpect(status().isCreated());

        userRepository.deleteByEmail(validEmail);
    }
    @Test
    void testSaveUserWithConflict() throws Exception {
        final var validEmail = "aaabbb@mail.com";
        final var entity = generateMock(User.class).withEmail(validEmail);
        userRepository.save(entity);
        final var request = generateMock(CreateUserRequest.class).withEmail(validEmail);

        mockMvc.perform(
                        post(BASE_URI)
                                .contentType(APPLICATION_JSON_VALUE)
                                .content(toJson(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email [ " + validEmail + " ] já cadastrado."))
                .andExpect(jsonPath("$.error").value(CONFLICT.getReasonPhrase()))
                .andExpect(jsonPath("$.path").value(BASE_URI))
                .andExpect(jsonPath("$.status").value(CONFLICT.value()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());

        userRepository.deleteById(entity.getId());
    }

    @Test
    void testSaveUserWithNameEmptyThenThrowBadRequest() throws Exception {
        final var request = generateMock(CreateUserRequest.class).withName("");

        mockMvc.perform(
                        post(BASE_URI)
                                .contentType(APPLICATION_JSON_VALUE)
                                .content(toJson(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Exceção em atributos de validação"))
                .andExpect(jsonPath("$.error").value("Exceção de validação"))
                .andExpect(jsonPath("$.path").value(BASE_URI))
                .andExpect(jsonPath("$.status").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[?(@.fieldName=='name' && @.message=='O campo nome deve ter entre 3 e 50 caracteres')]").exists())
                .andExpect(jsonPath("$.errors[?(@.fieldName=='name' && @.message=='O campo nome é obrigatório')]").exists());
    }

    private String toJson(final Object object) throws Exception {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (final Exception e) {
            throw new Exception("Erro ao converter objeto para JSON", e);
        }
    }
}