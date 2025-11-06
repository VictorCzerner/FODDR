package com.czerner.foddr.dominio.serviços;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.czerner.foddr.dominio.dados.elencoRepository;
import com.czerner.foddr.dominio.entidades.elenco;
import com.czerner.foddr.dominio.entidades.forragens;

@Service  
public class elencoService {
    private final elencoRepository elencoRepository;

    static final int PLAYERS = 11;


    public elencoService(elencoRepository elencoRepository) {
        this.elencoRepository = elencoRepository;
    }

    public elenco CriaElenco(int ovr, int minRating, int maxRating) throws Exception {
        if (ovr < 81 || ovr > 99) {
            throw new IllegalArgumentException("OVR deve estar entre 81 e 99");
        }
        if (minRating < 75 || maxRating > 99 || minRating >= maxRating) {
            throw new IllegalArgumentException("Ratings inválidos");
        }
        int[][] tabela = CalculaTabela(ovr, minRating, maxRating).stream().toArray(int[][]::new);
        elenco elenco = new elenco(ovr, tabela);
        return elenco;
    }

    public List<int[]> SoluçõesPossiveis(elenco elenco, forragens forragens) {
    List<int[]> soluções = new ArrayList<>();
    int[] jogadores = forragens.getForragem();

    for (int[] s : elenco.getTabela()) {
        if (s == null) continue; // <-- pula se s for nulo

        int count = 0;
        for (int i = 0; i < s.length; i++) {
            int ovr = s[i];
            if (jogadores[i] >= ovr) {
                count++;
            }
        }

        if (count == jogadores.length) {
            soluções.add(s);
        }
    }

    return soluções;
}


    public elenco AdicionaElenco(int ovr, int minRating, int maxRating) throws Exception {
        if (elencoRepository.buscaPorOvr(ovr) != null) {
            throw new Exception("Elenco com ovr " + ovr + " já existe");
        }
        int[][] tabela = CalculaTabela(ovr, minRating, maxRating).stream().toArray(int[][]::new);
        elenco elenco = new elenco(ovr, tabela);
        return elencoRepository.AdicionaElenco(elenco);
    }

    public elenco buscaPorOvr(int ovr, int minRating, int maxRating) {
        int[][] tabela = CalculaTabela(ovr, minRating, maxRating).stream().toArray(int[][]::new);
        elenco elenco = new elenco(ovr, tabela);
        return elenco;
    }


    
    public static List<int[]> CalculaTabela(int targetOvr, int minRating, int maxRating) {
        List<int[]> results = new ArrayList<>();
        int range = maxRating - minRating + 1;
        int[] counts = new int[range];

        backtrack(counts, 0, 0, targetOvr, results, minRating, maxRating);

        // Limita para evitar travar (ajuste conforme necessidade)
        return results.stream()
                .collect(Collectors.toList());
    }

    private static void backtrack(int[] counts, int idx, int used, int targetOvr, List<int[]> results,
                                int minRating, int maxRating) {
        // quando completou os 11 jogadores, calcula o OVR
        if (used == PLAYERS) {
            double ovr = calcOvr(counts, minRating);
            if (ovr == targetOvr) {
                results.add(counts.clone());
            }
            return;
        }

        if (idx >= counts.length) return;

        // limite de 9 jogadores por rating
        int maxPorRating = Math.min(9, PLAYERS - used);

        // tenta colocar de 0 até o número máximo de jogadores possíveis nesse rating
        for (int c = 0; c <= maxPorRating; c++) {
            counts[idx] = c;
            backtrack(counts, idx + 1, used + c, targetOvr, results, minRating, maxRating);
            counts[idx] = 0; // reseta pra próxima opção
        }
    }

    private static double calcOvr(int[] counts, int minRating) {
        int totalPlayers = 0;
        double sum = 0;

        // calcula média ponderada dos ratings
        for (int i = 0; i < counts.length; i++) {
            int rating = minRating + i;
            sum += counts[i] * rating;
            totalPlayers += counts[i];
        }

        if (totalPlayers == 0) return 0;

        double avg = sum / totalPlayers;
        
        // bônus conforme fórmula do SBC (EA)
        double bonus = 0;
        for (int i = 0; i < counts.length; i++) {
            int rating = minRating + i;
            bonus += counts[i] * Math.max(0, rating - avg);
        }
        int rounding = (int) Math.round(bonus + sum);

        double finalOvr = rounding / (double) totalPlayers;
    
        return finalOvr;
    }
}
