package org.example.Mantenimiento;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

public final class Cliente {
    private Cliente(){}

    public static void ListarCliente(Connection cn){
        Statement statement = null;
        ResultSet resultado = null;

        System.out.println("Listado de Clientes");

        try {
            statement = cn.createStatement();
            String consulta =
                    "Select * from cliente";
            resultado = statement.executeQuery(consulta);
            while (resultado.next()){
                int IdCliente = resultado.getInt("IdCliente");
                String NomCli =
                        resultado.getString("NomCli");
                String ApeCli =
                        resultado.getString("ApeCli");
                String DriCli =
                        resultado.getString("DriCli");
                String TelefCli = resultado.getString("TelefCli");
                System.out.println(IdCliente + " : " + NomCli + " " + ApeCli +
                        " - dirección: " + DriCli + " - Telefono: " +
                        TelefCli);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void IngresarCliente(Connection cn) throws IOException, SQLException {
        String query = "INSERT INTO cliente VALUES (?, ?, ?, ?, ?)";

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("escribe el ID");
        String id = br.readLine();
        System.out.println("escribe el nombre");
        String nom_cli = br.readLine();
        System.out.println("escribe el apellido");
        String ape_cli = br.readLine();
        System.out.println("escribe la dirección");
        String dir_cli = br.readLine();
        System.out.println("escribe el teléfono");
        String tel_cli = br.readLine();

        try{
            PreparedStatement statement = cn.prepareStatement(query);
            statement.setString(1,id);
            statement.setString(2,nom_cli);
            statement.setString(3,ape_cli);
            statement.setString(4,dir_cli);
            statement.setString(5,tel_cli);
            statement.executeUpdate();
            System.out.println("Se insertó correctamente");
        } catch (SQLException ex){
            throw ex;
        }
    }

    public static void EliminarCliente(Connection cn){
        CallableStatement statement = null;
        String res;

        System.out.println("Selecciona el id del cliente a borrar" +
                " si no desea borrar a nadie escribe \"nadie\"");

        Scanner sc = new Scanner(System.in);
        res = sc.nextLine();
        //sc.close();

        if(res.trim().equals("nadie")){
            System.out.println("La operacion ha terminado");
        }else if(res.trim().equals("")){
            System.out.println("Por favor selecciona un valor correcto");
            EliminarCliente(cn);
        }else {
            try{
                String consulta = "{call borrarCliente(?)}";
                statement = cn.prepareCall(consulta);
                statement.setInt(1,Integer.parseInt(res));
                ResultSet rs = statement.executeQuery();
                while (rs.next()){
                    System.out.println(rs.getString(1));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static void ActualizarCliente(Connection cn) throws IOException, SQLException {
        String query = "update cliente set NomCli=?,ApeCli=?" +
                ",DriCli=?,TelefCli=? " +
                " where IdCliente=?";

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Selecciona el id del cliente a actualizar");
        String id = br.readLine();
        System.out.println("Reescribe el nombre");
        String nombre = br.readLine();
        System.out.println("Reescribe el apellido");
        String ape = br.readLine();
        System.out.println("Reescribe la direccion");
        String direccion = br.readLine();
        System.out.println("Reescribe el telefono");
        String telefono = br.readLine();

        try{
            PreparedStatement statement = cn.prepareStatement(query);
            statement.setString(1,nombre);
            statement.setString(2,ape);
            statement.setString(3,direccion);
            statement.setString(4,telefono);
            statement.setString(5,id);
            statement.executeUpdate();
            System.out.println("Se actualizó correctamente");
        } catch (SQLException ex){
            throw ex;
        }
    }

    public static void MenuCliente(Connection cn) throws SQLException, IOException {
        System.out.println("Presionar:\n1->Listar \n2->Eliminar\n3->Actualizar" +
                "\n4->Insertar\nCualquier otra tecla para salir del mantenimiento a tabla cliente");
        Scanner sc = new Scanner(System.in);
        String res = sc.nextLine();
        //sc.close();
        switch (res){
            case "1":
                ListarCliente(cn);
                MenuCliente(cn);
                break;
            case "2":
                EliminarCliente(cn);
                MenuCliente(cn);
                break;
            case "3":
                ActualizarCliente(cn);
                MenuCliente(cn);
            case "4":
                IngresarCliente(cn);
                MenuCliente(cn);
            default:
                break;
        }
    }
}
