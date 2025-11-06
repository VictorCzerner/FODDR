package com.czerner.foddr.adaptadores.apresentação.Requests;

/**
 * DTO usado pelo endpoint /sbc/encontrar.
 * Mantem a estrutura esperada no JSON de entrada.
 */
public class SBCRequest {

    private SBCData sbc;
    private int[] forragem;

    public SBCData getSbc() {
        return sbc;
    }

    public void setSbc(SBCData sbc) {
        this.sbc = sbc;
    }

    public int[] getForragem() {
        return forragem;
    }

    public void setForragem(int[] forragem) {
        this.forragem = forragem;
    }

    public static class SBCData {
        private int numElencos;
        private int[] ovrs;

        public int getNumElencos() {
            return numElencos;
        }

        public void setNumElencos(int numElencos) {
            this.numElencos = numElencos;
        }

        public int[] getOvrs() {
            return ovrs;
        }

        public void setOvrs(int[] ovrs) {
            this.ovrs = ovrs;
        }
    }
}
