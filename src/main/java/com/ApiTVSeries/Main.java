package com.ApiTVSeries;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Main {



    public static void main(String[] args) throws Exception {

        // Chamando Api
        String aPIKey = "k_48519ide";
        String json = new ImdbApiClient(aPIKey).getBody();

        // Parsing do Json
        JsonParser jsonParser = new ImdbSerieJsonParser(json);
        List<? extends Conteudo> series = jsonParser.parse();

        // Ordenar
        Collections.sort(series, Comparator.comparing(Conteudo::title));

        // Gerando o Html
        PrintWriter writer = new PrintWriter("SeriesDeTv.html");
        new HtmlGenerator(writer).generate(series);
        writer.close();



    }
}
record Series(String title, String urlImage, String rating, String year) implements Conteudo{
    @Override
       public int compareTo(Conteudo outro) {
        return this.rating().compareTo(outro.rating());
    }
}