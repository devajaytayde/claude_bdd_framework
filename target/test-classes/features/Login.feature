@ui @login
Feature: Login Functionality
  As a registered user
  I want to be able to login to the application
  So that I can access the inventory

  Background:
    Given the user is on the login page

  @smoke @positive
  Scenario: Successful login with valid credentials
    When the user enters username "standard_user" and password "secret_sauce"
    And the user clicks the login button
    Then the user should be redirected to the inventory page
    And the inventory page title should be "Products"

  @negative
  Scenario: Login with invalid credentials
    When the user enters username "invalid_user" and password "wrong_password"
    And the user clicks the login button
    Then an error message should be displayed
    And the error message should contain "Username and password do not match"

  @negative
  Scenario: Login with empty credentials
    When the user enters username "" and password ""
    And the user clicks the login button
    Then an error message should be displayed
    And the error message should contain "Username is required"

  @smoke @positive
  Scenario Outline: Login with multiple valid users
    When the user enters username "<username>" and password "<password>"
    And the user clicks the login button
    Then the user should be redirected to the inventory page

    Examples:
      | username        | password     |
      | standard_user   | secret_sauce |
      | problem_user    | secret_sauce |
      | performance_glitch_user | secret_sauce |
