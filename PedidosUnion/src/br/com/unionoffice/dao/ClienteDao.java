package br.com.unionoffice.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;

import br.com.unionoffice.modelo.Cliente;

public class ClienteDao {
	private EntityManager manager;

	public ClienteDao() throws SQLException {
		this.manager = ConnectionFactory.getManager();
	}

	public void inserirCliente(Cliente cliente) throws SQLException {
		try {
			this.manager.getTransaction().begin();
			this.manager.persist(cliente);
			this.manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
