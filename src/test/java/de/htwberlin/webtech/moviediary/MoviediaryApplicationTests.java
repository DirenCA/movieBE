package de.htwberlin.webtech.moviediary;

import de.htwberlin.webtech.moviediary.controller.MyController;
import de.htwberlin.webtech.moviediary.controller.UserController;
import de.htwberlin.webtech.moviediary.model.FilmEntry;
import de.htwberlin.webtech.moviediary.model.FilmUser;
import de.htwberlin.webtech.moviediary.service.FilmService;
import de.htwberlin.webtech.moviediary.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class MoviediaryApplicationTests {

	@MockBean
	private UserService userService;

	@Autowired
	private UserController userController;

	@Test
	void testRegisterUser() {
		String userName = "Test User";
		String password = "Test Password";

		FilmUser mockUser = new FilmUser();
		mockUser.setUserName(userName);
		mockUser.setPassword(password);

		when(userService.registerUser(userName, password)).thenReturn(mockUser);

		ResponseEntity<FilmUser> response = userController.registerUser(mockUser);
		FilmUser result = response.getBody();

		assertEquals(mockUser, result);
		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	void testLoginUser() {
		String userName = "Test User";
		String password = "Test Password";

		FilmUser mockUser = new FilmUser();
		mockUser.setUserName(userName);
		mockUser.setPassword(password);

		when(userService.loginUser(userName, password)).thenReturn(mockUser);

		ResponseEntity<FilmUser> response = userController.loginUser(mockUser);
		FilmUser result = response.getBody();

		assertEquals(mockUser, result);
		assertEquals(200, response.getStatusCodeValue());
	}

	@MockBean
	private FilmEntry filmService;

	@Autowired
	private MyController filmController;

	@Test
	void testGetFilm() {
		int filmId = 1;
		FilmEntry.Film mockFilm = new FilmEntry.Film(filmId, "Titel", "imageUrl", "overview", "releaseDate", "voteAverage", "genre");

		when(filmService.getFilm(filmId)).thenReturn(mockFilm);

		ResponseEntity<FilmEntry.Film> response = filmController.getFilm(filmId);
		FilmEntry.Film result = response.getBody();

		assertEquals(mockFilm, result);
		assertEquals(200, response.getStatusCodeValue());
	}

}
