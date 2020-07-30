package Kurs;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public class main {
	
	 public static List<Rates> CurrencyRates = new ArrayList<>();
	 public static List<Rates> CurrencyRates_TabelC = new ArrayList<>();

     public static String dateCurrency = "";
	
     

	public static void main(String[] args) throws SQLException {
		
		// TODO Auto-generated method stub
		
		 main list = new main(); 
		 Database db = new Database();

		 
		 list.HttpGet("A");
		 list.HttpGet("B");
		 list.HttpGet("C");
		 db.Connect();
		 db.CreateTable();
//		 list.DropTables();
		
		for (int i =0; i<CurrencyRates.size();i++) { 
			db.InsertCurrencyAB(CurrencyRates.get(i).getCurrency(), CurrencyRates.get(i).getCode(),  CurrencyRates.get(i).getMid(), dateCurrency);
		}
		for (int i =0; i<CurrencyRates_TabelC.size();i++) { 
			db.InsertCurrencyC(CurrencyRates_TabelC.get(i).getCurrency(), CurrencyRates_TabelC.get(i).getCode(), CurrencyRates_TabelC.get(i).getAsk(),  CurrencyRates_TabelC.get(i).getBid(), dateCurrency);
		}
		ResultSet CurrencyAB = db.SelectCurrencyAB();
		System.out.println("Currency Code Mid Date");
		while ( CurrencyAB.next() ) {
			String CurrAB = CurrencyAB.getString("Currency");
			String CodeAB = CurrencyAB.getString("Code");
			String MidAB = CurrencyAB.getString("Mid");
			String DateAB = CurrencyAB.getString("Data");
			 System.out.println(CurrAB + " " + CodeAB +" "+ MidAB +" " + DateAB);
		}
		ResultSet CurrencyC = db.SelectCurrencyC();
		System.out.println("Currency Code Ask Bid Data");
		while ( CurrencyC.next() ) {
			String CurrC = CurrencyC.getString("Currency");
			String CodeC = CurrencyC.getString("Code");
			String AskC = CurrencyC.getString("Ask");
			String BidC = CurrencyC.getString("Bid");
			String DateC = CurrencyC.getString("Data");
			 System.out.println(CurrC + " " + CodeC +" "+ AskC + " "+ BidC + " "+ DateC );
		}


	}
	public void HttpGet (String tables) {
		int status;
		URL url;
		try {
			url = new URL("http://api.nbp.pl/api/exchangerates/tables/"+tables+"?format=xml");
			HttpURLConnection con = (HttpURLConnection) url.openConnection(); //po³aczenie z webservicem
			con.setRequestMethod("GET");
			status = con.getResponseCode();
			BufferedReader in = new BufferedReader(
					  new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer content = new StringBuffer();
					while ((inputLine = in.readLine()) != null) {
					    content.append(inputLine);
					}
					in.close();
//			System.out.println("Success");
//			System.out.println(content.toString());
			SAXBuilder saxBuilder = new SAXBuilder();

	        Document document = (Document) saxBuilder.build(new ByteArrayInputStream(content.toString().getBytes()));
	        Element rootNode = document.getRootElement();
//	        System.out.println("Root element :" + document.getRootElement().getName());
	        List<Element> node = rootNode.getChildren();
	        List <Element> table = node.get(0).getChildren();
	        //System.out.println(node.getName());
	        
	        for (int temp = 0; temp < table.size(); temp++) {
	        	Element element = table.get(temp);
	           // System.out.println("\nCurrent Element :" + element.getName());
	        	if (element.getName() == "EffectiveDate") {
	        		dateCurrency = element.getText(); // pbranie daty
//	        		System.out.println(dateCurrency);
	        	}
	            if (element.getName() == "Rates") {
	            	List <Element> Rate = element.getChildren();
	            	for (int RatesElements = 0; RatesElements < Rate.size(); RatesElements++) {
	            		Element RatesElement = Rate.get(RatesElements);
	    	            //System.out.println("\nCurrent Element :" + RatesElement.getName());
	    	            //System.out.println("\nCurrent Element :" + currencyElement.getName());
//	    	           System.out.println(RatesElement.getChild("Currency").getText());
	    	           Rates CurrRates = new Rates(); 
	    	            CurrRates.setCurrency(RatesElement.getChild("Currency").getText()); //Pobranie kolejnych elemntów xml i zapisanie ich do tablicy
	    	            CurrRates.setCode(RatesElement.getChild("Code").getText());
	    	            if(tables !="C") {
	    	            CurrRates.setMid(Double.valueOf(RatesElement.getChild("Mid").getText()));
	    	            CurrencyRates.add(CurrRates);
	    	            }
	    	            else {
	    	            	CurrRates.setAsk(Double.valueOf(RatesElement.getChild("Ask").getText()));
	    	            	CurrRates.setBid(Double.valueOf(RatesElement.getChild("Bid").getText()));
	    	            	CurrencyRates_TabelC.add(CurrRates);
	    	            }
	    	            	
	    	            	
	    	            
	            		
	            	}
	            }
	        }
			con.disconnect();
		} catch (IOException | JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	  
}
