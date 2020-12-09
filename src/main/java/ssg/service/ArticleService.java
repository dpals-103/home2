package ssg.service;

import java.util.List;

import ssg.container.Container;
import ssg.dao.ArticleDao;
import ssg.dto.Article;
import ssg.dto.Board;

public class ArticleService {

	private static ArticleDao articleDao;

	public ArticleService() {
		articleDao = Container.articleDao;
	}

	public int makeboard(String title) {
		return articleDao.makeboard(title);
	}

	public Board getBoard(int id) {
		return articleDao.getBoard(id);
	}

	public int write(String title, String body, int boardId) {
		return articleDao.wirte(title,body,boardId); 
	}

	public List<Article> getArticles() {
		return articleDao.getArticles(); 
	}

}
