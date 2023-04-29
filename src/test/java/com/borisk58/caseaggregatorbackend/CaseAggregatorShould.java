package com.borisk58.caseaggregatorbackend;

import com.borisk58.caseaggregatorbackend.model.AggregatedCase;
import com.borisk58.caseaggregatorbackend.model.CaseStatus;
import com.borisk58.caseaggregatorbackend.services.aggregator.AggregatorService;
import com.borisk58.caseaggregatorbackend.services.crmfetcher.CrmFetcherService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
class CaseAggregatorShould {

	private final CrmFetcherService fetcherService;
	private final AggregatorService aggregatorService;

	CaseAggregatorShould() {
		AggregatedRepositoryMock aggregatedRepo = new AggregatedRepositoryMock();
		this.fetcherService = new CrmFetcherService(aggregatedRepo);
		this.aggregatorService = new AggregatorService(aggregatedRepo);
	}

	@Test
	void loadContextWithoutErrors() {
	}

	@Test
	void insertAllNewCases() {
		fetcherService.fetchCasesFromCrm();
	}

	@Test
	void fetchAllAggregatedCases() {
		fetcherService.fetchCasesFromCrm();
		List<AggregatedCase> aggregatedCases = aggregatorService.fetchAggregatedCases(null, null, CaseStatus.Any);
		Assert.isTrue((long) aggregatedCases.size() == 6, "Unexpected number of cases, expected 6");
	}

	@Test
	void fetchRelevantAggregatedCasesByErrorCode() {
		fetcherService.fetchCasesFromCrm();
		List<AggregatedCase> aggregatedCases = aggregatorService.fetchAggregatedCases(null, 101, CaseStatus.Any);
		Assert.isTrue((long) aggregatedCases.size() == 2, "Unexpected number of cases, expected 2");
		Assert.isTrue(aggregatedCases.stream().allMatch(c -> c.getErrorCode() == 101), "Irrelevant error code found, all should be 101");
	}
	@Test
	void fetchRelevantAggregatedCasesByProvider() {
		fetcherService.fetchCasesFromCrm();
		List<AggregatedCase> aggregatedCases = aggregatorService.fetchAggregatedCases(1211, null, CaseStatus.Any);
		Assert.isTrue((long) aggregatedCases.size() == 1, "Unexpected number of cases, expected 1");
		Assert.isTrue(aggregatedCases.stream().allMatch(c -> c.getProvider() == 1211), "Irrelevant provider found, all should be 1211");
	}

	@Test
	void fetchRelevantAggregatedCasesByState() {
		fetcherService.fetchCasesFromCrm();
		List<AggregatedCase> aggregatedCases = aggregatorService.fetchAggregatedCases(null, null, CaseStatus.Open);
		Assert.isTrue((long) aggregatedCases.size() == 2, "Unexpected number of cases, expected 2");
		Assert.isTrue(aggregatedCases.stream().allMatch(c -> c.getCases().stream().allMatch(x -> x.getStatus().equals(CaseStatus.Open)) ),
				"Irrelevant case status found, all should be 'Open'");
	}
}
