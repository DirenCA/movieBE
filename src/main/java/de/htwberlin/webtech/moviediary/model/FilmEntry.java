package de.htwberlin.webtech.moviediary.model;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htwberlin.webtech.moviediary.repository.FilmRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmEntry {

    private final FilmRepository filmRepository;
    private final HttpClient client;

    private static final String API_KEY = System.getenv("MOVIE_DB_API_KEY"); //Hier nehmen wir den Key aus der Umgebungsvariable
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"; // Basis-URL für Bilder

    @Autowired
    public FilmEntry(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
        this.client = HttpClient.newHttpClient();
        if (API_KEY == null) {
            throw new IllegalArgumentException("MOVIE_DB_API_KEY is not set in the environment variables");
        }
    }


    public List<Film> getPopularFilms() throws IOException, InterruptedException {
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY + "&language=en-US&page=1";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to get response from the movie database API");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.body());
        JsonNode results = root.path("results");

        List<Film> films = new ArrayList<>();
        for (JsonNode result : results) {
            String title = truncate(result.path("title").asText(), 255);
            String imageUrl = truncate(IMAGE_BASE_URL + result.path("poster_path").asText(), 255);
            String overview = truncate(result.path("overview").asText(), 255);
            String releaseDate = truncate(result.path("release_date").asText(), 255);
            String voteAverage = truncate(result.path("vote_average").asText(), 255);
            String genre = truncate(result.path("genre_ids").asText(), 255);
            Film film = new Film(title, imageUrl, overview, releaseDate, voteAverage, genre);
            films.add(film);
            filmRepository.save(film);

            System.out.println("Saved film: " + film.getTitle()); // Debugging line
        }
        System.out.println("Total films fetched: " + films.size()); // Debugging line

        return films;
    }

    public List<Film> getTopRatedFilms() throws IOException, InterruptedException {
    String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY + "&language=en-US&page=1";

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() != 200) {
        throw new IOException("Failed to get response from the movie database API");
    }

    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode root = objectMapper.readTree(response.body());
    JsonNode results = root.path("results");

    List<Film> films = new ArrayList<>();
    for (JsonNode result : results) {
        String title = truncate(result.path("title").asText(), 255);
        String imageUrl = truncate(IMAGE_BASE_URL + result.path("poster_path").asText(), 255);
        String overview = truncate(result.path("overview").asText(), 255);
        String releaseDate = truncate(result.path("release_date").asText(), 255);
        String voteAverage = truncate(result.path("vote_average").asText(), 255);
        String genre = truncate(result.path("genre_ids").asText(), 255);
        Film film = new Film(title, imageUrl, overview, releaseDate, voteAverage, genre);
        films.add(film);
        filmRepository.save(film);

        System.out.println("Saved film: " + film.getTitle()); // Debugging line
    }
    System.out.println("Total films fetched: " + films.size()); // Debugging line

    return films;
}

    public List<Film> getUpcomingFilms() throws IOException, InterruptedException {
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY + "&language=en-US&page=1";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to get response from the movie database API");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.body());
        JsonNode results = root.path("results");

        List<Film> films = new ArrayList<>();
        for (JsonNode result : results) {
            String title = truncate(result.path("title").asText(), 255);
            String imageUrl = truncate(IMAGE_BASE_URL + result.path("poster_path").asText(), 255);
            String overview = truncate(result.path("overview").asText(), 255);
            String releaseDate = truncate(result.path("release_date").asText(), 255);
            String voteAverage = truncate(result.path("vote_average").asText(), 255);
            String genre = truncate(result.path("genre_ids").asText(), 255);
            Film film = new Film(title, imageUrl, overview, releaseDate, voteAverage, genre);
            films.add(film);
            filmRepository.save(film);

            System.out.println("Saved film: " + film.getTitle()); // Debugging line
        }
        System.out.println("Total films fetched: " + films.size()); // Debugging line

        return films;
    }

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
            String title = truncate(result.path("title").asText(), 255);
            String imageUrl = truncate(IMAGE_BASE_URL + result.path("poster_path").asText(), 255);
            String overview = truncate(result.path("overview").asText(), 255);
            String releaseDate = truncate(result.path("release_date").asText(), 255);
            String voteAverage = truncate(result.path("vote_average").asText(), 255);
            String genre = truncate(result.path("genre_ids").asText(), 255);
            films.add(new Film(title, imageUrl, overview, releaseDate, voteAverage, genre));
        }
        return films;
    }

    private String truncate(String value, int length) {
        if (value == null) return null;
        return value.length() > length ? value.substring(0, length) : value;
    }

    @Entity
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    public static class Film {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String title;
        private String imageUrl;
        private String overview;
        private String releaseDate;
        private String voteAverage;
        private String genre;

        @ManyToMany(mappedBy = "films")
        private Set<Watchlist> watchlists = new HashSet<>();

        @OneToMany(mappedBy = "film")
        private Set<Rating> ratings = new HashSet<>();

        public Film(String title, String imageUrl, String overview, String releaseDate, String voteAverage, String genre) {
            this.title = title;
            this.imageUrl = imageUrl;
            this.overview = overview;
            this.releaseDate = releaseDate;
            this.voteAverage = voteAverage;
            this.genre = genre;
        }

        public Film() {}

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

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

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getVoteAverage() {
            return voteAverage;
        }

        public void setVoteAverage(String voteAverage) {
            this.voteAverage = voteAverage;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public Set<Watchlist> getWatchlists() {
            return watchlists;
        }

        public void setWatchlists(Set<Watchlist> watchlists) {
            this.watchlists = watchlists;
        }
    }
}
