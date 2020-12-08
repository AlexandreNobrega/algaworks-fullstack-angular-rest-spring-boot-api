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

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

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
@RequestMapping("/pessoas")
public class PessoaResource {
	
	/*
	 * Realizando Injeção, utilizando a anotação @Autowired, para que o spring ache uma implementação de PessoaRepository 
	 * e entregue para mim no objeto pessoaRepository
	 */
	@Autowired
	private PessoaRepository pessoaRepository;
	
	/*
	 * Realizando o mapeamento para o recurso "/pessoas", através da anotação @GetMapping para listar as pessoas
	 * @return
	 */
	@GetMapping
	public List<Pessoa> listar(){
		return pessoaRepository.findAll();
	}
	
	/**
	 * Criando Método com a anotação @PosMapping para adicionar uma nova pessoa
	 * @ResponseStatus com o parametro (HttpStaus.CREATED) utilizado para informar o Status de retorno 201 Created
	 * @param pessoa
	 *  
	 */
	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
			.buildAndExpand(pessoa.getCodigo()).toUri();
		
		return ResponseEntity.created(uri).body(pessoaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Pessoa> pessoa = this.pessoaRepository.findById(codigo);
		
		if (pessoa.isPresent()) {
			return	ResponseEntity.ok(pessoa.get());
		} else {
			return	ResponseEntity.notFound().build();
		}
	}
}
