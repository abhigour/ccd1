package com.ccd.shankara.nethra;

//import com.unbxd.ether.controller.*;
import com.ccd.shankara.controller.NethraAppController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.pippo.controller.ControllerApplication;
import ro.pippo.core.Pippo;

public class NethraApp extends ControllerApplication {
    private static final Logger LOGGER = LogManager.getLogger(NethraApp.class);

    public static void main(String[] args) {
        try {
            Pippo pippo = new Pippo(new NethraApp());
            pippo.start();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error initializing Ether apis,exiting...", e);
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void onInit() {
        // Get full taxonomy
//        GET("/sites/{siteKey}/taxonomy", TaxonomyController.class, "getFullTaxonomyHierarchy");
    	
//        GET("/sites/{siteKey}/taxonomy/{category_id}", TaxonomyController.class, "getCategoryNodeInfo");
//        GET("/sites/{siteKey}/taxonomy/{category_id}/siblings", TaxonomyController.class, "getCategorySiblings");
//        GET("/sites/{siteKey}/taxonomy/{category_id}/siblings/{parent_id}", TaxonomyController.class, "getCategorySiblingsByParentId");
//        GET("/sites/{siteKey}/taxonomy/{category_id}/children", TaxonomyController.class, "getCategoryChildren");
//        GET("/sites/{siteKey}/taxonomy/{category_id}/neighbours", TaxonomyController.class, "getCategoryNeighbours");
//        GET("/sites/{siteKey}/taxonomy/{category_id}/neighbours/{parent_id}", TaxonomyController.class, "getCategoryNeighboursByParentid");
//
//        PUT("/sites/{siteKey}/taxonomy", TaxonomyController.class, "createNewTaxonomy");
//        POST("/sites/{siteKey}/admin", TaxonomyController.class, "processAdmin");
//
        GET("/monitor", NethraAppController.class, "monitor");
        GET("/getdatabsename", NethraAppController.class, "getDatabaseName");
        // This is internally resolvable data. Patient ID. 
        GET("/patients/{petientId}", NethraAppController.class, "fetchRecentAppointment"); // return Doctors Name. 
        //POST("/pateints/{petientId}", NethraAppController.class, "postQuestion");
        POST("/admin", NethraAppController.class, "admin");
        POST("/admin/{collName}", NethraAppController.class, "createCollection");
        GET("/admin/collections", NethraAppController.class, "getCollections");
        
        
    }
}