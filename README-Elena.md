# Backend dev technical solution
This application offers an endpoint showing similar products to the one the user is currently seeing. To do this we agreed with our front-end applications to create a new REST API operation that will provide them the product detail of the similar products for a given one. [Here](./similarProducts.yaml) is the contract we agreed. 

We already have an endpoint that provides the product Ids similar for a given one. We also have another endpoint that returns the product detail by product id. [Here](./existingApis.yaml) is the documentation of the existing APIs.

## Run and test application in local 
You just need to have docker installed.

First of all, you may need to enable file sharing for the `shared` folder on your docker dashboard -> settings -> resources -> file sharing.

Then you can start the mocks, the application and other needed infrastructure with the following command.
```
docker-compose up
```
Check that mocks are working with a sample request to [http://localhost:3001/product/1/similarids](http://localhost:3001/product/1/similarids).

Check that application is working with a sample request to [http://localhost:5005/product/1/similar](http://localhost:5005/product/1/similar).

To execute the test run:
```
docker-compose run --rm k6 run scripts/test.js
```
Browse [http://localhost:3000/d/Le2Ku9NMk/k6-performance-test](http://localhost:3000/d/Le2Ku9NMk/k6-performance-test) to view the results.

## Evaluation
The following topics will be considered:
- Code clarity and maintainability
- Performance
- Resilience
