package com.czerner.foddr.aplicação;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.czerner.foddr.aplicação.Responses.SbcCategoryResponse;
import com.czerner.foddr.aplicação.Responses.SbcSetResponseSimples;
import com.czerner.foddr.dominio.serviços.serviçosData.SbcCatalogService;

@Component
public class ListarCategoriasComSetsUC {

    private final SbcCatalogService service;

    public ListarCategoriasComSetsUC(SbcCatalogService service) {
        this.service = service;
    }

    public List<SbcCategoryResponse> executar() {

        return service.listarCategorias().stream()
            .map(cat -> {
                List<SbcSetResponseSimples> sets = service.listarSetsDaCategoria(cat.getId()).stream()
                    .map(SbcSetResponseSimples::from)
                    .toList();
                if (sets.isEmpty()) {
                    return null;
                }
                return new SbcCategoryResponse(cat.getName(), sets);
            })
            .filter(Objects::nonNull)
            .toList();
    }
}
