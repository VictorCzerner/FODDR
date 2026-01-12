package com.czerner.foddr.dominio.serviços.ServiçosUser;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.czerner.foddr.dominio.dados.AccountTokenRepository;
import com.czerner.foddr.dominio.entidades.entidadesUser.AccountToken;
import com.czerner.foddr.dominio.entidades.entidadesUser.User;

@Service
public class AccountTokenService {

    private final AccountTokenRepository tokenRepo;

    public AccountTokenService(AccountTokenRepository tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    public AccountToken createActivationToken(User user) {

        AccountToken token = new AccountToken();
        token.setId(UUID.randomUUID());
        token.setUser(user);
        token.setType("ACTIVATION");
        token.setExpiresAt(
            Instant.now().plus(24, ChronoUnit.HOURS)
        );
        token.setUsed(false);

        return tokenRepo.save(token);
    }
}
