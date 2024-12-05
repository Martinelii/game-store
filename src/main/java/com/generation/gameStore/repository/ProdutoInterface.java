package com.generation.gameStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.gameStore.model.Produto;

public interface ProdutoInterface extends JpaRepository<Produto, Long>{
	public List <Produto> findByNomeContainingIgnoreCase(@Param("nome") String nome); 
}
