package br.com.renanalencar.orderserviceapi.services;

import br.com.renanalencar.orderserviceapi.entities.Order;
import models.requests.CreateOderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;

import java.util.List;

public interface OrderService {

    Order findById(final Long id);

    void save(CreateOderRequest request);

    OrderResponse update(Long id, UpdateOrderRequest request);

    void deleteById(final Long id);

    List<Order> findAll();
}
