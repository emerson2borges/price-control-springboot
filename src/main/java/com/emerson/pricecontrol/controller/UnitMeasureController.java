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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/unit-measure")
@RestController
public class UnitMeasureController {

    private ModelMapper mapper;
    private UnitMeasureService unitMeasureService;

    @Autowired
    public UnitMeasureController(@Autowired UnitMeasureService unitMeasureService) {
        this.unitMeasureService = unitMeasureService;
        this.mapper = new ModelMapper();
    }

    @GetMapping()
    public ResponseEntity<List<UnitMeasureDTO>> getAll() {
        List<UnitMeasure> unitsMeasures = unitMeasureService.findAll();
        return new ResponseEntity(unitsMeasures, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUnityById(@PathVariable Long id) {
        return new ResponseEntity(unitMeasureService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody UnitMeasureDTO unitMeasureDTO) {
        try {
            UnitMeasure unitMeasure = mapper.map(unitMeasureDTO, UnitMeasure.class);
            UnitMeasure createdUnitMeasure = unitMeasureService.createUnitMeasure(unitMeasure);
            return new ResponseEntity<>(createdUnitMeasure, HttpStatus.CREATED);
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

            // Mapeia as alterações do DTO para a entidade existente
            UnitMeasure unitMeasureDetails = mapper.map(unitMeasureDTO, UnitMeasure.class);
            UnitMeasure updatedUnitMeasure = unitMeasureService.updateUnitMeasure(unitMeasureDTO.getId(), unitMeasureDetails);
            return new ResponseEntity<>(updatedUnitMeasure, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try {
            unitMeasureService.deleteUnitMeasure(id);
            return new ResponseEntity<>(new ResponseDTO("Unidade de medida removida com sucesso!"), HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
