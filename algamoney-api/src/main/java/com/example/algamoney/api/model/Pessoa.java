package com.example.algamoney.api.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pessoa")
public class Pessoa {

	//Identificador de Pessoa
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codigo;

	@NotNull
	private String nome;

	@Embedded
	private Endereco endereco;

	@NotNull
	private Boolean ativo;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	@JsonIgnore //Anotação utilizada para que este atributo seja ignorado para que o mesmo não seja tratado como uma propriedade
	@Transient //Anotação utilizada para que este atributo seja ignorado pelo JPA, com isso não irá salvar no Banco de dados
	public boolean isInativo(){
		return !this.ativo; 
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}
