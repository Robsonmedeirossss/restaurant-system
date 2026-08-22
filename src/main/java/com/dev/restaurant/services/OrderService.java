package com.dev.restaurant.services;

import java.math.BigDecimal;
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

import jakarta.transaction.Transactional;
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
        System.out.println(this.getSubtotal(id) + "Aqui aqui aqui");
        return OrderMapper.toResponse(
            this.findEntityById(id)
        );
    }

    @Transactional
    public OrderResponse create(OrderRequest request) {
        
        RestaurantTable table = tableService.findEntityById(request.tableId());
        if(table.getStatus() != StatusTable.FREE) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("Não é possível criar um pedido para mesa com status %s", table.getStatus()));
        }

        table.setStatus(StatusTable.BUSY);

        return OrderMapper.toResponse(
            this.orderRepository.save(OrderMapper.toEntity(request, table))
        );
    }

    @Transactional
    public OrderResponse updateById(Long id, OrderUpdate request) {
        Order order = this.findEntityById(id);

        if(request.tableId() != null) {
            RestaurantTable table = this.tableService.findEntityById(request.tableId());
            order.setTable(table);
        }

        return OrderMapper.toResponse(
            this.orderRepository.save(request.merge(order))
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

    protected BigDecimal getSubtotal(Long orderId) {
        return this.orderRepository.getSubtotal(orderId);
    }
}
