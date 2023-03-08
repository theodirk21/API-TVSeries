package com.ApiTVSeries;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Series {

    private String id;
    private String rank;
    private String title;
    private String fullTitle;
    private String year;
    private String image;
    private List<String> crew;
    private String imdbRatingCount;

    private static String parseImdbRatingCount(String movie) {
        return parseAttribute("imDbRatingCount", movie);
    }

    private static List<String> parseCrew(String movie) {
        return Arrays.asList(parseAttribute("crew", movie).split(","));
    }

    private static String parseImages(String movie) {
        return parseAttribute("image", movie);
    }

    private static String parseYears(String movie) {
        return parseAttribute("year", movie);
    }

    private static String parseFullTitle(String movie) {
        return parseAttribute("fullTitle", movie);
    }

    private static String parseTitle(String movie) {
        return parseAttribute("title", movie);
    }

    private static String parseRank(String movie) {
        return parseAttribute("rank", movie);
    }

    private static String parseId(String movie) {
        return parseAttribute("id", movie);
    }
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Series{ ");
        sb.append(" Código ='").append(id).append('\'');
        sb.append(", Rank='").append(rank).append('\'');
        sb.append(", Título='").append(title).append('\'');
        sb.append(", Título Completo='").append(fullTitle).append('\'');
        sb.append(", Ano='").append(year).append('\'');
        sb.append(", Imagem='").append(image).append('\'');
        sb.append(", Atores=").append(crew);
        sb.append(", Ratting='").append(imdbRatingCount).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static Series instanceOf(String serieAsJson) {
        var newSerie = new Series();
        newSerie.id = parseId(serieAsJson);
        newSerie.rank = parseRank(serieAsJson);
        newSerie.title = parseTitle(serieAsJson);
        newSerie.fullTitle = parseFullTitle(serieAsJson);
        newSerie.year = parseYears(serieAsJson);
        newSerie.image = parseImages(serieAsJson);
        newSerie.crew = parseCrew(serieAsJson);
        newSerie.imdbRatingCount = parseImdbRatingCount(serieAsJson);
        return newSerie;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Series series = (Series) o;
        return Objects.equals(id, series.id) && Objects.equals(rank, series.rank) && Objects.equals(title, series.title) && Objects.equals(fullTitle, series.fullTitle) && Objects.equals(year, series.year) && Objects.equals(image, series.image) && Objects.equals(crew, series.crew) && Objects.equals(imdbRatingCount, series.imdbRatingCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rank, title, fullTitle, year, image, crew, imdbRatingCount);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getCrew() {
        return crew;
    }

    public void setCrew(List<String> crew) {
        this.crew = crew;
    }

    public String getImdbRatingCount() {
        return imdbRatingCount;
    }

    public void setImdbRatingCount(String imdbRatingCount) {
        this.imdbRatingCount = imdbRatingCount;
    }

    private static String parseAttribute(String attribute, String serie) {
       var matcher = Pattern.compile("\""+attribute+"\":\"(.*?)\"").matcher(serie);
        matcher.find();
        return matcher.group(1);
    }
}
