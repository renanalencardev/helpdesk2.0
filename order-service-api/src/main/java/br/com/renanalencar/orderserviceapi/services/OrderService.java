package br.com.renanalencar.orderserviceapi.services;

import models.requests.CreateOderRequest;

public interface OrderService {

    void save(CreateOderRequest request);
}
