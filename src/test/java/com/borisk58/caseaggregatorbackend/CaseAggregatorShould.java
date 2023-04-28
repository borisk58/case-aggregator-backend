package com.borisk58.caseaggregatorbackend;

import com.borisk58.caseaggregatorbackend.services.crmfetcher.CrmFetcherService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class CaseAggregatorShould {

	@Test
	void loadContextWithoutErrors() {
	}

	@Test
	void insertAllNewCases() {
		CasesRepositoryMock repo = new CasesRepositoryMock();
		StatusRepositoryMock statusRepo = new StatusRepositoryMock();
		AggregatedRepositoryMock aggregatedRepo = new AggregatedRepositoryMock();
		CrmFetcherService fetcherService = new CrmFetcherService(repo, statusRepo, aggregatedRepo);
		fetcherService.fetchCases();
		Assert.isTrue((long) repo.findAllCases().size() == 7, "count of cases is not as expected");
		Assert.isTrue((long) statusRepo.findAllStatuses().size() == 2, "not all CRMs were queried");
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
