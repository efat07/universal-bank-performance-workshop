package co.com.foundation.integration.marshaller.impl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.apache.commons.io.output.ByteArrayOutputStream;

import co.com.foundation.integration.filter.marshaller.Marshaller;
import universal_bank.crm.enterprise_model.framework._1_0.FinancialActionDomainObject;

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

public class JAXBMarshaller implements Marshaller<FinancialActionDomainObject, String> {

	private static final String JAXB_PACKAGES = "universal_bank.crm.enterprise_model.framework._1_0:universal_bank.crm.enterprise_model.framework.common_artifacts._1_0:universal_bank.crm.enterprise_model.framework.domain_events._1_0";
	private JAXBContext context;
	private javax.xml.bind.Marshaller ma;

	private JAXBMarshaller() throws JAXBException {
		super();
		context = JAXBContext.newInstance(JAXB_PACKAGES);
		ma = context.createMarshaller();
	}

	public static JAXBMarshaller newInstance() {
		try {
			return new JAXBMarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String marshall(final FinancialActionDomainObject input) {
		try {
			
			JAXBElement<FinancialActionDomainObject> element = new JAXBElement<FinancialActionDomainObject>( new QName("", "FinancialActionDomainObject"), FinancialActionDomainObject.class, null, input);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ma.marshal(element, output);
			return output.toString("UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
