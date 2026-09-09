package com.dev.restaurant.services;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.dev.restaurant.aws.S3Storage;
import com.dev.restaurant.dtos.mappers.ProductMapper;
import com.dev.restaurant.dtos.requests.creates.ProductRequest;
import com.dev.restaurant.dtos.requests.updates.ProductUpdate;
import com.dev.restaurant.dtos.responses.ProductResponse;
import com.dev.restaurant.entities.CategoryProduct;
import com.dev.restaurant.entities.Product;
import com.dev.restaurant.repositories.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryProductService categoryProductService;
    private final S3Storage storage;

    public Set<ProductResponse> findAll() {
        return this.productRepository.findAll()
            .stream()
            .map(ProductMapper::toResponse)
            .collect(Collectors.toSet());
    }

    public ProductResponse findById(Long id) {
        return ProductMapper.toResponse(
            this.findEntityById(id)
        );
    }

    @Transactional
    public ProductResponse create(ProductRequest request, MultipartFile image) {

        CategoryProduct category = this.categoryProductService.findEntityById(request.categoryId());

        String imageUrl = null;

        if(!image.isEmpty()) {
            try {
                imageUrl = storage.uploadFile(image.getBytes(), image.getOriginalFilename(), image.getContentType()).join();
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
        }

        return ProductMapper.toResponse(this.productRepository.save(
                ProductMapper.toEntity(request, category, imageUrl)
            )
        );
    }

    public ProductResponse updateById(Long id, ProductUpdate request) {

        Product product = this.findEntityById(id);

        if(request.categoryId() != null) {
            CategoryProduct category = this.categoryProductService.findEntityById(request.categoryId());
            product.setCategoryProduct(category);
        }

        return ProductMapper.toResponse(
            this.productRepository.save(request.merge(product))
        );
    }

    public void deleteById(Long id) {
        this.findEntityById(id);
        this.productRepository.deleteById(id);
    }


    protected Product findEntityById (Long id) {
        return this.productRepository.findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Nenhum produto encontrado para o id %s", id)
                )
        );
    } 
}
