package com.czerner.foddr.aplicação;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.czerner.foddr.dominio.dados.AccountTokenRepository;
import com.czerner.foddr.dominio.dados.UserRepository;
import com.czerner.foddr.dominio.entidades.entidadesUser.AccountToken;
import com.czerner.foddr.dominio.entidades.entidadesUser.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ActivateUserUC {

    private final AccountTokenRepository tokenRepo;
    private final UserRepository userRepo;

    public ActivateUserUC(AccountTokenRepository tokenRepo, UserRepository userRepo) {
        this.tokenRepo = tokenRepo;
        this.userRepo = userRepo;
    }

    public void execute(String tokenId) {

        AccountToken token = tokenRepo.findById(
                UUID.fromString(tokenId)
        ).orElseThrow(() ->
            new RuntimeException("Token inválido")
        );

        if (token.isUsed()) {
            throw new RuntimeException("Token já utilizado");
        }

        if (token.getExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("Token expirado");
        }

        User user = token.getUser();

        if (user.isActive()) {
            throw new RuntimeException("Conta já ativa");
        }

        user.setActive(true);
        token.setUsed(true);

        userRepo.save(user);
        tokenRepo.save(token);
    }
}
