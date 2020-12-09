package ssg.service;

import java.util.List;

import ssg.container.Container;
import ssg.dto.Board;
import ssg.util.Util;

public class BuildService {

	private ArticleService articleService;

	public BuildService() {
		articleService = Container.articleService;
	}

	public void buildSite() {
	
		// 사이트 파일 만들기 
		Util.makeDir("site");
		Util.makeDir("site/board");
		
		// 홈 템플릿 가져오기 
		String head = Util.getFileContents("site-template/head.html");
		String fileName = "공지사항" + ".html"; 
		
		StringBuilder sb = new StringBuilder();

		Article article = articleService.getArticles(); 
		
		sb.append("<!DOCTYPE html>");
		sb.append("<html lang=\"en\">");
		sb.append("<head>");

		sb.append("<meta charset=\"UTF-8\">");
		sb.append(" <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		sb.append("<title> 게시판 만들기" + board.title + "</title>");

		sb.append("</head>");
		sb.append("<body>");

		sb.append("<h1>게시물 상세페이지</h1>");
		sb.append("<biv>");
		sb.append("번호 : " + board.id + "<br>");
		sb.append("이름 : " + board.title + "<br>");
		sb.append("생성날짜 : " + board.regDate);

		sb.append("</biv>");

		sb.append("</body>");

		sb.append("</html>");
		
		
		Util.writeFile("site/board/" + fileName, head + "공지사항");
		


			
			
			
			
			// 파일쓰기 
			String fileName = "board" + board.id + ".html"; 
 			Util.writeFile("site/board/" + fileName, sb.toString());
 		
		}
	}

