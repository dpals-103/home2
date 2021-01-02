package ssg.dto;

import java.text.SimpleDateFormat;
import java.util.Map;

public class Article {
	
	public String title;
	public String regDate;
	public int id;
	public String extra__writer;
	public int count;
	public int likesCount;
	public int commentsCount;
	public String body;

	
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
