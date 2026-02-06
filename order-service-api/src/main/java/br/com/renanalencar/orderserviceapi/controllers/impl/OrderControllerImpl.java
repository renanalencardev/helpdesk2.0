package br.com.renanalencar.orderserviceapi.controllers.impl;

import br.com.renanalencar.orderserviceapi.controllers.OrderController;
import br.com.renanalencar.orderserviceapi.mapper.OrderMapper;
import br.com.renanalencar.orderserviceapi.services.OrderService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;
    private final OrderMapper mapper;

    @Override
    public ResponseEntity<OrderResponse> findById(Long id) {
        return ResponseEntity.ok().body(
                mapper.fromEntity(orderService.findById(id)));
    }

    @Override
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok().body(
                mapper.fromEntities(orderService.findAll())
        );
    }

    @Override
    public ResponseEntity<Void> save(CreateOderRequest request) {
        orderService.save(request);
        return ResponseEntity.status(CREATED).build();
    }

    @Override
    public ResponseEntity<OrderResponse> update(final Long id, UpdateOrderRequest request) {
        return ResponseEntity.ok().body(orderService.update(id, request));
    }

    @Override
    public ResponseEntity<Void> deleteById(Long id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
