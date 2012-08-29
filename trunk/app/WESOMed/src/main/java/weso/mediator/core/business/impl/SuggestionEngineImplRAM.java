package weso.mediator.core.business.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import weso.mediator.config.Configuration;
import weso.mediator.core.business.AbstractSuggestionEngine;
import weso.mediator.core.business.SuggestionException;
import weso.mediator.core.domain.Suggestion;
import weso.mediator.core.domain.SuggestionWithLabel;
import weso.mediator.core.domain.lucene.IndexLucene;
import weso.mediator.core.domain.lucene.RAMDirectoryLucene;
import weso.mediator.core.persistence.Connector;
import weso.mediator.factory.PersistenceFactory;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

/**
 * This class is an implementation of SuggestionEngine based by Lucene using directories in RAM memory
 *
 */
public class SuggestionEngineImplRAM extends AbstractSuggestionEngine<RAMDirectoryLucene>{
	
private static Logger logger = Logger.getLogger(SuggestionEngineImplRAM.class);
	
	private PersistenceFactory factory;
	private Connector connector;
	
	public SuggestionEngineImplRAM() throws IOException {
		super();
		factory = new PersistenceFactory();
		connector = factory.getConnector(Configuration.getProperty("connector.class.name"));
	}
	
	public SuggestionEngineImplRAM(List<RAMDirectoryLucene> directories) throws IOException {
		super(directories);
		factory = new PersistenceFactory();
		connector = factory.getConnector(Configuration.getProperty("connector.class.name"));
	}

	@Override
	public boolean areIndexesCreated() {
		return !indexDirectories.isEmpty();
	}

