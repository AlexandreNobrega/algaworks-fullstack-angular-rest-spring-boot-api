package com.example.algamoney.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

/**
 * Criando Anotações/Metadados para dizer algo a respeito desta Classe
 */

/**
 * A anotação @RestController informa que o retorno será do tipo Json, facilitando assim a leitura
 */

/**
 * A anotação @RestController informa que estou realizando o mapeamento da requisição, quando for feita a requisição através 
 * de uma URL que contém "/categorias", saberei que está vindo a partir desta Classe
 *
 */


@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	/*
	 * Realizando Injeção, utilizando a anotação @Autowired, para que o spring ache uma implementação de CategoriaRepository 
	 * e entregue para mim no objeto categoriaRepository
	 */
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	/*
	 * Realizando o mapeamento para o recurso "/categorias", através da anotação @GetMapping para listar as categorias
	 * @return
	 */
	@GetMapping
	public List<Categoria> listar(){
		return categoriaRepository.findAll();
	}
	
	/**
	 * Criando Método com a anotação @PosMapping para adicionar uma nova categoria
	 * @ResponseStatus com o parametro (HttpStaus.CREATED) utilizado para informar o Status de retorno 201 Created
	 * @param categoria
	 * 
	 * 
	 * 
	 */
	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
			.buildAndExpand(categoria.getCodigo()).toUri();
		
		return ResponseEntity.created(uri).body(categoriaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Categoria> categoria = this.categoriaRepository.findById(codigo);
		
		if (categoria.isPresent()) {
			return	ResponseEntity.ok(categoria.get());
		} else {
			return	ResponseEntity.notFound().build();
		}
	}
}
