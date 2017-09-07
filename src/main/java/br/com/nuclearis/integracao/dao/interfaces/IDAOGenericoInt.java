package br.com.nuclearis.integracao.dao.interfaces;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import br.com.nuclearis.integracao.exceptions.DAOException;

public interface IDAOGenericoInt<GDAO> {

	public void setObjectClass(Class<GDAO> classe);

	public Class<GDAO> getObjectClass();

	public GDAO salvarOuAtualizar(GDAO object) throws DAOException;

	public Session getSession();

	public SessionFactory getSessionFactory();

}