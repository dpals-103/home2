package ssg.service;

import java.util.List;

import ssg.container.Container;
import ssg.dto.Article;
import ssg.dto.Board;
import ssg.util.Util;

public class BuildService {

	private ArticleService articleService;
	private String head;
	private String footer;
	private String dir;

	public BuildService() {
		articleService = Container.articleService;
		head = Util.getFileContents("site_template/head.html");
		footer = Util.getFileContents("site_template/footer.html");
		dir = System.getProperty("user.dir");
	}

	public void buildSite() {
		System.out.println("html생성을 시작합니다");

		Util.makeDir("site");
		Util.copy("site_template/app.css", "site/app.css");
		Util.copy("site_template/app.js", "site/app.js");

		buildIndex();
		buildBoard();
		buildDetail();
		buildAbout();
		buildStats();
		buildSocial();
	}
	
	
	/*-------소셜페이지----------------*/
	private void buildSocial() {
		StringBuilder sb = new StringBuilder();
		String social = Util.getFileContents("site_template/social.html");
		
		//헤더
		sb.append(head);
		// 소셜페이지
		sb.append(social); 
		//푸터
		sb.append(footer);
		
		String filePath = "site/social.html"; 
		Util.writeFile(filePath, sb.toString());
	}

	/*------------- 통계페이지 -----------------------*/ 
	private void buildStats() {
		
		StringBuilder sb = new StringBuilder();
		
		String stats = Util.getFileContents("site_template/stats.html");

		//헤더 첨부
		sb.append(head);
		
		List<Board> boards = articleService.getBoards();
		
		String dataHtml = "";
		
		for(int i =1; i <= boards.size(); i++) {
			Board board = articleService.getBoard(i);
			dataHtml += "<ul class =\"data flex\">";
			dataHtml += "<li class =\"boardTitle\">" + board.title + "</li>";
			
			List<Article> articles = articleService.getArticles(board.id);
			
			dataHtml += " <li class =\"count\">" + articles.size() + "</li>";
			dataHtml += "</ul>";
		}
			
		stats = stats.replace("[data]", dataHtml);
		
		List<Article> articles = articleService.getArticles();
		int total_count = articles.size();  
		
		stats = stats.replace("[total.count]", String.valueOf(total_count));
		
		sb.append(stats);
		
		//푸터 첨부
		sb.append(footer);
		
		String filePath = "site/stats.html";
		Util.writeFile(filePath, sb.toString());
		System.out.println(filePath + "생성");
	}

	/*------------- 소개페이지 -----------------------*/ 
	private void buildAbout() {
		
		StringBuilder sb = new StringBuilder();

		String about = Util.getFileContents("site_template/about.html");

		sb.append(head);
		sb.append(about);
		sb.append(footer);

		String filePath = "site/about.html";
		Util.writeFile(filePath, sb.toString());
		System.out.println(filePath + "생성");
		
	}

	/*--------------게시글 상세보기 만들기---------------------*/
	private void buildDetail() {

		List<Board> boards = articleService.getBoards();
		// 상세보기 템플릿
		String detail = Util.getFileContents("site_template/detail.html");
		// 토스트 에디터
		String toast = Util.getFileContents("site_template/toast.html");

		for (Board board : boards) {

			List<Article> articles = articleService.getArticles(board.id);
	
			for (int i = 0; i < articles.size(); i++) {

				Article article = articles.get(i);
  
				StringBuilder mainContent = new StringBuilder();

				mainContent.append("<div class=\"detail_1 flex\">");
				mainContent.append("<span>" + article.title + "</span>");
				mainContent.append("</div>");

				mainContent.append("<div class=\"detail_2 flex\">");
				mainContent.append("<div>작성일 : </div>");
				mainContent.append("<div class=\"regDate\">" + article.regDate + "</div>");
				mainContent.append("<div>작성자 :</div>");
				mainContent.append("<div class=\"writer\">" + article.extra__writer + "</div>");
				mainContent.append("<div>조회수</div>");
				mainContent.append("<div class=\"count\">" + article.count + "</div>");
				mainContent.append("</div>");

				mainContent.append("<div class=\"detail_3\">");

				String edit = "<div class=\"detail_body\">{article.body}</div>";
				edit = edit.replace("{article.body}", article.body);

				mainContent.append(edit);
				mainContent.append("</div>");

				mainContent.append("<div class=\"detail_4 flex\">");

				String boardTitle = articleService.getBoard(board.id).title;

				if (i > 0) {
					mainContent.append("<div class=\"prev\">");
					mainContent.append(
							"<a href=\"" + boardTitle + "-article-" + (article.id - 1) + ".html\"> &lt; 이전글 </a>");
					mainContent.append("</div>");
				}

				mainContent.append("<div class=\"article_list\">");
				mainContent.append("<a href=\"" + boardTitle + "-1.html\">목록으로돌아가기</a>");
				mainContent.append("</div>");

				if (i + 1 < articles.size()) {
					mainContent.append("<div class=\"next\">");
					mainContent.append(
							"<a href=\"" + boardTitle + "-article-" + (article.id + 1) + ".html\">다음글 &gt;</a>");
					mainContent.append("</div>");
				}

				mainContent.append("</div>");
				mainContent.append("</div>");
				mainContent.append("</div>");
				
				String js = "<script src=\"app.js\"></script>";
				mainContent.append(js);
				
				mainContent.append("</section>");

				StringBuilder sb = new StringBuilder();

				// 헤더_상세보기템플릿 첨부
				sb.append(head);

				// 디테일 첨부
				sb.append(detail);
				sb.append(mainContent);

				// 토스트 에디터 첨부
				//sb.append(toast);

				// 댓글 기능 첨부 
				String reply = Util.getFileContents("site_template/reply.html"); 
				sb.append(reply); 
				
				
				// 푸터 첨부
				sb.append(footer);

				// 파일 생성
				String filePath = "site/" + boardTitle + "-article-" + article.id + ".html";
				Util.writeFile(filePath, sb.toString());
				System.out.println(filePath + "생성");
				
				
			}

		}
	}

