import holder.ImageSerializer;
import java.awt.image.*;
import java.io.*;
import java.sql.*;
import java.util.*;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lik
 */
public class Main {

    MainDB DB;
    CmeDB CmeDB;
    SetupDB SetupDB;

    Connection connection = DB.getConnection();
    private void displaySQLErrors(SQLException e) {
        System.out.println("SQLException: " + e.getMessage());
        System.out.println("SQLState: " + e.getSQLState());
        System.out.println("VendorError: " + e.getErrorCode());
    }
/*All create table methods will be put here. These methods will later be put into the start method
 * which start the database
 */




    //Start
    public boolean Start(){
        try{
            DB.createDB();
            DB.create_Table();
            CmeDB = new CmeDB();
            SetupDB = new SetupDB();
        return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean Stop(){
        try{
           return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
