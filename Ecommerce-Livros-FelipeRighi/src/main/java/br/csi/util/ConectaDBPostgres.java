package br.csi.util;

import br.csi.model.Usuario;

import java.sql.*;

public class ConectaDBPostgres {

    public Connection getConexao() {

        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(
                                    "jdbc:postgresql://localhost:5432/ecommercelivros",
                                    "postgres",
                                    "1234");
            return conn;

        }catch (ClassNotFoundException e){
            e.printStackTrace();
    }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }


/*
    public static void main(String[] args) {

        try{
            Class.forName(
                    "org.postgresql.Driver");
Connection conn =
        DriverManager.
                getConnection(
                        "jdbc:postgresql://localhost:5432/dbpoow1",
                        "postgres",
                        "1234");

            Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery("SELECT * FROM usuario");
            while (rs.next()){

                Usuario u =
                        new Usuario(
                                rs.getInt("idusuario"),
                                rs.getString("nome"),
                                rs.getString("email"),
                                rs.getString("senha"),
                                rs.getBoolean("ativo"));



            }

        }catch (ClassNotFoundException e){
            System.out.println("Problemas "+e.getMessage());
            e.printStackTrace();
        }catch (SQLException e){
            System.out.println("SQL: "+e.getMessage());
            e.printStackTrace();
        }



    }*/

}
