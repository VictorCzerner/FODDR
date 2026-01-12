package com.czerner.foddr.aplicação;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import com.czerner.foddr.aplicação.Responses.SBCResponse;
import com.czerner.foddr.dominio.entidades.SBC;
import com.czerner.foddr.dominio.entidades.elenco;
import com.czerner.foddr.dominio.entidades.forragens;
import com.czerner.foddr.dominio.entidades.forragensTOTW;
import com.czerner.foddr.dominio.serviços.SBCService;

@Component
public class SbcMachineUC {
    private final SBCService SBCService;

    public SbcMachineUC(SBCService sBCService) {
        SBCService = sBCService;
    }

    public SBCResponse executar(SBC sbc, forragens jogadores, forragensTOTW forragensTOTW) throws Exception {
        System.out.println("tabelas SBC:");
        for (int i = 0; i < sbc.getNumElencos(); i++) {
            System.out.println(i + ": " + sbc.getElencos().get(i).getTabela().length);
        }
        int[][] resultado = SBCService.encontrarSolucao(sbc, jogadores);
        List<int[]> elencosFaltantes = new ArrayList<>();
        List<int[]> elencosFaltantesPositivo = new ArrayList<>();
        List<int[]> elencosFaltantesNegativo = new ArrayList<>();
        
        if (resultado == null) {
            resultado = new int[0][];
        }

        System.out.println("Resultado SBC:");
        for (int i = 0; i < resultado.length; i++) {
            System.out.println(i + ": " + Arrays.toString(resultado[i]));
        }

        

        boolean solucaoCompleta = true;
        for (int[] linha : resultado) {
            if (linha == null) {
                solucaoCompleta = false;
                break;
            }
        }

        int[] total = null;
        if (resultado.length > 0) {
            int tamanho = jogadores.getForragem().length;
            for (int[] linha : resultado) {
                if (linha != null) {
                    tamanho = linha.length;
                    break;
                }
            }
            total = new int[tamanho];
            for (int[] linha : resultado) {
                if (linha == null) continue; // evitar NPE
                for (int j = 0; j < total.length; j++) {
                    total[j] += linha[j];
                }
            }
        }

        
        int[] jogadoresRestantes = jogadores.getForragem().clone();
        for (int[] linha : resultado) {
            if (linha == null) continue;  // <<< evita crash
            jogadoresRestantes = SBCService.subtraiForragem(jogadoresRestantes, linha);
        }
        forragens forragensRestantes = new forragens(jogadoresRestantes);
        

        if (!solucaoCompleta) {
            SBC sbcRestante = SBCService.criarSbcRestante(sbc, resultado);
            elencosFaltantes = SBCService.elencosIncompletos(sbcRestante.getElencos(), forragensRestantes);
            Pair<List<int[]>,List<int[]>> par = SBCService.completaSolucoes(elencosFaltantes, forragensRestantes);
            elencosFaltantesPositivo = par.getLeft();
            elencosFaltantesNegativo = par.getRight();
        }
        
        int indiceFaltante = 0;
        List<elenco> elencos = sbc.getElencos();
        int limite = Math.min(resultado.length, elencos.size());
        if (resultado.length != elencos.size()) {
            System.out.println("Aviso: quantidade de resultados (" + resultado.length + 
                               ") diferente do número de elencos (" + elencos.size() + ").");
        }

        for (int i = 0; i < limite; i++) {
            int[] linha = resultado[i];

            if (linha == null) {
                int[] incompleta = indiceFaltante < elencosFaltantes.size() ? elencosFaltantes.get(indiceFaltante) : new int[0];
                int[] tenho = indiceFaltante < elencosFaltantesPositivo.size() ? elencosFaltantesPositivo.get(indiceFaltante) : new int[0];
                int[] falta = indiceFaltante < elencosFaltantesNegativo.size() ? elencosFaltantesNegativo.get(indiceFaltante) : new int[0];

                elencos.get(i).setSolucaoIncompleta(incompleta);
                elencos.get(i).setSolucaoIncompletaTenho(tenho);
                elencos.get(i).setSolucaoIncompletaFalta(falta);
                indiceFaltante++;
            } else {
                elencos.get(i).setSolucao(linha);
                elencos.get(i).setCompleto();
            }
        }

        // Se houver mais elencos do que entradas em resultado, marcamos como incompletos vazios.
        for (int i = limite; i < elencos.size(); i++) {
            elencos.get(i).setSolucaoIncompleta(new int[0]);
            elencos.get(i).setSolucaoIncompletaTenho(new int[0]);
            elencos.get(i).setSolucaoIncompletaFalta(new int[0]);
        }

        List<int[]> informsUsados = SBCService.usarInforms(sbc, forragensTOTW);
        


        return new SBCResponse(
                Arrays.asList(resultado),
                total,
                elencosFaltantes,
                elencosFaltantesPositivo,
                elencosFaltantesNegativo,
                informsUsados,
                sbc
        );
    }

}
