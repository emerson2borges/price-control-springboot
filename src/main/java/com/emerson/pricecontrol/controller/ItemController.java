package com.emerson.pricecontrol.controller;

import com.emerson.pricecontrol.dto.ItemDTO;
import com.emerson.pricecontrol.dto.ResponseDTO;
import com.emerson.pricecontrol.entity.Item;
import com.emerson.pricecontrol.service.ItemService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/item")
public class ItemController {

    private ModelMapper mapper;

    @Autowired
    private ItemService itemService;

    public ItemController(@Autowired ItemService itemService) {
        this.itemService = itemService;
        this.mapper = new ModelMapper();
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.findAll();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return itemService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity createItem(@Valid @RequestBody ItemDTO itemDTO) {
        try {
            Item createdItem = itemService.createItem(itemDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity updateItem(@Valid @RequestBody ItemDTO itemDTO) {

        try {
            // Verifica se o ID está presente no corpo da requisição
            if (itemDTO.getId() == null) {
                return new ResponseEntity<>(new ResponseDTO("ID do item não fornecido"), HttpStatus.BAD_REQUEST);
            }

            // Verifica se o item existe
            Optional<Item> optionalItem = itemService.findById(itemDTO.getId());
            if (optionalItem.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO("Item não encontrado"), HttpStatus.NOT_FOUND);
            }

            // Mapeia as alerações do DTO para a entidade de item existente
            Item existingItem = optionalItem.get();
            mapper.map(itemDTO, existingItem);

            // Salva as alterações no repositório e retorna ao cliente
            Item updatedItem = itemService.updateItem(itemDTO);

//            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
            return ResponseEntity.ok(updatedItem);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteItem(@PathVariable Long id) {
        try {
            Optional<Item> item = itemService.findById(id);
            if (item.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO("Item não encontrado"), HttpStatus.NOT_FOUND);
            }
            itemService.deleteItem(id);
            return new ResponseEntity<>(new ResponseDTO("Item excluído com sucesso"), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseDTO(ex.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
