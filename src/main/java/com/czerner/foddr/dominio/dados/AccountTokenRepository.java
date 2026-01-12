package com.czerner.foddr.dominio.dados;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.czerner.foddr.dominio.entidades.entidadesUser.AccountToken;

@Repository
public interface AccountTokenRepository
        extends JpaRepository<AccountToken, UUID> {
}

