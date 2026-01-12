package com.czerner.foddr.adaptadores.apresentação;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.czerner.foddr.adaptadores.apresentação.Requests.SBCRequest;
import com.czerner.foddr.aplicação.BuscarSbcSetUC;
import com.czerner.foddr.aplicação.Responses.SBCResponse;
import com.czerner.foddr.aplicação.Responses.SbcSetResponse;
import com.czerner.foddr.aplicação.SbcMachineSetProntoUC;
import com.czerner.foddr.dominio.entidades.forragens;
import com.czerner.foddr.dominio.entidades.forragensTOTW;

@RestController
@RequestMapping("/sbc-sets")
public class SbcSetController {

    private final BuscarSbcSetUC buscarSbcSetUC;
    private final SbcMachineSetProntoUC sbcMachineSetProntoUC;

    public SbcSetController(BuscarSbcSetUC buscarSbcSetUC, SbcMachineSetProntoUC sbcMachineSetProntoUC) {
        this.buscarSbcSetUC = buscarSbcSetUC;
        this.sbcMachineSetProntoUC = sbcMachineSetProntoUC;
    }

    @GetMapping("/{setId}")
    public ResponseEntity<SbcSetResponse> buscarPorSetId(@PathVariable Integer setId) {
        return buscarSbcSetUC.executar(setId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{setId}/resolver")
    public ResponseEntity<SBCResponse> resolverSet(
            @PathVariable Integer setId,
            @RequestBody SBCRequest request
    ) {
        if (request == null || request.getForragem() == null) {
            throw new IllegalArgumentException("Forragem não informada.");
        }

        int[] forragem = request.getForragem();
        int[] forragemTotw = request.getForragemTotw();
        if (forragemTotw == null || forragemTotw.length == 0) {
            forragemTotw = Arrays.copyOf(forragem, forragem.length);
        }

        forragens jogadores = new forragens(forragem);
        forragensTOTW totw = new forragensTOTW(forragemTotw, true);

        final forragensTOTW finalTotw = totw;
        final forragens finalJogadores = jogadores;

        try {
            SBCResponse response = sbcMachineSetProntoUC.executar(setId, finalJogadores, finalTotw);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao resolver o SBC Set: " + e.getMessage(), e);
        }
    }
}
