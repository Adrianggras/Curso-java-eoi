package eoi.leerJSon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class leerPlayer {

	public static String readUrl2(String web) {
		try {
			URL url = new URL(web);
			URLConnection uc = url.openConnection();			
			uc.setRequestProperty("User-Agent", "PostmanRuntime/7.20.1");
			uc.setRequestProperty("X-Auth-Token", "eeadfa16afb343e0a8b6020335882438");
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
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			json = json + readUrl2("https://api.football-data.org/v2/players/" + i);
			json = json + ",";
		}
		json = json + readUrl2("https://api.football-data.org/v2/players/" + elementos) + "]";
		Gson gson = new Gson();
		Player[] player = gson.fromJson(json,
                Player[].class);
		for(int i=0;i<player.length;i++) {
			System.out.println(player[i].toString());
		}
	}
	
	public static void main(String[] args) {
		leerInternetGson(5);
	}
	
}
