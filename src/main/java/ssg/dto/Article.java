package ssg.dto;

import java.text.SimpleDateFormat;
import java.util.Map;

import lombok.Data;

@Data
public class Article {
	
	private String title;
	private String regDate;
	private int id;
	private String extra__writer;
	private int count;
	private int likesCount;
	private int commentsCount;
	private String body;

	
	public Article(Map<String, Object> map) {
		this.title = (String)map.get("title");
		this.regDate = (String)map.get("regDate");
		this.id = (int)map.get("id");
		this.count = (int)map.get("count");
		this.likesCount = (int)map.get("likesCount");
		this.commentsCount = (int)map.get("commentsCount");
		this.extra__writer = (String)map.get("extra__writer");
		this.body = (String)map.get("body");
	}


	
}
