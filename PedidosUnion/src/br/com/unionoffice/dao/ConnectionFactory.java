package br.com.unionoffice.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionFactory {
	private static EntityManagerFactory factory;
	private static EntityManager manager;

	static {
		factory = Persistence.createEntityManagerFactory("unionoffice");
	}

	public static EntityManager getManager() {
		if (manager == null) {
			manager = factory.createEntityManager();
		}
		return manager;
	}

	public static void close() throws SQLException {
		if (manager != null && manager.isOpen()) {
			manager.close();
			factory.close();
		}
	}

}