package com.czerner.foddr.dominio.entidades;

public class forragens {

    private int[] forragem = new int[15];

    public forragens(int[] forragem) {
        this.forragem = forragem;
    }

    public int[] getForragem() {
        return forragem.clone(); // Evita alterações externas acidentais
    }

    public void setForragem(int[] forragem) {
        this.forragem = forragem;
    }

    // === Getters individuais ===
    public int get79() { return forragem[0]; }
    public int get80() { return forragem[1]; }
    public int get81() { return forragem[2]; }
    public int get82() { return forragem[3]; }
    public int get83() { return forragem[4]; }
    public int get84() { return forragem[5]; }
    public int get85() { return forragem[6]; }
    public int get86() { return forragem[7]; }
    public int get87() { return forragem[8]; }
    public int get88() { return forragem[9]; }
    public int get89() { return forragem[10]; }
    public int get90() { return forragem[11]; }
    public int get91() { return forragem[12]; }
    public int get92() { return forragem[13]; }
    public int get93() { return forragem[14]; }

    // === Setters individuais ===
    public void set79(int jogador) { forragem[0] = jogador; }
    public void set80(int jogador) { forragem[1] = jogador; }
    public void set81(int jogador) { forragem[2] = jogador; }
    public void set82(int jogador) { forragem[3] = jogador; }
    public void set83(int jogador) { forragem[4] = jogador; }
    public void set84(int jogador) { forragem[5] = jogador; }
    public void set85(int jogador) { forragem[6] = jogador; }
    public void set86(int jogador) { forragem[7] = jogador; }
    public void set87(int jogador) { forragem[8] = jogador; }
    public void set88(int jogador) { forragem[9] = jogador; }
    public void set89(int jogador) { forragem[10] = jogador; }
    public void set90(int jogador) { forragem[11] = jogador; }
    public void set91(int jogador) { forragem[12] = jogador; }
    public void set92(int jogador) { forragem[13] = jogador; }
    public void set93(int jogador) { forragem[14] = jogador; }

    // === toString ===
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < forragem.length; i++) {
            sb.append(forragem[i]);
            if (i < forragem.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
