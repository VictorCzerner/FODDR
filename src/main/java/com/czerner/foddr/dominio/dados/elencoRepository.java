package com.czerner.foddr.dominio.dados;

import com.czerner.foddr.dominio.entidades.elenco;

public interface elencoRepository {

    elenco AdicionaElenco(elenco elenco);
    elenco buscaPorOvr(int ovr);

}
