package br.com.renanalencar.orderserviceapi.configs;

import br.com.renanalencar.orderserviceapi.decoders.RetrieveMessageErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    /**
     * Fornece um {@link ErrorDecoder} customizado para os clients Feign.
     *
     * <p>O decoder retornado é uma instância de {@link RetrieveMessageErrorDecoder} que:
     * <ol>
     *   <li>Interpreta a resposta HTTP de erro recebida pelo client Feign;</li>
     *   <li>Extrai do corpo da resposta uma mensagem mais informativa quando possível;</li>
     *   <li>Gera/exploda uma exceção apropriada contendo a mensagem extraída para que
     *       a camada superior possa tratar ou propagar o erro com contexto útil.</li>
     * </ol>
     *
     * Uso:
     * - O bean é registrado automaticamente no contexto do Spring e será utilizado
     *   por clients Feign configurados na aplicação.
     *
     * Contrato / critérios de sucesso:
     * - Entrada: nenhuma (bean sem parâmetros);
     * - Saída: instância de {@link ErrorDecoder} pronta para uso pelos clients Feign;
     * - Erros: não deve lançar exceções ao criar o bean; comportamentos de erro seguem
     *   a implementação de {@link RetrieveMessageErrorDecoder} em tempo de execução.
     *
     * @return uma instância de {@link RetrieveMessageErrorDecoder} utilizada como
     *         {@link ErrorDecoder} para clientes Feign.
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new RetrieveMessageErrorDecoder();
    }
}
