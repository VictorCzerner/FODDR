package com.czerner.foddr.infraestrutura;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.czerner.foddr.dominio.dados.UserRepository;
import com.czerner.foddr.dominio.entidades.entidadesUser.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(""));

        return new SecurityUser(user);
    }
}