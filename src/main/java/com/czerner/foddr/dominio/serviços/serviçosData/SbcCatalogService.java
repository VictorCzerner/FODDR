package com.czerner.foddr.dominio.serviços.serviçosData;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.czerner.foddr.dominio.dados.SbcCategoryRepository;
import com.czerner.foddr.dominio.dados.SbcSetRepository;
import com.czerner.foddr.dominio.entidades.entidadesData.SbcCategory;
import com.czerner.foddr.dominio.entidades.entidadesData.SbcSet;

@Service
public class SbcCatalogService {

    private static final int MIN_RATING = 82;
    private static final int MAX_RATING = 99;

    private final SbcCategoryRepository categoryRepo;
    private final SbcSetRepository setRepo;

    public SbcCatalogService(
        SbcCategoryRepository categoryRepo,
        SbcSetRepository setRepo
    ) {
        this.categoryRepo = categoryRepo;
        this.setRepo = setRepo;
    }

    public List<SbcCategory> listarCategorias() {
        return categoryRepo.buscarTodasCategorias();
    }

    public List<SbcSet> listarSetsDaCategoria(int categoryId) {
        return setRepo.findByCategoryWithRatingRequirementBetween(
            categoryId,
            MIN_RATING,
            MAX_RATING,
            Instant.now()
        );
    }
}
