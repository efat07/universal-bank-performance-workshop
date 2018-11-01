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
import javax.persistence.criteria.Root;

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

	public List<AutorizedCompany> verifyAutorizedCompany(final String officeCode) throws RetryException {

		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<AutorizedCompany> cq = cb.createQuery(AutorizedCompany.class);
			Root<AutorizedCompany> autorizedCompany = cq.from(AutorizedCompany.class);
			cq.select(autorizedCompany);
			cq.where(cb.equal(autorizedCompany.get("officeCode"), officeCode));
			List<AutorizedCompany> autorizedCompanies = em.createQuery(cq).getResultList();
			//Optional<AutorizedCompany> companyfound = autorizedCompanies.stream().findFirst();
			//List<AutorizedCompany> companyfound = autorizedCompanies.isEmpty()?autorizedCompanies:null;
			return autorizedCompanies;
		} catch (Exception e) {
			throw new RetryException("error executing query", e);
		}
	}

	public Optional<CorporateAccount> verifyAccount(final String account) throws RetryException {

		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<CorporateAccount> cq = cb.createQuery(CorporateAccount.class);
			Root<CorporateAccount> corporateAccount = cq.from(CorporateAccount.class);
			cq.select(corporateAccount);
			cq.where(cb.equal(corporateAccount.get("accountNumber"), account));
			List<CorporateAccount> accounts = em.createQuery(cq).getResultList();
		    Optional<CorporateAccount> accountFound = accounts.stream().findFirst();
		    return accountFound;
		} catch (Exception e) {
			throw new RetryException("error executing query", e);
		}
	}

	public Optional<CorporateCheckbook> verifyCheckBook(final String checkBookNumber) throws RetryException {

		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<CorporateCheckbook> cq = cb.createQuery(CorporateCheckbook.class);
			Root<CorporateCheckbook> corporateCheckbook = cq.from(CorporateCheckbook.class);
			cq.select(corporateCheckbook);
			cq.where(cb.equal(corporateCheckbook.get("referenceNumber"), checkBookNumber));
			List<CorporateCheckbook> checkBooks = em.createQuery(cq).getResultList();
			Optional<CorporateCheckbook> checkBookFound = checkBooks.stream().findFirst();
			return checkBookFound;
		} catch (Exception e) {
			throw new RetryException("error executing query", e);
		}
	}
}
