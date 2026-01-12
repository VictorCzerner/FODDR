package com.czerner.foddr.dominio.serviços;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import com.czerner.foddr.dominio.entidades.elenco;
import com.czerner.foddr.dominio.entidades.forragens;

class SBCServiceTest {

    @Test
    void elencosIncompletosSelecionaMelhorCombinacao() throws Exception {
        SBCService service = new SBCService(null, null);

        int[] forragem = {10, 5, 0, 3};
        forragens jogadores = new forragens(forragem);

        int[][] tabela = {
                {5, 2, 0, 1},
                {10, 0, 0, 3},
                {7, 5, 0, 0}
        };

        elenco elencoTeste = new elenco(0, null, 0);
        elencoTeste.setTabela(tabela);

        List<int[]> resultado = service.elencosIncompletos(List.of(elencoTeste), jogadores);

        assertEquals(1, resultado.size());
        assertArrayEquals(new int[]{5, 2, 0, 1}, resultado.get(0));
    }

    @Test
    void completaSolucoesQuandoForragemSuficiente() throws Exception {
        SBCService service = new SBCService(null, null);

        List<int[]> solucoes = Arrays.asList(
                new int[]{1, 0, 0},
                new int[]{0, 2, 1}
        );
        forragens jogadores = new forragens(new int[]{5, 5, 5});

        Pair<List<int[]>, List<int[]>> resultado = service.completaSolucoes(solucoes, jogadores);

        assertListEquals(Arrays.asList(
                new int[]{1, 0, 0},
                new int[]{0, 2, 1}
        ), resultado.getLeft());
        assertListEquals(Arrays.asList(
                new int[]{0, 0, 0},
                new int[]{0, 0, 0}
        ), resultado.getRight());
    }

    @Test
    void completaSolucoesQuandoForragemInsuficiente() throws Exception {
        SBCService service = new SBCService(null, null);

        List<int[]> solucoes = Arrays.asList(
                new int[]{1, 1, 0},
                new int[]{2, 1, 1}
        );
        forragens jogadores = new forragens(new int[]{2, 1, 0});

        Pair<List<int[]>, List<int[]>> resultado = service.completaSolucoes(solucoes, jogadores);

        assertListEquals(Arrays.asList(
                new int[]{1, 1, 0},
                new int[]{1, 0, 0}
        ), resultado.getLeft());
        assertListEquals(Arrays.asList(
                new int[]{0, 0, 0},
                new int[]{1, 1, 1}
        ), resultado.getRight());
    }

    private void assertListEquals(List<int[]> esperado, List<int[]> atual) {
        assertEquals(esperado.size(), atual.size(), "List sizes differ");
        for (int i = 0; i < esperado.size(); i++) {
            assertArrayEquals(esperado.get(i), atual.get(i), "Mismatch at index " + i);
        }
    }
}
