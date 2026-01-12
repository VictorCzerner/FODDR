package com.czerner.foddr.aplicação;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.czerner.foddr.adaptadores.apresentação.Requests.RegisterUserRequest;
import com.czerner.foddr.dominio.dados.UserRepository;
import com.czerner.foddr.dominio.entidades.entidadesUser.AccountToken;
import com.czerner.foddr.dominio.entidades.entidadesUser.User;
import com.czerner.foddr.dominio.serviços.ServiçosUser.AccountTokenService;

@Component
public class RegisterUserUC {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final AccountTokenService tokenService;


    public RegisterUserUC(UserRepository userRepo, PasswordEncoder encoder, AccountTokenService tokenService) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.tokenService = tokenService;
    }

    public void execute(RegisterUserRequest dto) {

        if (userRepo.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        User user = new User();
        user.setEmail(dto.email());
        user.setPasswordHash(encoder.encode(dto.password()));
        user.setActive(false); // importante

        userRepo.save(user);

        AccountToken token =
            tokenService.createActivationToken(user);

        // por enquanto:
        System.out.println("TOKEN: " + token.getId());
    }
}

