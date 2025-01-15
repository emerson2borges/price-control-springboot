package com.emerson.pricecontrol.controller;

import com.emerson.pricecontrol.dto.ResponseDTO;
import com.emerson.pricecontrol.dto.UnitMeasureDTO;
import com.emerson.pricecontrol.entity.UnitMeasure;
import com.emerson.pricecontrol.repository.UnitMeasureRepository;

import com.emerson.pricecontrol.service.UnitMeasureService;
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
@RequestMapping("/unit-measure")
@RestController
public class UnitMeasureController {

    private final View error;
    private UnitMeasureRepository unitMeasureRepository;
    private ModelMapper mapper;
    @Autowired
    private UnitMeasureService unitMeasureService;

    public UnitMeasureController(@Autowired UnitMeasureRepository unitMeasureRepository, View error) {
        this.unitMeasureRepository = unitMeasureRepository;
        this.mapper = new ModelMapper();
        this.error = error;
    }

    @GetMapping()
    public ResponseEntity getAll() {
        return new ResponseEntity<>(unitMeasureRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUnityById(@PathVariable Long id) {
        if (unitMeasureRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(unitMeasureRepository.findById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO("Unidade não encontrada"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody UnitMeasureDTO unitMeasureDTO) {
        try {
            // Verifica se o nome da unidade de medida já existe
            if (unitMeasureService.existsByName(unitMeasureDTO.getName())) {
                return new ResponseEntity<>(new ResponseDTO("Unidade com este nome já existe"), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(unitMeasureRepository.save(mapper.map(unitMeasureDTO, UnitMeasure.class)), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity put(@Valid @RequestBody UnitMeasureDTO unitMeasureDTO) {
        try {
            // Verifica se o ID está presente no corpo da requisicao
            if (unitMeasureDTO.getId() == null) {
                return new ResponseEntity<>(new ResponseDTO("ID da unidade não fornecido"), HttpStatus.BAD_REQUEST);
            }

            // Verifica se existe
            Optional<UnitMeasure> optionalUnitMeasure = unitMeasureRepository.findById(unitMeasureDTO.getId());
            if (optionalUnitMeasure.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO("Unidade não encontrada"), HttpStatus.NOT_FOUND);
            }

            // Verifica se o nome já existe em outra unidade de medida
            if (unitMeasureRepository.existsByName(unitMeasureDTO.getName())
                && !optionalUnitMeasure.get().getName().equals(unitMeasureDTO.getName())
            ) {
                return new ResponseEntity<>(new ResponseDTO("Unidade de medida com este nome já existe"), HttpStatus.BAD_REQUEST);
            }

            // Mapeia as alterações do DTO para a entidade existente
            UnitMeasure existingUnitMeasure = optionalUnitMeasure.get();
            mapper.map(unitMeasureDTO, existingUnitMeasure);

            // Salva as alterações no repositório
            UnitMeasure updatedUnitMeasure = unitMeasureRepository.save(existingUnitMeasure);
            return new ResponseEntity<>(updatedUnitMeasure, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        try {
            Optional<UnitMeasure> optionalUnitMeasure = unitMeasureRepository.findById(id);
            if (optionalUnitMeasure.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO("Unidade não encontrada"), HttpStatus.NOT_FOUND);
            }

            unitMeasureRepository.deleteById(id);
            return new ResponseEntity<>(new ResponseDTO("Unidade removida com sucesso!"), HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
