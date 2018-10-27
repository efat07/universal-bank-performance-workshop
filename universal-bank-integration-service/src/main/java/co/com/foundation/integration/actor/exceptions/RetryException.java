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

public class RetryException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public RetryException(String message, Throwable cause) {
		super(message, cause);
	}

	public RetryException(String message) {
		super(message);
	}
	
	public static RetryException build( final String message ) {
		return new RetryException(message);
	}
	
	public static RetryException build( final String message , final Throwable cause) {
		return new RetryException(message, cause);
	}
}
