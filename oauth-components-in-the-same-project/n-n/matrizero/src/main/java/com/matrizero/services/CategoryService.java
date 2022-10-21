package com.matrizero.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.matrizero.dto.CategoryDTO;
import com.matrizero.entities.Category;
import com.matrizero.repositories.CategoryRepository;
import com.matrizero.services.exception.DatabaseException;
import com.matrizero.services.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {

        List<Category> list = repository.findAll();

        // Lambda
        List<CategoryDTO> listDTO = list.stream().map( x -> new CategoryDTO(x)).collect(Collectors.toList());
		 /*
		    List<CategoryDTO> listDTO = new ArrayList<>();
		    for( Category category : list ) {
			 // CategoryDTO cDTO = new CategoryDTO(category);
			 // listDTO.add(cDTO);
			 listDTO.add(new CategoryDTO(category));
		 }
		 */
        return listDTO;
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(Pageable pageable){ // PageRequest pageRequest
        // Mode 1 - get Category converted to Page
        Page<Category> page = repository.findAll(pageable);
        //Mode 2 - Convert list to Page - But don't use because the set is only on moment you create
        // Then if modify the params on endpoint, don't will modify
        //List<Category> list = repository.findAll();
        // Page<Category> page = new PageImpl<Category>(list, pageRequest, list.size());
        // Convert Category to Category DTO
        return page.map(x -> new CategoryDTO(x));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> obj =  repository.findById(id);

        Category entity = obj.orElseThrow( () -> new ResourceNotFoundException("Entity not found"));
        // CategoryDTO idDTO = new CategoryDTO(entity);
        return new CategoryDTO(entity);
    }


    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        // CategoryDTO objDTO = new CategoryDTO(entity);
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try {
            Category entity = repository.getOne(id);
            entity.setName(dto.getName());
            entity = repository.save(entity);
            return new CategoryDTO(entity);
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

}
