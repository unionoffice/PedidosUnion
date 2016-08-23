package br.com.unionoffice.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.unionoffice.modelo.Estado;

public class CidadeDao {
	private EntityManager manager;
	
	public CidadeDao() throws SQLException{
		this.manager = ConnectionFactory.getManager();
	}
	
	public List<String> getCidades(Estado estado) throws SQLException{
		TypedQuery<String> query = manager.createQuery("select c.nome from Cidade c", String.class);
		return query.getResultList();
	}
}
