package com.dzien7.library.dao;

import com.dzien7.library.domain.Book;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class BookDaoSqlite implements BookDao{

    private Connection connection;



    public BookDaoSqlite(){
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:library.db");

        }
        catch ( Exception e){
            System.out.println(e.getMessage());
        }

        createTable();
    }
    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Books(" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"title TEXT, "
                +"author TEXT, "
                +"pages INTEGER "
                +");";


        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println("Nie udało się wykonać SQL " + e.getMessage());
        }

    }

    @Override
    public void addBook(Book book) {

        String sql = "INSERT INTO Books (title, author, pages)"
                +"VALUES('"+book.getTitle()+"','" +book.getAuthor()+"', "+book.getPages()+")";

        try{
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }
        catch (SQLException e){
            System.out.println("Nie udało się wykonać SQL"+e.getMessage());
        }
    }

    @Override
    public void removeBook(Book book) {

    }

    @Override
    public List<Book> getAllBooks() {
        return null;
    }
}

