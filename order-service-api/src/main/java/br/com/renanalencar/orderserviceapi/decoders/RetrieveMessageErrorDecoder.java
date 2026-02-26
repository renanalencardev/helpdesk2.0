package br.com.renanalencar.orderserviceapi.decoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import models.exceptions.GenericFeignException;

import java.io.InputStream;
import java.util.Map;

/**
 * Decodificador de erro personalizado para lidar com erros ao recuperar mensagens do serviço de mensagens.
 * Este decodificador lê o corpo da resposta de erro, extrai o status e os detalhes do erro, e
 * lança uma GenericFeignException com as informações relevantes.
 */
public class RetrieveMessageErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();

            final var error = mapper.readValue(bodyIs, Map.class);
            final var status = (Integer) error.get("status");
            return new GenericFeignException(status, error);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
