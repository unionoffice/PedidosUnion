package br.com.unionoffice.modelo;

public enum TipoCliente {
	PESSOA_FISICA("Pessoa Física"), PESSOA_JURIDICA("Pessoa Jurídica");
	private String descricao;

	private TipoCliente(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return descricao;
	}
}
