/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package consolamvc;

/**
 *
 * @author Leoch
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    // Parámetros de conexión
    String bd = "sistemapedidos";  // Base de datos
    String url = "jdbc:mysql://localhost:3306/";  // URL base de MySQL (sin la base de datos al final)
    String user = "root";  // Usuario de MySQL
    String password = "123456";  // Contraseña de MySQL
    String driver = "com.mysql.cj.jdbc.Driver";  // Driver actualizado para MySQL
    Connection cx;  // Objeto para la conexión

    // Constructor que permite establecer la base de datos
    public Conexion(String bd) {
        this.bd = bd;  // Asignar base de datos recibida como parámetro
    }

    // Método para conectar a la base de datos
    public Connection conectar() {
        try {
            // Cargar el driver
            Class.forName(driver);
            
            // Establecer la conexión usando los parámetros (URL, usuario y contraseña)
            cx = DriverManager.getConnection(url + bd, user, password);
            System.out.println("Conexion exitosa a la base de datos: " + bd);  // Mensaje de éxito
        } catch (ClassNotFoundException | SQLException ex) {
            // Si hay un error, se imprime el mensaje y se registra en el logger
            System.err.println("No se pudo conectar a la base de datos: " + bd);
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cx;  // Retornar la conexión (null si falló)
    }

    // Método para desconectar de la base de datos
    public void desconectar() {
        try {
            if (cx != null && !cx.isClosed()) {  // Verificar que la conexión no esté cerrada
                cx.close();  // Cerrar la conexión
                System.out.println("Desconexión exitosa de la base de datos: " + bd);  // Mensaje de éxito
            }
        } catch (SQLException ex) {
            // Si hay un error cerrando la conexión, se registra
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Método principal para prueba (opcional)
    public static void main(String[] args) {
        // Crear una instancia de la conexión para la base de datos "sistemapedidos"
        Conexion conexion = new Conexion("sistemapedidos");
        
        // Conectar a la base de datos
        Connection conn = conexion.conectar();
        
        // Realizar alguna operación si es necesario
        
        // Desconectar de la base de datos
        conexion.desconectar();
    }
}
