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
		Util.makeDir("site");
		Util.makeDir("site/board");

		int boardId = Container.session.isSelectBoardId; 
				
		Board board = articleService.getBoard(boardId);

			StringBuilder sb = new StringBuilder();

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
			
			
			
			// 파일쓰기 
			String fileName = "board" + board.id + ".html"; 
 			Util.writeFile("site/board/" + fileName, sb.toString());

		}
	}

