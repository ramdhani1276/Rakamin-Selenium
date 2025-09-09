Feature: Login page website Swag Labs

  @Regression @Positive
  Scenario: Success Login
    Given Halaman login Swag Labs
    When Input username
    And Input password
    And Click login button
    Then User on dashboard page

  @Regression @Negative
  Scenario: Failed Login
    Given Halaman login Swag Labs
    When Input username
    And Input invalid password
    And Click login button
    Then User get error message

  @TDD
  Scenario Outline: User login to Sauce Demo
    Given Halaman login Swag Labs
    When I input <email> as email
    And I input <password> as password
    And Click login button
    Then I verify <status> login result

    Examples:
    | email         | password     | status  |
    | standard_user | secret_sauce | Success |
    | standard_user | secret       | Failed  |