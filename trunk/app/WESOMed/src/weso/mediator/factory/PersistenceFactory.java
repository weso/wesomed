package weso.mediator.factory;

import weso.mediator.core.persistence.Connector;

public class PersistenceFactory {
	private Connector connector;
	
	public Connector getConnector(String className){
		if(connector == null){
			try {
				Class<?> clazz = Class.forName(className);		
				connector = (Connector) clazz.newInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		return connector;
	}
}
