package edu.jsu.mcis.cs425.project2;

import java.sql.SQLException;
import java.util.HashMap;

public class BeanApplicant {
    
    private String username;
    private String displayName;
    private int id;
    
    
    public void setUserInfo() throws SQLException{
        
        Database db = new Database();
        HashMap<String, String> userinfo = db.getUserInfo(username);
        id = Integer.parseInt(userinfo.get("id"));
        displayName = userinfo.get("displayname");

    }

    public String getDisplayName() {
        return displayName;
    }

    public int getId() {
        return id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}