package com.ApiMarvel;

import com.ApiTVSeries.Conteudo;
import com.ApiTVSeries.HtmlGenerator;
import com.ApiTVSeries.JsonParser;

import javax.swing.text.AbstractDocument;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception{


        String apiKey = "c5562c1811539ebd0334cc3abd3659f0";
        String privateKey = "cadb27d7691506ee8c430f864064f7ef1c61bc86";
        MarvelApiClient apiClient = new MarvelApiClient(apiKey, privateKey);
        String json = apiClient.getBody();


        JsonParser jsonParser = new MarvelSerieJsonParser(json);
        List<? extends Conteudo> conteudoList = jsonParser.parse();

        Collections.sort(conteudoList, Comparator.comparing(Conteudo::title));

        PrintWriter writer = new PrintWriter("SeriesMarvel.html");
        new HtmlGenerator(writer).generate(conteudoList);
        writer.close();


    }
}

record Serie(String title, String urlImage, String rating, String year) implements Conteudo{
    @Override
    public int compareTo(Conteudo outro) {
        return this.rating().compareTo(outro.rating());
    }
}
