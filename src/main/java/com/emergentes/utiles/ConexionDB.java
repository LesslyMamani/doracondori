
package com.emergentes.utiles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionDB {
    
    static String driver = "com.mysql.jdbc.Driver";
    static String url="jdbc:mysql://localhost:3306/te_libros";
    static String usuario="root";
    static String password="123456789";
//este es el password de la base de dto
    
    
    Connection conn=null;

    public ConexionDB() {
        try {
            Class.forName(driver);
            conn=DriverManager.getConnection(url,usuario,password);
            if(conn!=null){
                System.out.println("Conexion OK"+ conn);
            }
        } catch (ClassNotFoundException e) {
          //desde la consola lo colocamos
          System.out.println("Error de Driver : "+e.getMessage()); 
//en caso de que falle aparece el error de sql
          
        }      catch (SQLException ex) {
          //desde la consola lo colocamos
          System.out.println("Error de Error al Connectar: "+ex.getMessage()); 
//en caso de que falle aparece el error de sql
           }
         }
    
public Connection conectar(){
    return conn;
}    

public void desconectar()
        {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        }   
}   
}

