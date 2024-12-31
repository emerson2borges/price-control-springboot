package com.emerson.pricecontrol.controller;

import com.emerson.pricecontrol.entity.Brand;
import com.emerson.pricecontrol.repository.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

@Controller
@RequestMapping("/brand")
@RestController
public class BrandController {

        private final View error;
        private BrandRepository brandRepository;
        private ModelMapper mapper;

        public BrandController(@Autowired BrandRepository brandRepository, View error) {
            this.brandRepository = brandRepository;
            this.mapper = new ModelMapper();
            this.error = error;
        }

        @GetMapping()
        public ResponseEntity getAll() {
            return new ResponseEntity<>(brandRepository.findAll(), HttpStatus.OK);
        }

        @PostMapping()
        public ResponseEntity post(@RequestBody Brand brand) {
            try {
                return new ResponseEntity<>(brandRepository.save(mapper.map(brand, Brand.class)), HttpStatus.CREATED);
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

