package com.ccd.shankara.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import com.ccd.shankara.nethra.NethraApp;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import lombok.NoArgsConstructor;
import ro.pippo.controller.Controller;

@NoArgsConstructor
public class NethraAppController extends Controller {
    private static final Logger LOGGER = LogManager.getLogger(NethraApp.class);
    final static String USER_PASS = "<UserId>:<Password>";
    //final static String MONGO_URI_STRING = "mongodb://" + USER_PASS + "@cluster0-shard-00-00-7x3py.mongodb.net:27017,cluster0-shard-00-01-7x3py.mongodb.net:27017,cluster0-shard-00-02-7x3py.mongodb.net:27017/test?ssl=false&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true";
    final static String MONGO_URI_STRING = "mongodb+srv://" + USER_PASS + "@cluster0-7x3py.mongodb.net/test?retryWrites=true";
    final static String DBNAME = "nethra";
    final static String DISCUSSIONS_TABLE = "discussions"; // collection
    final static String VISITS_DOC_VALUE = 	"[" + 
			"    {" +
			"      doctor_name: doctor1," + 
			"      doctor_id: id_1," + 
			"      visit_date: 1-sept-2018," + 
			"      appointment_id: 123" + 
			"    }," + 
			"    {" + 
			"      doctor_name: doctor2," + 
			"      doctor_id: id_2," + 
			"      visit_date: 1-sept-2018," + 
			"      appointment_id: 1234" + 
			"    }" +
			"  ]";
    final static String VISITS_KEY = "visits";
    final static String PATIENT_COLLECTION_ID = "patientId";
    final static String PATIENT_COLLECTION_ID_VALUE = "IN1SEPT3";
    
    static MongoDatabase mongoDb = null;
    
    
    public void monitor() {
    	this.getResponse().send("Demo String");
    }
    
    public void getDatabaseName() {
    	this.getResponse().send(getDatabase(DBNAME).getName());
    }
    
    private MongoDatabase getDatabase(String dbName) {
    	if (mongoDb == null) {
    		try {
	        	//int connectTimeout = 1000 * 60; // 60 Seconds is actually too much. We could work with lesser time out. 
	        	//MongoClientOptions options = new MongoClientOptions.Builder().serverSelectionTimeout(connectTimeout).build();
	        	MongoClientURI uri = new MongoClientURI(MONGO_URI_STRING);
				MongoClient mongoClient = new MongoClient(uri); 
				mongoDb = mongoClient.getDatabase(dbName); // Change the name of test.	'
    		} catch (Exception e) {
    			System.out.println("Error in inserting row - " + e.getMessage());
    		}
    	}
		return mongoDb;
    }
    
    public void initCollection(MongoDatabase database, String collectionName) {
    	MongoCollection<Document> collection = database.getCollection(collectionName); // This would create collection as well.
    	
    	// TODO - this should not be hard coded. Some other way to create collection and adding rows. 
    	Document patientvisit = new Document(PATIENT_COLLECTION_ID, PATIENT_COLLECTION_ID_VALUE);
    	patientvisit.append(VISITS_DOC_VALUE, VISITS_DOC_VALUE);
    	try {
    		collection.insertOne(patientvisit); 
    	} catch (Exception e) {
    		System.out.println("Error in inserting row - " + e.getMessage()); 
    	}
    }
    
    public void createCollection() {
    	try {
    		String collName = getRequest().getParameter("collName").toString();
    		if (collName.isEmpty() || collName == null) {
    			collName = DISCUSSIONS_TABLE;
    		}
    		initCollection(getDatabase(DBNAME), collName);
    		getResponse().status(200);
    		getResponse().send("Collection Creation Successful");
    	} catch (Exception e) {
    		getResponse().status(500);
    		getResponse().send("Collection initialization Error - " + e.getMessage()); // TODO dont send the message on Webpage.
    	}
    	
    }
    
    public void getCollections() {
    	MongoIterable<String> collections = getDatabase(DBNAME).listCollectionNames();
    	getResponse().json().send(collections);
    }
    
    public void admin() {
    	try {
    		//String collName = getRequest().getParameter("collName").toString();
    		//initMongo(getDatabase(DBNAME), DISCUSSIONS_TABLE); // The initialization should not be part of admin. But the call createCollectino
    		getDatabase(DBNAME);
    		getResponse().status(200);
    		getResponse().send("Databse initialization Successful");
    	} catch (Exception e) {
    		getResponse().status(500);
    		getResponse().send("Databse initialization Error - " + e.getMessage()); // TODO dont send the message on Webpage.
    	}
    	
    }
    
    public void fetchRecentAppointment() {
    	try {
    		Document discussionDocument = getDatabase(DBNAME).getCollection(DISCUSSIONS_TABLE).findOneAndDelete(null);
    		getResponse().json().send(discussionDocument);
    	} catch (Exception e){
    		System.out.println(e.toString());
    		// TODO
    	}
    	
    }
}
