package org.example;

import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.sql.*;
import java.util.Scanner;


public class Principal_MySQL {
    public static void main(String[] args) throws SQLException, IOException, CsvValidationException {
        // Try para comprobar si está el connector:
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("No se ha encontrado el driver para MySQL");
            return;
        }
        System.out.println("Se ha cargado el Driver de MySQL");

        // Paso 2: Establecer conexión con la base de datos
        String cadenaConexion = "jdbc:mysql://localhost:3306/hitoindividualad?allowLoadLocalInfile=true";
        String user = "root";
        String pass = "curso";
        Connection con;
        try {
            con = DriverManager.getConnection(cadenaConexion, user, pass);
        } catch (SQLException e) {
            System.out.println("Error en la conexión con la BD");
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Se ha establecido la conexión con la Base de datos");

        // Paso 3: Interactuar con la BD

        Scanner sc = new Scanner(System.in);

        int entradaTeclado = 0;
        ResultSet rs;
        while (!(entradaTeclado == 3)) {
            System.out.println("Teclee la opción que desee:");
            System.out.println("1. Interactuar con los libros");
            System.out.println("2. Interactuar con los clientes");
            System.out.println("3. Terminar el programa.");
            entradaTeclado = sc.nextInt();

            switch (entradaTeclado) {
                case 1:
                    System.out.println("Has elegido la opción de interactuar con los libros.");
                    int entradaTecladoLibros = 0;
                    while (!(entradaTecladoLibros == 7)) {
                        System.out.println("Teclee la opción que desee:");
                        System.out.println("1. Registrar un libro nuevo");
                        System.out.println("2. Mostrar todos los libros");
                        System.out.println("3. Editar un libro");
                        System.out.println("4. Eliminar un libro.");
                        System.out.println("5. Exportar a archivo CSV (Crear copia de seguridad)");
                        System.out.println("6. Importar de archivo CSV (Restablecer de copia de seguridad)");
                        System.out.println("7. Terminar las operaciones y volver al menú principal.");
                        entradaTecladoLibros = sc.nextInt();

                        switch (entradaTecladoLibros) {
                            case 1:
                                System.out.println("Has elegido la opción de registrar un nuevo libro");
                                try{
                                    Statement sentencia = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                                    rs = sentencia.executeQuery("select * from libro");
                                    System.out.println("Introduzca los datos del nuevo libro:");
                                    String buff=sc.nextLine();
                                    System.out.println("Título del libro:");
                                    String titulo=sc.nextLine();
                                    System.out.println("Autor del libro:");
                                    String autor=sc.nextLine();
                                    System.out.println("Editorial:");
                                    String editorial=sc.nextLine();
                                    System.out.println("Stock:");
                                    int stock=sc.nextInt();
                                    System.out.println("Precio:");
                                    float precio=sc.nextFloat();
                                    rs.moveToInsertRow();
                                    rs.updateString("titulo",titulo);
                                    rs.updateString("autor",autor);
                                    rs.updateString("editorial",editorial);
                                    rs.updateInt("stock",stock);
                                    rs.updateFloat("Precio",precio);
                                    rs.insertRow();
                                    System.out.println("Libro insertado correctamente");
                                }catch (SQLException e){
                                    System.out.println("Error al introducir el nuevo libro");
                                    System.out.println(e.getMessage());
                                }
                                break;

                            case 2:
                                System.out.println("Has elegido la opción de mostrar los libros");
                                System.out.println("Listado de libros:");
                                try{
                                    Statement sentencia = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                                    rs = sentencia.executeQuery("select * from libro");
                                    while (rs.next()) {
                                        mostrarFila2(rs);
                                    }
                                } catch (SQLException e){
                                    System.out.println("Error al mostrar el listado de libros");
                                    System.out.println(e.getMessage());
                                }
                                break;

                            case 3:
                                System.out.println("Has elegido la opción de editar un libro");
                                try {
                                    Statement sentencia = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                                    System.out.println("Introduzca el titulo del libro a modificar");
                                    String buff=sc.nextLine();
                                    String n = sc.nextLine();
                                    System.out.println("Introduzca el titulo modificado");
                                    String tituloNuevo = sc.nextLine();
                                    System.out.println("Introduzca el nombre del autor modificado");
                                    String autorNuevo = sc.nextLine();
                                    System.out.println("Introduzca la editorial modificada:");
                                    String editorialNueva = sc.nextLine();
                                    System.out.println("Introduzca el stock de libros modificado:");
                                    int stockNuevo = sc.nextInt();
                                    System.out.println("Introduzca el precio del libro modificado:");
                                    float precioNuevo = sc.nextFloat();
                                    rs = sentencia.executeQuery("select * from libro where titulo='"+n+"'");

                                    boolean existe = rs.next();
                                    if(existe){
                                        rs.updateString("titulo",tituloNuevo);
                                        rs.updateString("autor",autorNuevo);
                                        rs.updateString("editorial",editorialNueva);
                                        rs.updateInt("stock",stockNuevo);
                                        rs.updateFloat("precio",precioNuevo);
                                        rs.updateRow();
                                    }

                                    System.out.println("Cambio aplicado correctamente.");

                                } catch (SQLException e) {
                                    System.out.println("Error al modificar los datos");
                                    System.out.println(e.getMessage());
                                }
                                break;

                            case 4:
                                System.out.println("Has elegido la opción de eliminar un libro");
                                try{
                                    Statement sentencia = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                                    System.out.println("Introduzca el ID del libro a eliminar:");
                                    int id = sc.nextInt();
                                    rs = sentencia.executeQuery("select * from libro where id_libro='"+id+"'");
                                    boolean existe = rs.next();
                                    if(existe){
                                        rs.deleteRow();
                                    }
                                    System.out.println("Libro eliminado correctamente");
                                }catch (SQLException e) {
                                    System.out.println("Error al realizar la operación");
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 5:
                                System.out.println("Has elegido la opción de exportar a archivo CSV (Crear copia de seguridad)");
                                System.out.println("Creando copia de seguridad...");
                                try {
                                    PrintWriter pw= new PrintWriter("archivoCSV_Libros_MySQL.csv","UTF-8");
                                    StringBuilder sb=new StringBuilder();

                                    String query="select * from libro";
                                    PreparedStatement ps=con.prepareStatement(query);
                                    rs=ps.executeQuery();

                                    while(rs.next()){
                                        sb.append(rs.getInt("id_libro"));
                                        sb.append(",");
                                        sb.append(rs.getString("titulo"));
                                        sb.append(",");
                                        sb.append(rs.getString("autor"));
                                        sb.append(",");
                                        sb.append(rs.getString("editorial"));
                                        sb.append(",");
                                        sb.append(rs.getInt("stock"));
                                        sb.append(",");
                                        sb.append(rs.getFloat("precio"));
                                        sb.append("\r\n");
                                    }

                                    pw.write(sb.toString());
                                    pw.close();
                                    System.out.println("Copia de seguridad creada con éxito.");
                                } catch (Exception e){
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 6:
                                System.out.println("Has elegido la opción de importar desde archivo CSV (Restablecer de copia de seguridad)");
                                System.out.println("Recopilando datos de copia de seguridad...");
                                PreparedStatement consulta;
                                PreparedStatement consulta1;
                                String url="archivoCSV_Libros_MySQL.csv";
                                consulta1 = con.prepareStatement("SET GLOBAL local_infile = 1");
                                consulta = con.prepareStatement("LOAD DATA LOCAL INFILE '"+url+"' INTO TABLE libro FIELDS TERMINATED BY ',' LINES TERMINATED BY '\r\n' ");
                                consulta1.executeUpdate();
                                consulta.executeUpdate();
                                System.out.println("Copia de seguridad restablecida correctamente");
                                break;
                            case 7:
                                System.out.println("Has elegido la opción de terminar estas operaciones y volver al menú principal.");
                                break;
                            default:
                                System.out.println("Introduzca un caracter válido");
                        }
                    }
                    break;

                case 2:
                    System.out.println("Has elegido la opción de interactuar con los clientes.");
                    int entradaTecladoClientes = 0;
                    while (!(entradaTecladoClientes == 7)) {
                        System.out.println("Teclee la opción que desee:");
                        System.out.println("1. Crear un nuevo cliente");
                        System.out.println("2. Mostrar todos los clientes");
                        System.out.println("3. Editar un cliente");
                        System.out.println("4. Eliminar un cliente.");
                        System.out.println("5. Exportar a archivo CSV (Crear copia de seguridad)");
                        System.out.println("6. Importar de archivo CSV (Restablecer de copia de seguridad)");
                        System.out.println("7. Terminar el programa y volver al menú principal.");
                        entradaTecladoClientes = sc.nextInt();

                        switch (entradaTecladoClientes) {
                            case 1:
                                System.out.println("Has elegido la opción de crear un nuevo cliente");
                                try{
                                    Statement sentencia = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                                    rs = sentencia.executeQuery("select * from cliente");
                                    System.out.println("Introduzca los datos del nuevo cliente:");
                                    String buff=sc.nextLine();
                                    System.out.println("Nombre del cliente:");
                                    String nombre=sc.nextLine();
                                    System.out.println("Apellidos de cliente:");
                                    String apellidos=sc.nextLine();
                                    System.out.println("Teléfono:");
                                    String tlf=sc.nextLine();
                                    System.out.println("Introduzca el ID del libro prestado por este cliente:");
                                    int id_libro=sc.nextInt();
                                    rs.moveToInsertRow();
                                    rs.updateString("nombre",nombre);
                                    rs.updateString("apellidos",apellidos);
                                    rs.updateString("tlf",tlf);
                                    rs.updateInt("id_libro",id_libro);
                                    rs.insertRow();
                                    System.out.println("Cliente insertado correctamente");
                                }catch (SQLException e){
                                    System.out.println("Error al introducir el nuevo cliente");
                                    System.out.println(e.getMessage());
                            }
                                break;

                            case 2:
                                System.out.println("Has elegido la opción de mostrar los clientes");
                                System.out.println("Listado de clientes:");
                                try{
                                    Statement sentencia = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                                    rs = sentencia.executeQuery("select * from cliente");
                                    while (rs.next()) {
                                        mostrarFila(rs);
                                    }
                                } catch (SQLException e){
                                    System.out.println("Error al mostrar el listado de clientes");
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 3:
                                System.out.println("Has elegido la opción de editar un cliente");
                                try {
                                    Statement sentencia = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                                    System.out.println("Introduzca el nombre deL cliente a modificar");
                                    String buff=sc.nextLine();
                                    String n = sc.nextLine();
                                    System.out.println("Introduzca el nombre modificado");
                                    String nombreNuevo = sc.nextLine();
                                    System.out.println("Introduzca los apellidos modificados");
                                    String apellidosNuevos = sc.nextLine();
                                    System.out.println("Introduzca el teléfono modificado:");
                                    String tlfNuevo = sc.nextLine();
                                    System.out.println("Introduzca el libro prestado modificado:");
                                    int id_libroNuevo = sc.nextInt();
                                    rs = sentencia.executeQuery("select * from cliente where nombre='"+n+"'");

                                    boolean existe = rs.next();
                                    if(existe){
                                        rs.updateString("nombre",nombreNuevo);
                                        rs.updateString("apellidos",apellidosNuevos);
                                        rs.updateString("tlf",tlfNuevo);
                                        rs.updateInt("id_libro",id_libroNuevo);
                                        rs.updateRow();
                                    }

                                    System.out.println("Cambio realizado correctamente.");

                                } catch (SQLException e) {
                                    System.out.println("Error al modificar los datos");
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 4:
                                System.out.println("Has elegido la opción de eliminar un cliente");
                                try{
                                    Statement sentencia = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                                    System.out.println("Introduzca el ID del cliente a eliminar:");
                                    int id = sc.nextInt();
                                    rs = sentencia.executeQuery("select * from cliente where id_usuario='"+id+"'");
                                    boolean existe = rs.next();
                                    if(existe){
                                        rs.deleteRow();
                                    }
                                    System.out.println("Cliente eliminado correctamente");
                                }catch (SQLException e) {
                                    System.out.println("Error al realizar la operación");
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 5:
                                System.out.println("Has elegido la opción de exportar a archivo CSV (Crear copia de seguridad)");
                                System.out.println("Creando copia de seguridad...");
                                try {
                                    PrintWriter pw= new PrintWriter("archivoCSV_Clientes_MySQL.csv","UTF-8");
                                    StringBuilder sb=new StringBuilder();

                                    String query="select * from cliente";
                                    PreparedStatement ps=con.prepareStatement(query);
                                    rs=ps.executeQuery();

                                    while(rs.next()){
                                        sb.append(rs.getInt("id_usuario"));
                                        sb.append(",");
                                        sb.append(rs.getString("nombre"));
                                        sb.append(",");
                                        sb.append(rs.getString("apellidos"));
                                        sb.append(",");
                                        sb.append(rs.getString("tlf"));
                                        sb.append(",");
                                        sb.append(rs.getInt("id_libro"));
                                        sb.append("\r\n");
                                    }
                                    pw.write(sb.toString());
                                    pw.close();
                                    System.out.println("Copia de seguridad creada con éxito.");
                                } catch (Exception e){
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 6:
                                System.out.println("Has elegido la opción de importar desde archivo CSV (Restablecer de copia de seguridad)");
                                System.out.println("Recopilando datos de copia de seguridad...");
                                PreparedStatement consulta;
                                PreparedStatement consulta1;
                                String url="archivoCSV_Clientes_MySQL.csv";
                                consulta1 = con.prepareStatement("SET GLOBAL local_infile = 1");
                                consulta = con.prepareStatement("LOAD DATA LOCAL INFILE '"+url+"' INTO TABLE cliente FIELDS TERMINATED BY ',' LINES TERMINATED BY '\r\n' ");
                                consulta1.executeUpdate();
                                consulta.executeUpdate();
                                System.out.println("Copia de seguridad restablecida correctamente");
                                break;
                            case 7:
                                System.out.println("Has elegido la opción de terminar estas operaciones y volver al menú principal.");
                                break;
                            default:
                                System.out.println("Introduzca un caracter válido");
                        }
                    }
                        break;

                case 3:
                    System.out.println("Saliendo del programa");
                    break;
                default:
                    System.out.println("Introduzca un caracter válido");
                    }
            }
            //Cerramos la conexión con la BD
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("No se ha podido cerrar la conexión con la BD");
                System.out.println(e.getMessage());
                return;
            }
            System.out.println("Se ha cerrado la base de datos");
    }
    private static void mostrarFila(ResultSet rs){
        try {
            System.out.print("ID: "+rs.getInt("id_usuario"));
            System.out.print(" ||| ");
            System.out.print("Nombre: "+rs.getString("nombre"));
            System.out.print(" ||| ");
            System.out.print("Apellidos: "+rs.getString("apellidos"));
            System.out.print(" ||| ");
            System.out.print("Teléfono: "+rs.getString("tlf"));
            System.out.print(" ||| ");
            System.out.print("ID libro prestado: "+rs.getInt("id_libro"));
            System.out.println();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void mostrarFila2(ResultSet rs){
        try {
            System.out.print("ID: "+rs.getInt("id_libro"));
            System.out.print(" ||| ");
            System.out.print("Título: "+rs.getString("titulo"));
            System.out.print(" ||| ");
            System.out.print("Autor: "+rs.getString("autor"));
            System.out.print(" ||| ");
            System.out.print("Editorial: "+rs.getString("editorial"));
            System.out.print(" ||| ");
            System.out.print("Stock: "+rs.getInt("stock"));
            System.out.print(" ||| ");
            System.out.print("Precio: "+rs.getFloat("precio"));
            System.out.println();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
