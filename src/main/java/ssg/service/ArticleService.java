package ssg.service;

import ssg.container.Container;
import ssg.dao.ArticleDao;
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

}
