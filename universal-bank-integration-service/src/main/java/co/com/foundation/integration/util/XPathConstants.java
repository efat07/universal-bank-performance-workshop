package co.com.foundation.integration.util;

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

public interface XPathConstants {

	public static final String OFFICE_CODE = "/*[local-name()='CRMBusinessEvent']/*[local-name()='enterpriseOperationEventObject']/*[local-name()='branchOfficeInfo']/*[local-name()='assignedLicense']/text()";
	public static final String ENTERPRISE_OPERATION_KEY = "/*[local-name()='CRMBusinessEvent']/*[local-name()='enterpriseOperationEventObject']/@enterpriseOperationKey";
	
}
