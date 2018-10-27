package co.com.foundation.integration.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

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

public class XMLUtils {

	private static final Logger LOGGER = LogManager.getLogger(XMLUtils.class);
	
	public static String evaluateExpression(final String expression, final String document) {

		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document doc = builder.parse(IOUtils.toInputStream(document, "UTF8"));
			XPathFactory xPathFactory = XPathFactory.newInstance();

			XPath xpath = xPathFactory.newXPath();
			XPathExpression xPathExpr = xpath.compile(expression);
			return (String) xPathExpr.evaluate(doc, XPathConstants.STRING);
		} catch (Exception e) {
			LOGGER.error("error evaluating xpath expression", e);
			return null;
		}
	}

}
