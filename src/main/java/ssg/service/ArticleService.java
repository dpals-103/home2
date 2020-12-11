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

	public int write(String title, String body, String writer, int boardId) {
		return articleDao.wirte(title,body,writer,boardId); 
	}

	public List<Article> getArticles() {
		return articleDao.getArticles(); 
	}

	public List<Article> getArticles(int boardId) {
		return articleDao.getArticles(boardId);
	}
	
	public List<Board> getBoards() {
		return articleDao.getBoards();
	}

	public List<Board> getBoardsByboardId(int boardId) {
		return articleDao.getBoardsByboardId(boardId);
	}



}
