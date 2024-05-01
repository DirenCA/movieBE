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

    private static final String API_KEY = System.getenv("MOVIE_DB_API_KEY");
    private HttpClient client;

    public FilmEntry() {
        this.client = HttpClient.newHttpClient();
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
