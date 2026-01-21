package com.czerner.foddr.adaptadores.apresentação.Requests;

import java.util.List;

/**
 * DTO usado pelo endpoint /sbc/encontrar.
 * Mantem a estrutura esperada no JSON de entrada.
 */
public class SBCRequest {

    private SBCData sbc;
    private int[] forragem;
    private int[] forragemTotw;
    private List<Integer> completedChallengeIds;

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

    public int[] getForragemTotw() {
        return forragemTotw;
    }

    public void setForragemTotw(int[] forragemTotw) {
        this.forragemTotw = forragemTotw;
    }

    public List<Integer> getCompletedChallengeIds() {
        return completedChallengeIds;
    }

    public void setCompletedChallengeIds(List<Integer> completedChallengeIds) {
        this.completedChallengeIds = completedChallengeIds;
    }

    public static class SBCData {
        private List<ElencoData> elencos;

        public List<ElencoData> getElencos() {
            return elencos;
        }

        public void setElencos(List<ElencoData> elencos) {
            this.elencos = elencos;
        }
    }

    public static class ElencoData {
        private int ovr;
        private int informs;

        public int getOvr() {
            return ovr;
        }

        public void setOvr(int ovr) {
            this.ovr = ovr;
        }

        public int getInforms() {
            return informs;
        }

        public void setInforms(int informs) {
            this.informs = informs;
        }
    }
}
