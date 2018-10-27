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
@Table(name="AUTORIZED_COMPANIES")
@NamedQuery(name="AutorizedCompany.findAll", query="SELECT a FROM AutorizedCompany a")
public class AutorizedCompany implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private Long id;

	@Column(name="COMPANY_NAME")
	private String companyName;

	@Column(name="OFFICE_CODE")
	private String officeCode;

	public AutorizedCompany() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOfficeCode() {
		return this.officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

}