package co.com.foundation.integration.services;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.JAXBException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.integration.business.PreActionsExecutor;
import co.com.foundation.integration.marshaller.impl.JAXBUnmarshaller;
import co.com.foundation.integration.model.FinancialActionRequestDTO;
import co.com.foundation.integration.model.FinancialActionResponseDTO;
import co.com.foundation.integration.workmodel.WorkItem;

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

@WebService(targetNamespace="http://universal-bank/enterprise/services/1.0.0")
public class FinancialActionsProcessorWS {

	private static final Logger LOGGER = LogManager.getLogger(FinancialActionsProcessorWS.class);
	
	@EJB
	private PreActionsExecutor executor;
	
	public FinancialActionsProcessorWS() {
		super(); 
	}

	@WebMethod(operationName="processFinancialAction",action="processFinancialAction")
	public FinancialActionResponseDTO processFinancialAction( @WebParam(name="request") FinancialActionRequestDTO request ) throws JAXBException{
		
		LOGGER.info("new message received to create work");
		LOGGER.info( request.getPayload() );
		
		// ---- validate if xsd-schema is well defined
		JAXBUnmarshaller.newInstance().unmarshall(request.getPayload());
		
		WorkItem item = new WorkItem();
		item.setXmlContent(request.getPayload());
		LOGGER.info("work created with id {}", item.getId());
		executor.execute(item);
		
		FinancialActionResponseDTO response = new FinancialActionResponseDTO();
		response.setTransactionId( request.getTransactionId() );
		
		return response;
	}
	
}
