package com.czerner.foddr.dominio.entidades;

import java.util.ArrayList;
import java.util.List;

import com.czerner.foddr.dominio.serviços.elencoService;

public class SBC {

    private int numElencos;
    private List<elenco> elencos;
    private boolean completo;
    elencoService serviços;

    public SBC(int numElencos, int[] ovrs, elencoService elencoService) throws Exception {
        this.serviços = elencoService;
        this.numElencos = numElencos;
        this.elencos = new ArrayList<>();
        for (int i = 0; i < numElencos; i++) {
            elencos.add(serviços.CriaElenco(ovrs[i], 79, 93));
        }
        this.completo = false;
    }

    public int getNumElencos() {
        return numElencos;
    }

    public List<elenco> getElencos() {
        return elencos;
    }

    public boolean isCompleto() {
        return completo;
    }

    public void setCompleto(boolean completo) {
        this.completo = completo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SBC [elencos: ");
        for (int i = 0; i < elencos.size(); i++) {
            sb.append(elencos.get(i).getOVR());
            if (i < elencos.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
