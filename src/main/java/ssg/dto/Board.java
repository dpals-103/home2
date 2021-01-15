package ssg.dto;

import java.util.Map;

import lombok.Data;

@Data
public class Board {
	
	private String title; 
	private int id; 
	private String regDate; 
	


	public Board(Map<String, Object> map) {
		this.title = (String)map.get("title"); 
		this.id = (int)map.get("id"); 
		this.regDate = (String)map.get("regDate"); 
	}
	
	
}


