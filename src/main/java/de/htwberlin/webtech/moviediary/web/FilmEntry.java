package de.htwberlin.webtech.moviediary.web;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;

public class FilmEntry {

    //private static final String API_KEY = System.getenv("MOVIE_DB_API_KEY"); //Hier nehmen wir den Key aus der Umgebungsvariable
    private HttpClient client;
    private static final String API_KEY = "80e985da0462754417cfb28e3bc443a3";

    public FilmEntry() {
        this.client = HttpClient.newHttpClient();
        if (API_KEY == null) {
            throw new IllegalArgumentException("MOVIE_DB_API_KEY is not set in the environment variables");
        }
    }

    public List<String> searchFilmsByQuery(String query) throws IOException, InterruptedException {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&query=" + encodedQuery;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to get response from the movie database API");
        }

        JSONObject jsonResponse = new JSONObject(response.body());
        JSONArray results = jsonResponse.getJSONArray("results");

        return results.toList().stream()
                .map(item -> ((JSONObject)item).getString("title"))
                .collect(Collectors.toList());
    }
}