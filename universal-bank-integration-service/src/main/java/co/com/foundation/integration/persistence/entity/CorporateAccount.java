package co.com.foundation.integration.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

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

@Entity
@Table(name="CORPORATE_ACCOUNTS")
@NamedQuery(name="CorporateAccount.findAll", query="SELECT c FROM CorporateAccount c")
public class CorporateAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private Long id;

	@Column(name="ACCOUNT_LINE")
	private String accountLine;

	@Column(name="ACCOUNT_NUMBER")
	private String accountNumber;

	@Column(name="ACCOUNT_PREFIX")
	private String accountPrefix;

	public CorporateAccount() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountLine() {
		return this.accountLine;
	}

	public void setAccountLine(String accountLine) {
		this.accountLine = accountLine;
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountPrefix() {
		return this.accountPrefix;
	}

	public void setAccountPrefix(String accountPrefix) {
		this.accountPrefix = accountPrefix;
	}

}