package com.czerner.foddr.dominio.serviços;

import java.util.List;

import org.springframework.stereotype.Service;

import com.czerner.foddr.dominio.entidades.SBC;
import com.czerner.foddr.dominio.entidades.elenco;
import com.czerner.foddr.dominio.entidades.forragens;
import com.czerner.foddr.dominio.entidades.forragensTOTW;

@Service
public class forragensTOTWService {

    private final forragensService forragensService;

    public forragensTOTWService(forragensService forragensService) {
        this.forragensService = forragensService;
    }

    /**
     * Cria uma forragem TOTW a partir de um array e define se é Inform.
     */
    public forragensTOTW criarForragemTOTW(int[] forragemArray) {
        forragens base = forragensService.criarForragem(forragemArray);
        return construirForragemTOTW(base, true);
    }

    /**
     * Soma duas forragens TOTW; o resultado mantém o flag Inform se qualquer uma das entradas for Inform.
     */
    public forragensTOTW somarForragensTOTW(forragensTOTW a, forragensTOTW b) {
        forragens resultado = forragensService.somarForragens(a, b);
        boolean inform = a.isEhInform() || b.isEhInform();
        return construirForragemTOTW(resultado, inform);
    }

    /**
     * Subtrai os jogadores TOTW usados, preservando o flag inform da base.
     */
    public forragensTOTW subtrairForragensTOTW(forragensTOTW base, forragensTOTW usada) {
        forragens resultado = forragensService.subtrairForragens(base, usada);
        return construirForragemTOTW(resultado, base.isEhInform());
    }

    /**
     * Exibe a forragem TOTW e indica se os jogadores são Inform.
     */
    public void exibirForragemTOTW(forragensTOTW forragemTOTW) {
        forragensService.exibirForragem(forragemTOTW);
        System.out.println("Tipo TOTW: " + (forragemTOTW.isEhInform() ? "Inform" : "Ouro"));
    }

    private forragensTOTW construirForragemTOTW(forragens base, boolean ehInform) {
        return new forragensTOTW(base.getForragem(), ehInform);
    }

    public List<int[]> usaTOTW (SBC sbc, List<int[]> soluções, forragensTOTW jogadores){
        List<elenco> elencos = sbc.getElencos();


        return soluções;
        
    }
}

