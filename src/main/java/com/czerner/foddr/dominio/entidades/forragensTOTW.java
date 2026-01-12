package com.czerner.foddr.dominio.entidades;

public class forragensTOTW extends forragens{

    private boolean ehInform;

    public forragensTOTW(int[] forragem, boolean ehInform) {
        super(forragem);
        this.ehInform=ehInform;
    }

    public boolean isEhInform() {
        return ehInform;
    }

    public void setEhInform(boolean ehInform) {
        this.ehInform = ehInform;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("forragensTOTW{");
        sb.append("ehInform=").append(ehInform);
        sb.append('}');
        return sb.toString();
    }
    
}
