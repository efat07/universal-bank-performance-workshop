//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.07.22 at 06:24:47 PM EDT 
//


package universal_bank.crm.enterprise_model.framework.domain_events._1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for financialActionDomainEvents complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="financialActionDomainEvents">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addCustomerDomainEvent" type="{http://universal-bank/crm/enterprise-model/framework/domain-events/1.0.0}addCustomerDomainEvent" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "financialActionDomainEvents", propOrder = {
    "addCustomerDomainEvent"
})
public class FinancialActionDomainEvents {

    protected AddCustomerDomainEvent addCustomerDomainEvent;

    /**
     * Gets the value of the addCustomerDomainEvent property.
     * 
     * @return
     *     possible object is
     *     {@link AddCustomerDomainEvent }
     *     
     */
    public AddCustomerDomainEvent getAddCustomerDomainEvent() {
        return addCustomerDomainEvent;
    }

    /**
     * Sets the value of the addCustomerDomainEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddCustomerDomainEvent }
     *     
     */
    public void setAddCustomerDomainEvent(AddCustomerDomainEvent value) {
        this.addCustomerDomainEvent = value;
    }

}
