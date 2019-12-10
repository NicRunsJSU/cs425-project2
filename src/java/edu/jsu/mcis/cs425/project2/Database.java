
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
    public String getSkillsListAsHTML(int userid) throws SQLException {

        StringBuilder skills = new StringBuilder();
        String skillsList;
        
        try {
            Connection conn = getConnection();
            
            String query = "SELECT skills.*, a.userid FROM skills LEFT JOIN ( SELECT * FROM applicants_to_skills WHERE userid = 1) AS a ON skills.id = a.skillsid; ";
            
            PreparedStatement pstatement = conn.prepareStatement(query);
            
            boolean hasresults = pstatement.execute();
            
            if (hasresults) {
                ResultSet resultSet = pstatement.getResultSet();
                
                while (resultSet.next()) {
                    String description = resultSet.getString("description");
                    int id = resultSet.getInt("id");
                    int user =resultSet.getInt("userid");
                    
                    
                    skills.append("<input type =\"checkbox\" name=\"skills\" value=\"");
                    skills.append("id");
                    skills.append("\" id=\"skills_").append(id).append("\" ");
                    
                    if(user != 0) {
                        skills.append("checked ");
                    }
                    
                    skills.append(">\n");
                    
                    skills.append("<label for=\"skills_").append(id).append("\">");
                    skills.append(description);
                    skills.append("</label><br />\n\n");
                    
                }
                
                
            }
            
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        skillsList = skills.toString();
        
        return skillsList;
      
    }
    public void setSkillsList(int userid, String[] skills){
        
        try{
               Connection conn = getConnection();
              
               PreparedStatement stmt = conn.prepareStatement("DELETE FROM applicants_to_skills WHERE userid = ?");
               stmt.setInt(1, userid);
               stmt.execute();
               PreparedStatement pstatement = conn.prepareStatement("INSERT INTO applicants_to_skills (userid, skillsid) VALUES(?,?)" );
                for (String skill : skills) {
                    int skillsid = Integer.parseInt(skill);
                    pstatement.setInt(1, userid);
                    pstatement.setInt(2, skillsid);
                    pstatement.addBatch();
                }
                int[] r= pstatement.executeBatch();
        }
        catch(Exception e){
            e.printStackTrace();
        
        }

    }
}
    

