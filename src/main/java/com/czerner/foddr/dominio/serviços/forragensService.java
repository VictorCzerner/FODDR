package com.czerner.foddr.dominio.serviços;

import com.czerner.foddr.dominio.entidades.forragens;
import org.springframework.stereotype.Service;

@Service
public class forragensService {

    /**
     * Cria uma nova instância de forragens a partir de um array com 15 valores.
     * Cada posição representa a quantidade de cartas (ou jogadores) com aquele OVR.
     */
    public forragens criarForragem(int[] forragemArray) {
        if (forragemArray == null || forragemArray.length != 15) {
            throw new IllegalArgumentException("O array de forragem deve conter exatamente 15 posições (79 a 93).");
        }
        return new forragens(forragemArray);
    }

    /**
     * Soma duas instâncias de forragens (útil quando queremos combinar inventários).
     */
    public forragens somarForragens(forragens a, forragens b) {
        int[] resultado = new int[15];
        int[] fA = a.getForragem();
        int[] fB = b.getForragem();

        for (int i = 0; i < 15; i++) {
            resultado[i] = fA[i] + fB[i];
        }

        return new forragens(resultado);
    }

    /**
     * Subtrai uma forragem de outra (ex: removendo os jogadores usados em um elenco).
     */
    public forragens subtrairForragens(forragens base, forragens usada) {
        int[] resultado = new int[15];
        int[] fBase = base.getForragem();
        int[] fUsada = usada.getForragem();

        for (int i = 0; i < 15; i++) {
            int valor = fBase[i] - fUsada[i];
            if (valor < 0) {
                throw new IllegalArgumentException("Não é possível subtrair mais do que a quantidade disponível (índice " + i + ")");
            }
            resultado[i] = valor;
        }

        return new forragens(resultado);
    }

    /**
     * Imprime uma forragem no formato legível (79 a 93).
     */
    public void exibirForragem(forragens f) {
        System.out.println("==== FORRAGEM ATUAL (79 a 93) ====");
        int[] data = f.getForragem();
        for (int i = 0; i < data.length; i++) {
            System.out.printf("%d: %d%n", 79 + i, data[i]);
        }
        System.out.println("=================================");
    }
}
