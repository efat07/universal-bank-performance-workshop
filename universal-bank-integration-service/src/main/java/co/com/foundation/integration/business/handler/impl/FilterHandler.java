package co.com.foundation.integration.business.handler.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.integration.business.handler.Handler;
import co.com.foundation.integration.persistence.dao.FinanceDAO;
import co.com.foundation.integration.persistence.entity.AutorizedCompany;
import co.com.foundation.integration.persistence.entity.CorporateAccount;
import co.com.foundation.integration.persistence.entity.CorporateCheckbook;
import universal_bank.crm.enterprise_model.framework._1_0.CRMBusinessEvent;
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

@Stateless(name = "filterHandler")
public class FilterHandler implements Handler<CRMBusinessEvent> {

	private static final Logger LOGGER = LogManager.getLogger(FilterHandler.class);

	@EJB
	private FinanceDAO financeDAO;
	
	@Override
	public boolean process(final CRMBusinessEvent input){

		LOGGER.info("start filter handler");
		
		String officeCode = input.getEnterpriseOperationEventObject().getBranchOfficeInfo().getAssignedLicense();
		List<AutorizedCompany> companyfound = financeDAO.verifyAutorizedCompany(officeCode);

		if (companyfound == null || companyfound.isEmpty()) {
			return false;
		} else {
			LOGGER.info("message comes from a valid company {}", companyfound.get(0).getCompanyName());
		}
		
		List<FinancialActionDomainObject> results = input.getEnterpriseOperationEventObject()
				.getFinancialActionDomainObject()
				.stream()
				.filter(predicate)
				.collect(Collectors.toList());
		
		//System.out.println("EYNER::::  predicate " + predicate);
		
		
		for (FinancialActionDomainObject financialActionDomainObject : results) {
			System.out.println("EYNER::::   financialActionDomainObject " + financialActionDomainObject.getDescription());
		}
		
		
		
		input.getEnterpriseOperationEventObject()
		.getFinancialActionDomainObject()
		.clear();
		
		//System.out.println("EYNER::::  input " + input);
		
		input.getEnterpriseOperationEventObject()
		.getFinancialActionDomainObject()
		.addAll(results);
		
		//System.out.println("EYNER::::  input " + input);
		
		LOGGER.info("end filter handler");
		return true;
	}
	
	private Predicate<FinancialActionDomainObject> predicate = ( action ) -> {
		
		String targetAccountNumber = action.getFinancialService().getEnterpriseServicesDomainEvents().getExecuteDomesticPaymentDomainEvent()
				.getTargetAccountNumber();
		LOGGER.info("defining predicates for target-account-number: {}", targetAccountNumber);
		Optional<CorporateAccount> accountFound = financeDAO.verifyAccount(targetAccountNumber);
		
		String checkBookReferenceNumber = action.getFinancialProduct().getEnterpriseProductsDomainEvents().getAddChekingAccountDomainEvent().getCheckbookReferecenNumber();
		LOGGER.info("defining predicates for target-check-book-number: {}", targetAccountNumber);
		Optional<CorporateCheckbook> checkBookFound = financeDAO.verifyCheckBook(checkBookReferenceNumber);
		
		return accountFound.isPresent() || checkBookFound.isPresent();
	};

}
