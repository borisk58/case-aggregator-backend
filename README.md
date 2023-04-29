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
<b>GET: /cases</b>
<p>Returns aggregated cases by zero or more of the following parameters.
If no parameters provided, all the aggregated cases are returned
<p>Parameters (all parameters are optional):
<ul>
<li>errorCode (int): fetch cases for this error code
<li>provider (int): fetch cases opened for this provider
<li>caseStatus (Open/Closed): fetch cases with specific status
<li>refresh (true/false): the aggregated cases will be retrieved from CRMs prior to be fetched. The refresh on demand is made only if at least 15 minutes passed since the last periodic sync 
</ul>
<p>Examples:
<p><b>http://localhost:8080/cases?refresh=true&caseStatus=Closed&errorCode=101</b>
<p><b>http://aggregated.cases.com/cases?errorCode=324;caseStatus=Open;provider=458</b>

# Config parameters
<ul>
<li> mongodb config string </li>
<li> mongodb database name </li>
<li> CRM synchronization interval (default 4 hours) </li>
</ul>



