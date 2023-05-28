package org.example;

import org.example.Mantenimiento.Cliente;
import org.example.Mantenimiento.Pedido;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    static Connection conexion;
    public static void main(String[] args) throws SQLException, IOException {
        conectar();
        Menu();
        cerrar();
    }

    public static void Menu() throws SQLException, IOException {
        System.out.println("Marca:\n1->Cliente\n2->Pedido\ncualquier otra tecla->salir");
        Scanner sc = new Scanner(System.in);
        String res = sc.nextLine();
        switch (res){
            case "1":
                Cliente.MenuCliente(conexion);
                Menu();
                break;
            case "2":
                Pedido.MenuPedido(conexion);
                Menu();
                break;
            default:
                System.out.println("Has salido de la aplicación");
                break;
        }
    }

    private static void conectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String url = "jdbc:mysql://localhost:3306/floreria";
        conexion = DriverManager.getConnection(url,"root","root");
    }

    private static void cerrar() throws SQLException{
        if(conexion!=null){
            conexion.close();
        }
    }
}