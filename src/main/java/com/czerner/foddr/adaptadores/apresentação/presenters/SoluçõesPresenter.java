package com.czerner.foddr.adaptadores.apresentação.presenters;

import com.czerner.foddr.aplicação.Responses.ElencoResponse;

public class SoluçõesPresenter {
    private final int[][] soluções;

    public SoluçõesPresenter(ElencoResponse elencoResponse) {
        this.soluções = elencoResponse.getTabela();
    }

    public int[][] getSoluções() {
        return soluções;
    }
}
