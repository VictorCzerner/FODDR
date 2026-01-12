package com.czerner.foddr.aplicação;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.czerner.foddr.aplicação.Responses.SbcSetResponse;
import com.czerner.foddr.dominio.serviços.serviçosData.SbcSetService;



@Component
public class BuscarSbcSetUC {

    private final SbcSetService sbcSetService;

    public BuscarSbcSetUC(SbcSetService sbcSetService) {
        this.sbcSetService = sbcSetService;
    }

    @Transactional(readOnly = true)
    public Optional<SbcSetResponse> executar(Integer setId) {
        return sbcSetService.buscarPorSetId(setId)
                .map(SbcSetResponse::new);
    }
}
