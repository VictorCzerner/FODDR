package com.czerner.foddr.aplicação.Responses;

import com.czerner.foddr.dominio.entidades.elenco;

public class ElencoResponse {
    private final int OVR;
    private final int[][] tabela;

    public ElencoResponse(elenco elenco) {
        OVR = elenco.getOVR();
        this.tabela = elenco.getTabela();
    }

    public int getOVR() {
        return OVR;
    }

    public int[][] getTabela() {
        return tabela;
    }

    
}
