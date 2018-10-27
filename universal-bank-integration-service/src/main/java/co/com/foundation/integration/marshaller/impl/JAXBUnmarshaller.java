package co.com.foundation.integration.marshaller.impl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;

import co.com.foundation.integration.filter.marshaller.Unmarshaller;
import universal_bank.crm.enterprise_model.framework._1_0.CRMBusinessEvent;

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

public class JAXBUnmarshaller implements Unmarshaller<String, CRMBusinessEvent> {

	private static final String JAXB_PACKAGES = "universal_bank.crm.enterprise_model.framework._1_0:universal_bank.crm.enterprise_model.framework.common_artifacts._1_0:universal_bank.crm.enterprise_model.framework.domain_events._1_0";
	private JAXBContext context;
	private javax.xml.bind.Unmarshaller um;

	private JAXBUnmarshaller() throws JAXBException {
		super();
		context = JAXBContext.newInstance(JAXB_PACKAGES);
		um = context.createUnmarshaller();
	}

	public static JAXBUnmarshaller newInstance() throws JAXBException {
		return new JAXBUnmarshaller();
	}

	@Override
	public CRMBusinessEvent unmarshall(final String input) {
		try {
			return (CRMBusinessEvent) um.unmarshal(IOUtils.toInputStream(input, "UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
