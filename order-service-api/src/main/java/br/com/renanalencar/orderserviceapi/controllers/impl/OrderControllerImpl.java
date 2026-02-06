package br.com.renanalencar.orderserviceapi.controllers.impl;

import br.com.renanalencar.orderserviceapi.controllers.OrderController;
import br.com.renanalencar.orderserviceapi.services.OrderService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    @Override
    public ResponseEntity<Void> save(CreateOderRequest request) {
        orderService.save(request);
        return ResponseEntity.status(CREATED).build();
    }

    @Override
    public ResponseEntity<OrderResponse> update(final String id, UpdateOrderRequest request) {
        return ResponseEntity.ok().body(orderService.update(id, request));
    }
}
