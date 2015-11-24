package br.com.unionoffice.modelo;

public enum TipoCliente {
	PESSOA_FISICA("Pessoa F�sica"), PESSOA_JURIDICA("Pessoa Jur�dica");
	private String descricao;

	private TipoCliente(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return descricao;
	}
}
