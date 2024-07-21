package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        String URL = "jdbc:mysql://localhost:3306/estudiantes2024A";
        String USER = "root";
        String PASSWORD = "";
        Connection conn = null;
        PreparedStatement pstmt=null;

        try{
            //establecer la conexion
            conn= DriverManager.getConnection(URL,USER,PASSWORD);
            //prepara la sentencia sql
            String sql="UPDATE estudiantes SET b1=? WHERE cedula=?";
            pstmt=conn.prepareStatement(sql);
            //seteamos lso valores de la sentencia previa
            pstmt.setInt(1,100);
            pstmt.setString(2,"1726195207");
            System.out.println(sql);
            int n=pstmt.executeUpdate();
            System.out.println("se modificaron:"+n+"lineas");

        } catch (SQLException e) {
            //throw new RuntimeException(e);
            System.out.println(e.getMessage());
        } finally {
            //cerramos la conexion
            try{
                if(pstmt!=null){
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}