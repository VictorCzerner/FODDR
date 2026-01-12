package com.czerner.foddr.dominio.serviços;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import com.czerner.foddr.dominio.entidades.SBC;
import com.czerner.foddr.dominio.entidades.elenco;
import com.czerner.foddr.dominio.entidades.entidadesData.SbcChallenge;
import com.czerner.foddr.dominio.entidades.entidadesData.SbcRequirement;
import com.czerner.foddr.dominio.entidades.entidadesData.SbcSet;
import com.czerner.foddr.dominio.entidades.forragens;
import com.czerner.foddr.dominio.entidades.forragensTOTW;
import com.czerner.foddr.dominio.entidades.graph.Node;
import com.czerner.foddr.dominio.serviços.serviçosData.SbcSetService;


@Service
public class SBCService {

    private final Map<String, Boolean> memo = new HashMap<>();
    private final elencoService elencoService;
    private final SbcSetService SbcSetService;
    private final List<int[][]> restoreElenco;
    private final AtomicBoolean encontrouSolucao = new AtomicBoolean(false);
    private int melhorProgresso;
    private int[][] melhorResultadoParcial;

    public SBCService(elencoService elencoService, SbcSetService SbcSetService) {
        this.elencoService = elencoService;
        this.SbcSetService = SbcSetService;
        this.restoreElenco = new ArrayList<>();
    }

    public SBC criaSBC(List<elenco> elencos) {
        return new SBC(elencos);
    }

    public SBC criaSbcPorOvrs(List<Integer> ovrs) throws Exception {
        if (ovrs == null || ovrs.isEmpty()) {
            throw new IllegalArgumentException("Lista de elencos não informada.");
        }

        List<elenco> elencos = new ArrayList<>();
        for (Integer ovr : ovrs) {
            if (ovr == null || ovr <= 0) {
                throw new IllegalArgumentException("OVR inválido fornecido para o SBC.");
            }
            elencos.add(elencoService.CriaElenco(ovr, 0));
        }

        return criaSBC(elencos);
    }

