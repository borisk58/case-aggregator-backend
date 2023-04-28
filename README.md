# Case Aggregator Service
The Case Aggregator service's major purpose is to improve developer productivity, 
by grouping together cases based on common denominators. 
This ensures that engineers are not working in parallel on two separate cases which are essentially the same issue, and also speeds up the response time for the customers.

# Solution
The service fetches the specified CRM APIs periodically in order to get the updated cases status
and groups the cases by the (well-known) error code and the provider.
The aggregated cases are then stored in the local database (MongoDB).
The service responds to the developers frontend requests providing aggregated information 
by the error code, provider, and case state (all the parameters are optional).
# Endpoints

## Querying aggregated cases upon the developers frontend requests
GET: /cases
<b> Returns aggregated cases by zero or more of the following parameters: provider, error code, case status (open or closed).
If no parameters provided, all the aggregated cases are returned
<b>Example: http://aggregated.cases.com/cases?errorCode=324;caseStatus=Open;provider=ABC</b>


## Fetching CRM's on demand
GET:  /cases
<p>Fetches CRMs on demand and brings new or changed cases if last update was made more than 15 minutes ago
The same parameters as in Querying with additional boolean for refreshing cases before query
<p>Example: http://aggregated.cases.com/cases?errorCode=324;refresh=true



