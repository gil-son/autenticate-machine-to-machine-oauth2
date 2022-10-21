package com.matrizero.services;

import com.matrizero.dto.UserDTO;
import com.matrizero.entities.User;
import com.matrizero.repositories.RoleRepository;
import com.matrizero.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repository.findByEmail(username);
        if (user == null) {
            logger.error("User not found: " + username);
            throw new UsernameNotFoundException("Email not found");
        }
        logger.info("User found: " + username);
        return user;
    }


    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {

        List<User> list = repository.findAll();
        // Lambda
        List<UserDTO> listDTO = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
        return listDTO;
    }


    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable){ // PageRequest pageRequest
        // Mode 1 - get User converted to Page
        Page<User> page = repository.findAll(pageable);

        return page.map(x -> new UserDTO(x));
    }

}
