package com.czerner.foddr.adaptadores.apresentação.Requests;

public class SolucoesPossiveisRequest {

    private int ovr;
    private int minRating;
    private int maxRating;
    private int[] forragem;

    public int getOvr() {
        return ovr;
    }

    public void setOvr(int ovr) {
        this.ovr = ovr;
    }

    public int getMinRating() {
        return minRating;
    }

    public void setMinRating(int minRating) {
        this.minRating = minRating;
    }

    public int getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(int maxRating) {
        this.maxRating = maxRating;
    }

    public int[] getForragem() {
        return forragem;
    }

    public void setForragem(int[] forragem) {
        this.forragem = forragem;
    }
}

