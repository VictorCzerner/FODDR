package com.czerner.foddr.aplicação.Responses;

import java.util.List;

public record SbcCategoryResponse(
    String category,
    List<SbcSetResponseSimples> sets
) {}
