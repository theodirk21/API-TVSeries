package com.ApiTVSeries;

import java.io.PrintWriter;
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
        List<Series> series = new ImdbSerieJsonParser(json).parse();
        System.out.println(series);

        // Gerando o Html
        PrintWriter writer = new PrintWriter("conteudo.html");
        new HtmlGenerator(writer).generate(series);
        writer.close();


    }
}
record Series(String title, String urlImage, String rating, String year) {
    }