package com.czerner.foddr.dominio.serviços;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.czerner.foddr.dominio.entidades.elenco;
import com.czerner.foddr.dominio.entidades.forragens;

@Service
public class elencoService {

    static final int PLAYERS = 11;
    private static final int MAX_COMBINACOES = 200;

    private int totw;

    public elenco CriaElenco(int ovr, int totw) {
        int minRating = 79;
        int maxRating = 93;
        this.totw = totw;

        List<int[]> tabelaList = CalculaTabela(ovr, minRating, maxRating);
        int[][] tabela = tabelaList.toArray(new int[0][]);

        return new elenco(ovr, tabela, totw);
    }

    public elenco buscaPorOvr(int ovr, int minRating, int maxRating) {
        List<int[]> tabelaList = CalculaTabela(ovr, minRating, maxRating);
        int[][] tabela = tabelaList.toArray(new int[0][]);

        return new elenco(ovr, tabela, 0);
    }

    public List<int[]> SoluçõesPossiveis(elenco elenco, forragens forragens) {
        List<int[]> solucoes = new ArrayList<>();
        int[] jogadores = forragens.getForragem();

        for (int[] s : elenco.getTabela()) {
            if (s == null) continue;

            boolean ok = true;
            for (int i = 0; i < s.length; i++) {
                if (jogadores[i] < s[i]) {
                    ok = false;
                    break;
                }
            }

            if (ok) {
                solucoes.add(s);
            }
        }

        return solucoes;
    }

    public static List<int[]> CalculaTabela(int targetOvr, int minRating, int maxRating) {
        List<int[]> results = new ArrayList<>();
        int range = maxRating - minRating + 1;
        int[] counts = new int[range];

        backtrack(counts, 0, 0, targetOvr, results, minRating);

        // Envia a primeira combinação para o fim da lista (evita priorizar a mais básica)
        if (!results.isEmpty()) {
            int[] primeiro = results.remove(0);
            results.add(primeiro);
        }

        return results;
    }

    private static void backtrack(
            int[] counts,
            int idx,
            int used,
            int targetOvr,
            List<int[]> results,
            int minRating
    ) {
        if (results.size() >= MAX_COMBINACOES) return;

        if (used == PLAYERS) {
            double ovr = calcOvr(counts, minRating);
            if (ovr == targetOvr) {
                results.add(counts.clone());
            }
            return;
        }

        if (idx >= counts.length) return;

        int maxPorRating = Math.min(9, PLAYERS - used);

        for (int c = 0; c <= maxPorRating; c++) {
            if (results.size() >= MAX_COMBINACOES) return;

            counts[idx] = c;
            backtrack(
                counts,
                idx + 1,
                used + c,
                targetOvr,
                results,
                minRating
            );
            counts[idx] = 0;
        }
    }

    private static double calcOvr(int[] counts, int minRating) {
        int totalPlayers = 0;
        double sum = 0;

        for (int i = 0; i < counts.length; i++) {
            int rating = minRating + i;
            sum += counts[i] * rating;
            totalPlayers += counts[i];
        }

        if (totalPlayers != PLAYERS) return -1;

        double avg = sum / totalPlayers;

        double bonus = 0;
        for (int i = 0; i < counts.length; i++) {
            int rating = minRating + i;
            bonus += counts[i] * Math.max(0, rating - avg);
        }

        int rounding = (int) Math.round(bonus + sum);
        double arredonda = bonus+sum;
        if (rounding-0.25 > arredonda) {
            return rounding / (double) totalPlayers;
        }
        else {return 0;}
    }
}
