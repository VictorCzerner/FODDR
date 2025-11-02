package com.czerner.foddr.dominio.entidades;


import com.czerner.foddr.dominio.dados.converters.IntMatrixJsonConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "elenco")
@Data
@NoArgsConstructor
public class elenco {
    
    @Id
    @Column(name = "ovr")
    private int OVR;

    @Column(columnDefinition = "json", nullable = false)
    @Convert(converter = IntMatrixJsonConverter.class)
    private int[][] tabela;


    public elenco(int OVR, int[][] tabela) {
        this.OVR = OVR;
        this.tabela = tabela;
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



    @Override
    public String toString() {
        return "" + OVR;
    }

}