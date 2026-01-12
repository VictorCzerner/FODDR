package com.czerner.foddr.aplicação.Responses;

import java.util.ArrayList;
import java.util.List;

import com.czerner.foddr.dominio.entidades.SBC;

public class SBCResponse {
    private List<int[]> elencosCompletos = new ArrayList<>();
    private int[] total;
    private List<int[]> elencosFaltantes = new ArrayList<>();
    private List<int[]> elencosFaltantesPositivo = new ArrayList<>();
    private List<int[]> elencosFaltantesNegativo = new ArrayList<>();
    private SBC sbc;
    private List<int[]> informsUsado = new ArrayList<>();

    public SBCResponse(List<int[]> elencosCompletos, int[] total, List<int[]> elencosFaltantes,
            List<int[]> elencosFaltantesPositivo, List<int[]> elencosFaltantesNegativo,List<int[]> informsUsado , SBC sbc) {
        this.elencosCompletos = elencosCompletos;
        this.total = total;
        this.elencosFaltantes = elencosFaltantes;
        this.elencosFaltantesPositivo = elencosFaltantesPositivo;
        this.elencosFaltantesNegativo = elencosFaltantesNegativo;
        this.informsUsado=informsUsado;
        this.sbc=sbc;
    }

    public SBC getSbc() {
        return sbc;
    }

    public List<int[]> getInformsUsado() {
        return informsUsado;
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

    public List<int[]> getElencosFaltantesPositivo() {
        return elencosFaltantesPositivo;
    }

    public List<int[]> getElencosFaltantesNegativo() {
        return elencosFaltantesNegativo;
    }


}
