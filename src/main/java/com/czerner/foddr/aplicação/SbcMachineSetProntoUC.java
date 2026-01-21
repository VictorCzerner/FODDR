package com.czerner.foddr.aplicação;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.czerner.foddr.aplicação.Responses.SBCResponse;
import com.czerner.foddr.dominio.entidades.SBC;
import com.czerner.foddr.dominio.entidades.forragens;
import com.czerner.foddr.dominio.entidades.forragensTOTW;
import com.czerner.foddr.dominio.entidades.entidadesData.SbcSet;
import com.czerner.foddr.dominio.serviços.SBCService;
import com.czerner.foddr.dominio.serviços.serviçosData.SbcSetService;

@Component
public class SbcMachineSetProntoUC {
    private final SBCService SbcService;
    private final SbcSetService SbcSetService;
    private final SbcMachineUC SbcMachineUC;

    public SbcMachineSetProntoUC(SBCService SbcService, SbcMachineUC SbcMachineUC, SbcSetService SbcSetService){
        this.SbcService = SbcService;
        this.SbcMachineUC = SbcMachineUC;
        this.SbcSetService = SbcSetService;
    }

    public SBCResponse executar(
            int setId,
            forragens jogadores,
            forragensTOTW forragensTOTW,
            List<Integer> completedChallengeIds
    ) throws Exception {

        SbcSet sbcSet = SbcSetService.buscarPorSetId(setId)
        .orElseThrow(() -> new RuntimeException("SBC Set não encontrado: " + setId));
        if (completedChallengeIds != null && !completedChallengeIds.isEmpty()) {
            sbcSet.setChallenges(
                sbcSet.getChallenges().stream()
                    .filter(challenge -> !completedChallengeIds.contains(challenge.getChallengeId()))
                    .collect(Collectors.toList())
            );
        }

        SBC sbc = SbcService.CriaSbcPorSet(sbcSet);
        return SbcMachineUC.executar(sbc, jogadores, forragensTOTW);
    }

}
