package com.dev.restaurant.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dev.restaurant.dtos.mappers.OrderMapper;
import com.dev.restaurant.dtos.requests.creates.OrderRequest;
import com.dev.restaurant.dtos.requests.updates.OrderUpdate;
import com.dev.restaurant.dtos.responses.OrderResponse;
import com.dev.restaurant.entities.Order;
import com.dev.restaurant.entities.RestaurantTable;
import com.dev.restaurant.enums.StatusTable;
import com.dev.restaurant.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final TableService tableService;

    public List<OrderResponse> findAll() {
        return this.orderRepository.findAll()
            .stream()
            .map(OrderMapper::toResponse)
            .toList();
    }

    public OrderResponse findById(Long id) {
        return OrderMapper.toResponse(
            this.findEntityById(id)
        );
    }

    public OrderResponse create(OrderRequest request) {
        
        RestaurantTable table = tableService.findEntityById(request.tableId());
        table.setStatus(StatusTable.BUSY);

        return OrderMapper.toResponse(
            this.orderRepository.save(OrderMapper.toEntity(request, table))
        );
    }

    public OrderResponse updateById(Long id, OrderUpdate request) {
        Order order = this.findEntityById(id);

        if(request.tableId() != null) {
            RestaurantTable table = this.tableService.findEntityById(request.tableId());
            return OrderMapper.toResponse(
                this.orderRepository.save(request.merge(order, table))
            );
        }

        return OrderMapper.toResponse(
            this.orderRepository.save(request.merge(order, order.getTable()))
        );
    }

    public void deleteById(Long id) {
        this.findEntityById(id);
        this.orderRepository.deleteById(id);
    }
    

    protected Order findEntityById(Long id) {
        return this.orderRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                String.format("Nenhuma pedido encontrado para o id %s", id)
            ));
    }
}