    private int[][] deepClone(int[][] original) {
        if (original == null) return null;
        int[][] clone = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            clone[i] = (original[i] != null ? original[i].clone() : null);
        }
        return clone;
    }


    public int[][] encontrarSolucao(SBC sbc, forragens jogadores) throws Exception {
        encontrouSolucao.set(false);
        memo.clear();
        melhorProgresso = -1;

        int totalElencos = sbc.getNumElencos();

        // PREPARAR RESULTADO FINAL NA ORDEM ORIGINAL
        int[][] resultadoFinal = new int[totalElencos][];
        melhorResultadoParcial = new int[totalElencos][];

        // Marcar índice original dos elencos
        int pos = 0;
        for (elenco e : sbc.getElencos()) {
            e.setOriginalIndex(pos++);
            List<int[]> novo = elencoService.SoluçõesPossiveis(e, jogadores);
            e.setTabela(novo.toArray(new int[0][]));
        }

        // Ordenar por menor número de combinações (melhor performance)
        sbc.getElencos().sort(
            Comparator.comparingInt(e -> 
                e.getTabela().length == 0 ? Integer.MAX_VALUE : e.getTabela().length
            )
        );


        // COMEÇA RECURSÃO
        buscarRecursivo(sbc, jogadores, resultadoFinal, 0);
        
        int[][] resultadoParaCliente = encontrouSolucao.get()
                ? resultadoFinal
                : (melhorResultadoParcial != null ? melhorResultadoParcial : resultadoFinal);

        return resultadoParaCliente;
    }


    //---------------------- RECURSÃO OTIMIZADA -----------------------------

    private void buscarRecursivo(
            SBC sbc,
            forragens jogadores,
            int[][] resultadoFinal,
            int idx
    ) throws Exception {

        if (encontrouSolucao.get()) return;

        List<elenco> elencos = sbc.getElencos();
        int totalElencos = elencos.size();

        // Solução completa
        if (idx == totalElencos) {
            encontrouSolucao.set(true);
            return;
        }

        // Memoization
        String chave = idx + "-" + Arrays.toString(jogadores.getForragem());
        if (memo.containsKey(chave)) return;


        List<int[]> tabelaOtimizada = elencoService.SoluçõesPossiveis(elencos.get(idx), jogadores);
        int[][] tabela = tabelaOtimizada.toArray(new int[tabelaOtimizada.size()][]);

        if (tabela == null || tabela.length == 0) {
            // Sem combinação para este elenco: mantemos como null e seguimos para os próximos
            memo.put(chave, false);
            buscarRecursivo(sbc, jogadores, resultadoFinal, idx + 1);
            return;
        }

        // Tentar todas combinações possíveis
        for (int[] tentativa : tabela) {
            if (tentativa == null) continue;

            // Testar se cabe na forragem
            if (!cabeNaForragem(jogadores.getForragem(), tentativa)) continue;

            // Aplicar uso da forragem
            int[] novaForragem = subtraiForragem(jogadores.getForragem(), tentativa);
            forragens novosJogadores = new forragens(novaForragem);

            // Posição original do elenco (ordem final correta!)
            int posOriginal = elencos.get(idx).getOriginalIndex();
            resultadoFinal[posOriginal] = tentativa;
            if (idx + 1 > melhorProgresso) {
                melhorProgresso = idx + 1;
                melhorResultadoParcial = deepClone(resultadoFinal);
            }

            // Recursão no próximo elenco
            System.out.println(idx);
            buscarRecursivo(sbc, novosJogadores, resultadoFinal, idx + 1);

            if (encontrouSolucao.get()) return;

            // Backtracking
            resultadoFinal[posOriginal] = null;
        }

        memo.put(chave, false);
    }

    private boolean cabeNaForragem(int[] forragem, int[] tentativa) {
        for (int i = 0; i < forragem.length; i++) {
            if (tentativa[i] > forragem[i]) return false;
        }
        return true;
    }

    public int[] subtraiForragem(int[] base, int[] uso) {
        base = base.clone();
        for (int i = 0; i < base.length; i++) base[i] -= uso[i];
        return base;
    }

    private int subtraiMaximo(int[] base, int[] uso) {
        base = base.clone();
        int inverte = -1;
        int jogadoresNegativo = 0;
        for (int i = 0; i < base.length; i++) base[i] -= uso[i];
        for (int i = 0; i < base.length; i++){
            if (base[i] < 0){
                jogadoresNegativo += (base[i]*inverte);
            }
        }
        return jogadoresNegativo;
    }

    public List<int[]> elencosIncompletos(List<elenco> elencosFaltantes, forragens jogadores){
        List<int[]> soluções = new ArrayList<>();
        forragens copia = new forragens(jogadores.getForragem().clone());


        for (int i = 0; i < elencosFaltantes.size(); i++){
            int maximoJogadores = Integer.MAX_VALUE;
            int indice = -1;
            int[][] tabela = elencosFaltantes.get(i).getTabela();
            for (int j= 0; j< tabela.length ;j++) {
                int jogadoresFaltantes = subtraiMaximo(copia.getForragem(), tabela[j]);
                if (jogadoresFaltantes < maximoJogadores) {
                    maximoJogadores = jogadoresFaltantes;
                    indice = j;
                }
            }
            if (indice >= 0) {
                soluções.add(tabela[indice]);
                copia.setForragem(subtraiForragem(copia.getForragem(), tabela[indice]));
            }
        }

        return soluções;
    }

    public Pair<List<int[]>, List<int[]>> completaSolucoes( List<int[]> solucoes, forragens jogadores) {

        List<int[]> elencosPositivo = new ArrayList<>();
        List<int[]> elencosNegativo = new ArrayList<>();

        // Clona forragem única para o processo sequencial
        int[] forragem = jogadores.getForragem().clone();

        for (int[] atual : solucoes) {

            int[] tenho = new int[atual.length];
            int[] faltam = new int[atual.length];

            for (int j = 0; j < atual.length; j++) {

                int falta = atual[j] - forragem[j];

                if (falta <= 0) {
                    // tenho tudo ou mais do que precisava
                    tenho[j] = atual[j];
                    faltam[j] = 0;
                } else {
                    // tenho parcialmente → falta o resto
                    tenho[j] = forragem[j];
                    faltam[j] = falta;
                }

                // consome forragem
                forragem[j] = Math.max(0, forragem[j] - atual[j]);
            }

            elencosPositivo.add(tenho);
            elencosNegativo.add(faltam);
        }

        return Pair.of(elencosPositivo, elencosNegativo);
    }

    public SBC criarSbcRestante(SBC sbc, int[][] melhorResultado) throws Exception {
        if (melhorResultado == null || sbc == null) return null;

        List<elenco> faltantes = new ArrayList<>();
        for (elenco e : sbc.getElencos()) {
            int original = e.getOriginalIndex();
            if (original >= melhorResultado.length || melhorResultado[original] == null) {
                faltantes.add(e);
            }
        }

        if (faltantes.isEmpty()) return null;

        faltantes.sort(Comparator.comparingInt(elenco::getOriginalIndex));
        List<Integer> ovrs = new ArrayList<>();
        for (elenco faltante : faltantes) {
            ovrs.add(faltante.getOVR());
        }

        return criaSBC(faltantes);
    }

    public List<int[]> usarInforms(SBC sbc, forragensTOTW forragensTOTW) {

        List<int[]> informsPorElenco = new ArrayList<>();

        int[] totwDisponivel = forragensTOTW.getForragem().clone(); // copiar para não alterar original
        List<elenco> elencos = sbc.getElencos();

        for (elenco e : elencos) {

            int totwNecessarios = e.getTotw();
            int[] informsUsados = new int[totwDisponivel.length];

            // Se o elenco não está completo → não usar TOTW aqui
            if (totwNecessarios <= 0) {
                informsPorElenco.add(informsUsados);
                continue;
            }

            if (!e.getCompleto()) {
                int[] solucao = e.getSolucaoIncompletaTenho();

                // Tentar encontrar TOTW dentro dos ratings usados pela solução
                for (int rating = 0; rating < solucao.length; rating++) {

                    if (totwNecessarios <= 0) break;

                    boolean solucaoUsaRating = solucao[rating] > 0;
                    boolean tenhoTOTWNoRating = totwDisponivel[rating] > 0;

                    if (solucaoUsaRating && tenhoTOTWNoRating) {

                        // Quantos TOTW podemos usar nesse rating
                        int usarAqui = Math.min(solucao[rating], totwDisponivel[rating]);
                        usarAqui = Math.min(usarAqui, totwNecessarios);

                        informsUsados[rating] = usarAqui;

                        // Atualizar necessidades e estoque
                        totwDisponivel[rating] -= usarAqui;
                        totwNecessarios -= usarAqui;
                    }
                }

                // Salvando lista de informs para esse elenco
                e.setUsoInforms(informsUsados);
                informsPorElenco.add(informsUsados);
                continue;
            }

            int[] solucao = e.getSolucao();

            // Tentar encontrar TOTW dentro dos ratings usados pela solução
            for (int rating = 0; rating < solucao.length; rating++) {

                if (totwNecessarios <= 0) break;

                boolean solucaoUsaRating = solucao[rating] > 0;
                boolean tenhoTOTWNoRating = totwDisponivel[rating] > 0;

                if (solucaoUsaRating && tenhoTOTWNoRating) {

                    // Quantos TOTW podemos usar nesse rating
                    int usarAqui = Math.min(solucao[rating], totwDisponivel[rating]);
                    usarAqui = Math.min(usarAqui, totwNecessarios);

                    informsUsados[rating] = usarAqui;

                    // Atualizar necessidades e estoque
                    totwDisponivel[rating] -= usarAqui;
                    totwNecessarios -= usarAqui;
                }
            }

            // Salvando lista de informs para esse elenco
            e.setUsoInforms(informsUsados);
            informsPorElenco.add(informsUsados);
        }

        return informsPorElenco;
    }

    public SBC CriaSbcPorSet(SbcSet sbcSet){
        List<SbcChallenge> challenges = sbcSet.getChallenges();
        List<elenco> elencos = new ArrayList<>();

        for (SbcChallenge c : challenges){
            int informs = 0;
            int ovr = 0;
            List<SbcRequirement> requirements = c.getRequirements();
            for (SbcRequirement r : requirements) {
                if ("PLAYER_RARITY".equals(r.getType())) {
                    informs = 1;
                }
                if ("TEAM_RATING_1_TO_100".equals(r.getType())) {
                    ovr = r.getEligibilityValue();
                }

            }
            elenco e = elencoService.CriaElenco(ovr, informs);
            System.out.println(e.getOVR());
            elencos.add(e);
        }

        return criaSBC(elencos);
    }



    
}
