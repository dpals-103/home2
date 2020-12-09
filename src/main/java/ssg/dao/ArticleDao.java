package ssg.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ssg.dto.Article;
import ssg.dto.Board;
import ssg.mysqlUtill.MysqlUtil;
import ssg.mysqlUtill.SecSql;


public class ArticleDao {

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

	public int wirte(String title, String body, int boardId) {
		// TODO Auto-generated method stub
		return 0;
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

}
