package co.com.foundation.integration.actor.exceptions;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad Javeriana (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * AES - PICA (http://www.javeriana.edu.co/programas/especializacion-en-arquitectura-empresarial-de-software)
 * Ejercicio: universal bank integration
 * Autor: PICA
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */

public class ActorModelException extends Exception {

	private static final long serialVersionUID = 1L;

	public ActorModelException() {
		super();
	}

	public ActorModelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ActorModelException(String message, Throwable cause) {
		super(message, cause);
	}

	public ActorModelException(String message) {
		super(message);
	}

	public ActorModelException(Throwable cause) {
		super(cause);
	}

}
