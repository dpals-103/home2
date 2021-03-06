package ssg.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ssg.dto.Article;
import ssg.dto.Board;
import ssg.mysqlUtill.MysqlUtil;
import ssg.mysqlUtill.SecSql;
import ssg.util.Util;

public class ArticleDao {

	int lastArticleId;

	public ArticleDao() {
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

		sql.append("INSERT INTO article set title =? , body =?, writer =?, boardId =?, regDate=now()", title, body,
				writer, boardId);

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
		sql.append(
				"select A.*,M.name as extra__writer from article as A inner join `member` as M on A.memberId = M.id where boardId=?",
				boardId);

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleMapList) {
			newArticles.add(new Article(articleMap));
		}

		return newArticles;
	}
	
	
	public List<Article> getForPrintArticles() {
		List<Article> newArticles = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("select A.*,M.name as extra__writer from article as A inner join `member` as M on A.memberId = M.id");

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

	public String getArticle(int articleId, int boardId) {

		SecSql sql = new SecSql();
		sql.append("select title from article where id=? and boardId =? ", articleId, boardId);

		return MysqlUtil.selectRowStringValue(sql);

	}

	public int modify(Map<String, Object> args) {
		SecSql sql = new SecSql();

		int id = (int) args.get("id");
		String title = args.get("title") != null ? (String) args.get("title") : null;
		String body = args.get("body") != null ? (String) args.get("body") : null;
		int likesCount = args.get("likesCount") != null ? (int) args.get("likesCount") : -1;
		int commentsCount = args.get("commentsCount") != null ? (int) args.get("commentsCount") : -1;

		sql.append("update article set updateDate = now()");

		if (title != null) {
			sql.append(",title = ?", title);
		}
		if (body != null) {
			sql.append(",body = ?", body);
		}
		if (commentsCount != -1) {
			sql.append(",commentsCount = ?", commentsCount);

		}
		if (likesCount != -1) {
			sql.append(",likesCount = ?", likesCount);
		}

		sql.append("where id =?", id);

		return MysqlUtil.update(sql);
	}

	public int updateDbPageCounts() {

		SecSql sql = new SecSql();

		sql.append("UPDATE article");
		sql.append("INNER JOIN (");
		sql.append("SELECT CAST(REPLACE(");
		sql.append("REPLACE(pathWoQueryStr,\"/article-\",\"\"),");
		sql.append("\".html\",\"\")");
		sql.append("AS UNSIGNED) AS articleId, `count`");
		sql.append("FROM(");
		sql.append("SELECT");
		sql.append("IF(");
		sql.append("INSTR(ga4_PC.pagePath, '?') = 0,");
		sql.append("ga4_PC.pagePath,");
		sql.append("SUBSTR(ga4_PC.pagePath, 1, INSTR(ga4_PC.pagePath,'?')-1)");
		sql.append(") AS pathWoQueryStr,");
		sql.append("SUM(ga4_PC.count) AS `count`");
		sql.append("FROM ga4DataPageCount AS ga4_PC");
		sql.append("WHERE ga4_PC.pagePath LIKE '/article-%.html'");
		sql.append("GROUP BY pathWoQueryStr");
		sql.append(") AS ga4_PC");
		sql.append(")AS ga4_PC");
		sql.append("ON article.id = ga4_PC.articleId");
		sql.append("SET article.count = ga4_PC.count;");

		return MysqlUtil.update(sql);

	}

	public List<Article> getBestArticles() {
		
		List<Article> bestArticles = new ArrayList<>();

		SecSql sql = new SecSql();
		
		sql.append("select *"); 
		sql.append("from article"); 
		sql.append("order by `count` desc"); 
		sql.append("limit 5;"); 
	

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleMapList) {
			bestArticles.add(new Article(articleMap));
		}

		return bestArticles;
	}

}
