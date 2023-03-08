package com.ApiTVSeries;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Main {
    private static final String imdbAPI = "https://imdb-api.com/en/API/Top250Movies/";
    private static final String aPIKey = ("k_48519ide");

    public static void main(String[] args) throws Exception {
        if (aPIKey == null || aPIKey.isEmpty()) {
            throw new IllegalStateException("Chave está vazia");
        }
        final var request = HttpRequest.newBuilder()
                .uri(new URI(imdbAPI + aPIKey))
                .GET()
                .build();

        final var httpClient = HttpClient.newHttpClient();
        final var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());


        final var seriesJson = parseJsonSeries(response.body());
        final var series = seriesJson.stream()
                .map(Series::instanceOf)
                .collect(Collectors.toList());
        System.out.println(series);

    }
    private static List<String> parseJsonSeries(String json) {
        final var contentMatcher = Pattern.compile("\\[(.*?)\\]").matcher(json);
        if (!contentMatcher.find()) {
            throw new IllegalStateException("Data on wrong format");
        }
        var content = contentMatcher.group(0);
        final var moviesMatcher = Pattern.compile("\\{(.*?)\\}").matcher(content);
        var groupIndex = 0;
        return moviesMatcher.results().map(result -> result.group()).collect(Collectors.toList());
    }


}