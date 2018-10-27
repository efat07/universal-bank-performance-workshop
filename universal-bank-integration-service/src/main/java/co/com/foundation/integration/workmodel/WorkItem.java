package co.com.foundation.integration.workmodel;

import java.io.Serializable;
import java.util.UUID;

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

public class WorkItem implements Serializable {

	public static enum WORK_STATE{ CREATED, ASSIGNED_TO_MASTER, ASSIGNED_TO_WORKER };
	
	private static final long serialVersionUID = 1L;

	private String id = UUID.randomUUID().toString();
	private WORK_STATE status = WORK_STATE.CREATED;
	private String xmlContent;
	private int attempts = 0;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getXmlContent() {
		return xmlContent;
	}

	public void setXmlContent(String xmlContent) {
		this.xmlContent = xmlContent;
	}

	public int getAttempts() {
		return attempts;
	}

	public void trackAttempt() {
		attempts++;
	}
	
	public void markAsAssignedToMaster() {
		status = WORK_STATE.ASSIGNED_TO_MASTER;
	}
	
	public void markAsAssignedToWorker() {
		status = WORK_STATE.ASSIGNED_TO_WORKER;
	}

	public WORK_STATE getStatus() {
		return status;
	}
	

}
