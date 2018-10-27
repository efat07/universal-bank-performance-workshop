//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.07.22 at 06:24:47 PM EDT 
//


package universal_bank.crm.enterprise_model.framework.domain_events._1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import universal_bank.crm.enterprise_model.framework.common_artifacts._1_0.AccountType;
import universal_bank.crm.enterprise_model.framework.common_artifacts._1_0.CardType;


/**
 * <p>Java class for cancelCardDomainEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cancelCardDomainEvent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="accountType" type="{http://universal-bank/crm/enterprise-model/framework/common-artifacts/1.0.0}AccountType"/>
 *         &lt;element name="cardType" type="{http://universal-bank/crm/enterprise-model/framework/common-artifacts/1.0.0}CardType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="operationIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cancelCardDomainEvent", propOrder = {
    "accountNumber",
    "accountType",
    "cardType"
})
public class CancelCardDomainEvent {

    @XmlElement(required = true)
    protected String accountNumber;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected AccountType accountType;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected CardType cardType;
    @XmlAttribute(name = "operationIdentifier")
    protected String operationIdentifier;

    /**
     * Gets the value of the accountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the value of the accountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountNumber(String value) {
        this.accountNumber = value;
    }

    /**
     * Gets the value of the accountType property.
     * 
     * @return
     *     possible object is
     *     {@link AccountType }
     *     
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Sets the value of the accountType property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountType }
     *     
     */
    public void setAccountType(AccountType value) {
        this.accountType = value;
    }

    /**
     * Gets the value of the cardType property.
     * 
     * @return
     *     possible object is
     *     {@link CardType }
     *     
     */
    public CardType getCardType() {
        return cardType;
    }

    /**
     * Sets the value of the cardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardType }
     *     
     */
    public void setCardType(CardType value) {
        this.cardType = value;
    }

    /**
     * Gets the value of the operationIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationIdentifier() {
        return operationIdentifier;
    }

    /**
     * Sets the value of the operationIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationIdentifier(String value) {
        this.operationIdentifier = value;
    }

}
