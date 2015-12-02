package br.com.unionoffice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.unionoffice.modelo.Estado;
import br.com.unionoffice.modelo.Representante;

public class RepresentanteDao {
	private Connection conexao;
	PreparedStatement statement;
	String sql;
	
	public RepresentanteDao() throws SQLException{
		conexao = ConnectionFactory.getConexao();
	}
	
	public List<Representante> listar() throws SQLException{
		List<Representante> representantes = new ArrayList<Representante>();
		sql = "SELECT * FROM representante";
		statement = conexao.prepareStatement(sql);	
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			Representante r = new Representante();
			r.setId(rs.getInt("id_representante"));
			r.setCodigo(rs.getString("codigo"));
			r.setNome(rs.getString("nome"));
			representantes.add(r);			
		}
		rs.close();
		statement.close();
		return representantes;
	}
}
