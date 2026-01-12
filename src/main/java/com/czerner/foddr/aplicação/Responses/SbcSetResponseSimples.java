package com.czerner.foddr.aplicação.Responses;

import java.time.Instant;

import com.czerner.foddr.dominio.entidades.entidadesData.SbcSet;

public record SbcSetResponseSimples(
    int setId,
    String name,
    Long expiresAt
) {
    public static SbcSetResponseSimples from(SbcSet set) {
        return new SbcSetResponseSimples(
            set.getSetId(),
            set.getName(),
            toEpochSeconds(set.getExpiresAt())
        );
    }

    private static Long toEpochSeconds(Instant instant) {
        return instant != null ? instant.getEpochSecond() : null;
    }
}
