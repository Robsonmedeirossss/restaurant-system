package com.dev.restaurant.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dev.restaurant.dtos.mappers.ProductOrderMapper;
import com.dev.restaurant.dtos.requests.creates.ProductOrderRequest;
import com.dev.restaurant.dtos.requests.updates.ProductOrderUpdate;
import com.dev.restaurant.dtos.responses.ProductOrderResponse;
import com.dev.restaurant.entities.Order;
import com.dev.restaurant.entities.Product;
import com.dev.restaurant.entities.ProductOrder;
import com.dev.restaurant.repositories.ProductOrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductOrderService {
    private final ProductOrderRepository productOrderRepository;
    private final OrderService orderService;
    private final ProductService productService;


    public List<ProductOrderResponse> findAll() {
        return this.productOrderRepository.findAll()
                .stream()
                .map(ProductOrderMapper::toResponse)
                .toList();
    }

    public ProductOrderResponse findByid(Long id) {
        return ProductOrderMapper.toResponse(
            this.findEntityById(id)
        );
    }

    public ProductOrderResponse create(ProductOrderRequest request) {

        Product product = this.productService.findEntityById(request.productId());
        Order order = this.orderService.findEntityById(request.orderId());

        if(product.getStock() < request.quantity()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("""
                    Quantidade de %s insuficiente no estoque, solicitado: %s, dispon[ivel: %s""",
                    product.getName(), request.quantity(), product.getStock()));
        }

        if(!order.getStatus().canBeAdd()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "item s[o pode ser adiconado para pedidos com status: PENDING ou DOING");
        }

        return ProductOrderMapper.toResponse(
            this.productOrderRepository.save(ProductOrderMapper.toEntity(request, product, order))
        );
    }

    public ProductOrderResponse updateById(Long id, ProductOrderUpdate request) {
        ProductOrder productOrder = this.findEntityById(id);
        Product product = productOrder.getProduct();
        Order order = productOrder.getOrder();  

        if(request.productId() != null) {
            product = this.productService.findEntityById(request.productId());
        }

        if(request.orderId() != null) {
            order = this.orderService.findEntityById(request.orderId());
        }

        return ProductOrderMapper.toResponse(
            this.productOrderRepository.save(request.merge(productOrder, product, order))
        );
    }

    public void deleteById(Long id) {
        this.findEntityById(id);
        this.productOrderRepository.deleteById(id);
    }

    private ProductOrder findEntityById(Long id) {
        return this.productOrderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Nenhum item de pedido encontrado para o id %s", id)
                ));
    }

}
