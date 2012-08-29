package weso.mediator.factory;

import weso.mediator.facade.WESOMed;
import weso.mediator.facade.WESOMedLucene;

public class WESOMedFactory {
	private static WESOMed<?> mediator;

	public static WESOMed<?> getWESOMed() {
		if(mediator == null)
			mediator = new WESOMedLucene();
		return mediator;
	}
}
