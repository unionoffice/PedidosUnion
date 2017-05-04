package br.com.unionoffice.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.unionoffice.modelo.Produto;

public class ProdutoDao {
	private EntityManager manager;

	public ProdutoDao() throws SQLException {
		this.manager = ConnectionFactory.getManager();
	}

	public void inserirProduto(Produto produto) {
		this.manager.getTransaction().begin();
		this.manager.persist(produto);
		this.manager.getTransaction().commit();
	}

	public void alterarProduto(Produto produto) {
		this.manager.getTransaction().begin();
		this.manager.merge(produto);
		this.manager.getTransaction().commit();
	}

	public List<Produto> listar() {
		TypedQuery<Produto> query = this.manager.createQuery("select p from Produto p order by p.codigo",
				Produto.class);
		return query.getResultList();
	}

}