	/*---------------게시판만들기---------------------------*/
	private void buildBoard() {

		List<Board> boards = articleService.getBoards();

		for (Board board : boards) {

			int boardId = board.id;

			/* 게시글가져오기 */
			List<Article> serchArticles = articleService.getArticles(boardId);
			/* 페이지당 나타나는 게시글 수 */
			int itemsInAPage = 10;
			/* 게시판별 게시글 수 */
			int articlesCount = serchArticles.size();
			/* 게시글 수 에 따른 페이지 수 */
			int totalPage = (int) Math.ceil((double) articlesCount / itemsInAPage);

			for (int page = 1; page <= totalPage; page++) {

				StringBuilder sb = new StringBuilder();

				// 헤더 첨부
				sb.append(head);

				// 리스트(항목 부분) 시작_첨부
				String list = Util.getFileContents("site_template/list.html");

				String boardTitle = articleService.getBoard(boardId).title;
				list = list.replace("[board.title]", boardTitle);

				sb.append(list);

				// 리스트(db 부분) 시작

				StringBuilder mainList = new StringBuilder();

				int startPos = articlesCount - 1;
				startPos -= (page - 1) * itemsInAPage;
				int endPos = startPos - (itemsInAPage - 1);

				if (endPos < 0) {
					endPos = 0;
				}

				if (startPos < 0) {
					break;
				}

				for (int i = startPos; i >= endPos; i--) {

					Article article = serchArticles.get(i);

					mainList.append("<div class = \"list flex\">");

					/* mainList.append(" <div class=\"list__id\">" + article.id + "</div>"); */
					mainList.append(" <div class=\"list__title\"><a href =\"" + boardTitle + "-article-" + article.id
							+ ".html\">" + article.title + "</a></div>");
					mainList.append(" <div class=\"list__writer\">" + article.extra__writer + "</div>");
					mainList.append(" <div class=\"list__count\">" + article.count + "</div>");
					mainList.append(" <div class=\"list__regDate\">" + article.regDate + "</div>");

					mainList.append("</div>");
				}

				mainList.append("</main>");

				sb.append(mainList);

				// 리스트 페이징
				StringBuilder paging = new StringBuilder();

				paging.append("<ul class=\"page flex\">");

				/* <이전글> 기능 구현범위 */
				if (page > 1) {
					paging.append("<li><a href=\"" + boardTitle + "-" + (page - 1) + ".html\">&lt; 이전글</a></li>");
				}

				/* 열려있는 페이지박스에 클래스 추가하기 */
				for (int i = 1; i <= totalPage; i++) {
					String selectClass = "";

					if (i == page) {
						selectClass = "<li class =\"list__link--selected\">";
						paging.append(selectClass);
						paging.append("<a href=\"" + boardTitle + "-" + i + ".html\">" + i + "</a><li>");
					} else {
						paging.append("<li><a href=\"" + boardTitle + "-" + i + ".html\">" + i + "</a><li>");
					}
				}

				/* <다음글> 기능 구현범위 */
				if (page < totalPage) {
					paging.append("<li><a href=\"" + boardTitle + "-" + (page + 1) + ".html\">다음글 &gt;</a></li>");
				}
				paging.append("</ul>");
				paging.append("</div>");
				paging.append("</section>");

				// 페이징 첨부
				sb.append(paging);

				// 푸터 첨부
				sb.append(footer);
				// 파일 생성
				String filePath = "site/" + boardTitle + "-" + page + ".html";
				Util.writeFile(filePath, sb.toString());
				System.out.println(filePath + "생성");

			}

		}
	}

	/*---------------홈만들기---------------------------*/
	private void buildIndex() {

		StringBuilder sb = new StringBuilder();

		String boardListHtml = "";
		List<Board> boards = articleService.getBoards();

		for (Board board : boards) {
			boardListHtml += "<li><a href=\"" + board.title + "-1.html\">" + board.title + "</a></li>";
		}

		head = head.replace("[aaa]", boardListHtml);

		String main = Util.getFileContents("site_template/main.html");

		sb.append(head);
		sb.append(main);
		sb.append(footer);

		String filePath = "site/index.html";
		Util.writeFile(filePath, sb.toString());
		System.out.println(filePath + "생성");
	}

}
