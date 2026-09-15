package com.dev.restaurant.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dev.restaurant.dtos.mappers.OrderMapper;
import com.dev.restaurant.dtos.mappers.ProductOrderMapper;
import com.dev.restaurant.dtos.requests.creates.OrderRequest;
import com.dev.restaurant.dtos.requests.creates.ProductOrderRequest;
import com.dev.restaurant.dtos.requests.filters.ProductOrderFilter;
import com.dev.restaurant.dtos.requests.updates.OrderUpdate;
import com.dev.restaurant.dtos.requests.updates.ProductOrderStatusRequest;
import com.dev.restaurant.dtos.requests.updates.ProductOrderUpdate;
import com.dev.restaurant.dtos.responses.OrderResponse;
import com.dev.restaurant.dtos.responses.PageProductOrderResponse;
import com.dev.restaurant.dtos.responses.ProductOrderResponse;
import com.dev.restaurant.entities.Order;
import com.dev.restaurant.entities.Product;
import com.dev.restaurant.entities.ProductOrder;
import com.dev.restaurant.entities.RestaurantTable;
import com.dev.restaurant.enums.StatusOrder;
import com.dev.restaurant.enums.StatusProductOrder;
import com.dev.restaurant.enums.StatusTable;
import com.dev.restaurant.repositories.OrderRepository;
import com.dev.restaurant.repositories.ProductOrderRepository;
import com.dev.restaurant.specifications.ProductOrderSpecification;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final TableService tableService;
    private final ProductService productService;
    private final ProductOrderRepository productOrderRepository; 

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

     protected ProductOrder findProductOrderEntityById(Long id) {
        return this.productOrderRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                String.format("Nenhum product order encontrado para o id %s", id)
            ));
    }

    protected BigDecimal getSubtotal(Long orderId) {
        return this.orderRepository.getSubtotal(orderId);
    }

    @Transactional
    public ProductOrderResponse addProductOrder(Long orderId, ProductOrderRequest request) {
        Order order = this.findEntityById(orderId);
        Product product = this.productService.findEntityById(request.productId());

        if(this.orderRepository.productOrderAlreadyExists(order.getId(), product.getId())){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Produto já existe no pedido, se quiser alterar a quantidade, utilize o patch"
            );
        }
        
        if(!order.getStatus().canBeAdd()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Não é possível adicionar um item para um pedido cujo status não é: PENDING  ou DOING"
            );
        }

        if(product.getStock() < request.quantity()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format( 
                "Estoque insuficiente para adicionar o produto,solicitado: %s, disponível: %s ",
                 request.quantity(), product.getStock())
            );
        }

        ProductOrder productOrder = this.productOrderRepository.save(ProductOrderMapper.toEntity(
            request, product, order));

         
        order.addProductOrder(productOrder);

        return ProductOrderMapper.toResponse(productOrder);
    }

    public List<ProductOrderResponse> findAllProductsOrderByOrderId(Long orderId) {

        Order order = this.findEntityById(orderId);

        return this.orderRepository.findProductsOrderByOrderId(order.getId())
            .stream()
            .map(ProductOrderMapper::toResponse)
            .toList();
    }

    public ProductOrderResponse findProductOrderById(
        Long orderId,
        Long productOrderId
    ) {

        this.findEntityById(orderId);
        ProductOrder productOrder = this.findProductOrderEntityById(productOrderId);

        return ProductOrderMapper.toResponse(
            productOrder
        );
   }

   public ProductOrderResponse updateProductOrderById(
    Long orderId,
    Long productOrderId,
    ProductOrderUpdate request
    ) {

        this.findEntityById(orderId);
        ProductOrder productOrder = this.findProductOrderEntityById(productOrderId);

        return ProductOrderMapper.toResponse(
            this.productOrderRepository.save(request.merge(productOrder))
        );
   }

   public void deleteProductOrderById(Long orderId, Long productOrderId) {
        this.findEntityById(orderId);
        this.findProductOrderEntityById(productOrderId);

        this.productOrderRepository.deleteById(productOrderId);
   }

   @Transactional
   public ProductOrderResponse changeStatusById(Long orderId, Long productOrderId, ProductOrderStatusRequest statusRequest) {
    Order order = this.findEntityById(orderId);    
    ProductOrder productOrder = this.findProductOrderEntityById(productOrderId);

    if(!this.productOrderRepository.checkIfOrderAndProductOrderExist(productOrderId, orderId)) {
        throw new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            String.format("ProductOrder de id %d não existe no pedido de id %d", productOrderId, orderId)
        );
    }

        if(!productOrder.getStatus().canTransiction(statusRequest.status())){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format(
                    "Não é possível alterar o status de [%s] para [%s].",
                    productOrder.getStatus(), statusRequest.status())
                );
        }

        productOrder.setStatus(statusRequest.status());

        if(productOrder.getStatus().isDoing()) {
            order.setStatus(StatusOrder.DOING);
            productOrder.setPreparationStartAt(LocalDateTime.now());
        }

        if(productOrder.getStatus().isDone()) {
            productOrder.setPreparationEndAt(LocalDateTime.now());
        }

        return ProductOrderMapper.toResponse(productOrder);
   }

    public PageProductOrderResponse findAllProductOrder(ProductOrderFilter requestFilters) {
            ProductOrderFilter filter = requestFilters.setDefault();

            Sort sort = Sort.by(
                Sort.Direction.fromString(filter.direction()),
                filter.orderBy()
            );

            Pageable pageable = PageRequest.of(filter.page(), filter.size(), sort);

            Page<ProductOrder> productsOrdersPage = this.productOrderRepository.findAll(
                ProductOrderSpecification.withFilters(requestFilters),
                pageable
            );

            List<ProductOrderResponse> productsOrdersResponse = productsOrdersPage
                .getContent()
                .stream()
                .map(ProductOrderMapper::toResponse)
                .toList();
            
            return PageProductOrderResponse.builder()
                .productsOrder(productsOrdersResponse)
                .page(productsOrdersPage.getNumber())
                .totalElements(((int)productsOrdersPage.getTotalElements()))
                .totalPages(productsOrdersPage.getTotalPages())
                .build();
            
        }

        public List<ProductOrder> findAllDoingProductsOrders() {
            return this.productOrderRepository.findByStatus(StatusProductOrder.DOING)
            .stream()
            .toList();
        }
}   
