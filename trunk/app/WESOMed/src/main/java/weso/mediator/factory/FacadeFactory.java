package weso.mediator.factory;

import weso.mediator.facade.WESOMed;
import weso.mediator.facade.WESOMedImpl;

public class FacadeFactory {
	private static WESOMed<?> suggestion;
	public static WESOMed<?> getFacade() {
		if(suggestion == null)
			suggestion = new WESOMedImpl();
		return suggestion;
	}
}
