package com.czerner.foddr.aplicação;

import org.springframework.stereotype.Component;

import com.czerner.foddr.aplicação.Responses.ElencoResponse;
import com.czerner.foddr.dominio.entidades.elenco;
import com.czerner.foddr.dominio.serviços.elencoService;

@Component
public class BuscaMenorPossivelUC {
    private final elencoService elencoService;

    public BuscaMenorPossivelUC(com.czerner.foddr.dominio.serviços.elencoService elencoService) {
        this.elencoService = elencoService;
    }

    public ElencoResponse executar(int ovr, int minRating, int maxRating) {
        if (ovr < 81 || ovr > 99) {
            throw new IllegalArgumentException("OVR deve estar entre 81 e 99");
        }
        if (minRating < 75 || maxRating > 99 || minRating >= maxRating) {
            throw new IllegalArgumentException("Ratings inválidos");
        }
        elenco elenco = elencoService.buscaPorOvr(ovr, minRating, maxRating);
        return new ElencoResponse(elenco);
    }
}
