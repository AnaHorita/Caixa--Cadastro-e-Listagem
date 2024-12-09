/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FilmesDAO {
    
    Connection conn;
    PreparedStatement st;
    ResultSet rs;
    
     //conexao
    //desconexao
    //itens do crud 
    
       public boolean conectar() {
        try {           
           Class.forName("com.mysql.cj.jdbc.Driver");
           conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cenaflix","root","lucaslindo");
           return true;
        } catch (ClassNotFoundException ex) {
            System.out.println("O Driver não está disponível na biblioteca");
            return false;
        } catch (SQLException ex) {
            System.out.println("Sintaxe de comando invalida " + ex.getMessage());
            return false;
        }
    }
       
       
       
          public int salvar(Filmes f){
        int status;
        try {
            st = conn.prepareStatement("INSERT INTO filmes VALUES(?,?,?,?)");
           
           st.setInt(1,f.getId());
            st.setString(2, f.getNome());
            st.setDate (3,f.getDatalancamento());
            st.setString(4,f.getCategoria());
            status = st.executeUpdate();
            return status;
                    
        } catch(SQLException ex){
            System.out.println("Erro ao conectar" + ex.getMessage());
            return ex.getErrorCode();
        }
  
    }
          
        
       public boolean excluir(String nome){
        try {
            st = conn.prepareStatement("DELETE FROM filmes WHERE nome = ?");
            st.setString(1, nome);
            st.executeUpdate();
            return true;
                    
        } catch(SQLException ex){
            return false;
        }
        
    }    
       
   public int atualizar(Filmes f){
        int status;
        try {
            st = conn.prepareStatement("UPDATE filmes SET datalancamento = ?, categoria = ? WHERE nome = ?");
          
            st.setDate(1, f.getDatalancamento());
            st.setString(2, f.getCategoria());
             st.setString(3, f.getNome());
            status = st.executeUpdate();
            return status;
                    
        } catch(SQLException ex){
            System.out.println("Erro ao atualizar: " + ex.getMessage());
            return ex.getErrorCode();
        }
    
    }     
              
          
            public Filmes consultar (String nome){
         
        try {
            Filmes f = new Filmes();
            st = conn.prepareStatement("SELECT * from filmes WHERE nome = ?");
            st.setString(1, nome);
            rs = st.executeQuery();
            //verificar se a consulta encontrou o funcionário com a matrícula informada
            if(rs.next()){ // se encontrou o funcionário, vamos carregar os dados
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setDatalancamento(rs.getDate("datalancamento"));
                f.setCategoria(rs.getString("categoria"));
                return f;
            }else{
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }
            
  
    public List<Filmes> getFilmes( ){
        
        String sql = "SELECT * FROM filmes  ";
       
        try{
            st = conn.prepareStatement(sql); 
         
            rs = st.executeQuery();
            List<Filmes> listaFilmes = new ArrayList<>();
            while(rs.next()){
                Filmes f = new Filmes();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setDatalancamento(rs.getDate("datalancamento"));
                f.setCategoria(rs.getString("categoria"));
                listaFilmes.add(f);
            }
            return listaFilmes;            
        }catch(SQLException ex){
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        } 
    
    }          
   
    public List<Filmes> getFilmesCategoria(String categoria ){
        
        String sql = "SELECT * FROM filmes WHERE categoria = ? ";
       
        try{
           
            st = conn.prepareStatement(sql); 
            st.setString(1,categoria);
            rs = st.executeQuery();
            List<Filmes> listaFilmesCategoria = new ArrayList<>();
            while(rs.next()){
                Filmes f = new Filmes();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setDatalancamento(rs.getDate("datalancamento"));
                f.setCategoria(rs.getString("categoria"));
                listaFilmesCategoria.add(f);
            }
            return listaFilmesCategoria;            
        }catch(SQLException ex){
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        } 
    
    }    
    
   public void desconectar(){
        try{
            conn.close();
        }catch(SQLException ex){
            
        }
    
    }

   
}
