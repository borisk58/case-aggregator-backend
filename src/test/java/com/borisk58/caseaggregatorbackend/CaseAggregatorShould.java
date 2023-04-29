package com.borisk58.caseaggregatorbackend;

import com.borisk58.caseaggregatorbackend.services.crmfetcher.CrmFetcherService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CaseAggregatorShould {

	@Test
	void loadContextWithoutErrors() {
	}

	@Test
	void insertAllNewCases() {
		AggregatedRepositoryMock aggregatedRepo = new AggregatedRepositoryMock();
		CrmFetcherService fetcherService = new CrmFetcherService(aggregatedRepo);
		fetcherService.fetchCases();
	}

	@Test
	void fetchRelevantAggregatedCases() {

	}

	@Test
	void fetchRelevantAggregatedCasesByErrorCode() {

	}
	@Test
	void fetchRelevantAggregatedCasesByProvider() {

	}

	@Test
	void fetchRelevantAggregatedCasesByState() {

	}
}
