package com.dev.restaurant.worker;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dev.restaurant.entities.ProductOrder;
import com.dev.restaurant.services.OrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class KitchenWorker {

    private final OrderService orderService;

    @Transactional(readOnly = true)
    @Scheduled(fixedRate = 60000)
    public void checkLateProductsOrders() {
        List<ProductOrder> productsOrders = this.orderService.findAllDoingProductsOrders();

        productsOrders.forEach(this::checkItemLate);
    }

    public void checkItemLate(ProductOrder item) {
        
        if(item.getPreparationStartAt() == null) {
                return;
        }

        Integer preparationMinutes = item.getProduct().getPreparationMinutes();
        long currentTime = Duration.between(item.getPreparationStartAt(), LocalDateTime.now()).toMinutes();

            if(currentTime > preparationMinutes) {
                System.out.println(String.format("""
                [AVISO DE ATRASO]
                - Pedido: %d
                - Mesa: %d
                - Produto: %s
                - Tempo de preparo: %d min
                - Tempo atual: %d min
                """,
                    item.getId(),
                    item.getOrder().getTable().getNumber(),
                    item.getProduct().getName(),
                    item.getProduct().getPreparationMinutes(),
                    currentTime));
            }
    }
}
