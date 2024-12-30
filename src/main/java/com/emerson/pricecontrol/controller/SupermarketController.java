package com.emerson.pricecontrol.controller;

import com.emerson.pricecontrol.entity.Supermarket;
import org.modelmapper.ModelMapper;

import com.emerson.pricecontrol.repository.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

@Controller
@RequestMapping("/supermarket")
@RestController
public class SupermarketController {

    private final View error;
    private SupermarketRepository supermarketRepository;
    private ModelMapper mapper;

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
            System.out.println(supermarket.toString());

            return new ResponseEntity<>(supermarketRepository.save(mapper.map(supermarket, Supermarket.class)), HttpStatus.CREATED);
        } catch (Exception ex) {
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}