package br.com.renanalencar.orderserviceapi.services.impl;

import br.com.renanalencar.orderserviceapi.entities.Order;
import br.com.renanalencar.orderserviceapi.mapper.OrderMapper;
import br.com.renanalencar.orderserviceapi.repositories.OrderRepository;
import br.com.renanalencar.orderserviceapi.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateOderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.LocalDateTime.now;
import static models.enums.OrderStatusEnum.CLOSED;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    @Override
    public Order findById(final Long id) {
        return repository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ordem de serviço não encontrada com o id: " + id + ", Tipo: " + Order.class.getSimpleName()));
    }
    @Override
    public void save(CreateOderRequest request) {
        final var entity = repository.save(mapper.fromRequest(request));
        log.info("Order saved: {}", entity);
    }

    public OrderResponse update(final Long id, UpdateOrderRequest request) {
        Order entity = findById(id);
        entity = mapper.fromRequest(entity, request);

        if(entity.getStatus().equals(CLOSED)){
            entity.setClosedAt(now());
        }
        return mapper.fromEntity(repository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(findById(id));
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Order> findAllPaginated(Integer page, Integer linesPerPage, String direction, String orderBy) {
        PageRequest pageRequest = PageRequest.of(
                page,
                linesPerPage,
                Sort.Direction.valueOf(direction),
                orderBy
        );
        return repository.findAll(pageRequest);
    }
}
