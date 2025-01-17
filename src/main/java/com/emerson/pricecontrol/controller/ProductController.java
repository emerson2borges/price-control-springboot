package com.emerson.pricecontrol.controller;

import com.emerson.pricecontrol.dto.ProductDTO;
import com.emerson.pricecontrol.dto.ResponseDTO;
import com.emerson.pricecontrol.entity.Product;
import com.emerson.pricecontrol.service.ProductService;
import com.emerson.pricecontrol.service.SupermarketService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
@RestController
public class ProductController {

    private final SupermarketService supermarketService;
    private ModelMapper mapper;

    private ProductService productService;

    @Autowired
    public ProductController(@Autowired ProductService productService, SupermarketService supermarketService) {
        this.productService = productService;
        this.mapper = new ModelMapper();
        this.supermarketService = supermarketService;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        return new ResponseEntity(productService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity post(@Valid @RequestBody ProductDTO productDTO) {
        try {
            Product product = mapper.map(productDTO, Product.class);
            Product createdProduct = productService.createProduct(product);
            System.out.println(product);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity put(@Valid @RequestBody ProductDTO productDTO) {
        try {
            // Verifica se o ID está presente no corpo da requisicao
            if (productDTO.getId() == null) {
                return new ResponseEntity<>(new ResponseDTO("ID do produto não fornecido"), HttpStatus.BAD_REQUEST);
            }

            // Mapeia as alterações do DTO para a entidade exisente
            Product productDetails = mapper.map(productDTO, Product.class);
            Product updatedProduct = productService.updateProduct(productDTO.getId(), productDetails);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(new ResponseDTO("Produto removido com sucesso!"), HttpStatus.OK);
        } catch (Exception error){
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
