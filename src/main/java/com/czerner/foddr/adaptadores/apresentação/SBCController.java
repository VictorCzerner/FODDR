package com.czerner.foddr.adaptadores.apresentação;

import com.czerner.foddr.aplicação.Responses.SBCResponse;
import com.czerner.foddr.dominio.entidades.SBC;
import com.czerner.foddr.dominio.entidades.forragens;
import com.czerner.foddr.dominio.serviços.SBCService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sbc")
public class SBCController {

    private final SBCService sbcService;

    public SBCController(SBCService sbcService) {
        this.sbcService = sbcService;
    }

    /**
     * Testa a execução do método encontrarSolucao()
     *
     * Exemplo de POST:
     * {
     *   "sbc": {
     *     "numElencos": 3,
     *     "ovrs": [84,85,86]
     *   },
     *   "forragem": [2,1,0,3,4,5,1,0,0,0,0,0,0,0,0]
     * }
     */
    @PostMapping("/encontrar")
    public SBCResponse encontrar(@RequestBody SBCRequest request) {
        try {
            SBC sbc = sbcService.criaSBC(request.getSbc().getNumElencos(), request.getSbc().getOvrs());
            forragens jogadores = new forragens(request.getForragem());
            return sbcService.encontrarSolucao(sbc, jogadores);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar a busca: " + e.getMessage(), e);
        }
    }

    // DTO (estrutura do JSON de entrada)
    public static class SBCRequest {
        private SBCData sbc;
        private int[] forragem;

        public SBCData getSbc() { return sbc; }
        public void setSbc(SBCData sbc) { this.sbc = sbc; }

        public int[] getForragem() { return forragem; }
        public void setForragem(int[] forragem) { this.forragem = forragem; }
    }

    public static class SBCData {
        private int numElencos;
        private int[] ovrs;

        public int getNumElencos() { return numElencos; }
        public void setNumElencos(int numElencos) { this.numElencos = numElencos; }

        public int[] getOvrs() { return ovrs; }
        public void setOvrs(int[] ovrs) { this.ovrs = ovrs; }
    }
}
