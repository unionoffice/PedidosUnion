package br.com.unionoffice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.unionoffice.modelo.Cliente;

public class ClienteDao {
	private Connection conexao;
	PreparedStatement statement, statement2;
	String sql;

	public ClienteDao() throws SQLException {
		conexao = ConnectionFactory.getConexao();
	}

	public void inserirCliente(Cliente cliente) throws SQLException {
		try {
			conexao.setAutoCommit(false);
			//sql = "INSERT INTO cliente VALUES(?"
			
		} catch (SQLException e) {
			throw e;
		} finally {
			conexao.setAutoCommit(true);
		}
	}
}
