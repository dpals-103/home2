package ssg.dao;

import ssg.mysqlUtill.MysqlUtil;
import ssg.mysqlUtill.SecSql;

public class Ga4DataDao {

	public int deletePagePath(String pagePath) {
		SecSql sql = new SecSql();
		sql.append("delete");
		sql.append("from ga4DataPageCount");
		sql.append("where pagePath = ?", pagePath);
		
		return MysqlUtil.delete(sql);
	}

	public int savePagePath(String pagePath, int count) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO ga4DataPageCount");
		sql.append("SET regDate = now()");
		sql.append(", updateDate = now()");
		sql.append(",pagePath = ?", pagePath);
		sql.append(",count = ?", count);
		
		return MysqlUtil.insert(sql);
		
	}

}
