package weso.mediator.core.persistence.jena;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/*
 * This is a class to pass to wesomed the model to load entities to index. This model
 * can be passed through a physical file or a reference to the model.
 */
public class JenaModelFileWrapper {
	
	private static JenaModelFileWrapper instance = null;
	private Model model = null;
	
	public static JenaModelFileWrapper getInstance(){
		if(instance == null) {
			instance = new JenaModelFileWrapper();
		}
		return instance;
	}
	
	private JenaModelFileWrapper() {
		model = ModelFactory.createDefaultModel();
	}
	
	public void loadModelFromFile(File source) throws FileNotFoundException {
		model.read(new FileInputStream(source), "");
	}
	
	public void loadModelFromModel(Model sourceModel) {
		model.add(sourceModel);
	}
	
	public Model getModel() {
		return model;
	}

}
