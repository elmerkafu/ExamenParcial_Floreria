package org.example.Mantenimiento;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

public final class Pedido {

    private Pedido(){}
    public static void ListarPedido(Connection cn){
        Statement statement = null;
        ResultSet resultado = null;

        System.out.println("Listado de Pedidos");

        try {
            statement = cn.createStatement();
            String consulta =
                    "Select * from pedido";
            resultado = statement.executeQuery(consulta);
            while (resultado.next()){
                int NroPedido = resultado.getInt("NroPedido");
                String Fecha =
                        resultado.getString("Fecha");
                String Monto =
                        resultado.getString("Monto");
                int IdCliente =
                        resultado.getInt("IdCliente");
                int IdVendedor =
                        resultado.getInt("IdVendedor");
                int IdTransp =
                        resultado.getInt("IdTransp");
                System.out.println("Nro Pedido: " + NroPedido + " - " +
                        "Fecha: " + Fecha + " - Monto: " + Monto + " - " +
                        "IdCliente: " + IdCliente + " - IdVendedor:" +
                        IdVendedor + " - IdTransporte: " + IdTransp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void ListarPedidoUnico(Connection cn){
        Statement statement = null;
        ResultSet resultado = null;
        String res;
        String condition;

        System.out.println("Selecciona el id del pedido que quieres ver");
        Scanner sc = new Scanner(System.in);
        res = sc.nextLine();

        System.out.println("Listado de Pedidos");


        try {
            statement = cn.createStatement();
            String consulta =
                    "Select * from pedido where NroPedido = " + res ;
            resultado = statement.executeQuery(consulta);
            while (resultado.next()){
                int NroPedido = resultado.getInt("NroPedido");
                String Fecha =
                        resultado.getString("Fecha");
                String Monto =
                        resultado.getString("Monto");
                int IdCliente =
                        resultado.getInt("IdCliente");
                int IdVendedor =
                        resultado.getInt("IdVendedor");
                int IdTransp =
                        resultado.getInt("IdTransp");
                System.out.println("Nro Pedido: " + NroPedido + " - " +
                        "Fecha: " + Fecha + " - Monto: " + Monto + " - " +
                        "IdCliente: " + IdCliente + " - IdVendedor:" +
                        IdVendedor + " - IdTransporte: " + IdTransp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void IngresarPedido(Connection cn) throws IOException, SQLException {
        String query = "INSERT INTO pedido VALUES (?, ?, ?, ?, ?, ?)";

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("escribe el NroPedido");
        String NroPedido = br.readLine();
        System.out.println("escribe la fecha (yyyy-mm-dd)");
        String fecha = br.readLine();
        System.out.println("escribe el monto");
        String monto = br.readLine();
        System.out.println("escribe el IdCliente");
        String IdCliente = br.readLine();
        System.out.println("escribe el IdVendedor");
        String IdVendedor = br.readLine();
        System.out.println("escribe el IdTransp");
        String IdTransp = br.readLine();

        try{
            PreparedStatement statement = cn.prepareStatement(query);
            statement.setString(1,NroPedido);
            statement.setString(2,fecha);
            statement.setString(3,monto);
            statement.setString(4,IdCliente);
            statement.setString(5,IdVendedor);
            statement.setString(6,IdTransp);
            statement.executeUpdate();
            System.out.println("Se insertó correctamente");
        } catch (SQLException ex){
            throw ex;
        }
    }

    public static void EliminarPedido(Connection cn){
        CallableStatement statement = null;
        String res;

        System.out.println("Selecciona el nro del pedido a borrar" +
                " si no desea borrar a nadie escribe \"nadie\"");

        Scanner sc = new Scanner(System.in);
        res = sc.nextLine();
        //sc.close();

        if(res.trim().equals("nadie")){
            System.out.println("La operacion ha terminado");
        }else if(res.trim().equals("")){
            System.out.println("Por favor selecciona un valor correcto");
            EliminarPedido(cn);
        }else {
            try{
                String consulta = "{call borrarPedido(?)}";
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

    public static void ActualizarPedido(Connection cn) throws IOException, SQLException {
        String query = "update pedido set Fecha=?,Monto=?" +
                ",IdCliente=?,IdVendedor=?, IdTransp=? " +
                " where NroPedido=?";

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("escribe el NroPedido");
        String NroPedido = br.readLine();
        System.out.println("escribe la fecha (yyyy-mm-dd)");
        String fecha = br.readLine();
        System.out.println("escribe el monto");
        String monto = br.readLine();
        System.out.println("escribe el IdCliente");
        String IdCliente = br.readLine();
        System.out.println("escribe el IdVendedor");
        String IdVendedor = br.readLine();
        System.out.println("escribe el IdTransp");
        String IdTransp = br.readLine();

        try{
            PreparedStatement statement = cn.prepareStatement(query);
            statement.setString(1,fecha);
            statement.setString(2,monto);
            statement.setString(3,IdCliente);
            statement.setString(4,IdVendedor);
            statement.setString(5,IdTransp);
            statement.setString(6,NroPedido);
            statement.executeUpdate();
            System.out.println("Se actualizó correctamente");
        } catch (SQLException ex){
            throw ex;
        }
    }

    public static void MenuPedido(Connection cn) throws SQLException, IOException {
        System.out.println("Presionar:\n1->Listar \n2->Eliminar\n3->Actualizar" +
                "\n4->Insertar\n5->Listar por id \nCualquier otra tecla para salir del mantenimiento a tabla pedido");
        Scanner sc = new Scanner(System.in);
        String res = sc.nextLine();
        //sc.close();
        switch (res){
            case "1":
                ListarPedido(cn);
                MenuPedido(cn);
                break;
            case "2":
                EliminarPedido(cn);
                MenuPedido(cn);
                break;
            case "3":
                ActualizarPedido(cn);
                MenuPedido(cn);
            case "4":
                IngresarPedido(cn);
                MenuPedido(cn);
                break;
            case "5":
                ListarPedidoUnico(cn);
                MenuPedido(cn);
            default:
                break;
        }
    }
}
