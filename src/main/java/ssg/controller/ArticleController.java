package ssg.controller;

import java.util.Scanner;

import ssg.container.Container;
import ssg.service.ArticleService;


public class ArticleController extends Controller{
	
	private static ArticleService articleService; 
	
	public ArticleController() {
		articleService = Container.articleService; 
	}

	@Override
	public void doAction(String cmd) {
	if(cmd.equals("article makeboard")) {
		doMakeboard(cmd); 
	}
	}

	
	private void doMakeboard(String cmd) {
		Scanner scanner = Container.scanner;
		System.out.println("==게시판생성==");

		String title;
		
		System.out.printf("게시판 이름 : " );
		title = scanner.nextLine();
		
		int id = articleService.makeboard(title);  
				
		System.out.printf("%s 게시판이 생성되었습니다\n", title);
		
		Container.session.selectedBoard(id); 
		
	}

}
