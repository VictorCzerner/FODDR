package com.czerner.foddr.adaptadores.apresentação;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.czerner.foddr.adaptadores.apresentação.presenters.SbcCatalogPresenter;
import com.czerner.foddr.aplicação.ListarCategoriasComSetsUC;
import com.czerner.foddr.aplicação.Responses.SbcCategoryResponse;

@RestController
@RequestMapping("/api/sbc")
public class SbcCatalogController {

    private final ListarCategoriasComSetsUC uc;
    private final SbcCatalogPresenter presenter;

    public SbcCatalogController(
        ListarCategoriasComSetsUC uc,
        SbcCatalogPresenter presenter
    ) {
        this.uc = uc;
        this.presenter = presenter;
    }

    @GetMapping("/categories")
    public List<SbcCategoryResponse> listar() {
        return presenter.present(uc.executar());
    }
}
