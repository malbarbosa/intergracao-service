package br.com.nuclearis.integracao.exceptions;

public class DAOException extends Exception {

	private static final long serialVersionUID = 2692994251322161103L;

	public DAOException(Throwable e) {
		super(e);
	}

	public DAOException(String mensagem) {
		super(mensagem);
	}

}
