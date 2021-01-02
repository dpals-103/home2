package ssg;

import ssg.util.Util;

public class Main {
	public static void main(String[] args) {

		testApi();
		
		//new App().run();
	}

	private static void testApi() {
		String url ="https://disqus.com/api/3.0/forums/listThreads.json";
		
		String rs = Util.callApi(url, "api_key=YjjuMG96pqVNFfDz4GsjVi1NKb7WwF12nkhLA5ztOx9GgcR7l86n10vHlQCYp17a", "forum=jeya-portfolio", "thread:ident=IT-article-2.html");
		System.out.println(rs);
	}
}
