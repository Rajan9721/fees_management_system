/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fees_management_system;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author rajan
 */
public class DBConnection {
    public static Connection getMysqlConnection(){
        
        Connection con = null;
        
        try{            
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fees_management", "root", "0170");            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return con;
        
    }
    
}
