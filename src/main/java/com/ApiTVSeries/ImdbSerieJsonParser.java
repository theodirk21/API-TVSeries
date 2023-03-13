package com.ApiTVSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImdbSerieJsonParser {

    private  String json;
    public ImdbSerieJsonParser(String json) {
        this.json = json;
    }

        public List<Series> parse() {
            String[] seriesArray = parseJsonSeries(json);

            List<String> titles = parseTitles(seriesArray);
            List<String> urlImages = parseUrlImages(seriesArray);
            List<String> ratings = parseRatings(seriesArray);
            List<String> years = parseYears(seriesArray);

            List<Series> series = new ArrayList<>();

            for (int i =0; i < titles.size(); i++) {
                series.add(new Series(titles.get(i), urlImages.get(i) , ratings.get(i), years.get(i)));
            }
            return series;
        }

        private  String[] parseJsonSeries(String json) {
            Matcher matcher = Pattern.compile(".*\\[(.*)\\].*").matcher(json);

            if (!((Matcher) matcher).matches()) {
                throw new IllegalArgumentException("no match in " + json);
            }

            String[] moviesArray = matcher.group(1).split("\\},\\{");
            moviesArray[0] = moviesArray[0].substring(1);
            int last = moviesArray.length - 1;
            String lastString = moviesArray[last];
            moviesArray[last] = lastString.substring(0, lastString.length() - 1);
            return moviesArray;
        }

        private  List<String> parseTitles(String[] seriesArray) {
            return parseAttribute(seriesArray, 3);
        }

        private  List<String> parseUrlImages(String[] seriesArray) {
            return parseAttribute(seriesArray, 5);
        }

        private  List<String> parseRatings(String[] seriesArray) {
            return parseAttribute(seriesArray, 7);
        }

        private  List<String> parseYears(String[] seriesArray) {
            return parseAttribute(seriesArray, 4);
        }


        private  List<String> parseAttribute(String[] jsonMovies, int pos) {
            return Stream.of(jsonMovies)
                    .map(e -> e.split("\",\"")[pos])
                    .map(e -> e.split(":\"")[1])
                    .map(e -> e.replaceAll("\"", ""))
                    .collect(Collectors.toList());
        }
}

