package com.czerner.foddr.dominio.entidades;

import java.util.ArrayList;
import java.util.List;

public class SBC {

    private int numElencos;
    private final List<elenco> elencos;
    private boolean completo;

    public SBC(List<elenco> elencos) {
        if (elencos == null) {
            throw new IllegalArgumentException("Lista de elencos não pode ser nula.");
        }
        this.elencos = new ArrayList<>(elencos);
        this.numElencos = elencos.size();
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
