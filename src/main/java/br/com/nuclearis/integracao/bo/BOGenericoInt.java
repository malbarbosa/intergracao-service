package br.com.nuclearis.integracao.bo;

import br.com.nuclearis.integracao.bo.interfaces.IBOGenericoInt;
import br.com.nuclearis.integracao.dao.interfaces.IDAOGenericoInt;

public abstract class BOGenericoInt<GBO> implements IBOGenericoInt<GBO> {

	private IDAOGenericoInt<GBO> daoGenerico;

	public BOGenericoInt() {
	}

	protected abstract void setDAO();

	public void setDAO(IDAOGenericoInt<GBO> dao) {
		daoGenerico = dao;
	}

	public Class<GBO> getObjectClass() {
		return daoGenerico.getObjectClass();
	}

	public IDAOGenericoInt<GBO> getDAO() {
		return daoGenerico;
	}

}