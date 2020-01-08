package eoi.leerJSon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class leerGson {

	public static String readUrl2(String web) {
		try {
			URL url = new URL(web);
			URLConnection uc = url.openConnection();			
			uc.setRequestProperty("User-Agent", "PostmanRuntime/7.20.1");			
			uc.connect();
			String lines = new BufferedReader(
					new InputStreamReader(uc.getInputStream(), 
							StandardCharsets.UTF_8))
					.lines()
					.collect(Collectors.joining());
			//System.out.println(lines);
			return lines;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void leerInternetGson(int elementos) {
		
		String json = "[";
		for(int i=1;i<elementos;i++) {
			json = json + readUrl2("https://swapi.co/api/people/" + i + "/?format=json");
			json = json + ",";
		}
		json = json + readUrl2("https://swapi.co/api/people/" + elementos + "/?format=json") + "]";
		Gson gson = new Gson();
		People[] people = gson.fromJson(json,
                People[].class);
		
		// Imprime personas
		/*for(int i=0;i<people.length;i++) {
			System.out.println(people[i].toString());
		}*/
			

	}
	
	public static void main(String[] args) {
		leerInternetGson(5);
		
	}
	
	
}
