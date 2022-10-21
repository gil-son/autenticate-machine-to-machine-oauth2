package com.matrizero.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.matrizero.dto.CategoryDTO;
import com.matrizero.dto.ProductDTO;
import com.matrizero.entities.Category;
import com.matrizero.entities.Product;
import com.matrizero.repositories.CategoryRepository;
import com.matrizero.repositories.ProductRepository;
import com.matrizero.services.exception.DatabaseException;
import com.matrizero.services.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        List<Product> list = repository.findAll();
        // Lambda
        List<ProductDTO> listDTO = list.stream().map( x -> new ProductDTO(x)).collect(Collectors.toList());
        return listDTO;
    }


    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(Pageable pageable){
        // Mode 1 - get Product converted to Page
        Page<Product> page = repository.findAll(pageable);
        //Mode 2 - Convert list to Page - But don't use because the set is only on moment you create
        // Then if modify the params on endpoint, don't will modify
        //List<Product> list = repository.findAll();
        // Page<Product> page = new PageImpl<Product>(list, pageRequest, list.size());
        // Convert Product to Product DTO
        return page.map(x -> new ProductDTO(x));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj =  repository.findById(id);

        Product entity = obj.orElseThrow( () -> new ResourceNotFoundException("Entity not found"));
        // ProductDTO idDTO = new ProductDTO(entity);
        return new ProductDTO(entity, entity.getCategories());
    }


    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDtoEntity(dto, entity);
        entity = repository.save(entity);
        // ProductDTO objDTO = new ProductDTO(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = repository.getOne(id);
            copyDtoEntity(dto, entity);
            entity = repository.save(entity);
            return new ProductDTO(entity);
        }catch(EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found it: "+id);
        }

    }



    public void delete(Long id) {
        try {
            repository.deleteById(id);
        }catch(EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        catch(DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity Violation");
        }

    }


    private void copyDtoEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDate(dto.getDate());
        entity.setImgUrl(dto.getImgUrl());
        entity.setPrice(dto.getPrice());

        entity.getCategories().clear();

        for(CategoryDTO catDTO : dto.getCategories()) {
            Category category = categoryRepository.getOne(catDTO.getId());
            entity.getCategories().add(category);
        }

    }

}
