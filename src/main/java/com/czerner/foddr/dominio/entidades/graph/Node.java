package com.czerner.foddr.dominio.entidades.graph;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public int level;
    public int id;              // índice dentro do nível
    public int[] cost;          // [c1, c2, c3]
    public List<Node> next = new ArrayList<>();
    
    
}
