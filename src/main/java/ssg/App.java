package ssg;

import java.util.Scanner;

import ssg.container.Container;
import ssg.controller.ArticleController;
import ssg.controller.Controller;
import ssg.mysqlUtill.MysqlUtil;
import ssg.service.ArticleService;
import ssg.service.MemberService;

public class App {
	
	private ArticleService articleService; 
	private MemberService memberService; 
	
	
	App(){
		articleService = Container.articleService; 
		memberService = Container.memberService; 
	}
	

	public void run() {
		Scanner sc = Container.scanner;

		while (true) {
			System.out.printf("명령어 ) ");
			String cmd = sc.nextLine();

			//MysqlUtil.setDBInfo("127.0.0.1", "dpals103", "dlgywn0168", "a1");
			MysqlUtil.setDBInfo("127.0.0.1", "sbsst", "sbs123414", "a1");

			boolean needToExit = false;

			// 프로그램종료
			if (cmd.equals("exit")) {
				System.out.println("시스템종료");
				needToExit = true;
			}

			Controller controller = getControllerByCmd(cmd);
			if (controller != null) {
				controller.doAction(cmd);
			}

			MysqlUtil.closeConnection();

			if (needToExit) {
				break;
			}
		}

		sc.close();

	}

	private Controller getControllerByCmd(String cmd) {
		if (cmd.startsWith("article")) {
			return Container.articleController;
		} else if (cmd.startsWith("member")) {
			return Container.memberController;
		} else if (cmd.startsWith("build")) {
			return Container.buildController;
		}
		return null;
	}

}
