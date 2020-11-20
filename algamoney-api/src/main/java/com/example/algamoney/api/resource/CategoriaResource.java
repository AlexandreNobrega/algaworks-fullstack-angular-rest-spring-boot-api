package com.example.algamoney.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	 * Realizando o mapeamento para o recurso "/categorias", através da anotação @GetMapping
	 * @return
	 */
	@GetMapping
	public List<Categoria> listar(){
		return categoriaRepository.findAll();
	}
}
