package com.ApiTVSeries;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Main {

    public static void main(String[] args) throws Exception {

        String apiKey = "k_48519ide";
        URI apiIMDB = URI.create("https://imdb-api.com/en/API/Top250TVs/" + apiKey);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(apiIMDB).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        String[] seriesArray = parseJsonSeries(json);

        List<String> titles = parseTitles(seriesArray);
        titles.forEach(System.out::println);

        List<String> ratting = parseRatting(seriesArray);
        ratting.forEach(System.out::println);

        List<String> years = parseYears(seriesArray);
        years.forEach(System.out::println);
    }



    private static String[] parseJsonSeries(String json) {
        Matcher matcher = Pattern.compile(".*\\[(.*)\\].*").matcher(json);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("no match in " + json);
        }

        String[] seriesArray = matcher.group(1).split("\\},\\{");
        seriesArray[0] = seriesArray[0].substring(1);
        int last = seriesArray.length - 1;
        String lastString = seriesArray[last];
        seriesArray[last] = lastString.substring(0, lastString.length() - 1);
        return seriesArray;
    }

    private static List<String> parseTitles(String[] seriesArray) {
        return parseAttribute(seriesArray, 3);
    }

    private static List<String> parseRatting(String[] seriesArray) {
        return parseAttribute(seriesArray, 7);
    }

    private static List<String> parseYears(String[] seriesArray) {
        return parseAttribute(seriesArray, 4);
    }


    private static List<String> parseAttribute(String[] seriesArray, int pos) {
        return Stream.of(seriesArray)
                .map(e -> e.split("\",\"")[pos])
                .map(e -> e.split(":\"")[1])
                .map(e -> e.replaceAll("\"", ""))
                .collect(Collectors.toList());
    }
}