package com.example.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.service.PessoaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Criando Anotações/Metadados para dizer algo a respeito desta Classe
 */

/**
 * A anotação @RestController informa que o retorno será do tipo Json, facilitando assim a leitura
 */

/**
 * A anotação @RestController informa que estou realizando o mapeamento da
 * requisição, quando for feita a requisição através de uma URL que contém
 * "/pessoas", saberei que está vindo a partir desta Classe
 *
 */
@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	/*
	 * Realizando Injeção, utilizando a anotação @Autowired, para que o spring ache
	 * uma implementação de PessoaRepository e entregue para mim no objeto
	 * pessoaRepository
	 */
	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private PessoaService pessoaService;

	/*
	 * Realizando o mapeamento para o recurso "/pessoas", através da
	 * anotação @GetMapping para listar as pessoas
	 */
	@GetMapping
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}

	/**
	 * Criando Método com a anotação @PosMapping para adicionar uma nova pessoa
	 * 
	 * @ResponseStatus com o parametro (HttpStaus.CREATED) utilizado para informar o
	 * Status de retorno 201 Created
	 * @param pessoa
	 * 
	 */
	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Pessoa> pessoa = this.pessoaRepository.findById(codigo);

		if (pessoa.isPresent()) {
			return ResponseEntity.ok(pessoa.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Irá retornar o codigo 204, tem um sucesso, não tenho nada para retornar, fiz
	 * o que você pediu mas não tenho nada de conteúdo para retornar, apenas deletar
	 * o codigo
	 */
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		this.pessoaRepository.deleteById(codigo);
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa) {

		Pessoa pessoaSalva = pessoaService.atualizar(codigo, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	
	}

	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		pessoaService.atualizarPropriedadeAtivo(codigo, ativo);
	}
}
