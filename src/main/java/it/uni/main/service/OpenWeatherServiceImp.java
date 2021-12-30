package it.uni.main.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;


import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.uni.main.interfaceToUse.OpenWeatherService;




@Service
public class OpenWeatherServiceImp implements OpenWeatherService{
	
	/**
	 * metodo per chiamare un API tramite url con return del JSON ricevuto dall'API
	 * 
	 * @param myUrl url fonte di previsioni di 5 giorni ogni 3 ore
	 * @return String JSON
	 */
	@Override
	public JsonObject callApi(String myUrl) 
	{
		//System.out.println("URL----->" + myUrl);
		
		JsonObject Jobject = new JsonObject();
		try {
			URLConnection openConnection = new URL(myUrl).openConnection();
			InputStream in = openConnection.getInputStream();//QUI ECCEZIONE
			
			String data = "";
			String line = "";
			try {
			   InputStreamReader inR = new InputStreamReader( in );
			   BufferedReader buf = new BufferedReader( inR );
			  
			   while ( ( line = buf.readLine() ) != null ) {
				   data+= line;
			   }
			} finally {
			   in.close();
			}
			Gson gson = new Gson();
			Jobject = gson.fromJson(data, JsonObject.class);
				
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Jobject;
	}
	
	
	public void apriDaFILE(String nomeFile, JsonObject jObj, JsonArray jArr){
		try{
			Scanner scr = new Scanner(new BufferedReader(new FileReader(nomeFile)));
			String inJSON = "";
			while(scr.hasNext())
				inJSON += scr.nextLine();
			Gson gson = new Gson();
			if(jObj == null)
				jArr = gson.fromJson(inJSON, JsonArray.class);
			else
				jObj = gson.fromJson(inJSON, JsonObject.class);
		}
		catch(Exception e){
			System.out.println("file " + nomeFile + "  non aperto o vuoto"  );
		}
	}
	
	
	
	@Deprecated
	/**
	 * metodo che che converte oggetto di tipo String in tipo JsonObject
	 * @param toConvert oggetto di tipo String
	 */
	@Override
	public JsonObject toJsonObject(String toConvert) 
	{
		/*JSONObject tmp = new JSONObject();
		tmp.put(toConvert, );
		*/
		return null;
	}

	
	
	/*
	 * metodo che converte un file txt con JSON e restituisce un oggetto JSONObject
	 * @param myFile - file con jSON txt 
	 */
	public JsonObject leggiJsondaFile(String myFile)
	{
		JsonObject Jobject = null;
		String data = "";
		String line = "";
		try {
		   FileReader FR = new FileReader(myFile);
		   BufferedReader buf = new BufferedReader( FR );
		  
		   while ( ( line = buf.readLine() ) != null ) {
			   data+= line;
		   }
		   buf.close();
		   Gson gson = new Gson();
		   Jobject = gson.fromJson(data, JsonObject.class);
		}
		 catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Jobject;
		}
	
	
	
		public void CreateTxtFile() {
		String fileName = "my-file.txt";
	    String encoding = "UTF-8";
	    
	    try{
	    PrintWriter writer = new PrintWriter(fileName, encoding);
	    writer.close();
	    }
	    catch (IOException e){
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
  }


	

	public String CurrentTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);
	}
	
	
}
