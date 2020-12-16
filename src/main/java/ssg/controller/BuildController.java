package ssg.controller;

import java.util.Scanner;

import ssg.container.Container;
import ssg.service.ArticleService;
import ssg.service.BuildService;
import ssg.util.Util;

public class BuildController extends Controller{
	private BuildService buildService; 
	
	public BuildController(Scanner sc) {
		buildService = Container.buildService; 
		
	}

	@Override
	public void doAction(String cmd) {
		if(cmd.equals("build site")) {
			doBuildsite(cmd); 
		}
	}

	
	private void doBuildsite(String cmd) {
		buildService.buildSite();
		System.out.println("html 생성을 시작합니다");
	}

	

}
