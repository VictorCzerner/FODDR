package com.czerner.foddr.adaptadores.apresentação.presenters;

import java.util.List;

import org.springframework.stereotype.Component;

import com.czerner.foddr.aplicação.Responses.SbcCategoryResponse;

@Component
public class SbcCatalogPresenter {

    public List<SbcCategoryResponse> present(List<SbcCategoryResponse> response) {
        // por enquanto é 1:1
        // futuramente pode formatar datas, textos etc.
        return response;
    }
}