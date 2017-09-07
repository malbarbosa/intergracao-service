package br.com.nuclearis.integracao.dao;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.nuclearis.integracao.dao.interfaces.IDAOGenericoInt;
import br.com.nuclearis.integracao.exceptions.DAOException;

@Component("daoNuclearisInt")
@Scope("prototype")
public class DAONuclearisInt<GDAO> implements IDAOGenericoInt<GDAO> {

	private static Logger log = Logger.getLogger(DAONuclearisInt.class);

	@Resource(name = "sessionFactoryNuclearisInt")
	private SessionFactory sessionFactoryInt;

	private Class<GDAO> oClass;

	public DAONuclearisInt() {
		log.debug("---> Nova instância do DAONuclearisBin criada: new DAONuclearis()");
	}

	public DAONuclearisInt(Class<GDAO> classe) {
		log.debug("---> Nova instância do DAONuclearisBin criada: new DAONuclearis(" + classe + ")");
		this.oClass = classe;
	}

	@Override
	public void setObjectClass(Class<GDAO> classe) {
		this.oClass = classe;
	}

	@Override
	public Class<GDAO> getObjectClass() {
		return this.oClass;
	}

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactoryInt;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactoryInt = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED, timeout = 30, value = "txManagerNuclearis")
	public GDAO salvarOuAtualizar(GDAO object) throws DAOException {
		try {

			object = (GDAO) getSession().merge(object);

			/*
			 * Depois de algumas pesquisas sobre o flush verifiquei em posts mais antigos que essa pratica era bastante desencorajada pela comunidade, porém em posts mais recentes constatei que o proprio doc do Hibernate tem exemplos onde
			 * se usa o flush para evitar um OutOfMemory. *** O mais relevante é que sem o flush nesse ponto do código fica impossível tratar os erros de constraints e dar mensagens básicas para o usuário como por exemplo,
			 * "Usuário já cadastrado" pois a Exception de constraint violation só ocorrerá quando o hibernate der o flush no momento que julgar mais conveniente. Quando removi o flush deste ponto passei a ter uma exceção de
			 * ConstraintViolation com usuário ja cadastrado bem mais a frente na execução durante um método de letura usuario.getPorEmail.
			 */
			getSession().flush();

			return object;

		} catch (Exception e) {

			throw new DAOException(e);
		}

	}

	@Override
	public Session getSession() {

		// -> O Hibernate utiliza uma sessao por Thread. Significa que mesmo que sejam feitas persistencias de objetos diferentes dentro da mesma THREAD será utilizada a mesma
		// sessão

		return sessionFactoryInt.getCurrentSession();

	}

	protected Query criarConsultaHQL(String hql) throws DAOException {

		try {
			Query q = getSession().createQuery(hql);
			return q;

		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	protected Criteria criarCriteria() throws DAOException {

		try {
			return getSession().createCriteria(oClass);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	protected SQLQuery criarConsultaSQL(String sql) throws DAOException {

		try {

			SQLQuery q = getSession().createSQLQuery(sql);
			return q;

		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

}
