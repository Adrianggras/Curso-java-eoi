package com.adrian.eoi2020_leerXML;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LeerMarca {
	public static List<News> news;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		leerMarca();
		//leerMundo();
	}

	public static List<String> obtenerXmls() {

		try {
			URL url = new URL("https://www.marca.com/rss.html");
			URLConnection uc = url.openConnection();
			uc.connect();
			List<String> lines = new BufferedReader(new InputStreamReader(uc.getInputStream(), StandardCharsets.UTF_8))
					.lines() // Lo divide en lineas
					.filter(line -> line.contains(".xml") && !line.contains("link")).map(t -> {
						int inicio = t.indexOf("href");
						int fin = t.indexOf("xml");
						String urldepurada = t.substring(inicio + 6, fin + 3);
						return urldepurada;
					}).collect(Collectors.toList());
			return lines;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> obtenerXmlsMundo() {

		try {
			URL url = new URL("http://rss.elmundo.es/rss/");
			URLConnection uc = url.openConnection();
			uc.connect();
			List<String> lines = new BufferedReader(new InputStreamReader(uc.getInputStream(), StandardCharsets.UTF_8))
					.lines() // Lo divide en lineas
					.filter(line -> line.contains("application/rss+xml") && !line.contains("data2")
							&& !line.contains("blogs"))
					.map(t -> {
						int inicio = t.indexOf("href");
						int fin = t.indexOf("xml");
						String urldepurada = t.substring(inicio + 6, fin + 3);
						urldepurada = urldepurada.replace("estaticos.elmundo", "e00-elmundo.uecdn");
						urldepurada = urldepurada.replace("http", "https");
						return urldepurada;
					}).collect(Collectors.toList());

			for (String line : lines) {
				System.out.println(line);
			}

			return lines;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void leerPagina(String categorie) {
		URL url;
		Document doc;

		System.out.println("Tratando: " + categorie);

		try {
			url = new URL(categorie);
			URLConnection uc = url.openConnection();
			uc.connect();
			List<String> lines = new BufferedReader(new InputStreamReader(uc.getInputStream(), StandardCharsets.UTF_8))
					.lines().collect(Collectors.toList());
			// System.out.println(lines);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(url.openStream()); // Así se abre un xml de Internet
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("item");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					ArrayList<String> categoriesList = new ArrayList<>();
					for (int j = 0; j < eElement.getElementsByTagName("category").getLength(); j++) {
						categoriesList.add(eElement.getElementsByTagName("category").item(0).getTextContent());
					}

					if (eElement.getElementsByTagName("dc:creator").getLength() > 0) {

						boolean isInTheList = news.stream().anyMatch(a -> a.getGuid()
								.equals(eElement.getElementsByTagName("guid").item(0).getTextContent()));

						if (!isInTheList)
							news.add(new News(eElement.getElementsByTagName("title").item(0).getTextContent(),
									eElement.getElementsByTagName("description").item(0).getTextContent(),
									eElement.getElementsByTagName("dc:creator").item(0).getTextContent(),
									eElement.getElementsByTagName("link").item(0).getTextContent(), categoriesList,
									eElement.getElementsByTagName("guid").item(0).getTextContent(),
									eElement.getElementsByTagName("pubDate").item(0).getTextContent()));
					}
				}

			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void imprimirNoticias() {

		int count = 1;
		for (News News : news) {
			System.out.println(count++ + " " + News);
		}
	}

	public static void leerMarca() {
		// Guardo todas las rutas de los ficheros xml
		// donde hay Noticias en el marca
		List<String> xmlLeidos = obtenerXmls();
		for (String pagina : xmlLeidos) {
			leerPagina(pagina);
		}
		imprimirNoticias();
	}

	public static void leerMundo() {
		// Guardo todas las rutas de los ficheros xml
		// donde hay Noticias en el marca
		List<String> xmlLeidos = obtenerXmlsMundo();
		for (String pagina : xmlLeidos) {
			leerPagina(pagina);
		}
		imprimirNoticias();
	}
}
