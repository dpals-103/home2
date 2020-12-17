package ssg.service;

import java.util.Collections;
import java.util.List;

import ssg.container.Container;
import ssg.dto.Article;
import ssg.dto.Board;
import ssg.util.Util;

public class BuildService {

	private ArticleService articleService;

	public BuildService() {
		articleService = Container.articleService;
	}

	public void buildSite() {

		// -----------메인 홈 만들기 -------------------
		Util.makeDir("site");
		Util.makeDir("site/home");
		String dir = System.getProperty("user.dir");

		// 홈 템플릿 가져오기
		String head = Util.getFileContents("site_template/part/head.html");

		// DB에서 게시판 목록 불러와서 업데이트 하기
		String boardListHtml = "";
		List<Board> boards = articleService.getBoards();

		for (Board board : boards) {
			boardListHtml += "<li><a href=\"" + dir + "/site/board/" + board.title + "-1.html\">" + board.title
					+ "</a></li>";
		}

		head = head.replace("[aaa]", boardListHtml);

		String footer = Util.getFileContents("site_template/part/footer.html");

		StringBuilder sb = new StringBuilder();

		sb.append("<!DOCTYPE html>");
		sb.append("<html lang=\"en\">");
		sb.append("<head>");

		sb.append("<meta charset=\"UTF-8\">");
		sb.append(" <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		sb.append("<title>index</title>");

		sb.append("</head>");

		sb.append("<body>");

		sb.append("<section class = \"con-min-width\">");
		sb.append("<div class=\"con\">");
		sb.append("<div class=\"section-1__home flex\">");
		sb.append("<div class=\"img-1\">");
		sb.append(
				"<img src=\"https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FOkHJU%2FbtqP4mlH810%2FKVNu5ZeCqN5kXnsT93K6c0%2Fimg.jpg\" alt=\"\">");
		sb.append("</div>");
		sb.append("<div class=\"img-2\">");
		sb.append(
				"<img src=\"https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbfmISn%2FbtqP6nScU7Z%2FHhHiDnrnmmEt1rPaNkVNs0%2Fimg.jpg\" alt=\"\">");
		sb.append("</div>");
		sb.append("<div class=\"text-box\">");
		sb.append("<span class=\"text-1\">Hi, I'm Je-ya! :)");
		sb.append("</span><br><br>");
		sb.append("<span class=\"text-2\">Lorem ipsum dolor sit amet consectetur adipisici\r\n"
				+ "ng elit. Nam perferendis recusandae rem fuga du\r\n"
				+ "cimus, eaque natus earum labore praesentium m\r\n"
				+ "aiores quae deserunt voluptas error! Id voluptates\r\n" + "quaerat iste ullam doloremque!");
		sb.append("</span>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("</section>");

		sb.append("</body>");

		sb.append("</html>");

		// 파일쓰기
		String fileName = "index" + ".html";
		Util.writeFile("site/home/" + fileName, head + sb.toString() + footer);
		System.out.printf("==%s 생성==\n", fileName);

		// -----------홈만들기 끝-------------------

		// -----------공지사항 게시판 만들기-------------------
		Util.makeDir("site/board");
		String list = Util.getFileContents("site_template/part/list.html");
		String titleHtml = "";

		titleHtml += "<span>Notice</span>";
		list = list.replace("[bbb]", titleHtml);

		// 페이징 제한
		int itemsInAPage = 10;

		// 공지사항 게시판의 게시글 불러오기
		List<Article> serchArticles_notice = articleService.getArticles(1);

		int totalPage = (int) Math.ceil((double) serchArticles_notice.size() / itemsInAPage);

		// 페이지가 1부터 시작될 때
		for (int page = 1; page <= totalPage; page++) {

			StringBuilder sb_notice = new StringBuilder();

			int startPos = serchArticles_notice.size() - 1;
			startPos -= (page - 1) * itemsInAPage;
			int endPos = startPos - (itemsInAPage - 1);

			if (endPos < 0) {
				endPos = 0;
			}

			if (startPos < 0) {
				break;
			}

			sb_notice.append("<main>");

			for (int i = startPos; i >= endPos; i--) {

				Article article = serchArticles_notice.get(i);

				sb_notice.append("<div class = \"list flex\">");

				sb_notice.append(" <div class=\"list__id\">" + article.id + "</div>");
				sb_notice
						.append(" <div class=\"list__title\"><a href =\"" + dir + "/site/article/notice/Notice-article-"
								+ article.id + ".html\">" + article.title + "</a></div>");
				sb_notice.append(" <div class=\"list__writer\">" + article.writer + "</div>");
				sb_notice.append(" <div class=\"list__count\">" + article.count + "</div>");
				sb_notice.append(" <div class=\"list__regDate\">" + article.regDate + "</div>");

				sb_notice.append("</div>");

			}

			sb_notice.append("</main>");
			sb_notice.append("<ul class=\"page flex\">");

			// <이전글> 기능 구현범위
			if (page > 1) {
				sb_notice.append("<li><a href=\"Notice-" + (page - 1) + ".html\">&lt; 이전글</a></li>");
			}

			String paging = "";

			for (int i = 1; i <= totalPage; i++) {
				String selectClass = ""; 

				if(i == page) {
					selectClass = "<li class =\"list__link--selected\">";
					paging += selectClass + "<a href=\"Notice-" + i + ".html\">" + i + "</a><li>";
				} else {
					paging += "<li><a href=\"Notice-" + i + ".html\">" + i + "</a><li>";
				}
			}

			sb_notice.append(paging);

			// <다음글> 기능 구현범위
			if (page < totalPage) {
				sb_notice.append("<li><a href=\"Notice-" + (page + 1) + ".html\">다음글 &gt;</a></li>");
			}

			sb_notice.append("</ul>");

			sb_notice.append("</div>");
			sb_notice.append("</section>");

			// Collections.reverse(articles);

			// 파일쓰기
			String notice_fileName = "Notice-" + page + ".html";
			Util.writeFile("site/board/" + notice_fileName, head + list + sb_notice.toString() + footer);
			System.out.printf("==%s 생성==\n", notice_fileName);
		}

		// -----------공지사항 게시판 만들기 끝 -------------------

		// -----------자유게시판 만들기-------------------

		Util.makeDir("site/board");
		titleHtml = "";
		list = Util.getFileContents("site_template/part/list.html");
		
		titleHtml += "<span>Free</span>";
		list = list.replace("[bbb]", titleHtml);


		// 자유게시판 게시판의 게시글 불러오기
		List<Article> serchArticles_free = articleService.getArticles(2);

		totalPage = (int) Math.ceil((double) serchArticles_free.size() / itemsInAPage);
		
		// 페이지가 1부터 시작될 때
		for (int page = 1; page <= totalPage; page++) {

			StringBuilder sb_free = new StringBuilder();

			int startPos = serchArticles_free.size() - 1;
			startPos -= (page - 1) * itemsInAPage;
			int endPos = startPos - (itemsInAPage - 1);

			if (endPos < 0) {
				endPos = 0;
			}

			if (startPos < 0) {
				break;
			}

			sb_free.append("<main>");

			for (int i = startPos; i >= endPos; i--) {

				Article article = serchArticles_free.get(i);

				sb_free.append("<div class = \"list flex\">");

				sb_free.append(" <div class=\"list__id\">" + article.id + "</div>");
				sb_free.append(" <div class=\"list__title\"><a href =\"" + dir + "/site/article/free/free-"
								+ article.id + ".html\">" + article.title + "</a></div>");
				sb_free.append(" <div class=\"list__writer\">" + article.writer + "</div>");
				sb_free.append(" <div class=\"list__count\">" + article.count + "</div>");
				sb_free.append(" <div class=\"list__regDate\">" + article.regDate + "</div>");

				sb_free.append("</div>");

			}

			sb_free.append("</main>");
			sb_free.append("<ul class=\"page flex\">");

			// <이전글> 기능 구현범위
			if (page > 1) {
				sb_free.append("<li><a href=\"Free Board-" + (page - 1) + ".html\">&lt; 이전글</a></li>");
			}

			String paging = "";

			for (int i = 1; i <= totalPage; i++) {
				String selectClass = ""; 

				if(i == page) {
					selectClass = "<li class =\"list__link--selected\">";
					paging += selectClass + "<a href=\"Free Board-" + i + ".html\">" + i + "</a><li>";
				} else {
					paging += "<li><a href=\"Free Board-" + i + ".html\">" + i + "</a><li>";
				}
			}

			sb_free.append(paging);

			// <다음글> 기능 구현범위
			if (page < totalPage) {
				sb_free.append("<li><a href=\"Free Board-" + (page + 1) + ".html\">다음글 &gt;</a></li>");
			}

			sb_free.append("</ul>");

			sb_free.append("</div>");
			sb_free.append("</section>");

			// Collections.reverse(articles);

			// 파일쓰기
			String free_fileName = "Free Board-" + page + ".html";
			Util.writeFile("site/board/" + free_fileName, head + list + sb_free.toString() + footer);
			System.out.printf("==%s 생성==\n", free_fileName);
		}

		// -----------자유게시판 만들기 끝-------------------

		// ----------- 공지사항 게시글 디테일 만들기 -------------------

		Util.makeDir("site/article");
		Util.makeDir("site/article/notice");

		String detail = Util.getFileContents("site_template/part/detail.html");

		List<Article> articles = articleService.getArticles(1);

		for (Article article : articles) {

		

			detail = detail.replace("${detail_title}", article.title);
			detail = detail.replace("${detail_regDate}", article.regDate);
			detail = detail.replace("${detail_writer}", article.writer);
			detail = detail.replace("${detail_count}",String.valueOf(article.count));
			detail = detail.replace("${detail_body}", article.body);
			
			/*
			sb_article.append("<section class=\"con-min-width\">");
			sb_article.append("<div class=\"con\">");
			sb_article.append("<div class=\"section-1__detail flex\">");

			sb_article.append("<div class=\"detail_1 flex\">");
			sb_article.append("<span>" + article.title + "</span>");
			sb_article.append("</div>");

			sb_article.append("<div class=\"detail_2 flex\">");
			sb_article.append("<div>작성일 : </div>");
			sb_article.append("<div class=\"regDate\">" + article.regDate + "</div>");
			sb_article.append("<div>작성자 :</div>");
			sb_article.append("<div class=\"writer\">" + article.writer + "</div>");
			sb_article.append("<div>조회수</div>");
			sb_article.append("<div class=\"count\">" + article.count + "</div>");
			sb_article.append("</div>");

			sb_article.append("	<div class=\"detail_3\">");
			sb_article.append("<div class=\"detail_body\"></div>");
			sb_article.append("</div>");
*/
			
			StringBuilder sb_article = new StringBuilder();
			
			sb_article.append("<div class=\"detail_4 flex\">");

			if (article.id > 1) {
				sb_article.append("<div class=\"prev\">");
				sb_article.append("<a href=\"Noticle-article-" + (article.id - 1) + ".html\"> &lt; 이전글 </a>");
				sb_article.append("</div>");
			}

			sb_article.append("<div class=\"article_list\">");
			sb_article.append("<a href=\"" + dir + "/site/board/Notice-1.html\">목록으로돌아가기</a>");
			sb_article.append("</div>");

			if (article.id < articles.size()) {
				sb_article.append("<div class=\"next\">");
				sb_article.append("<a href=\"Noticle-article-" + (article.id + 1) + ".html\">다음글 &gt;</a>");
				sb_article.append("</div>");
			}

			sb_article.append("</div>");

			sb_article.append("</div>");
			sb_article.append("</div>");
			sb_article.append("</section>");

			String toast = Util.getFileContents("site_template/part/toast.html");
			String article_fileName = "Notice-article-" + article.id + ".html";
			Util.writeFile("site/article/notice/" + article_fileName, head + detail + sb_article + toast + footer);
			System.out.printf("==%s 생성==\n", article_fileName);
		}

	}

}
