package com.matrizero.services;

import com.matrizero.dto.EventDTO;
import com.matrizero.entities.City;
import com.matrizero.entities.Event;
import com.matrizero.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    // LIST
    public List<EventDTO> findAllList(){
        List<Event> EventList = repository.findAll();
        //for(Event c : EventList){dto.add(new EventDTO(c));}
        return EventList.stream().map( x -> new EventDTO(x)).collect(Collectors.toList());
    }

    // PAGED
    @Transactional(readOnly = true)
    public Page<EventDTO> findAllPaged(Pageable pageable){ // PageRequest pageRequest
        Page<Event> page = repository.findAll(pageable);
        return page.map(x -> new EventDTO(x));
    }


    @Transactional
    public EventDTO insert(EventDTO dto) {
        Event entity = new Event();
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setUrl(dto.getUrl());
        entity.setCity(new City(dto.getCityId(), null));
        entity = repository.save(entity);
        return new EventDTO(entity);
    }
    
}
