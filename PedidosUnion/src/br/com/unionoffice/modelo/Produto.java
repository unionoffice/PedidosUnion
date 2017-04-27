package br.com.unionoffice.modelo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String codigo;
	private String descResumida;
	@Column(columnDefinition = "TEXT")
	private String descDetalhada;
	private BigDecimal preco;
	private String foto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescResumida() {
		return descResumida;
	}

	public void setDescResumida(String descResumida) {
		this.descResumida = descResumida;
	}

	public String getDescDetalhada() {
		return descDetalhada;
	}

	public void setDescDetalhada(String descDetalhada) {
		this.descDetalhada = descDetalhada;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

}
