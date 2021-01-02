package ssg.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssg.apidto.DisqusApiDataListTread;
import ssg.container.Container;
import ssg.dto.Article;
import ssg.dto.Board;
import ssg.util.Util;

public class DisqusApiService {
	private ArticleService articleService;

	public DisqusApiService() {
		articleService = Container.articleService;
	}

	public Map<String, Object> getArticleData(Article article) {
		
			String fileName = Container.buildService.getArticleDetailFileName(article.id);

			String url = "https://disqus.com/api/3.0/forums/listThreads.json";

			DisqusApiDataListTread disqusApiDataListTread = (DisqusApiDataListTread) Util.callApiResponseTo(
					DisqusApiDataListTread.class, url, "api_key=" + Container.config.getDisqusApiKey(),
					"forum=" + Container.config.getDisqusForum(), "thread:ident=" + fileName);

			if (disqusApiDataListTread == null) {
				return null;
			}

			Map<String, Object> rs = new HashMap<>();

			rs.put("likesCount", disqusApiDataListTread.response.get(0).likes);
			rs.put("commentsCount", disqusApiDataListTread.response.get(0).posts);

			return rs;
			
		}
	}

