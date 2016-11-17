package com.dzien7.library.dao;

import com.dzien7.library.domain.Book;
import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;

import java.lang.reflect.Field;
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
        // to ponizej to jest wlasnie refleksja
        String simpleClassName = book.getClass().getSimpleName();
        System.out.println(simpleClassName);
        Field[] fields = book.getClass().getDeclaredFields();
        StringBuilder attributeString = new StringBuilder(" (");

        for (Field field: fields ){
            if ( !field.getName().equals ("id")){
                attributeString.append (field.getName());
                attributeString.append(",");
            }
            System.out.println(field.getName());
            System.out.println(field.getType());
        }
        attributeString.deleteCharAt(attributeString.length()-1);
        attributeString.append(")");
        System.out.println(attributeString);
        System.out.println(book.getTitle());

        String sql = "INSERT INTO " +simpleClassName+"s"+attributeString
                +"VALUES('"+book.getTitle()+"','" +book.getAuthor()+"', "+book.getPages()+")";

        try{
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }
        catch (SQLException e){
            System.out.println("Nie udało się wykonać SQL"+e.getMessage());
        }
    }

    public static void main(String[] args ){
        BookDaoSqlite bookDaoSqlite = new BookDaoSqlite();
        bookDaoSqlite.addBook(new Book("Pozytywne Myślenie","Brian Tracy",259));
    }

    @Override
    public void removeBook(Book book) {

    }

    @Override
    public List<Book> getAllBooks() {
        return null;
    }
}

