package com.emerson.pricecontrol.controller;

import com.emerson.pricecontrol.entity.UnitMeasure;
import com.emerson.pricecontrol.repository.UnitMeasureRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

@Controller
@RequestMapping("/unit-measure")
@RestController
public class UnitMeasureController {

    private final View error;
    private UnitMeasureRepository unitMeasureRepository;
    private ModelMapper mapper;

    public UnitMeasureController(@Autowired UnitMeasureRepository unitMeasureRepository, View error) {
        this.unitMeasureRepository = unitMeasureRepository;
        this.mapper = new ModelMapper();
        this.error = error;
    }

    @GetMapping()
    public ResponseEntity getAll() {
        return new ResponseEntity<>(unitMeasureRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody UnitMeasure unitMeasure) {
        try {
            return new ResponseEntity<>(unitMeasureRepository.save(mapper.map(unitMeasure, UnitMeasure.class)), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
