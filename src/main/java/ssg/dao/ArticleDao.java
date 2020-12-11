package ssg.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ssg.dto.Article;
import ssg.dto.Board;
import ssg.mysqlUtill.MysqlUtil;
import ssg.mysqlUtill.SecSql;


public class ArticleDao {
	
	int lastArticleId;
	
	public ArticleDao(){
		lastArticleId = 0; 
	}

	public int makeboard(String title) {

		SecSql sql = new SecSql();

		sql.append("INSERT INTO board");
		sql.append(" SET regDate = NOW()");
		sql.append(", title = ?", title);

		return MysqlUtil.insert(sql);
		
	}

	public Board getBoard(int id) {

		SecSql sql = new SecSql();
		sql.append("select * from board where id = ?", id);

		Map<String, Object> map = MysqlUtil.selectRow(sql);

		if (map.isEmpty()) {
			return null;
		}

		return new Board(map);

	}

	public int wirte(String title, String body, String writer, int boardId) {
		SecSql sql = new SecSql();
		
		sql.append("INSERT INTO article set title =? , body =?, writer =?, boardId =?, regDate=now()",
				title, body, writer, boardId);
		
		return MysqlUtil.insert(sql);

	}

	public List<Article> getArticles() {
		List<Article> newArticles = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("select * from article");

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleMapList) {
			newArticles.add(new Article(articleMap));
		}

		return newArticles;
	}

	public List<Article> getArticles(int boardId) {
		List<Article> newArticles = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("select * from article where boardId=?", boardId);

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleMapList) {
			newArticles.add(new Article(articleMap));
		}

		return newArticles;
	}
	
	
	public List<Board> getBoards() {
		
		List<Board> boards = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("select * from board");

		List<Map<String, Object>> boardMapList = MysqlUtil.selectRows(sql);
		
		if (boardMapList.isEmpty()) {
			return null;
		}

		for (Map<String, Object> boardMap : boardMapList) {
			boards.add(new Board(boardMap));
		}

		return boards;
	}

	public List<Board> getBoardsByboardId(int boardId) {

		List<Board> boards = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("select * from board where boardId =?", boardId);

		List<Map<String, Object>> boardMapList = MysqlUtil.selectRows(sql);
		
		if (boardMapList.isEmpty()) {
			return null;
		}

		for (Map<String, Object> boardMap : boardMapList) {
			boards.add(new Board(boardMap));
		}

		return boards;

	}

	

}
