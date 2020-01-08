package eoi.leerJSon;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class App {
	
	private static ArrayList<People> people = new ArrayList<>();

	public static void leerFichero() {

		Object obj;
		try {
			// parseado el fichero "profesor.json"
			obj = new JSONParser().parse(new FileReader("profesor.json"));
			// casteando obj a JSONObject
			JSONObject jo = (JSONObject) obj;
			// cogiendo el nombre y el apellido
			String nombre = (String) jo.get("nombre");
			String apellido = (String) jo.get("apellido");
			System.out.println(nombre);
			System.out.println(apellido);
			// cogiendo la edad como long
			long edad = (long) jo.get("edad");
			System.out.println(edad);
			// cogiendo direccion
			Map domicilio = ((Map) jo.get("domicilio"));
			// iterando direccion Map
			Iterator<Map.Entry> itr1 = domicilio.entrySet().iterator();
			while (itr1.hasNext()) {
				Map.Entry pareja = itr1.next();
				System.out.println(pareja.getKey() + " : " + pareja.getValue());
			}

			// Imprimiendo direccion
			System.out.println("La dirección es: " + domicilio.get("direccion"));

			// cogiendo números de teléfonos
			JSONArray ja = (JSONArray) jo.get("numerosTelefonos");
			// iterando números de teléfonos
			Iterator itr2 = ja.iterator();
			while (itr2.hasNext()) {
				itr1 = ((Map) itr2.next()).entrySet().iterator();
				while (itr1.hasNext()) {
					Map.Entry pareja = itr1.next();
					System.out.println(pareja.getKey() + " : " + pareja.getValue());
				}
			}

			// Imprimir primer elemento
			for (int i = 0; i < ja.size(); i++) {
				JSONObject jo2 = (JSONObject) ja.get(i);
				System.out.println("El número de mi " + jo2.get("tipo") + " es " + jo2.get("numero"));
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}

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
	
	/**
	 * Método que recibe una url y la imprima
	 * @param web
	 */
	public static void leerInternet(String url) {
		String json = readUrl2(url);
		Object obj,obj2;
		try {
			// leo el json devuelto por Internet
			obj = new JSONParser().parse(json);
			// casteando obj a JSONObject
			JSONObject jo = (JSONObject) obj;
			
			// Guardar personaje en un objeto
			people.add(new People(
					jo.get("name").toString(),
					jo.get("height").toString(),
					jo.get("mass").toString(),
					jo.get("hair_color").toString(),
					jo.get("skin_color").toString(),
					jo.get("eye_color").toString(),
					jo.get("birth_year").toString(),
					jo.get("gender").toString(),
					jo.get("homeworld").toString(),
					new ArrayList<String>((JSONArray)jo.get("films")),
					new ArrayList<String>((JSONArray)jo.get("species")),
					new ArrayList<String>((JSONArray)jo.get("vehicles")),
					new ArrayList<String>((JSONArray)jo.get("starships")),
					jo.get("created").toString(),
					jo.get("edited").toString(),
					jo.get("url").toString()
					));
			
			//System.out.println(people.get(0));
			
			System.out.println(jo.get("name"));
			System.out.println("Su altura es: " + jo.get("height") + " cm");
			JSONArray peliculas = (JSONArray) jo.get("films");
			//System.out.println(peliculas.get(0)); // saca una peli
			for (int i = 0; i < peliculas.size(); i++) {
				//System.out.println(peliculas.get(i));
				String jsonpelicula = readUrl2(peliculas.get(i).toString() + "?format=json");
				obj2 = new JSONParser().parse(jsonpelicula);
				// casteando obj a JSONObject
				JSONObject jo2 = (JSONObject) obj2;
				System.out.println(jo2.get("title"));
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// leerFichero();
		Scanner scn = new Scanner(System.in);
		System.out.println("Introduce el código del personaje:");
		String codigo = scn.nextLine();
		leerInternet("https://swapi.co/api/people/" + codigo + "/?format=json");
	}
}
