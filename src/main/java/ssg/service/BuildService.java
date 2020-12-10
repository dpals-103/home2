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
			boardListHtml += "<li><a href=\"file:///C:/work/sts-4.8.0.RELEASE-workspace/ssg/site/board/" + board.title + ".html\">" 
		+ board.title + "</a></li>";
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

		sb.append("<section class = \"section-1 con-min-width\">");
		sb.append("<div class=\"con\">");
		sb.append("<div class = \"section-contents\">");
		sb.append("Lorem ipsum dolor sit amet consectetur adipisicing elit. "
				+ "Nam deleniti numquam iusto quae necessitatibus eveniet esse velit eius "
				+ "inventore dolores atque rerum maiores quis sapiente, magnam corporis, ut "
				+ "nesciunt! Impedit. Cumque, unde quo excepturi totam iste ad perspiciatis dolores "
				+ "distinctio laborum. Ducimus fuga explicabo culpa perferendis consectetur assumenda"
				+ " ullam dicta nostrum tempore voluptatibus ex, amet aspernatur enim, voluptatum, voluptatem "
				+ "aliquid! Sunt suscipit maxime rerum? Ducimus, cumque nisi assumenda ipsa dicta impedit ad "
				+ "itaque repudiandae quas error accusantium perferendis deserunt a ea doloremque ipsum nobis "
				+ "esse illum debitis nesciunt aliquam quod? Velit incidunt voluptatem, eum est cum labore at "
				+ "vero maxime dolores distinctio ad facilis ullam porro tempora repellendus et necessitatibus, "
				+ "quibusdam dignissimos voluptas sunt. Tempora porro quidem aliquam optio magnam! Facilis, "
				+ "ex minima? Ipsum similique corrupti ad nam soluta tempore quia, aspernatur fuga nulla est totam,"
				+ " hic repellendus voluptatem aliquam nobis fugiat provident, "
				+ "amet dolore exercitationem quam. Laborum, consectetur quas?");
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
		String notice = Util.getFileContents("site_template/part/notice.html");

		StringBuilder sb_notice = new StringBuilder();

		List<Article> articles = articleService.getArticles();
		//List<Article> articlesList = articleService.getArticlesList();
		Collections.reverse(articles);	

		sb_notice.append("<main>");
		
		for (Article article : articles) {
			sb_notice.append("<div class = \"notice flex_list\">");

			sb_notice.append(" <div class=\"notice__id\">" + article.id + "</div>");
			sb_notice.append(" <div class=\"notice__title\">" + article.title + "</div>");
			sb_notice.append(" <div class=\"notice__writer\">" + article.writer + "</div>");
			sb_notice.append(" <div class=\"notice__count\">" + article.count + "</div>");
			sb_notice.append(" <div class=\"notice__regDate\">" + article.regDate + "</div>");

			sb_notice.append("</div>");
			
		}
		
		sb_notice.append("</main>");
		sb_notice.append("</div>");
		sb_notice.append("</div>");
		sb_notice.append("</section>");
		

		// 파일쓰기
		String notice_fileName = "Notice" + ".html";
		Util.writeFile("site/board/" + notice_fileName, head + notice + sb_notice.toString() + footer);
		System.out.printf("==%s 생성==\n", notice_fileName);
		
		// -----------공지사항 게시판 만들기 끝 -------------------
		
		
		
		// -----------자유게시판 만들기-------------------
		
		Util.makeDir("site/board");

		String freeboard = Util.getFileContents("site_template/part/freeboard.html");
		
		StringBuilder sb_free = new StringBuilder();

		List<Article> free_articles = articleService.getArticles();
		Collections.reverse(free_articles);	

		sb_free.append("<main>");
		for (Article article : free_articles) {
			sb_free.append("<div class = \"freeboard flex_list\">");

			sb_free.append(" <div class=\"freeboard__id\">" + article.id + "</div>");
			sb_free.append(" <div class=\"freeboard__title\">" + article.title + "</div>");
			sb_free.append(" <div class=\"freeboard__writer\">" + article.writer + "</div>");
			sb_free.append(" <div class=\"freeboard__count\">" + article.count + "</div>");
			sb_free.append(" <div class=\"freeboard__regDate\">" + article.regDate + "</div>");

			sb_free.append("</div>");	
		}
		
		sb_free.append("</main>");
		sb_free.append("</div>");
		sb_free.append("</div>");
		sb_free.append("</section>");
		

		// 파일쓰기
		String freeboard_fileName = "Free board" + ".html";
		Util.writeFile("site/board/" + freeboard_fileName, head + freeboard + sb_free.toString() + footer);
		System.out.printf("==%s 생성==\n", freeboard_fileName);
		
		// -----------자유게시판 끝-------------------
	}
}
