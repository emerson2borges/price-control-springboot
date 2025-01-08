package com.emerson.pricecontrol.controller;

import com.emerson.pricecontrol.dto.BrandDTO;
import com.emerson.pricecontrol.dto.ResponseDTO;
import com.emerson.pricecontrol.dto.SupermarketDTO;
import com.emerson.pricecontrol.entity.Brand;
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

import java.util.Optional;

@Controller
@RequestMapping("/supermarket")
@RestController
public class SupermarketController {

    private final View error;
    private SupermarketRepository supermarketRepository;
    private ModelMapper mapper;

    @Autowired
    private SupermarketService supermarketService;

    public SupermarketController(@Autowired SupermarketRepository supermarketRepository, View error) {
        this.supermarketRepository = supermarketRepository;
        this.mapper = new ModelMapper();
        this.error = error;
    }

    @GetMapping()
    public ResponseEntity getAll() {
        return new ResponseEntity<>(supermarketRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody Supermarket supermarket) {
        try {
            // Verifica se o nome do supermercado já existe
            if (supermarketService.existsByName(supermarket.getName())) {
                return new ResponseEntity<>("Supermercado com este nome já existe.", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(supermarketRepository.save(mapper.map(supermarket, Supermarket.class)), HttpStatus.CREATED);
        } catch (Exception ex) {
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity put(@PathVariable Long id, @Valid @RequestBody SupermarketDTO supermarketDTO) {
////        System.out.println(supermarketDTO.getId());
//        try {
//            Optional<Supermarket> optionalSupermarket = supermarketRepository.findById(id);
//            if (optionalSupermarket.isPresent()) {
//                Supermarket existingSupermarket = optionalSupermarket.get();
//                mapper.map(supermarketDTO, existingSupermarket);
//                supermarketRepository.save(existingSupermarket);
//                return new ResponseEntity<>(existingSupermarket, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(new ResponseDTO("Supermercado não encontrado"), HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception error) {
//            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    @PutMapping()
    public ResponseEntity put(@Valid @RequestBody SupermarketDTO supermarketDTO){
        try {
            // Verifica se o ID está presente no corpo da requisição
            if (supermarketDTO.getId() == null) {
                return new ResponseEntity<>(new ResponseDTO("ID do supermercado não fornecido"), HttpStatus.BAD_REQUEST);
            }

            // Verifica se o supermercado existe
            Optional<Supermarket> optionalSupermarket = supermarketRepository.findById(supermarketDTO.getId());
            if (optionalSupermarket.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO("Supermercado não encontrado"), HttpStatus.NOT_FOUND);
            }

            // Verifica se o nome já exise em outro supermercado
            if (supermarketService.existsByName(supermarketDTO.getName())
                && !optionalSupermarket.get().getName().equals(supermarketDTO.getName())
            ) {
                return new ResponseEntity<>(new ResponseDTO("Supermercado já existe"), HttpStatus.BAD_REQUEST);
            }

            // Mapeia as alterações do DTO para a entidade existente
            Supermarket existingSupermarket = optionalSupermarket.get();
            mapper.map(supermarketDTO, existingSupermarket);

            // Salva as alterações no repositório
            Supermarket updatedSupermarket = supermarketRepository.save(existingSupermarket);
            return new ResponseEntity<>(new ResponseDTO("Supermercado atualizado com sucesso!"), HttpStatus.OK);
        } catch (Exception error){
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        try {
            Optional<Supermarket> optionalSupermarket = supermarketRepository.findById(id);
            if (optionalSupermarket.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO("Supermercado não encontrado"), HttpStatus.NOT_FOUND);
            }

            supermarketRepository.deleteById(id);
            return new ResponseEntity<>(new ResponseDTO("Supermercado removido com sucesso!"), HttpStatus.OK);
        } catch (Exception error){
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}