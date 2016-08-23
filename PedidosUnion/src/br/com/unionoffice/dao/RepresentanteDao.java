package br.com.unionoffice.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.unionoffice.modelo.Representante;

public class RepresentanteDao {
	private EntityManager manager;

	public RepresentanteDao() throws SQLException {
		this.manager = ConnectionFactory.getManager();
	}

	public List<Representante> listar() throws SQLException {
		TypedQuery<Representante> query = manager.createQuery("select r from Representante r order by r.codigo",
				Representante.class);
		return query.getResultList();
	}
}
