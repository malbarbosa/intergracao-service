package br.com.nuclearis.integracao.bo.interfaces;

import br.com.nuclearis.integracao.dao.interfaces.IDAOGenericoInt;

public interface IBOGenericoInt<GBO> {

	public Class<GBO> getObjectClass();

	public IDAOGenericoInt<GBO> getDAO();

	public void setDAO(IDAOGenericoInt<GBO> dao);

}