package com.emerson.pricecontrol.controller;

import com.emerson.pricecontrol.dto.ResponseDTO;
import com.emerson.pricecontrol.dto.SupermarketDTO;
import com.emerson.pricecontrol.entity.Supermarket;
import com.emerson.pricecontrol.repository.SupermarketRepository;

import com.emerson.pricecontrol.service.SupermarketService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/supermarket")
@RestController
public class SupermarketController {

    private ModelMapper mapper;

    private SupermarketService supermarketService;

    @Autowired
    public SupermarketController(@Autowired SupermarketService supermarketService) {
        this.supermarketService = supermarketService;
        this.mapper = new ModelMapper();
    }

    @GetMapping()
    public ResponseEntity<List<Supermarket>> getAll() {
        List<Supermarket> supermarkets = supermarketService.findAll();
        return new ResponseEntity<>(supermarkets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupermarketDTO> getById(@PathVariable Long id) {
        return new ResponseEntity(supermarketService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody SupermarketDTO supermarketDTO) {
        try {
            Supermarket supermarket = mapper.map(supermarketDTO, Supermarket.class);
            Supermarket createSupermarket = supermarketService.createSupermarket(supermarket);
            return new ResponseEntity<>(createSupermarket, HttpStatus.CREATED);
        } catch (Exception ex) {
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping()
    public ResponseEntity put(@Valid @RequestBody SupermarketDTO supermarketDTO){
        try {
            // Verifica se o ID está presente no corpo da requisição
            if (supermarketDTO.getId() == null) {
                return new ResponseEntity<>(new ResponseDTO("ID do supermercado não fornecido"), HttpStatus.BAD_REQUEST);
            }

            // Mapeia as alterações do DTO para a entidade existente
            Supermarket supermarketDetail = mapper.map(supermarketDTO, Supermarket.class);
            Supermarket updatedSupermarket = supermarketService.updateSupermarket(supermarketDTO.getId(), supermarketDetail);
            return new ResponseEntity<>(updatedSupermarket, HttpStatus.OK);

        } catch (Exception ex){
            return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        try {
            supermarketService.deleteSupermarket(id);
            return new ResponseEntity<>(new ResponseDTO("Supermercado removido com sucesso!"), HttpStatus.OK);
        } catch (Exception error){
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}