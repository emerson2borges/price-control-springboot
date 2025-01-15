package com.emerson.pricecontrol.controller;

import com.emerson.pricecontrol.dto.BrandDTO;
import com.emerson.pricecontrol.dto.ResponseDTO;
import com.emerson.pricecontrol.entity.Brand;
import com.emerson.pricecontrol.entity.Product;
import com.emerson.pricecontrol.entity.Supermarket;
import com.emerson.pricecontrol.repository.BrandRepository;
import com.emerson.pricecontrol.service.BrandService;
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
@RequestMapping("/brand")
@RestController
public class BrandController {

    private final View error;
    private BrandRepository brandRepository;
    private ModelMapper mapper;
    @Autowired
    private BrandService brandService;

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
            // Verifica se a marca ja existe
            if (brandService.existsByName(brand.getName())) {
                return new ResponseEntity<>("Marca com este nome já existe.", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(brandRepository.save(mapper.map(brand, Brand.class)), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity put(@Valid @RequestBody BrandDTO brandDTO) {
        try {
            // Verifica se o ID está presente no corpo da requisicao
            if (brandDTO.getId() == null) {
                return new ResponseEntity<>(new ResponseDTO("ID da marca não fornecida"), HttpStatus.BAD_REQUEST);
            }

            // Verifica se a marca existe
            Optional<Brand> optionalBrand = brandRepository.findById(brandDTO.getId());
            if (optionalBrand.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO("Marca não encontrada"), HttpStatus.NOT_FOUND);
            }

            // Verifica se o nome da marca já existe
            if (brandService.existsByName(brandDTO.getName())
                && !optionalBrand.get().getName().equals(brandDTO.getName())
            ) {
                return new ResponseEntity<>(new ResponseDTO("Marca com esse nome já existe"), HttpStatus.BAD_REQUEST);
            }

            // Mapeia as alterações do DTO para a entidade existente
            Brand existingBrand = optionalBrand.get();
            mapper.map(brandDTO, existingBrand);

            // Salva as alterações no repositório
            Brand updatedBrand = brandRepository.save(existingBrand);
            return new ResponseEntity<>(updatedBrand, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        try {
            Optional<Brand> optionalBrand = brandRepository.findById(id);
            if (optionalBrand.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO("Maraca não encontrada"), HttpStatus.NOT_FOUND);
            }

            brandRepository.deleteById(id);
            return new ResponseEntity<>(new ResponseDTO("Marca removida com sucesso!"), HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

