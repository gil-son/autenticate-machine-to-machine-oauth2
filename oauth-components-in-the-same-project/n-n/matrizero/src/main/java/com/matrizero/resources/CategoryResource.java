package com.matrizero.resources;

import java.net.URI;


import com.matrizero.dto.CategoryDTO;
import com.matrizero.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    CategoryService service;

	/*
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll(){
		List<CategoryDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	*/

	/*
	@GetMapping
	public ResponseEntity<Page<CategoryDTO>> findAll(

			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linePerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction

			){

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		Page<CategoryDTO> getPage = service.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(getPage);
	}
	*/

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> findAll(Pageable pageable){

        Page<CategoryDTO> getPage = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(getPage);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
        CategoryDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }


    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }



    @DeleteMapping( value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
