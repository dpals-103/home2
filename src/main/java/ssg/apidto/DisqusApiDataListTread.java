package ssg.apidto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DisqusApiDataListTread {
	public int code;
	public List<Response> response;

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Response {
		public int likes;
		public int posts;
		
	}

}
