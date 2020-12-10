package ssg.controller;

import java.util.Scanner;

import ssg.container.Container;
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
		if (cmd.startsWith("article selectBoard ")) {
			doSelect(cmd);
		}
		if (cmd.equals("article add")) {
			dowrite(cmd);
		}
		
	}

	
	// 게시판 선택하기 
	
	private void doSelect(String cmd) {
		System.out.println("==게시판 선택하기==");
		
		int inputedId = Integer.parseInt(cmd.split("")[2]);
		
		Board board = articleService.getBoard(inputedId);
		
		System.out.printf("%s게시판이 선택되었습니다.\n", board.title);
		
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
		
		int id = articleService.write(title,body,writer,boardId); 
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
