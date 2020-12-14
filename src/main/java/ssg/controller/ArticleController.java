package ssg.controller;

import java.util.List;
import java.util.Scanner;

import ssg.container.Container;
import ssg.dto.Article;
import ssg.dto.Board;
import ssg.service.ArticleService;

public class ArticleController extends Controller {

	private static ArticleService articleService;


	public ArticleController() {
		articleService = Container.articleService;
	}

	@Override
	public void doAction(String cmd) {
		if (cmd.equals("article makeboard")) {
			doMakeboard(cmd);
		}
		if (cmd.equals("article testData")) {
			doMakedata(cmd);
		}
		if (cmd.startsWith("article selectBoard ")) {
			doSelect(cmd);
		}
		if (cmd.equals("article add")) {
			dowrite(cmd);
		}
		if (cmd.startsWith("article list")) {
			showList(cmd);
		}

	}

	private void doMakedata(String cmd) {

		// 공지사항
		for (int i = 1; i <= 30; i++) {
			articleService.write("제목" + i, "내용" + i, "홍길동" + i, 1);
		}

		// 자유게시판
		for (int i = 1; i <= 30; i++) {
			articleService.write("제목" + i, "내용" + i, "홍길순" + i, 2);
		}

		System.out.println("게시글 데이터를 생성했습니다.");
	}

	// 게시물 리스팅
	private void showList(String cmd) {
		System.out.println("==게시물리스팅==");

		int page = Integer.parseInt(cmd.split(" ")[2]);

		int boardId = Container.session.isSelectBoardId;

		List<Article> serchArticles = articleService.getArticles(boardId);

		int itemsInAPage = 10;
		int startPos = serchArticles.size() - 1;
		startPos -= (page - 1) * itemsInAPage;
		int endPos = startPos - (itemsInAPage - 1);

		if (endPos < 0) {
			endPos = 0;
		}

		if (startPos < 0) {
			System.out.println("해당 페이지는 존재하지 않습니다");
			return;
		}

		for (int i = startPos; i >= endPos; i--) {
			Article article = serchArticles.get(i);
			System.out.printf("%d / %s / %d / %s\n", article.id, article.title, article.count, article.regDate);
		}

	}

	// 게시판 선택하기

	private void doSelect(String cmd) {
		System.out.println("==게시판 선택하기==");

		int inputedId = Integer.parseInt(cmd.split(" ")[2]);

		Board board = articleService.getBoard(inputedId);

		System.out.printf("게시판이 선택되었습니다.");
		// System.out.printf("%s게시판이 선택되었습니다.\n", board.title);

		Container.session.selectedBoard(inputedId);
	}

	// 게시글 작성
	private void dowrite(String cmd) {
		Scanner sc = Container.scanner;
		System.out.println("==게시글작성==");

		String title;
		String body;
		String writer;

		System.out.printf("제목 : ");
		title = sc.nextLine();
		System.out.printf("내용 : ");
		body = sc.nextLine();
		System.out.printf("작성자 : ");
		writer = sc.nextLine();

		int boardId = Container.session.isSelectBoardId;

		int id = articleService.write(title, body, writer, boardId);
	}

	// 게시판 생성
	private void doMakeboard(String cmd) {
		Scanner sc = Container.scanner;
		System.out.println("==게시판생성==");

		String title;

		System.out.printf("게시판 이름 : ");
		title = sc.nextLine();

		int id = articleService.makeboard(title);

		System.out.printf("%s 게시판이 생성되었습니다\n", title);

		Container.session.selectedBoard(id);

	}

}
