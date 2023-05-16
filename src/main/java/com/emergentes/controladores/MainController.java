package com.emergentes.controladores;

import com.emergentes.modelo.Libro;
import com.emergentes.utiles.ConexionDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //parametros requeridos en la conneccion
        //PRACTICA ANTERIOR DE DB_BIBLIO
        /* String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/te_libros";
        String usuario = "root";
        String pasword = "123456789";

        try {

            //
            Connection conn = null;
            String sql = "select * from libros";
            PreparedStatement ps = null;
            ResultSet rs = null;

            ArrayList<Libro> lista = new ArrayList<Libro>();

            Class.forName(driver);
            conn = DriverManager.getConnection(url, usuario, pasword);

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Libro l = new Libro();
                
                l.setId(rs.getInt("isbn"));
                l.setTitulo(rs.getString("titulo"));
                l.setCategoria(rs.getString("categoria"));

                lista.add(l);
            }

            request.setAttribute("lista", lista);
            request.getRequestDispatcher("index.jsp").forward(request, response);
         */

        System.out.println("INICIANDO CONTROLES... ");
        try {
            String op = (request.getParameter("op") != null) ? request.getParameter("op") : "list";

            ArrayList<Libro> lista = new ArrayList<Libro>();
            ConexionDB canal = new ConexionDB();
            Connection conn = canal.conectar();
            PreparedStatement ps;
            ResultSet rs;

            // String accion = (op != null) ? op : "list";
//condicion si la obtenio el valor en caso de que no sera insercion, editar, eliminar
            //if (accion.equals("list")) {
            if (op.equals("list")) {
                //preparar el listado
                String sql = "select * from libros";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Libro lib = new Libro();
                    lib.setId(rs.getInt("id"));
                    lib.setIsbn(rs.getString("isbn"));
                    lib.setTitulo(rs.getString("titulo"));
                    lib.setCategoria(rs.getString("categoria"));

                    lista.add(lib);
                }
                request.setAttribute("lista", lista);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

            if (op.equals("nuevo")) {
                //preparar la insercion
                Libro li = new Libro();
                request.setAttribute("lib", li);
                request.getRequestDispatcher("editar.jsp").forward(request, response);

            }
            if (op.equals("eliminar")) {
                //preparare la edicion
                int id = Integer.parseInt(request.getParameter("id"));
                String sql = "delete from libros where id =?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
                //update se utiliza para aliminacion editar o actualizar
                response.sendRedirect("MainController");

            }
            /* if (op.equals("editar")) {
                    //proceso de aliminacion

                }*/
            //aqui se estara haciendo el crud. en estos pasos.

            // System.out.println("La opcion es op " + op);
        } //las llamadas desde url
        catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*  catch (ClassNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);*/


 /*catch(SQLException e){
            System.out.println("Error al conectar"+e.getMessage());*/
//saldra esta excepcion mientras no cumpla condicion    
// }
// }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //llamadas desde formulario
        try {
            int id = Integer.parseInt(request.getParameter("isbn"));
            String isbn = request.getParameter("isbn");
            String titulo = request.getParameter("titulo");
            String categoria = request.getParameter("categoria");

            Libro lib = new Libro();
            lib.setId(id);
            lib.setTitulo(titulo);
            lib.setCategoria(categoria);

            ConexionDB canal = new ConexionDB();
            Connection conn = canal.conectar();

            PreparedStatement ps;

            if (id == 0) {
                //nuevo registro 
                String sql = "insert into libros (isbn, titulo, categoria)values (?,?,?)";

                ps = conn.prepareStatement(sql);
                ps.setString(1, lib.getIsbn());
                ps.setString(2, lib.getTitulo());
                ps.setString(3, lib.getCategoria());

                ps.executeUpdate();
                response.sendRedirect("MainController");
            }
        } catch (SQLException e) {
            System.out.println("Error en SQL"+e.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
