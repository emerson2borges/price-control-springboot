package com.emerson.pricecontrol.controller;

import com.emerson.pricecontrol.dto.ProductDTO;
import com.emerson.pricecontrol.dto.ResponseDTO;
import com.emerson.pricecontrol.entity.Product;
import com.emerson.pricecontrol.repository.ProductRepository;
import com.emerson.pricecontrol.service.ProductService;
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
@RequestMapping("/product")
@RestController
public class ProductController {

    private final View error;
    private ProductRepository productRepository;
    private ModelMapper mapper;
    @Autowired
    private ProductService productService;

    public ProductController(@Autowired ProductRepository productRepository, View error) {
        this.productRepository = productRepository;
        this.mapper = new ModelMapper();
        this.error = error;
    }

    @GetMapping()
    public ResponseEntity getAll() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable Long id) {
        Optional<Product> productById = productRepository.findById(id);
        if (productById.isPresent()) {
            return new ResponseEntity<>(productById, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO("Produto não encontrado"), HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping()
//    public ResponseEntity post(@RequestBody Product product) {
//        try {
//            return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
//        } catch (Exception ex) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping()
    public ResponseEntity post(@Valid @RequestBody ProductDTO productDTO) {
        try {
            if (productRepository.existsByName(productDTO.getName())) {
//                return new ResponseEntity<>("Produto com este nome já existe.", HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(new ResponseDTO("Produto com este nome já existe"), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(productRepository.save(mapper.map(productDTO, Product.class)), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity put(@Valid @RequestBody ProductDTO productDTO) {
        try {
            // Verifica se o ID está presente no corpo da requisicao
            if (productDTO.getId() == null) {
                return new ResponseEntity<>(new ResponseDTO("ID do produto não fornecido"), HttpStatus.BAD_REQUEST);
            }

            // Verifica se o produto existe
            Optional<Product> optionalProduct = productRepository.findById(productDTO.getId());
            if (optionalProduct.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO("Produto não encontrado"), HttpStatus.NOT_FOUND);
            }

            // Verifica se o nome do produto já existe
            if (productRepository.existsByName(productDTO.getName())
                && !optionalProduct.get().getName().equals(productDTO.getName())
            ) {
                return new ResponseEntity<>(new ResponseDTO("Marca com esse nome já existe"), HttpStatus.BAD_REQUEST);
            }

            // Mapeia as alterações do DTO para a entidade exisente
            Product existingProduct = optionalProduct.get();
            mapper.map(productDTO, existingProduct);

            // Salva as alterações no repositório e retorna ao cliente
            Product updatedProduct = productRepository.save(existingProduct);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            if (optionalProduct.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO("Produto não encontrado"), HttpStatus.NOT_FOUND);
            }

            productRepository.deleteById(id);
            return new ResponseEntity<>(new ResponseDTO("Produto removido com sucesso!"), HttpStatus.OK);
        } catch (Exception error){
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
