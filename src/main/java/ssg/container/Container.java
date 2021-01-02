package ssg.container;

import java.util.Scanner;

import ssg.controller.ArticleController;
import ssg.controller.BuildController;
import ssg.controller.MemberController;
import ssg.dao.ArticleDao;
import ssg.service.ArticleService;
import ssg.service.BuildService;
import ssg.service.DisqusApiService;
import ssg.service.MemberService;

public class Container {

	public static ArticleController articleController;
	public static MemberController memberController;
	public static BuildController buildController;
	public static Scanner scanner;
	public static ArticleService articleService;
	public static ArticleDao articleDao;
	public static BuildService buildService;
	public static Session session;
	public static MemberService memberService;
	public static AppConfig config;
	public static DisqusApiService disqusApiService;

	static {
		
		

		config = new AppConfig();
		scanner = new Scanner(System.in);
		session = new Session();
		disqusApiService = new DisqusApiService();
		articleDao = new ArticleDao();

		memberService = new MemberService();
		articleService = new ArticleService();
		buildService = new BuildService();
		

		articleController = new ArticleController();
		memberController = new MemberController();
		buildController = new BuildController(scanner);
	}

}
