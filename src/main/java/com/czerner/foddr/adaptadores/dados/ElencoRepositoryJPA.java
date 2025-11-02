package com.czerner.foddr.adaptadores.dados;

import org.springframework.stereotype.Repository;

import com.czerner.foddr.adaptadores.dados.jpaRepository.ElencoRepositorySpringData;
import com.czerner.foddr.dominio.dados.elencoRepository;
import com.czerner.foddr.dominio.entidades.elenco;

@Repository
public class ElencoRepositoryJPA implements elencoRepository {

    private final ElencoRepositorySpringData jpaRepo;

    public ElencoRepositoryJPA(ElencoRepositorySpringData jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @SuppressWarnings("null")
    @Override
    public elenco AdicionaElenco(elenco elenco) {
        return jpaRepo.save(elenco);
    }

    @Override
    public elenco buscaPorOvr(int ovr) {
        return jpaRepo.findById(ovr).orElse(null);
    }
}