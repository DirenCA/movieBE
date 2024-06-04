package de.htwberlin.webtech.moviediary.web;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

public class FilmEntry {

    private static final String API_KEY = System.getenv("MOVIE_DB_API_KEY"); //Hier nehmen wir den Key aus der Umgebungsvariable
    private HttpClient client;
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"; // Basis-URL für Bilder


    public FilmEntry() {
        this.client = HttpClient.newHttpClient();
        if (API_KEY == null) {
            throw new IllegalArgumentException("MOVIE_DB_API_KEY is not set in the environment variables");
        }
    }

    //public List<String> getPopularFilms() throws IOException, InterruptedException {}

    public List<Film> searchFilmsByQuery(String query) throws IOException, InterruptedException {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&query=" + encodedQuery;

        if (encodedQuery.isEmpty()) {
            throw new IllegalArgumentException("Query is empty");
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to get response from the movie database API");
        }

        //Das JSON-Objekt parsen
        //Durch den Object-Mapper kann ich nun
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.body());
        JsonNode results = root.path("results");

        List<Film> films = new ArrayList<>();
        for (JsonNode result : results) {
            String title = result.path("title").asText();
            String imageUrl = IMAGE_BASE_URL + result.path("poster_path").asText();
            films.add(new Film(title, imageUrl));
        }
        return films;
}

    public class Film {
        private String title;
        private String imageUrl;

        public Film(String title, String imageUrl) {
            this.title = title;
            this.imageUrl = imageUrl;
        }

        // getters and setters
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

}


