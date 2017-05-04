package br.com.unionoffice.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.unionoffice.modelo.Cliente;

public class ClienteDao {
	private EntityManager manager;

	public ClienteDao() throws SQLException {
		this.manager = ConnectionFactory.getManager();
	}

	public void inserirCliente(Cliente cliente) {
		this.manager.getTransaction().begin();
		this.manager.persist(cliente);
		this.manager.getTransaction().commit();
	}

	public List<Cliente> listar() {
		TypedQuery<Cliente> query = this.manager.createQuery("select c from Cliente c order by c.nomeRazaoSocial",
				Cliente.class);
		return query.getResultList();
	}

	public List<Cliente> buscarPorDocumento(String documento) {
		TypedQuery<Cliente> query = this.manager.createQuery(
				"select c from Cliente c where c.cpfCnpj like :documento order by c.nomeRazaoSocial ", Cliente.class);
		query.setParameter("documento", "%" + documento + "%");
		return query.getResultList();
	}

	public List<Cliente> buscarPorNome(String nome) {
		TypedQuery<Cliente> query = this.manager.createQuery(
				"select c from Cliente c where c.nomeRazaoSocial like :nome order by c.nomeRazaoSocial ",
				Cliente.class);
		query.setParameter("nome", "%" + nome + "%");
		return query.getResultList();
	}
	
	public List<Cliente> buscarQualquer(String parametro) {
		TypedQuery<Cliente> query = this.manager.createQuery(
				"select c from Cliente c where c.nomeRazaoSocial like :parametro or c.endereco like :parametro or c.email like :parametro or c.observacoes like :parametro order by c.nomeRazaoSocial ",
				Cliente.class);
		query.setParameter("parametro", "%" + parametro + "%");
		return query.getResultList();
	}

	public void excluirCliente(int idCliente) {
		Cliente cliente = this.manager.find(Cliente.class, idCliente);
		this.manager.getTransaction().begin();
		this.manager.remove(cliente);
		this.manager.getTransaction().commit();
	}

	public void alterarCliente(Cliente cliente) {
		this.manager.getTransaction().begin();
		this.manager.merge(cliente);
		this.manager.getTransaction().commit();
	}
}
