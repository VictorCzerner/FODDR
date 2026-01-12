package com.czerner.foddr.dominio.entidades;

public class elenco {
    

    private int OVR;


    private int[][] tabela;

    private int originalIndex;

    private int totw;

    private boolean completo = false;

    private int[] solução;
    private int[] solucaoIncompleta;
    private int[] solucaoIncompletaTenho;
    private int[] solucaoIncompletaFalta;
    private int[] usoInforms;

    public elenco(int OVR, int[][] tabela, int totw) {
        this.OVR = OVR;
        this.tabela = tabela;
        this.totw=totw;
    }

    public int getOriginalIndex() { return originalIndex; }
    public void setOriginalIndex(int idx) { this.originalIndex = idx; }

    public boolean getCompleto(){return completo;}
    public void setCompleto(){completo=true;}

    public void setSolucao(int[] solução){
        this.solução=solução;
    }
    public void setSolucaoIncompleta(int[] solução){
        solucaoIncompleta=solução;
    }
    public void setSolucaoIncompletaTenho(int[] solução){
        solucaoIncompletaTenho=solução;
    }
    public void setSolucaoIncompletaFalta(int[] solução){
        solucaoIncompletaFalta=solução;
    }
    public void setUsoInforms(int[] usoInforms){
        this.usoInforms=usoInforms;
    }
    public int[] getSolucao(){
        return solução;
    }
    public int[] getSolucaoIncompleta(){
        return solucaoIncompleta;
    }
    public int[] getSolucaoIncompletaTenho(){
        return solucaoIncompletaTenho;
    }
    public int[] getSolucaoIncompletaFalta(){
        return solucaoIncompletaFalta;
    }
    public int[] getUsoInforms(){
        return usoInforms;
    }

    public int getOVR() {
        return OVR;
    }



    public void setOVR(int OVR) {
        this.OVR = OVR;
    }



    public int[][] getTabela() {
        return tabela;
    }



    public void setTabela(int[][] tabela) {
        this.tabela = tabela;
    }

    public int getTotw(){
        return totw;
    }

    public void setTotw(int totw){
        this.totw=totw;
    }



    @Override
    public String toString() {
        return "" + OVR;
    }

}