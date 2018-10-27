package co.com.foundation.integration.util;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.integration.actor.exceptions.RetryException;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad Javeriana (Bogotá - Colombia) Departamento de Ingeniería de
 * Sistemas y Computación Licenciado bajo el esquema Academic Free License
 * version 2.1
 *
 * AES - PICA
 * (http://www.javeriana.edu.co/programas/especializacion-en-arquitectura-empresarial-de-software)
 * Ejercicio: universal bank integration Autor: PICA
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

public final class MQDispatcher {

	private static final Logger LOGGER = LogManager.getLogger(MQDispatcher.class);

	private ConnectionFactory connectionFactory;
	private Queue queue;

	public MQDispatcher(final ConnectionFactory connectionFactory, final Queue queue) {
		super();
		this.connectionFactory = connectionFactory;
		this.queue = queue;
	}

	public void dispatchMessage(final String message) {

		try {

			LOGGER.info("sending message to queue: {}", queue);
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);
			TextMessage textMessage = session.createTextMessage(message);
			producer.send(textMessage);

		} catch (JMSException e) {
			LOGGER.error("error sending message to queue: {}", queue,e);
			throw new RetryException("\"error sending message to queue",e);
		}
	}

}
