package br.com.unionoffice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.unionoffice.modelo.Cliente;
import br.com.unionoffice.modelo.Contato;

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
			sql = "INSERT INTO cliente VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";			
			statement = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setInt(1, cliente.getTipo().ordinal());
			statement.setString(2, cliente.getRgIe());
			statement.setString(3, cliente.getCpfCnpj());
			statement.setString(4, cliente.getNomeRazaoSocial());
			statement.setString(5, cliente.getCep());
			statement.setString(6, cliente.getEndereco());
			statement.setString(7, cliente.getNumero());
			statement.setString(8, cliente.getComplemento());
			statement.setString(9, cliente.getBairro());
			statement.setInt(10, cliente.getEstado().ordinal());
			statement.setString(11, cliente.getCidade());
			statement.setString(12, cliente.getEmail());
			statement.setInt(13, cliente.getRepresentante().getId());
			statement.setString(14, cliente.getObservacoes());
			statement.execute();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				cliente.setId(rs.getInt(0));
			}else{
				throw new SQLException("Erro ao inserir cliente");
			}
			rs.close();
			sql = "INSERT INTO contato VALUES(?,?,?,?,?)";
			statement2 = conexao.prepareStatement(sql);
			for (Contato contato : cliente.getContatos()) {
				statement2.setString(1, contato.getNome());
				statement2.setString(2, contato.getDepartamento());
				statement2.setString(3, contato.getTelefone());
				statement2.setString(4, contato.getEmail());
				statement2.setInt(5, cliente.getId());
				statement2.execute();
			}
			statement.close();
			statement2.close();
		} catch (SQLException e) {
			throw e;
		} finally {
			conexao.setAutoCommit(true);
		}
	}
}
