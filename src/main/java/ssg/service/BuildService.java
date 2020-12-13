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

		// 홈 템플릿 가져오기
		String head = Util.getFileContents("site_template/part/head.html");

		// DB에서 게시판 목록 불러와서 업데이트 하기
		String boardListHtml = "";
		List<Board> boards = articleService.getBoards();

		for (Board board : boards) {
			boardListHtml += "<li><a href=\"file:///C:/work/sts-4.8.0.RELEASE-workspace/ssg/site/board/" + board.title
					+ "-1" + ".html\">" + board.title + "</a></li>";
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

		String paging = "";

		for (int i = 1; i <= serchArticles_notice.size() / 10; i++) {
			paging += " <div class=\"box\">"
					+ " <a href=\"file:///C:/work/sts-4.8.0.RELEASE-workspace/ssg/site/board/Notice-" + i + ".html\">"
					+ "[" + i + "]" + "</a>" + " </div>";
		}

		// 페이지가 1부터 시작될 때
		for (int page = 1; page <= 100; page++) {

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
				sb_notice.append(" <div class=\"list__title\">" + article.title + "</div>");
				sb_notice.append(" <div class=\"list__writer\">" + article.writer + "</div>");
				sb_notice.append(" <div class=\"list__count\">" + article.count + "</div>");
				sb_notice.append(" <div class=\"list__regDate\">" + article.regDate + "</div>");

				sb_notice.append("</div>");

			}

			sb_notice.append("</main>");
			sb_notice.append("<div class=\"page flex\">");
			sb_notice.append(paging);
			sb_notice.append("</div>");
			sb_notice.append("</div>");
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

		titleHtml += "<span>Free board</span>";
		list = list.replace("[bbb]", titleHtml);

		paging = "";

		for (int i = 1; i <= serchArticles_notice.size() / 10; i++) {
			paging += " <div class=\"box\">"
					+ " <a href=\"file:///C:/work/sts-4.8.0.RELEASE-workspace/ssg/site/board/Free board-" + i
					+ ".html\">" + "[" + i + "]" + "</a>" + " </div>";
		}
		// 자유게시판 게시판의 게시글 불러오기
		List<Article> serchArticles_free = articleService.getArticles(2);

		// 페이지가 1부터 시작될 때
		for (int page = 1; page <= 100; page++) {

			StringBuilder sb_freeboard = new StringBuilder();

			int startPos = serchArticles_free.size() - 1;
			startPos -= (page - 1) * itemsInAPage;
			int endPos = startPos - (itemsInAPage - 1);

			if (endPos < 0) {
				endPos = 0;
			}

			if (startPos < 0) {
				break;
			}

			sb_freeboard.append("<main>");

			for (int i = startPos; i >= endPos; i--) {

				Article article = serchArticles_free.get(i);

				sb_freeboard.append("<div class = \"list flex\">");

				sb_freeboard.append(" <div class=\"list__id\">" + article.id + "</div>");
				sb_freeboard.append(" <div class=\"list__title\">" + article.title + "</div>");
				sb_freeboard.append(" <div class=\"list__writer\">" + article.writer + "</div>");
				sb_freeboard.append(" <div class=\"list__count\">" + article.count + "</div>");
				sb_freeboard.append(" <div class=\"list__regDate\">" + article.regDate + "</div>");

				sb_freeboard.append("</div>");
			}

			sb_freeboard.append("</main>");
			sb_freeboard.append("<div class=\"page flex\">");
			sb_freeboard.append(paging);
			sb_freeboard.append("</div>");
			sb_freeboard.append("</div>");
			sb_freeboard.append("</div>");

			sb_freeboard.append("</section>");

			// Collections.reverse(articles);

			// 파일쓰기
			String freeboard_fileName = "Free board-" + page + ".html";
			Util.writeFile("site/board/" + freeboard_fileName, head + list + sb_freeboard.toString() + footer);
			System.out.printf("==%s 생성==\n", freeboard_fileName);
		}

		// -----------자유게시판 만들기 끝-------------------
	
		// -----------게시글 디테일 만들기 -------------------
		
		Util.makeDir("site/article");
		Util.makeDir("site/article/notice");
		String detail = Util.getFileContents("site_template/part/detail.html");
		
		// 공지사항 게시판의 게시글 불러오기
		serchArticles_notice = articleService.getArticles(1);
		
		for(Article article : serchArticles_notice) {
			detail = detail.replace("[title]", article.title);
			detail = detail.replace("[regDate]", article.regDate);
			detail = detail.replace("[count]", String.valueOf(article.count));
			detail = detail.replace("[body]", article.body);
	
			String article_fileName = "Notice-article-" + article.id + ".html";
			Util.writeFile("site/article/notice" + article_fileName, head + list + detail + footer);
			System.out.printf("==%s 생성==\n", article_fileName);
		}


	}
}
