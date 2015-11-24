package br.com.unionoffice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.unionoffice.modelo.Estado;

public class CidadeDao {
	private Connection conexao;
	PreparedStatement statement;
	String sql;
	
	public CidadeDao() throws SQLException{
		conexao = ConnectionFactory.getConexao();
	}
	
	public List<String> getCidades(Estado estado) throws SQLException{
		List<String> cidades = new ArrayList<String>();
		sql = "SELECT * FROM cidade WHERE uf = ?";
		statement = conexao.prepareStatement(sql);
		statement.setInt(1, estado.ordinal());
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			cidades.add(rs.getString("nome"));
			
		}
		rs.close();
		statement.close();
		return cidades;
	}
}
