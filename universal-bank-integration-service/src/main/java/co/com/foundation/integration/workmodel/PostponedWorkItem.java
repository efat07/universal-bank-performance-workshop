package co.com.foundation.integration.workmodel;

import java.util.UUID;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ObjectUtils;

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

public class PostponedWorkItem implements Delayed {

	private String id = UUID.randomUUID().toString();
	private long origin;
	private long delay;
	private WorkItem workItem;

	public PostponedWorkItem(final WorkItem workItem, final long delay) {
		this.origin = System.currentTimeMillis();
		this.workItem = workItem;
		this.delay = delay;
	}
	
	public String getId() {
		return id;
	}

	public WorkItem delegateWorkItem() {
		WorkItem copy = ObjectUtils.cloneIfPossible(workItem);
		this.workItem = null;
		return copy;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(delay - (System.currentTimeMillis() - origin), TimeUnit.MILLISECONDS);
	}

	@Override
	public int compareTo(Delayed delayed) {
		if (delayed == this) {
			return 0;
		}

		if (delayed instanceof PostponedWorkItem) {
			long diff = delay - ((PostponedWorkItem) delayed).delay;
			return ((diff == 0) ? 0 : ((diff < 0) ? -1 : 1));
		}

		long d = (getDelay(TimeUnit.MILLISECONDS) - delayed.getDelay(TimeUnit.MILLISECONDS));
		return ((d == 0) ? 0 : ((d < 0) ? -1 : 1));
	}

	@Override
	public String toString() {
		return "PostponedWorkItem [origin=" + origin + ", delay=" + delay + ", workItem=" + workItem + "]";
	}

}