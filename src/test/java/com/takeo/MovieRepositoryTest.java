package com.takeo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.takeo.model.Movie;
import com.takeo.repo.MovieRepository;

@DataJpaTest
public class MovieRepositoryTest {
	
	@Autowired
	private MovieRepository movieRepo;
	
	@Test
	@DisplayName("It should save the movie to the database")
	void save()
	{
		Movie avatarMovie=new Movie();
		avatarMovie.setName("Avatar");
		avatarMovie.setGenre("Action");
		avatarMovie.setReleaseDate(LocalDate.of(2009, Month.MARCH, 23));
		
		//calling the method or unit that is being tested
		Movie newMovie=movieRepo.save(avatarMovie);
		
		//verify whether the expected behavior is correct or not
		assertNotNull(newMovie);
		assertThat(newMovie.getId()).isNotEqualTo(null);
	}
	
	@Test
	@DisplayName("It should return all movies")
	void getAllMovies()
	{
		Movie avatarMovie=new Movie();
		avatarMovie.setName("Avatar");
		avatarMovie.setGenre("Action");
		avatarMovie.setReleaseDate(LocalDate.of(2009, Month.MARCH, 23));
		movieRepo.save(avatarMovie);
		
		Movie titanicMovie=new Movie();
		titanicMovie.setName("Titanic");
		titanicMovie.setGenre("Romantic");
		titanicMovie.setReleaseDate(LocalDate.of(1998, Month.DECEMBER, 5));
		movieRepo.save(titanicMovie);
		
		List<Movie> list= movieRepo.findAll();
		
		assertNotNull(list);
		assertThat(list).isNotNull();
		assertEquals(2, list.size());
	}
	
	void getMovieById()
	{

		Movie avatarMovie=new Movie();
		avatarMovie.setName("Avatar");
		avatarMovie.setGenre("Action");
		avatarMovie.setReleaseDate(LocalDate.of(2009, Month.MARCH, 23));
		movieRepo.save(avatarMovie);
		
		Movie newMovie=movieRepo.findById(avatarMovie.getId()).get();
		
		assertNotNull(newMovie);
		assertEquals("Action", newMovie.getGenre());
		assertThat(newMovie.getReleaseDate()).isBefore(LocalDate.of(2009, Month.MARCH, 23));
	}
	
	@Test
	@DisplayName("It should return the movies list with gernre romance")
	void getMovieByGenre()
	{

		Movie avatarMovie=new Movie();
		avatarMovie.setName("Avatar");
		avatarMovie.setGenre("Action");
		avatarMovie.setReleaseDate(LocalDate.of(2009, Month.MARCH, 23));
		movieRepo.save(avatarMovie);
		
		Movie titanicMovie=new Movie();
		titanicMovie.setName("Titanic");
		titanicMovie.setGenre("Romantic");
		titanicMovie.setReleaseDate(LocalDate.of(1998, Month.DECEMBER, 5));
		movieRepo.save(titanicMovie);
		
		List<Movie> list=movieRepo.findByGenre("Romantic");
		
		assertNotNull(list);
		assertThat(list.size()).isEqualTo(1);
	}
	
	@Test
	@DisplayName("It should update the movie genre with Fantacy")
	void updateMovie()
	{

		Movie avatarMovie=new Movie();
		avatarMovie.setName("Avatar");
		avatarMovie.setGenre("Action");
		avatarMovie.setReleaseDate(LocalDate.of(2009, Month.MARCH, 23));
		movieRepo.save(avatarMovie);
		
		Movie existingMovie=movieRepo.findById(avatarMovie.getId()).get();
		existingMovie.setGenre("Fantacy");
		Movie updatedMovie=movieRepo.save(existingMovie);
		
		assertEquals("Fantacy", updatedMovie.getGenre());
		assertEquals("Avatar", updatedMovie.getName());
	}
	
	@Test
	@DisplayName("It Should delete the existing movie")
	void deleteMovie()
	{

		Movie avatarMovie=new Movie();
		avatarMovie.setName("Avatar");
		avatarMovie.setGenre("Action");
		avatarMovie.setReleaseDate(LocalDate.of(2009, Month.MARCH, 23));
		movieRepo.save(avatarMovie);
		Long id = avatarMovie.getId();
		
		Movie titanicMovie=new Movie();
		titanicMovie.setName("Titanic");
		titanicMovie.setGenre("Romantic");
		titanicMovie.setReleaseDate(LocalDate.of(1998, Month.DECEMBER, 5));
		movieRepo.save(titanicMovie);
		
		movieRepo.delete(avatarMovie);
		
		List<Movie> list=movieRepo.findAll();
		
		Optional<Movie> existingMovie = movieRepo.findById(id);
		
		assertEquals(1, list.size());
		assertThat(existingMovie).isEmpty();
	}
	
	
	

}
