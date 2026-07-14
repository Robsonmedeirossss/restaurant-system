package com.dev.restaurant.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dev.restaurant.dtos.mappers.TableMapper;
import com.dev.restaurant.dtos.requests.TableRequest;
import com.dev.restaurant.dtos.requests.TableUpdate;
import com.dev.restaurant.dtos.responses.TableResponse;
import com.dev.restaurant.entities.RestaurantTable;
import com.dev.restaurant.exceptions.BusinessRuleException;
import com.dev.restaurant.repositories.RestaurantTableRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TableService {

  private final RestaurantTableRepository tableRepository;

  public TableResponse create(TableRequest tableRequest) {

    if(this.tableRepository.existsByTableNumber(tableRequest.number())) {
      throw new BusinessRuleException(
        String.format("Mesa já cadastrada com o número %s", tableRequest.number())
      );
    }

    return TableMapper.toResponse(
      this.tableRepository.save(
        TableMapper.toEntity(tableRequest)
      )
    );
  }

  public TableResponse findById(Long id) {
    return TableMapper.toResponse(
      this.findEntityById(id)
    );
  }

  public Set<TableResponse> findAll() {
    return this.tableRepository.findAll()
      .stream()
      .map(TableMapper::toResponse)
      .collect(Collectors.toSet());
  }

  public TableResponse updateById(Long id, TableUpdate tableUpdate) {
    RestaurantTable table = this.findEntityById(id);

    return TableMapper.toResponse(
      this.tableRepository.save(tableUpdate.merge(table))
    );
  }

  public void deleteById(Long id) {
    this.tableRepository.deleteById(id);
  }

  private RestaurantTable findEntityById(Long id) {
    return this.tableRepository.findById(id)
      .orElseThrow(() -> new BusinessRuleException(String.format(
        "Nenhuma mesa encontrada com o id %s", id)));
  }

}
