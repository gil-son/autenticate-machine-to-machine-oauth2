package com.matrizero.resources;

import com.matrizero.dto.EventDTO;
import com.matrizero.services.EventService;
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
@RequestMapping(value = "/events")
public class EventResource {

    @Autowired
    EventService service;

    // PAGED
    @GetMapping("/paged")
    public ResponseEntity<Page<EventDTO>> findAllPaged(Pageable pageable){
        Page<EventDTO> getPage = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(getPage);
    }


    // LIST
    @GetMapping
    public ResponseEntity<List<EventDTO>> findAllList(){
        List<EventDTO> getList = service.findAllList();
        return ResponseEntity.ok().body(getList);
    }


    @PostMapping
    public ResponseEntity<EventDTO> insert(@RequestBody @Valid EventDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }


}
