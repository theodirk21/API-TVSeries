package com.ApiTVSeries;

public interface Conteudo extends Comparable<Conteudo>{

    String title();
    String urlImage();
    String rating();
    String year();
}
