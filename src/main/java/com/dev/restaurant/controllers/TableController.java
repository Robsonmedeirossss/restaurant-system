package com.dev.restaurant.controllers;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dev.restaurant.dtos.requests.creates.TableRequest;
import com.dev.restaurant.dtos.requests.updates.TableUpdate;
import com.dev.restaurant.dtos.responses.TableResponse;
import com.dev.restaurant.services.TableService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Mesas", description = "Endpoint para gerenciar as mesas do restaurante")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/restaurant/tables")
public class TableController {

  private final TableService tableService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Set<TableResponse> findAll() {
    return this.tableService.findAll();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public TableResponse findById(@PathVariable Long id) {
    return this.tableService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TableResponse create(@Valid @RequestBody TableRequest request ) {
    return this.tableService.create(request);
  }

  @PatchMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public TableResponse create(
    @Valid @RequestBody TableUpdate request,
    @PathVariable Long id
   ) {
    return this.tableService.updateById(id, request);
  }
  
  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Long id) {
    this.tableService.deleteById(id);
  }
}
