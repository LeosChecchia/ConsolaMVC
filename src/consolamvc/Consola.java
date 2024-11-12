/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package consolamvc;

/**
 *
 * @author Leoch
 */




import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Consola {
    static Conexion conexion = new Conexion("sistemapedidos");
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();  // Limpiar el buffer
                switch (opcion) {
                    case 1:
                        crearCliente();
                        break;
                    case 2:
                        leerClientes();
                        break;
                    case 3:
                        editarCliente();
                        break;
                    case 4:
                        eliminarCliente();
                        break;
                    case 5:
                        System.out.println("Saliendo...");
                        conexion.desconectar();
                        break;
                    default:
                        System.out.println("Opción inválida.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número.");
                scanner.nextLine(); // Limpiar el buffer
                opcion = 0;
            }
        } while (opcion != 5);
    }

    // Vista: mostrar menú
    public static void mostrarMenu() {
        System.out.println("\nGestión de Clientes");
        System.out.println("1. Crear Cliente");
        System.out.println("2. Leer Clientes");
        System.out.println("3. Editar Cliente");
        System.out.println("4. Eliminar Cliente");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // Crear Cliente
    public static void crearCliente() {
        Cliente cliente = capturarDatosCliente();
        if (cliente == null) return;

        try (Connection cx = conexion.conectar();
             PreparedStatement stmt = cx.prepareStatement("INSERT INTO Clientes (nombre, apellido, direccion, telefono, empresa) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getDireccion());
            stmt.setString(4, cliente.getTelefono());
            stmt.setString(5, cliente.getEmpresa());

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Cliente creado exitosamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al crear el cliente: " + e.getMessage());
        }
    }

    // Leer Clientes
    public static void leerClientes() {
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        try (Connection cx = conexion.conectar();
             Statement stmt = cx.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Clientes")) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("empresa")
                );
                listaClientes.add(cliente);
            }

            mostrarClientes(listaClientes);
        } catch (SQLException e) {
            System.out.println("Error al leer los clientes: " + e.getMessage());
        }
    }

    // Mostrar Clientes
    public static void mostrarClientes(ArrayList<Cliente> listaClientes) {
        if (listaClientes.isEmpty()) {
            System.out.println("No hay clientes para mostrar.");
        } else {
            System.out.println("Lista de Clientes:");
            for (Cliente cliente : listaClientes) {
                System.out.println(cliente);
            }
        }
    }

    // Editar Cliente
    public static void editarCliente() {
        System.out.print("Ingrese el nombre del cliente a editar: ");
        String nombre = scanner.nextLine();
        Cliente nuevoCliente = capturarDatosCliente();

        try (Connection cx = conexion.conectar();
             PreparedStatement stmt = cx.prepareStatement("UPDATE Clientes SET apellido=?, direccion=?, telefono=?, empresa=? WHERE nombre=?")) {
            stmt.setString(1, nuevoCliente.getApellido());
            stmt.setString(2, nuevoCliente.getDireccion());
            stmt.setString(3, nuevoCliente.getTelefono());
            stmt.setString(4, nuevoCliente.getEmpresa());
            stmt.setString(5, nombre);

            int filasAfectadas = stmt.executeUpdate();
            System.out.println(filasAfectadas > 0 ? "Cliente actualizado exitosamente." : "Cliente no encontrado.");
        } catch (SQLException e) {
            System.out.println("Error al editar el cliente: " + e.getMessage());
        }
    }

    // Eliminar Cliente
    public static void eliminarCliente() {
        System.out.print("Ingrese el nombre del cliente a eliminar: ");
        String nombre = scanner.nextLine();

        try (Connection cx = conexion.conectar();
             PreparedStatement stmt = cx.prepareStatement("DELETE FROM Clientes WHERE nombre=?")) {
            stmt.setString(1, nombre);

            int filasAfectadas = stmt.executeUpdate();
            System.out.println(filasAfectadas > 0 ? "Cliente eliminado exitosamente." : "Cliente no encontrado.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar el cliente: " + e.getMessage());
        }
    }

    // Capturar datos del cliente
    public static Cliente capturarDatosCliente() {
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("Ingrese dirección: ");
        String direccion = scanner.nextLine();

        String telefono = "";
        while (true) {
            System.out.print("Ingrese teléfono (solo números): ");
            telefono = scanner.nextLine();
            try {
                validarTelefono(telefono);  // Validamos el teléfono
                break;  // Si no lanza excepción, salimos del bucle
            } catch (TelefonoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.print("Ingrese empresa: ");
        String empresa = scanner.nextLine();

        return new Cliente(nombre, apellido, direccion, telefono, empresa);
    }

    // Método para validar que el teléfono sea numérico
    public static void validarTelefono(String telefono) throws TelefonoInvalidoException {
        if (!telefono.matches("\\d+")) {  // Verifica que el teléfono solo contenga dígitos
            throw new TelefonoInvalidoException("El teléfono debe contener solo números.");
        }
    }
}

