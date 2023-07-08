package com.takeo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.takeo.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	List<Movie> findByGenre(String genre);

}
