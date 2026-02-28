@api
Feature: User API Operations
  As an API consumer
  I want to manage users via the REST API
  So that I can perform CRUD operations

  @smoke @api
  Scenario: Get list of users
    When I send a GET request to "/users" with parameter "page" as "1"
    Then the response status code should be 200
    And the response should contain "data"
    And the response "page" value should be "1"

  @smoke @api
  Scenario: Get a single user
    When I send a GET request to "/users/2"
    Then the response status code should be 200
    And the response "data.id" value should be "2"
    And the response "data.email" should not be empty

  @api
  Scenario: Create a new user
    Given I have the following user data:
      | name  | Janet Doe      |
      | job   | QA Engineer    |
    When I send a POST request to "/users" with the user data
    Then the response status code should be 201
    And the response should contain "id"
    And the response "name" value should be "Janet Doe"

  @api
  Scenario: Update a user
    Given I have the following user data:
      | name  | John Updated   |
      | job   | Senior QA      |
    When I send a PUT request to "/users/2" with the user data
    Then the response status code should be 200
    And the response "name" value should be "John Updated"

  @api
  Scenario: Delete a user
    When I send a DELETE request to "/users/2"
    Then the response status code should be 204

  @api @negative
  Scenario: Get non-existent user
    When I send a GET request to "/users/999"
    Then the response status code should be 404
