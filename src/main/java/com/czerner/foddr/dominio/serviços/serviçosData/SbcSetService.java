package com.czerner.foddr.dominio.serviços.serviçosData;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.czerner.foddr.dominio.dados.SbcSetRepository;
import com.czerner.foddr.dominio.entidades.entidadesData.SbcSet;

@Service
public class SbcSetService {

    private final SbcSetRepository sbcSetRepository;

    public SbcSetService(SbcSetRepository sbcSetRepository) {
        this.sbcSetRepository = sbcSetRepository;
    }

    public Optional<SbcSet> buscarPorSetId(Integer setId) {
        if (setId == null) {
            throw new IllegalArgumentException("setId não pode ser nulo");
        }
        return sbcSetRepository.findBySetId(setId);
    }
}
