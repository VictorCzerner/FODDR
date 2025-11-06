package com.czerner.foddr.adaptadores.apresentação.presenters;

import java.util.List;

public class SolucoesPossiveisPresenter {

    private final int[][] solucoes;

    public SolucoesPossiveisPresenter(List<int[]> solucoes) {
        this.solucoes = solucoes.stream()
                .map(int[]::clone)
                .toArray(int[][]::new);
    }

    public int[][] getSolucoes() {
        return solucoes;
    }
}