	@Override
	public List<Suggestion> getSuggestions(String label, String directoryName)
			throws SuggestionException {
		logger.info("Start searching suggestions for the label \"" + label + "\" in the directory " + directoryName);
		IndexSearcher is = null;
		try {
			label = filterStopWords(label);
			//Obtain the Directory of Lucene to search for
			RAMDirectoryLucene directory = getDirectory(directoryName);
			is = new IndexSearcher(IndexReader.open(directory.getDirectory()));
			//Obtains all the analyzable fields of the directory
			String [] fields = getDirectoryFields(directory);
			logger.info("Performing a BasicQuery");
			//Create a QueryParser with the analyzable fields
			MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_36, fields, 
					new StandardAnalyzer(Version.LUCENE_36));
			//Split the label and perform a boolean simple query
			String [] parts = label.split(" ");
			String query = simpleQuery(parts);
			Query q = parser.parse(query);	
			TopDocs hits = is.search(q, 300);
			
			//If the results are empty, try a FuzzyQuery
			if(hits.scoreDocs.length == 0) {
				logger.info("The results are empty. Perform a FuzzyQuery");
				query = fuzzyQuery(parts);
				q = parser.parse(query);
				hits = is.search(q, 300);
			}
			//Obtain the list of results
			List<Suggestion> result = obtainResults(is, hits, directory.getReturnedIndex().getFieldName());
			return result;
		} catch (CorruptIndexException e) {
			logger.error("The index is corrupt when trying to get suggestions", e);
			throw new SuggestionException(e);
		} catch (IOException e) {
			logger.error("Error accessing the index when trying to get suggestions", e);
			throw new SuggestionException(e);
		} catch (ParseException e) {
			logger.error("Error parsing the query when trying to get suggestions", e);
			throw new SuggestionException(e);
		} finally {
			try {
				//Close the IndexSearcher to avoid too much open connections to a single Index
				if (is != null) is.close();
			} catch (IOException e) {
				logger.error("Error closing IndexSearcher when trying to get suggestions", e);
				throw new SuggestionException(e);
			}
		}
	}
	
	@Override
	public List<SuggestionWithLabel> getSuggestionsWithLabel(String label,
			String directoryName) throws SuggestionException {
		logger.info("Start searching suggestions for the label \"" + label + "\" in the directory " + directoryName);
		IndexSearcher is = null;
		try {
			label = filterStopWords(label);
			//Obtain the Directory of Lucene to search for
			RAMDirectoryLucene directory = getDirectory(directoryName);
			is = new IndexSearcher(IndexReader.open(directory.getDirectory()));
			//Obtains all the analyzable fields of the directory
			String [] fields = getDirectoryFields(directory);
			logger.info("Performing a BasicQuery");
			//Create a QueryParser with the analyzable fields
			MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_36, fields, 
					new StandardAnalyzer(Version.LUCENE_36));
			//Split the label and perform a boolean simple query
			String [] parts = label.split(" ");
			String query = simpleQuery(parts);
			Query q = parser.parse(query);	
			TopDocs hits = is.search(q, 300);
			
			//If the results are empty, try a FuzzyQuery
			if(hits.scoreDocs.length == 0) {
				logger.info("The results are empty. Perform a FuzzyQuery");
				query = fuzzyQuery(parts);
				q = parser.parse(query);
				hits = is.search(q, 300);
			}
			//Obtain the list of results
			List<SuggestionWithLabel> result = obtainResultsWithLabel(is, hits, 
					directory.getReturnedIndex().getFieldName(), fields);
			return result;
		} catch (CorruptIndexException e) {
			logger.error("The index is corrupt when trying to get suggestions", e);
			throw new SuggestionException(e);
		} catch (IOException e) {
			logger.error("Error accessing the index when trying to get suggestions", e);
			throw new SuggestionException(e);
		} catch (ParseException e) {
			logger.error("Error parsing the query when trying to get suggestions", e);
			throw new SuggestionException(e);
		} finally {
			try {
				//Close the IndexSearcher to avoid too much open connections to a single Index
				if (is != null) is.close();
			} catch (IOException e) {
				logger.error("Error closing IndexSearcher when trying to get suggestions", e);
				throw new SuggestionException(e);
			}
		}
	}
	
	/**
	 * This method obtains a list of entities with its labels and its probabilities.
	 * @param is The object IndexSearch that allow obtain entities and its labels in the directories of Lucene
	 * @param hits A list of documents of Lucene resulting from search in the directories
	 * @param fieldName The name of the field of the document that contain the id of the entities
	 * @param fields  Fields of documents that contains the label of the entities
	 * @return A list of entities
	 * @throws SuggestionException This exceptions is thrown is there are any problem with indexes of Lucene
	 */
	private List<SuggestionWithLabel> obtainResultsWithLabel(IndexSearcher is, TopDocs hits,
			String fieldName, String[] fields) throws SuggestionException {
		SortedSet<Suggestion> result = new TreeSet<Suggestion>();
		double [] scores = new double[hits.scoreDocs.length];
		//Obtain the field and the score of each document result by the query.
		for(int i = 0; i < hits.scoreDocs.length; i++) {
			ScoreDoc score = hits.scoreDocs[i];
			int docIndex = score.doc; 
			try {
				Document doc = is.doc(docIndex);
				String resourceUri = doc.getFieldable(fieldName).stringValue();
				float scoreResult = score.score;
				scores[i] = (double) scoreResult;
				//Obtain the label(s) of the entity
				SuggestionWithLabel sugg = new SuggestionWithLabel(resourceUri, scoreResult);
				for(int j = 0; j < fields.length; j++) {
					sugg.addLabel(doc.getFieldable(fields[j]).stringValue());
				}
				result.add(sugg);
			} catch (CorruptIndexException e) {
				logger.error("The index is corrupt when obtaining search results", e);
				throw new SuggestionException(e);
			} catch (IOException e) {
				logger.error("Error accessing the index when obtaining seach results", e);
				throw new SuggestionException(e);
			}
		}
		result = normalizeResults(scores, result);
		List<SuggestionWithLabel> suggestions = new LinkedList<SuggestionWithLabel>();
		for(Suggestion sug : result) {
			suggestions.add((SuggestionWithLabel)sug);
		}
		//Sort the list according to the probability
		Collections.sort(suggestions);
		return suggestions;
	}

	/**
	 * This method obtains a Collection of URI resources and his score after a search in Lucene.
	 * @param is The IndexSearcher of Lucene to search in the directory
	 * @param hits The results by the Lucene query.
	 * @param fieldName The name of the field that contain the id of the resources.
	 * @return A list with all resources ordered by Lucene score.
	 * @throws SuggestionException
	 */
	private List<Suggestion> obtainResults(IndexSearcher is, TopDocs hits,
			String fieldName) throws SuggestionException {
		SortedSet<Suggestion> result = new TreeSet<Suggestion>();
		double [] scores = new double[hits.scoreDocs.length];
		//Obtain the field and the score of each document result by the query.
		for(int i = 0; i < hits.scoreDocs.length; i++) {
			ScoreDoc score = hits.scoreDocs[i];
			int docIndex = score.doc; 
			try {
				Document doc = is.doc(docIndex);
				String resourceUri = doc.getFieldable(fieldName).stringValue();
				float scoreResult = score.score;
				scores[i] = (double) scoreResult;
				result.add(new Suggestion(resourceUri, scoreResult));
			} catch (CorruptIndexException e) {
				logger.error("The index is corrupt when obtaining search results", e);
				throw new SuggestionException(e);
			} catch (IOException e) {
				logger.error("Error accessing the index when obtaining seach results", e);
				throw new SuggestionException(e);
			}
		}
		result = normalizeResults(scores, result);
		return new LinkedList<Suggestion>(result);
	}

	private SortedSet<Suggestion> normalizeResults(double[] scores,
			SortedSet<Suggestion> results) {
		SortedSet<Suggestion> rank = new TreeSet<Suggestion>(); 
		Percentile per = new Percentile();
		double firstQuartile = per.evaluate(scores, 10.0);
		double secondQuartile = per.evaluate(scores, 90.0);
		
		rank = rankResults(scores, results, firstQuartile, secondQuartile);
		return rank;
	}

	/**
	 * This method obtain the probability of each result and ordered them in a collection.
	 * @param scores The scores results by the Lucene query.
	 * @param results A list with the entities result by the Lucene query.
	 * @param firstPercentile The first percentile of the scores to calculate the probablities of each entity.
	 * @param secondPercentile The second percentile of the scores to calculate the probabilities of each entity.
	 */
	private SortedSet<Suggestion> rankResults(double[] scores,
			SortedSet<Suggestion> results, double firstPercentile,
			double secondPercentile) {
		SortedSet<Suggestion> rank = new TreeSet<Suggestion>();
		//We obtain the acumulate value of the three parts in wich the scores are divided.
		double first = 0.0, second = 0.0, third = 0.0;
		for(int i = 0; i < scores.length; i++){
			if(scores[i] >= secondPercentile) {
				first += scores[i];
			} else if(scores[i] >= firstPercentile && scores[i] < secondPercentile) {
				second += scores[i];
			} else if(scores[i] < firstPercentile) {
				third += scores[i];
			}
		}		
		
		//Assing to each entity its probability.
		Iterator<Suggestion> iter = results.iterator();
		while(iter.hasNext()) {
			Suggestion sug = iter.next();
			if(sug.getProbability() >= secondPercentile) {
				float f = sug.getProbability() * (float)0.199999;
				float result = (f / (float)first) + (float)0.80;
				sug.setProbability(result);
			} else if(sug.getProbability() >= firstPercentile && sug.getProbability() < secondPercentile) {
				float f = sug.getProbability() * (float)0.60;
				float result = (f / (float)second) + (float)0.20;
				sug.setProbability(result);
			} else if(sug.getProbability() < firstPercentile) {
				float f = sug.getProbability() * (float)0.20;
				float result = f / (float)third;
				sug.setProbability(result + (float)0.001);
			}
			rank.add(sug);
		}
		
		return rank;
	}

	/**
	 * This method builds a BooleanQuery composed by a FuzzyQuery.
	 * @param parts
	 * @return The query of Lucene
	 */
	private String fuzzyQuery(String[] parts) {
		String query = "";
		for(String part : parts) {
			query += part + "~ OR ";
		}
		int last = query.lastIndexOf("OR");
		query = query.substring(0, last);
		return query;
	}

	/**
	 * This method builds a BooleanQuery composed by a simple TermQuery.
	 * @param parts
	 * @return The query of Lucene
	 */
	private String simpleQuery(String[] parts) {
		String query = "";
		for(String part : parts) {
			query += part + " OR ";
		}
		int last = query.lastIndexOf("OR");
		query = query.substring(0, last);
		return query;
	}

	@Override
	public void indexEntities(String directoryName, String query,
			List<IndexLucene> indexers) throws SuggestionException {
		logger.info("Begin the entity indexation " + directoryName);
		RAMDirectoryLucene directory = null;
		try {
			//Obtain the directory of the list
			logger.info("Search directory");
			directory = getDirectory(directoryName);
			directory.setDirectory(null);
			/*File f = new File(directory.getDirectoryPath());
			logger.info("Delete success of directory " + directoryName + 
					": " + Util.deleteDirectory(f) + f.getAbsolutePath());*/
		} catch (SuggestionException e) {
			logger.error("Directory not found with name " + directoryName + ". Create a new one");
			//If not exist the directory, create one
			IndexLucene returnedIndex = (IndexLucene)indexers.get(0);
			indexers.remove(0);
			directory = new RAMDirectoryLucene(directoryName, directoryName, returnedIndex, indexers);
			indexDirectories.add(directory);
			indexers.add(returnedIndex);
		}
		
		
		IndexWriter iw = null;
		try {
			//Create the indexWriter of Lucene
			RAMDirectory dir = new RAMDirectory();
			iw = new IndexWriter(dir, 
					new IndexWriterConfig(Version.LUCENE_36, new StandardAnalyzer(Version.LUCENE_36)));
			//Execute the query
			ResultSet rs = connector.executeQuery(query);
			while(rs.hasNext()) {
				Document doc = new Document();
				QuerySolution sol = rs.next();
				//for each result, create a document to index in the directory
				for(IndexLucene index : indexers) {
					//If the property isn't queryable, we obtain the value directly of the Index.
					String propertyValue = index.getProperty();
					//If the property is queryable, we obtain the value of the result of the query
					if(index.isQueryable()) {
						if(sol.get(index.getProperty()) != null){
							propertyValue = sol.get(index.getProperty()).toString();
							int pos= propertyValue.indexOf("^^");
							propertyValue = propertyValue.substring(0, (pos==-1?propertyValue.length():pos));
						} else {
							propertyValue = "";
						}
					}
					Field field = new Field(index.getFieldName(), true, propertyValue, Field.Store.YES, 
							index.getIndexOption(), index.getTermVectorOption());
					doc.add(field);
				}
				iw.addDocument(doc);
			}
			directory.setDirectory(dir);
		} catch (CorruptIndexException e) {
			logger.error("Error indexing entities", e);
			throw new SuggestionException(e);
		} catch (LockObtainFailedException e) {
			logger.error("Error indexing entities", e);
			throw new SuggestionException(e);
		} catch (IOException e) {
			logger.error("Error indexing entities", e);
			throw new SuggestionException(e);
		} finally {
				try {
					if(iw != null) iw.close();
				} catch (CorruptIndexException e) {
					logger.error("Error closing index writer", e);
					throw new SuggestionException(e);
				} catch (IOException e) {
					logger.error("Error closing index writer", e);
					throw new SuggestionException(e);
				}
		}		
	}
	
	/**
	 * This method obtains the analyzable fields of a directory.
	 * @param directory
	 * @return
	 */
	private String[] getDirectoryFields(RAMDirectoryLucene directory) {
		List<String> fields = new LinkedList<String>();
		for(IndexLucene index : directory.getIndexers()) {
			if(index.getIndexOption().equals(org.apache.lucene.document.Field.Index.ANALYZED)) {
				fields.add(index.getFieldName());
			}
		}
		if(directory.getReturnedIndex().getIndexOption().equals(org.apache.lucene.document.Field.Index.ANALYZED)) {
			fields.add(directory.getReturnedIndex().getFieldName());
		}
		String[] result = new String[fields.size()];
		return fields.toArray(result);
	}

}
