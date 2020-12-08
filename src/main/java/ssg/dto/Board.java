package ssg.dto;

import java.util.Map;

public class Board {
	
	public String title; 
	public int id; 
	public String regDate; 
	
	public Board(Map<String, Object> map) {
		this.title = (String)map.get("title"); 
		this.id = (int)map.get("id"); 
		this.regDate = (String)map.get("regDate"); 
	}
}
