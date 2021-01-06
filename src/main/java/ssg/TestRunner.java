package ssg;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.analytics.data.v1alpha.AlphaAnalyticsDataClient;
import com.google.analytics.data.v1alpha.DateRange;
import com.google.analytics.data.v1alpha.Dimension;
import com.google.analytics.data.v1alpha.Entity;
import com.google.analytics.data.v1alpha.Metric;
import com.google.analytics.data.v1alpha.Row;
import com.google.analytics.data.v1alpha.RunReportRequest;
import com.google.analytics.data.v1alpha.RunReportResponse;

import ssg.apidto.DisqusApiDataListTread;
import ssg.container.Container;
import ssg.util.Util;

public class TestRunner {
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class TestDataType1 {
		public int age;
		public int height;
		public String name;
	}

	void run() {
		// testApi2();
		testGoogleCredentitials();
		testUpdateGoogleAnalyticsApi();

	}

	private void testUpdateGoogleAnalyticsApi() {
		Container.googleAnalyticsApiService.updatePageCountsData();
		String ga4PropertyId = Container.config.getGa4PropertyId();
		try (AlphaAnalyticsDataClient analyticsData = AlphaAnalyticsDataClient.create()) {
			RunReportRequest request = RunReportRequest.newBuilder()
					.setEntity(Entity.newBuilder().setPropertyId(ga4PropertyId))
					// 실제데이터를 어떤 분류로 볼 것인지
					.addDimensions(Dimension.newBuilder().setName("pagePath"))
					// 사용자 수
					.addMetrics(Metric.newBuilder().setName("activeUsers"))
					.addDateRanges(DateRange.newBuilder().setStartDate("2020-03-31").setEndDate("today")).build();

			// Make the request
			RunReportResponse response = analyticsData.runReport(request);

			System.out.println("Report result:");
			for (Row row : response.getRowsList()) {
				System.out.printf("%s, %s%n", row.getDimensionValues(0).getValue(), row.getMetricValues(0).getValue());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	

	// 환경변수 설정 테스트 --
	private void testGoogleCredentitials() {
		String keyFilePath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
		System.out.println(keyFilePath);
	}

	private void testJackson5() {
		String jsonString = "[{\"age\":22,\"name\":\"홍길동\",\"height\":178},{\"age\":23, \"name\":\"홍길순\",\"height\":158},{\"age\":25, \"name\":\"임꺽정\",\"height\":168}]";

		ObjectMapper ob = new ObjectMapper();
		List<TestDataType1> rs = null;

		try {
			rs = ob.readValue(jsonString, new TypeReference<List<TestDataType1>>() {
			});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}
		System.out.println(rs.get(1).height);

	}

	private void testJackson4() {
		String jsonString = "[{\"age\":22, \"name\":\"홍길동\"},{\"age\":23, \"name\":\"홍길순\"},{\"age\":25, \"name\":\"임꺽정\"}]";

		ObjectMapper ob = new ObjectMapper();
		List<Map<String, Object>> rs = null;

		try {
			rs = ob.readValue(jsonString, List.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}
		System.out.println(rs.get(2).get("age"));

	}

	private void testJacson3() {
		String jsonString = "[1,2,3]";

		ObjectMapper ob = new ObjectMapper();
		List<Integer> rs = null;

		try {
			rs = ob.readValue(jsonString, List.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}
		System.out.println(rs.get(1));

	}

	private void testJacson2() {
		String jsonString = "1";

		ObjectMapper ob = new ObjectMapper();
		Integer rs = null;

		try {
			rs = ob.readValue(jsonString, Integer.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}
		System.out.println(rs);

	}

	private void testJacson() {
		String jsonString = "{\"age\":22, \"name\":\"홍길동\"}";

		ObjectMapper ob = new ObjectMapper();
		Map rs = null;

		try {
			rs = ob.readValue(jsonString, Map.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}
		System.out.println(rs.get("age"));
	}

	private void testApi() {
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";

		String rs = Util.callApi(url, "api_key=YjjuMG96pqVNFfDz4GsjVi1NKb7WwF12nkhLA5ztOx9GgcR7l86n10vHlQCYp17a",
				"forum=jeya-portfolio", "thread:ident=IT-article-2.html");
		System.out.println(rs);
	}

	// 좋아요
	private void testApi2() {
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";

		Map<String, Object> rs = Util.callApiResponseToMap(url, "api_key=" + Container.config.getDisqusApiKey(),
				"forum=jeya-portfolio", "thread:ident=article-1.html");
		List<Map<String, Object>> response = (List<Map<String, Object>>) rs.get("response");
		Map<String, Object> thread = response.get(0);
		System.out.println(rs);
	}

	private void testApi3() {
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";

		DisqusApiDataListTread rs = (DisqusApiDataListTread) Util.callApiResponseTo(DisqusApiDataListTread.class, url,
				"api_key=" + Container.config.getDisqusApiKey(), "forum=jeya-portfolio", "thread:ident=article-1.html");
		System.out.println(rs);
		// System.out.println(rs.response.get(0).posts);
	}

}
