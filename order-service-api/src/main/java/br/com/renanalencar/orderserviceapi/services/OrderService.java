package br.com.renanalencar.orderserviceapi.services;

import br.com.renanalencar.orderserviceapi.entities.Order;
import models.requests.CreateOderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;

public interface OrderService {

    Order findById(final String id);

    void save(CreateOderRequest request);

    OrderResponse update(String id, UpdateOrderRequest request);
}
