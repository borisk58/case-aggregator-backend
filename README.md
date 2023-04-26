# Case Aggregator Service
The Case Aggregator service's major purpose is to improve developer productivity, 
by grouping together cases based on common denominators. 
This ensures that engineers are not working in parallel on two separate cases which are essentially the same issue, and also speeds up the response time for the customers.

# Solution
The service fetches the specified CRM APIs periodically in order to get the updated cases status
and groups the cases by the (well-known) error code, the case provider name and affected products.
The aggregated cases are then stored in the local database (MongoDB).
The service responds to the developers frontend requests providing aggregated information 
by the error code, case provider, and the case status (all the parameters are optional).
# Endpoints

## Fetching CRM's on demand
## Querying aggregated cases upon the developers frontend requests



