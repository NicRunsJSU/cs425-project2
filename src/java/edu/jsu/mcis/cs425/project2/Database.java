
package edu.jsu.mcis.cs425.project2;



import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONArray;


import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
//attempting to fix parsing error
public class Database {
    //
    String sessID;
    
    
    public HashMap getUserInfo(String username) throws SQLException {
        
        HashMap<String, String> results = null;
       
        
        try { 
        
            Connection conn = getConnection();
            
            String query = "SELECT * FROM  user WHERE username = ?";
            PreparedStatement pstatement = conn.prepareStatement(query);
            pstatement.setString(1, username);
            
            boolean hasresults = pstatement.execute();
            
            if (hasresults){
               
                HashMap hm = new HashMap<>();
                
                ResultSet resultset = pstatement.getResultSet();
                
                if (resultset.next()){
                    
                    //initialize HashMap; add user data from resultset
                    //use key name "id" for the ID, and "displayname" for the 
                    // displayname
                    
                    
                   String id = String.valueOf(resultset.getInt("id"));
                   String displayName = resultset.getString("displayname");
                   results = new HashMap<>();
                   results.put("id", id);
                   results.put("displayname", displayName);
                   
                    
                }
                
                
            }
            
            return results;
        }
       finally{}
                
    }
    
    private Connection getConnection() {
        
        Connection conn = null;
        
        try {
            
            Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/db_pool");
            conn = ds.getConnection();
            
        }        
        catch (Exception stinkystinky) { stinkystinky.printStackTrace(); }
        
        return conn;

    }
}