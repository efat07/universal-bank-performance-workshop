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
@Table(name="FINANCE_MESSAGE")
@SequenceGenerator(name="FINANCE_MESSAGE_GEN",sequenceName="FINANCE_MESSAGE_SEQ", allocationSize=1)
@NamedQuery(name="FinanceMessage.findAll", query="SELECT f FROM FinanceMessage f")
public class FinanceMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FINANCE_MESSAGE_GEN")
	private Long id;

	@Column(name="CONTAINS_PRODUCT")
	private boolean containsProduct;

	@Column(name="CONTAINS_SERVICE")
	private boolean containsService;

	@Column(name="EXTERNAL_KEY")
	private String externalKey;

	@Lob
	@Column(name="XML_MESSAGE")
	private String xmlMessage;

	public FinanceMessage() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean getContainsProduct() {
		return this.containsProduct;
	}

	public void setContainsProduct(boolean containsProduct) {
		this.containsProduct = containsProduct;
	}

	public boolean getContainsService() {
		return this.containsService;
	}

	public void setContainsService(boolean containsService) {
		this.containsService = containsService;
	}

	public String getExternalKey() {
		return this.externalKey;
	}

	public void setExternalKey(String externalKey) {
		this.externalKey = externalKey;
	}

	public String getXmlMessage() {
		return this.xmlMessage;
	}

	public void setXmlMessage(String xmlMessage) {
		this.xmlMessage = xmlMessage;
	}

}