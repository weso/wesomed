package main.java.weso.mediator.factory;

import main.java.weso.mediator.facade.WESOMed;
import main.java.weso.mediator.facade.WESOMedImpl;

public class FacadeFactory {
	private static WESOMed<?> suggestion;
	public static WESOMed<?> getFacade() {
		if(suggestion == null)
			suggestion = new WESOMedImpl();
		return suggestion;
	}
}
