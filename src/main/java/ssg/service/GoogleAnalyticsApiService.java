package ssg.service;

import java.io.IOException;

import com.google.analytics.data.v1alpha.AlphaAnalyticsDataClient;
import com.google.analytics.data.v1alpha.DateRange;
import com.google.analytics.data.v1alpha.Dimension;
import com.google.analytics.data.v1alpha.Entity;
import com.google.analytics.data.v1alpha.Metric;
import com.google.analytics.data.v1alpha.Row;
import com.google.analytics.data.v1alpha.RunReportRequest;
import com.google.analytics.data.v1alpha.RunReportResponse;

import ssg.container.Container;
import ssg.dao.ArticleDao;
import ssg.dao.Ga4DataDao;

public class GoogleAnalyticsApiService {
	private Ga4DataDao ga4DataDao; 
	
	public GoogleAnalyticsApiService() {
		ga4DataDao = new Ga4DataDao(); 
	}
		



	public boolean updateGa4PageCountsData() {
		
		String ga4PropertyId = Container.config.getGa4PropertyId();
		
		try (AlphaAnalyticsDataClient analyticsData = AlphaAnalyticsDataClient.create()) {
			RunReportRequest request = RunReportRequest.newBuilder()
					.setEntity(Entity.newBuilder().setPropertyId(ga4PropertyId))
					// 실제데이터를 어떤 분류로 볼 것인지
					.addDimensions(Dimension.newBuilder().setName("pagePath"))
					// 사용자 수
					.addMetrics(Metric.newBuilder().setName("activeUsers"))
					.addDateRanges(DateRange.newBuilder().setStartDate("2020-03-31").setEndDate("today")).build();

			
			RunReportResponse response = analyticsData.runReport(request);

			System.out.println("Report result:");
			
			for (Row row : response.getRowsList()) {
				String pagePath = row.getDimensionValues(0).getValue();
				int count = Integer.parseInt(row.getMetricValues(0).getValue());
				
				System.out.printf("pagePath : %s, counts : %d\n", pagePath, count);
				
				update(pagePath, count);
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}


	private void update(String pagePath, int count) {
		ga4DataDao.deletePagePath(pagePath);
		ga4DataDao.savePagePath(pagePath,count);
		
	}




	public void updatePageCount() {
		updateGa4PageCountsData();
		Container.articleService.updateDbPageCounts();
	}

}
