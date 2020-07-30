package Kurs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	 public static final String DRIVER = "org.sqlite.JDBC";
     public static final String DB_URL = "jdbc:sqlite:test.db";
	private static Connection conn;
    private static Statement stat;
    
    
    public void Connect() {
    	try {
            Class.forName(Database.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }
		 
		 try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        }
    }
	public void CreateTable( ) {
		String TableAB = "CREATE TABLE IF NOT EXISTS CurrencyAB (Currency varchar(255), Code varchar(3), Mid double,Data varchar(10),PRIMARY KEY (Currency,Data))";
		String TableC = "CREATE TABLE IF NOT EXISTS CurrencyC (Currency varchar(255), Code varchar(3), Ask double, Bid double,Data varchar(10),PRIMARY KEY (Currency,Data))";
		
		 try {
             stat.execute(TableAB);
             stat.execute(TableC);
             System.out.println("Success");
         } catch (SQLException e) {
             System.err.println("Blad przy tworzeniu tabeli");
             e.printStackTrace();
         }
		
	}
	public void DropTables() {
		
		String DropTableAB = "DROP TABLE CurrencyAB";
		String DropTableC = "DROP TABLE CurrencyC";
		
		 try {
             stat.execute(DropTableAB);
             stat.execute(DropTableC);
             System.out.println("Success");
         } catch (SQLException e) {
             System.err.println("Blad przy usuwaniu tabeli");
             e.printStackTrace();
         }
		
	}
	public void InsertCurrencyAB(String Currency,String Code,double Mid, String dateCurrencyIns) {
		
		String insertAB = "insert into CurrencyAB values (?, ?, ?, ?);";
		try {
            PreparedStatement prepStmt = conn.prepareStatement(insertAB);
            prepStmt.setString(1, Currency);
            prepStmt.setString(2, Code);
            prepStmt.setDouble(3, Mid);
            prepStmt.setString(4, dateCurrencyIns);
            prepStmt.execute();
		} catch (SQLException e) {
            System.err.println("Blad przy wstawianiu danych");
            e.printStackTrace();
        }
		
	}
	public void InsertCurrencyC(String Currency,String Code,double Ask,double Bid, String dateCurrencyIns) {
		
		String insertAB = "insert into CurrencyC values (?, ?, ?, ?,?);";
		try {
            PreparedStatement prepStmt = conn.prepareStatement(insertAB);
            prepStmt.setString(1, Currency);
            prepStmt.setString(2, Code);
            prepStmt.setDouble(3, Ask);
            prepStmt.setDouble(4, Bid);
            prepStmt.setString(5, dateCurrencyIns);
            prepStmt.execute();
		} catch (SQLException e) {
            System.err.println("Blad przy wstawianiu danych");
            e.printStackTrace();
        }
		
	}
	public ResultSet SelectCurrencyAB () {
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			 rs = stmt.executeQuery("select * from CurrencyAB");
			
		} catch (SQLException e) {
            System.err.println("Blad przy wyœwietleniu danych");
            e.printStackTrace();
        }
		return rs;
	}
	public ResultSet SelectCurrencyC () {
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			 rs = stmt.executeQuery("select * from CurrencyC");
			
		} catch (SQLException e) {
            System.err.println("Blad przy wyœwietleniu danych");
            e.printStackTrace();
        }
		return rs;
	}
}
