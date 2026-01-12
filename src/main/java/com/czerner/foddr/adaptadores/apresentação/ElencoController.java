package com.czerner.foddr.adaptadores.apresentação;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.czerner.foddr.adaptadores.apresentação.Requests.SolucoesPossiveisRequest;
import com.czerner.foddr.adaptadores.apresentação.presenters.SolucoesPossiveisPresenter;
import com.czerner.foddr.adaptadores.apresentação.presenters.SoluçõesPresenter;
import com.czerner.foddr.aplicação.BuscaMenorPossivelUC;
import com.czerner.foddr.aplicação.Responses.ElencoResponse;
import com.czerner.foddr.dominio.entidades.elenco;
import com.czerner.foddr.dominio.entidades.forragens;
import com.czerner.foddr.dominio.serviços.elencoService;


@RestController
@RequestMapping("/elenco")
public class ElencoController {

    private final BuscaMenorPossivelUC buscaMenorPossivelUC;
    private final elencoService elencoService;

    public ElencoController(BuscaMenorPossivelUC buscaMenorPossivelUC, elencoService elencoService) {
        this.buscaMenorPossivelUC = buscaMenorPossivelUC;
        this.elencoService = elencoService;
    }


    // --- GET /elenco/{ovr} ---
    // Busca um elenco pelo OVR
    @GetMapping("/{ovr}/{MIN}/{MAX}")
    public ResponseEntity<SoluçõesPresenter> buscarElenco(@PathVariable int ovr, @PathVariable int MIN, @PathVariable int MAX) {
        ElencoResponse elencoResponse = buscaMenorPossivelUC.executar(ovr, MIN, MAX);
        SoluçõesPresenter resultado = new SoluçõesPresenter(elencoResponse);
        if (resultado.getSoluções() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/solucoes")
    public ResponseEntity<SolucoesPossiveisPresenter> buscarSolucoesPossiveis(
            @RequestBody SolucoesPossiveisRequest request) {
        validarForragem(request.getForragem());

        ElencoResponse elencoResponse = buscaMenorPossivelUC.executar(request.getOvr(),
                request.getMinRating(), request.getMaxRating());
        forragens jogadores = new forragens(request.getForragem());
        elenco elencoCalculado = new elenco(elencoResponse.getOVR(), elencoResponse.getTabela(), 0);
        List<int[]> solucoes = elencoService.SoluçõesPossiveis(elencoCalculado, jogadores);
        return ResponseEntity.ok(new SolucoesPossiveisPresenter(solucoes));
    }

    private void validarForragem(int[] forragem) {
        if (forragem == null || forragem.length != 15) {
            throw new IllegalArgumentException("A lista de forragem deve conter 15 posições (79 a 93).");
        }
    }
}
