package com.generation.gameStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.gameStore.model.Categoria;

public interface CategoriaInterface extends JpaRepository<Categoria, Long> {
	public List <Categoria> findByGeneroContainingIgnoreCase(@Param("genero") String genero); 
}
