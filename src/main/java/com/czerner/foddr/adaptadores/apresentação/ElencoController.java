package com.czerner.foddr.adaptadores.apresentação;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.czerner.foddr.adaptadores.apresentação.presenters.SoluçõesPresenter;
import com.czerner.foddr.aplicação.BuscaMenorPossivelUC;
import com.czerner.foddr.aplicação.Responses.ElencoResponse;


@RestController
@RequestMapping("/elenco")
public class ElencoController {

    private final BuscaMenorPossivelUC buscaMenorPossivelUC;

    public ElencoController(BuscaMenorPossivelUC buscaMenorPossivelUC) {
        this.buscaMenorPossivelUC = buscaMenorPossivelUC;
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
}