package weso.mediator.factory;

import weso.mediator.facade.WESOMed;
import weso.mediator.facade.WESOMedLucene;

public class FacadeFactory {
	private static WESOMed<?> mediator;

	public static WESOMed<?> getFacade() {
		if(mediator == null)
			mediator = new WESOMedLucene();
		return mediator;
	}
}
