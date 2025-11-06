package com.czerner.foddr.dominio.serviços;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.stereotype.Service;

import com.czerner.foddr.aplicação.Responses.SBCResponse;
import com.czerner.foddr.dominio.entidades.SBC;
import com.czerner.foddr.dominio.entidades.elenco;
import com.czerner.foddr.dominio.entidades.forragens;

@Service
public class SBCService {

    private final elencoService elencoService;
    private final List<int[][]> restoreElenco;
    private final AtomicBoolean encontrouSolucao = new AtomicBoolean(false);;

    public SBCService(elencoService elencoService) {
        this.elencoService = elencoService;
        this.restoreElenco = new ArrayList<>();
    }

    public SBC criaSBC(int numElencos, int[] ovrs) throws Exception {
        return new SBC(numElencos, ovrs, elencoService);
    }


    public SBCResponse encontrarSolucao(SBC sbc, forragens jogadores) throws Exception {
        encontrouSolucao.set(false);

        List<int[]> elencosCompletos = new ArrayList<>();
        List<int[]> elencosFaltantes = new ArrayList<>();
        int[] total = null;

        for (elenco e : sbc.getElencos()) {
            List<int[]> novo = elencoService.SoluçõesPossiveis(e, jogadores);
            int[][] tabela = novo.toArray(new int[0][]); // converte List<int[]> para int[][]
            e.setTabela(tabela);
            restoreElenco.add(e.getTabela().clone());
            System.out.println(Arrays.toString(jogadores.getForragem()));
            System.out.println(Arrays.deepToString(e.getTabela()));
        }

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

    private void buscarRecursivo(SBC sbc, forragens jogadores,
                                 List<int[]> elencosCompletos, int maxElencos,
                                 List<int[]> elencosFaltantes) throws Exception {

        if (encontrouSolucao.get()) return;
        System.out.println(jogadores);                            

        List<elenco> elencos = sbc.getElencos();
        

        if (elencos == null || elencos.isEmpty()) return;
        if (elencosCompletos.size() >= elencos.size()) return;
        
        forragens jogadoresOri;
        int[] forragemAnterior = jogadores.getForragem();
        for (int i = 0; i < forragemAnterior.length; i++) {
            if (!elencosCompletos.isEmpty()){
                forragemAnterior[i] += elencosCompletos.get(elencosCompletos.size()-1)[i];
            }
        }
        jogadoresOri = new forragens(forragemAnterior);

        System.out.println("backup:"+ Arrays.deepToString(restoreElenco.get(1)));
        

        boolean encontrado = false;
        int[] jogadoresAtual = new int[jogadores.getForragem().length];
       
        
        int idx = elencosCompletos.size();
        if (idx >= elencos.size()) return;
        
        int[][] tabelaAtual = elencos.get(idx).getTabela();
        System.out.println(idx + " " + tabelaAtual.length + " " + elencosCompletos.size());
        if (tabelaAtual != null || tabelaAtual.length != 0) {
        
            for (int i = 0; i < tabelaAtual.length; i++) {
                
                if (tabelaAtual[i] == null || tabelaAtual[i].length == 0) continue;
                

                boolean possivel = true;
                System.out.println(elencosCompletos.size() + "TENTANDO: " + Arrays.toString(tabelaAtual[i]));
                for (int j = 0; j < jogadores.getForragem().length; j++) {
                    jogadoresAtual[j] = jogadores.getForragem()[j] - tabelaAtual[i][j];
                    if (jogadoresAtual[j] < 0) {
                        possivel = false;
                        break;
                    }
                }

                if (possivel) {
                    System.out.println(elencosCompletos.size() + "✓ Tentativa bem-sucedida: " + Arrays.toString(tabelaAtual[i]));
                    elencosCompletos.add(tabelaAtual[i]);
                    
                    if (elencosCompletos.size() > maxElencos) {
                        maxElencos = elencosCompletos.size();
                    }
                    encontrado = true;

                    if (elencosCompletos.size() < elencos.size()) {
                        System.out.println("out");
                        tabelaAtual[i] = null;

                    } else {
                        break;
                    }

                    forragens novaForragem = new forragens(jogadoresAtual);
                    System.out.println("-----------------------");
                    buscarRecursivo(sbc, novaForragem, elencosCompletos, maxElencos, elencosFaltantes);

                    if (encontrouSolucao.get()) return;
                }
            }
        }

        // backtracking
        if (!encontrado && !elencosCompletos.isEmpty()) {
            elencosCompletos.remove(elencosCompletos.size() - 1);
            System.out.println(idx);
            elencos.get(idx).setTabela(restoreElenco.get(idx).clone());
            buscarRecursivo(sbc, jogadoresOri, elencosCompletos, maxElencos, elencosFaltantes);
            return;
        }

        // caso de subdivisão
        if (!encontrado && elencosCompletos.isEmpty() && !encontrouSolucao.get()) {

            for (int i = maxElencos; i < elencos.size(); i++) {
                int[][] tabela = elencos.get(i).getTabela();
                if (tabela != null && tabela.length > 0) {
                    elencosFaltantes.add(tabela[0]);
                }
            }

            if (maxElencos != 0) { // evita recursão infinita com 1 elenco
                int[] divideElencos = new int[maxElencos];
                for (int i = 0; i < maxElencos; i++) {
                    divideElencos[i] = elencos.get(i).getOVR();
                }
                SBC sbcDividido = new SBC(maxElencos, divideElencos, elencoService);

                for (elenco e : sbcDividido.getElencos()) {
                    List<int[]> novo = elencoService.SoluçõesPossiveis(e, jogadores);
                    int[][] tabela = novo.toArray(new int[0][]); // converte List<int[]> para int[][]
                    e.setTabela(tabela);
                    System.out.println(Arrays.toString(jogadores.getForragem()));
                    System.out.println(Arrays.deepToString(e.getTabela()));
                }

                buscarRecursivo(sbcDividido, jogadoresOri, elencosCompletos, maxElencos, elencosFaltantes);
            }

            encontrouSolucao.set(true);
            return;
        }

        if (encontrado && (elencosCompletos.size() == sbc.getNumElencos())) {
            encontrouSolucao.set(true);
        }
    }
}

