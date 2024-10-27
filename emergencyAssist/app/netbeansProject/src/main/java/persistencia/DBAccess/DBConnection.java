/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DBAccess;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import servicioscomunes.excepciones.DBException;

/**
 *
 * @author yania
 * TODO: Exception handling like this is not acceptable! Change it!
 */
public class DBConnection {
    
    // class level (singleton)
    private static DBConnection theDBConnection;
    
    public static DBConnection getInstance() throws DBException {
        if (theDBConnection == null) {
            Properties prop = new Properties();
            InputStream read; 
            String url, user, password;
            try {
                // config.db file should be copied into classpath together with DBConnection.class
                read = DBConnection.class.getResourceAsStream("/config.db");
                prop.load(read);
                url = prop.getProperty("url");
                user = prop.getProperty("user");
                password = prop.getProperty("password");
                read.close(); 
                theDBConnection = new DBConnection(url, user, password);
            } catch (FileNotFoundException e) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE,"DB configuration file not found.", e);     
                throw new DBException("DB configuration file not found.", e);
            } catch (IOException e) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Couln't read DB configuration file.", e);  
                throw new DBException("Couln't read DB configuration file.", e);
            }      
        }
        return theDBConnection;
    }

    // instance level
    private Connection conn;
    private String url;
    private String user;
    private String password;

    private DBConnection(String url, String user, String password) throws DBException {        
        this.url = url;
        this.user = user;
        this.password = password;
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver"); 
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Derby driver not found.", ex);
            throw new DBException("Derby driver not found.", ex);
        }
    }
    
    public void openConnection() throws DBException {
        try {
            conn = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error openning db connection.", ex);
            throw new DBException("Error openning db connection.", ex);
        }
    }
    
    public void closeConnection() throws DBException {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error closing db connection.", ex);
            throw new DBException("Error closing db connection.", ex);
        }
    }

    public PreparedStatement getStatement(String s) throws DBException {
        PreparedStatement stmt = null;
        try {
                stmt = conn.prepareStatement(s);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error getting db statment.", ex);    
            throw new DBException("Error getting db statment.", ex);
        }
        return stmt;
    }
    
}


