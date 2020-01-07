package com.adrian.eoi2020_leerXML;

import java.io.Serializable;
import java.util.ArrayList;

public class News implements Serializable {
	
	private String title;
	private String description;
	private String creator;
	private String link;
	private ArrayList<String> categories;
	private String guid;
	private String date;

	public News(String title, String description, String creator, String link, ArrayList<String> categories,
			String guid, String date) {
		this.title = title;
		this.description = description;
		this.creator = creator;
		this.link = link;
		this.categories = categories;
		this.guid = guid;
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public ArrayList<String> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<String> categories) {
		this.categories = categories;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "guid= " + guid + " title=" + title;
	}


}
