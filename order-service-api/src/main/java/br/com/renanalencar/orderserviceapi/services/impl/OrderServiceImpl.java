package br.com.renanalencar.orderserviceapi.services.impl;

import br.com.renanalencar.orderserviceapi.mapper.OrderMapper;
import br.com.renanalencar.orderserviceapi.repositories.OrderRepository;
import br.com.renanalencar.orderserviceapi.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.requests.CreateOderRequest;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    @Override
    public void save(CreateOderRequest request) {
        final var entity = repository.save(mapper.fromRequest(request));
        log.info("Order saved: {}", entity);
    }
}
