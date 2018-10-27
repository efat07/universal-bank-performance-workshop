package co.com.foundation.integration.persistence.dao;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import co.com.foundation.integration.actor.exceptions.RetryException;
import co.com.foundation.integration.persistence.entity.AutorizedCompany;
import co.com.foundation.integration.persistence.entity.ChannelConfiguration;
import co.com.foundation.integration.persistence.entity.CorporateAccount;
import co.com.foundation.integration.persistence.entity.CorporateCheckbook;
import co.com.foundation.integration.persistence.entity.FinanceMessage;

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


@Stateless
public class FinanceDAO {

	private EntityManager em;

	@PostConstruct
	public void init() {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("finance-pu");
		em = emfactory.createEntityManager();
	}

	public ChannelConfiguration getChannelConfigurationById(final String id) {
		return em.find(ChannelConfiguration.class, id);
	}

	public void createFinanceMessage(final FinanceMessage message) {
		em.getTransaction().begin();
		em.persist(message);
		em.getTransaction().commit();
	}

	public Optional<AutorizedCompany> verifyAutorizedCompany(final String officeCode) throws RetryException {

		try {

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<AutorizedCompany> cq = cb.createQuery(AutorizedCompany.class);
			cq.from(AutorizedCompany.class);
			List<AutorizedCompany> autorizedCompanies = em.createQuery(cq).getResultList();

			Optional<AutorizedCompany> companyfound = autorizedCompanies.stream()
					.filter(autorizedCompany -> officeCode.equals(autorizedCompany.getOfficeCode())).findFirst();

			return companyfound;
		} catch (Exception e) {
			throw new RetryException("error executing query", e);
		}
	}

	public Optional<CorporateAccount> verifyAccount(final String account) throws RetryException {

		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<CorporateAccount> cq = cb.createQuery(CorporateAccount.class);
			cq.from(CorporateAccount.class);
			List<CorporateAccount> accounts = em.createQuery(cq).getResultList();

			Optional<CorporateAccount> accountFound = accounts.stream()
					.filter(approvedAccount -> account.equals(approvedAccount.getAccountNumber())).findFirst();

			return accountFound;
		} catch (Exception e) {
			throw new RetryException("error executing query", e);
		}
	}

	public Optional<CorporateCheckbook> verifyCheckBook(final String checkBookNumber) throws RetryException {

		try {

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<CorporateCheckbook> cq = cb.createQuery(CorporateCheckbook.class);
			cq.from(CorporateCheckbook.class);
			List<CorporateCheckbook> checkBooks = em.createQuery(cq).getResultList();

			Optional<CorporateCheckbook> checkBookFound = checkBooks.stream()
					.filter(approvedCheckBook -> checkBookNumber.equals(approvedCheckBook.getReferenceNumber()))
					.findFirst();

			return checkBookFound;
		} catch (Exception e) {
			throw new RetryException("error executing query", e);
		}
	}

}
