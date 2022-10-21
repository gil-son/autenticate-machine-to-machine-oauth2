package com.matrizero.services;

import com.matrizero.dto.CityDTO;
import com.matrizero.entities.City;
import com.matrizero.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;

    // LIST
    public List<CityDTO> findAllList(){
        List<City> CityList = repository.findAll(Sort.by("name"));
        //for(City c : CityList){dto.add(new CityDTO(c));}
        return CityList.stream().map( x -> new CityDTO(x)).collect(Collectors.toList());
    }

    // PAGED
    @Transactional(readOnly = true)
    public Page<CityDTO> findAllPaged(Pageable pageable){ // PageRequest pageRequest
        Page<City> page = repository.findAll(pageable);
        return page.map(x -> new CityDTO(x));
    }


    @Transactional
    public CityDTO insert(CityDTO dto) {
        City entity = new City();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new CityDTO(entity);
    }
    
}
