package com.generation.gameStore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import com.generation.gameStore.model.Produto;
import com.generation.gameStore.repository.CategoriaInterface;
import com.generation.gameStore.repository.ProdutoInterface;

import jakarta.validation.Valid;

@Controller
@RequestMapping ("/produto")
@CrossOrigin (origins = "*", allowedHeaders = "*")
public class ProdutoController {
	
	@Autowired
	public ProdutoInterface produtoInterface;
	
	@Autowired
	public CategoriaInterface categoriaInterface;
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll(){
		return ResponseEntity.ok(produtoInterface.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id){
		return produtoInterface.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(produtoInterface.findByNomeContainingIgnoreCase(nome));
	}
	
	@PostMapping
	public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto){
		return ResponseEntity.status(HttpStatus.CREATED).
				body(produtoInterface.save(produto));
	}
	
	@PutMapping
	public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto){
		if(produtoInterface.existsById(produto.getId())) {
			if(categoriaInterface.existsById(produto.getCategoria().getId())) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(produtoInterface.save(produto));
			}
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existente!", null);	
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Produto> produto = produtoInterface.findById(id);
		
		if(produto.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		produtoInterface.deleteById(id);
	}
}
