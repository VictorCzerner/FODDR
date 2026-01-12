package com.czerner.foddr.adaptadores.apresentação;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.czerner.foddr.adaptadores.apresentação.Requests.SBCRequest;
import com.czerner.foddr.aplicação.Responses.SBCResponse;
import com.czerner.foddr.aplicação.SbcMachineUC;
import com.czerner.foddr.dominio.entidades.SBC;
import com.czerner.foddr.dominio.entidades.elenco;
import com.czerner.foddr.dominio.entidades.forragens;
import com.czerner.foddr.dominio.entidades.forragensTOTW;
import com.czerner.foddr.dominio.serviços.SBCService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/sbc")
public class SBCController {

    private final SBCService sbcService;
    private final SbcMachineUC sbcMachineUC;

    public SBCController(SBCService sbcService) {
        this.sbcService = sbcService;
        this.sbcMachineUC = new SbcMachineUC(sbcService);
    }

    /**
     * Testa a execução do método encontrarSolucao()
     *
     * Exemplo de POST:
     * {
     *   "sbc": {
     *     "elencos": [
     *       { "ovr": 84, "informs": 0 },
     *       { "ovr": 85, "informs": 1 }
     *     ]
     *   },
     *   "forragem": [2,1,0,3,4,5,1,0,0,0,0,0,0,0,0]
     * }
     */
    @PostMapping("/encontrar")
    public SBCResponse encontrar(@RequestBody SBCRequest request) {
        try {
            SBCRequest.SBCData sbcData = request.getSbc();
            if (sbcData == null || sbcData.getElencos() == null || sbcData.getElencos().isEmpty()) {
                throw new IllegalArgumentException("Lista de elencos do SBC não informada.");
            }

            List<SBCRequest.ElencoData> elencosRequest = sbcData.getElencos();
            List<Integer> ovrs = new ArrayList<>(elencosRequest.size());
            List<Integer> informsPorElenco = new ArrayList<>(elencosRequest.size());

            for (SBCRequest.ElencoData elencoData : elencosRequest) {
                if (elencoData == null) {
                    throw new IllegalArgumentException("Dados do elenco ausentes.");
                }
                int ovr = elencoData.getOvr();
                if (ovr <= 0) {
                    throw new IllegalArgumentException("Informe o OVR de cada elenco.");
                }
                ovrs.add(ovr);
                informsPorElenco.add(Math.max(0, elencoData.getInforms()));
            }

            SBC sbc = sbcService.criaSbcPorOvrs(ovrs);

            int[] forragem = request.getForragem();
            int[] forragemTotw = request.getForragemTotw();

            if (forragem == null) {
                throw new IllegalArgumentException("Forragem não informada.");
            }

            if (forragemTotw == null || forragemTotw.length == 0) {
                forragemTotw = Arrays.copyOf(forragem, forragem.length);
            }

            forragens jogadores = new forragens(forragem);
            forragensTOTW totw = new forragensTOTW(forragemTotw, true);

            List<elenco> elencos = sbc.getElencos();
            for (int i = 0; i < elencos.size(); i++) {
                int valor = i < informsPorElenco.size() ? informsPorElenco.get(i) : 0;
                elencos.get(i).setTotw(valor);
            }

            return sbcMachineUC.executar(sbc, jogadores, totw);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar a busca: " + e.getMessage(), e);
        }
    }

}
