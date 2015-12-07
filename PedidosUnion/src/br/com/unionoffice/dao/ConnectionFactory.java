package br.com.unionoffice.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConnectionFactory {
	private static final String URL = "jdbc:mysql://179.188.16.86:3306/unionoffice1";
	private static final String USER = "unionoffice1";
	private static final String PASSWORD = "UniOffice2015";
	private static Connection conexao;
	
	public static Connection getConexao() throws SQLException{
		if (conexao == null) {
			conexao = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("CONECTOU");
		}
		return conexao;
	}
	
	public static void close() throws SQLException{
		System.out.println("PASSOU AQUI");
		if (conexao != null && !conexao.isClosed()) {
			conexao.close();
		}
	}
}
