package com.czerner.foddr.dominio.serviços;

import com.czerner.foddr.aplicação.Responses.SBCResponse;
import com.czerner.foddr.dominio.entidades.SBC;
import com.czerner.foddr.dominio.entidades.elenco;
import com.czerner.foddr.dominio.entidades.forragens;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SBCService {

    private final elencoService elencoService;
    private boolean encontrouSolucao;

    public SBCService(elencoService elencoService) {
        this.elencoService = elencoService;
    }

    public SBC criaSBC(int numElencos, int[] ovrs) throws Exception {
        return new SBC(numElencos, ovrs, elencoService);
    }

    public SBCResponse encontrarSolucao(SBC sbc, forragens jogadores) throws Exception {
        encontrouSolucao = false;

        List<int[]> elencosCompletos = new ArrayList<>();
        List<int[]> elencosFaltantes = new ArrayList<>();
        int[] total = null;

        buscarRecursivo(sbc, jogadores, elencosCompletos, 0, elencosFaltantes);

        if (!elencosCompletos.isEmpty()) {
            total = new int[elencosCompletos.get(0).length];
            for (int[] elenco : elencosCompletos) {
                for (int j = 0; j < total.length; j++) {
                    total[j] += elenco[j];
                }
            }
        }

        return new SBCResponse(elencosCompletos, total, elencosFaltantes);
    }

    private void buscarRecursivo(SBC sbc, forragens jogadores, List<int[]> elencosCompletos,
                                 int maxElencos, List<int[]> elencosFaltantes) throws Exception {

        if (encontrouSolucao) return;

        List<elenco> elencos = sbc.getElencos();
        if (elencosCompletos.size() >= elencos.size()) return;

        boolean encontrado = false;
        int[] jogadoresAtual = new int[jogadores.getForragem().length];

        int idx = elencosCompletos.size();
        int[][] tabelaOriginal = elencos.get(idx).getTabela();

        // Cria cópia da tabela para “riscar” combinações testadas sem afetar o original
        int[][] tabelaAtual = Arrays.stream(tabelaOriginal)
                .map(linha -> linha != null ? linha.clone() : null)
                .toArray(int[][]::new);

        for (int i = 0; i < tabelaAtual.length; i++) {
            if (tabelaAtual[i] != null) {
                boolean positivo = true;

                for (int j = 0; j < jogadores.getForragem().length; j++) {
                    jogadoresAtual[j] = jogadores.getForragem()[j] - tabelaAtual[i][j];
                    if (jogadoresAtual[j] < 0) {
                        positivo = false;
                        break;
                    }
                }

                if (positivo) {
                    elencosCompletos.add(tabelaAtual[i]);
                    maxElencos = Math.max(maxElencos, elencosCompletos.size());
                    encontrado = true;

                    // Riscagem local (mantém lógica original)
                    if (elencosCompletos.size() < elencos.size()) {
                        tabelaAtual[i] = null;
                    } else {
                        break;
                    }

                    forragens att = new forragens(jogadoresAtual.clone());
                    buscarRecursivo(sbc, att, elencosCompletos, maxElencos, elencosFaltantes);
                    if (encontrouSolucao) return;

                    // Backtracking
                    elencosCompletos.remove(elencosCompletos.size() - 1);
                }
            }
        }

        if (!encontrado && !elencosCompletos.isEmpty()) {
            elencosCompletos.remove(elencosCompletos.size() - 1);
            buscarRecursivo(sbc, jogadores, elencosCompletos, maxElencos, elencosFaltantes);
            return;
        }

        // Se não encontrou solução parcial, cria subSBC com parte dos elencos
        if (!encontrado && elencosCompletos.isEmpty() && !encontrouSolucao) {
            int[] divE = new int[maxElencos];
            for (int i = 0; i < maxElencos; i++) divE[i] = elencos.get(i).getOVR();

            SBC subSbc = new SBC(maxElencos, divE, elencoService);
            SBCResponse sub = encontrarSolucao(subSbc, jogadores);

            elencosCompletos.addAll(sub.getElencosCompletos());
            if (sub.getTotal() != null) {
                elencosFaltantes.addAll(sub.getElencosFaltantes());
            }

            // Adiciona elencos faltantes deste nível
            for (int i = maxElencos; i < elencos.size(); i++) {
                elencosFaltantes.add(elencos.get(i).getTabela()[0]);
            }

            encontrouSolucao = true;
            return;
        }

        // Quando completa todos os elencos
        if (encontrado && (elencosCompletos.size() == sbc.getNumElencos())) {
            encontrouSolucao = true;
        }
    }
}
