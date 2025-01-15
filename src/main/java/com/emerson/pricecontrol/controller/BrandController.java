package com.emerson.pricecontrol.controller;

import com.emerson.pricecontrol.exception.ResourceNotFoundException;
import com.emerson.pricecontrol.dto.BrandDTO;
import com.emerson.pricecontrol.dto.ResponseDTO;
import com.emerson.pricecontrol.entity.Brand;
import com.emerson.pricecontrol.service.BrandService;
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
@RequestMapping("/brand")
@RestController
public class BrandController {

    private ModelMapper mapper;

    @Autowired
    private BrandService brandService;

    public BrandController(@Autowired BrandService brandService) {
        this.brandService = brandService;
        this.mapper = new ModelMapper();
    }

    @GetMapping()
    public ResponseEntity<List<BrandDTO>> getAll() {
        List<Brand> brands = brandService.findAll();

        return new ResponseEntity(brands, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDTO> findById(@PathVariable Long id) {
        return new ResponseEntity(brandService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody BrandDTO brandDTO) {
        try {
            Brand brand = mapper.map(brandDTO, Brand.class);
            Brand createdBrand = brandService.createBrand(brand);
            return new ResponseEntity<>(createdBrand, HttpStatus.CREATED);
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

            Brand brandDetails = mapper.map(brandDTO, Brand.class);
            Brand updatedBrand = brandService.updateBrand(brandDTO.getId(), brandDetails);
            return new ResponseEntity<>(updatedBrand, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        try {
            brandService.deleteBrand(id);
            return new ResponseEntity<>(new ResponseDTO("Marca excluída com sucesso!"), HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

