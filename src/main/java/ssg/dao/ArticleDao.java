package ssg.dao;

import java.util.Map;

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
	
}
