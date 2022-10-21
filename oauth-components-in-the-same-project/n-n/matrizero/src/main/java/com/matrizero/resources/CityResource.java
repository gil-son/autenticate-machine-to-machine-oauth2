package com.matrizero.resources;

import com.matrizero.dto.CityDTO;
import com.matrizero.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/cities")
public class CityResource {

    @Autowired
    CityService service;


    // LIST
    @GetMapping
    public ResponseEntity<List<CityDTO>> findAllList(){
        List<CityDTO> getList = service.findAllList();
        return ResponseEntity.ok().body(getList);
    }


    // PAGED
    @GetMapping("/paged")
    public ResponseEntity<Page<CityDTO>> findAllPaged(Pageable pageable){
        Page<CityDTO> getPage = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(getPage);
    }


    @PostMapping
    public ResponseEntity<CityDTO> insert(@RequestBody @Valid CityDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }




}
