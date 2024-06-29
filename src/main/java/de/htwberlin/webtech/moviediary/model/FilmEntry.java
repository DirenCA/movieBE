package de.htwberlin.webtech.moviediary.model;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

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

    private static final String API_KEY = System.getenv("MOVIE_DB_API_KEY"); // Hier nehmen wir den Key aus der Umgebungsvariable
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"; // Basis-URL für Bilder

    @Autowired
    public FilmEntry(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
        this.client = HttpClient.newHttpClient();
        if (API_KEY == null) {
            throw new IllegalArgumentException("MOVIE_DB_API_KEY is not set in the environment variables");
        }
    }

    public List<Film> getDiscoverFilms(int page) throws IOException, InterruptedException {
        return fetchFilmsFromApi("https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US&page=" + page);
    }

    public List<Film> getPopularFilms() throws IOException, InterruptedException {
        return fetchFilmsFromApi("https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY + "&language=en-US&page=1");
    }

    public List<Film> getTopRatedFilms() throws IOException, InterruptedException {
        return fetchFilmsFromApi("https://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY + "&language=en-US&page=1");
    }

    public List<Film> getUpcomingFilms() throws IOException, InterruptedException {
        return fetchFilmsFromApi("https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY + "&language=en-US&page=1");
    }

    public List<Film> searchFilmsByQuery(String query) throws IOException, InterruptedException {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&query=" + encodedQuery;

        if (encodedQuery.isEmpty()) {
            throw new IllegalArgumentException("Query is empty");
        }

        return fetchFilmsFromApi(url);
    }

    private Map<Integer, String> fetchGenresFromApi() throws IOException, InterruptedException {
        String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + API_KEY + "&language=en-US";
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
        JsonNode genres = root.path("genres");

        Map<Integer, String> genreMap = new HashMap<>();
        for (JsonNode genre : genres) {
            int id = genre.path("id").asInt();
            String name = genre.path("name").asText();
            genreMap.put(id, name);
        }

        return genreMap;
    }

    private List<Film> fetchFilmsFromApi(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to get response from the movie database API");
        }

        Map<Integer, String> genreMap = fetchGenresFromApi(); // Abrufen Genre-Liste

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.body());
        JsonNode results = root.path("results");

        List<Film> films = new ArrayList<>();
        for (JsonNode result : results) {
            long id = result.path("id").asLong();
            String title = truncate(result.path("title").asText(), 255);
            String imageUrl = truncate(IMAGE_BASE_URL + result.path("poster_path").asText(), 255);
            String overview = truncate(result.path("overview").asText(), 255);
            String releaseDate = truncate(result.path("release_date").asText(), 255);
            String voteAverage = truncate(result.path("vote_average").asText(), 255);
            String genreIds = result.path("genre_ids").asText();
            String[] ids = genreIds.split(",");
            List<String> genreNames = new ArrayList<>();
            for (String genreIdStr : ids) {
                int genreId = Integer.parseInt(genreIdStr.trim());
                String genreName = genreMap.get(genreId);
                if (genreName != null) {
                    genreNames.add(genreName);
                }
            }
            String genre = String.join(", ", genreNames);
            Film film = new Film(id, title, imageUrl, overview, releaseDate, voteAverage, genre);

            // Prüfen, ob der Film bereits in der Datenbank vorhanden ist
            if (!filmRepository.existsById(id)) {
                filmRepository.save(film);
                System.out.println("Saved film: " + film.getTitle()); // Debugging line
            }

            films.add(film);
        }
        System.out.println("Total films fetched: " + films.size()); // Debugging line

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
        private long id; // Verwende die TMDb-Film-ID als Primärschlüssel
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

        //Gibt die Film-Objekte als String zurück. Zum Prüfen, ob die Filme korrekt gespeichert wurden.
        @Override
        public String toString() {
            return "FilmEntry.Film{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", imageUrl='" + imageUrl + '\'' +
                    ", release='" + releaseDate + '\'' +
                    '}';
        }

        public Film(long id, String title, String imageUrl, String overview, String releaseDate, String voteAverage, String genre) {
            this.id = id;
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
