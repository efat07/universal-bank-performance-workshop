//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.07.22 at 06:24:47 PM EDT 
//


package universal_bank.crm.enterprise_model.framework.common_artifacts._1_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CardType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CardType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DEBIT"/>
 *     &lt;enumeration value="CREDIT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CardType", namespace = "http://universal-bank/crm/enterprise-model/framework/common-artifacts/1.0.0")
@XmlEnum
public enum CardType {

    DEBIT,
    CREDIT;

    public String value() {
        return name();
    }

    public static CardType fromValue(String v) {
        return valueOf(v);
    }

}