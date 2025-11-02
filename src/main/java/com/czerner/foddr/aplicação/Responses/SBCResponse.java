package com.czerner.foddr.aplicação.Responses;

import java.util.ArrayList;
import java.util.List;

public class SBCResponse {
    private List<int[]> elencosCompletos = new ArrayList<>();
    private int[] total;
    private List<int[]> elencosFaltantes = new ArrayList<>();

    public SBCResponse(List<int[]> elencosCompletos, int[] total, List<int[]> elencosFaltantes) {
        this.elencosCompletos = elencosCompletos;
        this.total = total;
        this.elencosFaltantes = elencosFaltantes;
    }

    public List<int[]> getElencosCompletos() {
        return elencosCompletos;
    }

    public int[] getTotal() {
        return total;
    }

    public List<int[]> getElencosFaltantes() {
        return elencosFaltantes;
    }
}
