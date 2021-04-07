
@mailchimp
Feature: Register account
  I want to register an account


  Scenario Outline: Register
  Given I have entered Mailchimp site
    And I have typed in <email>
    And I have also typed in <username>
    And I have as well typed in <password>
    When I press sign up an account is made
    Then I verify <status> of account

    Examples: 
      | email | username | password   | status |
      | "CorrectEmail" | "CorrectUsername" | "Ab!45678" | "Check your email" |
      | "NoEmail" | "CorrectUsername" | "Ab!45678" | "Please enter a value" |
      | "CorrectEmail" | "100+UserName" | "Ab!45678" | "Enter a value less than 100 characters long" |
      | "CorrectEmail" | "UserAlreadyExist" | "Ab!45678" | "Another user with this username already exists. Maybe it's your evil twin. Spooky." |
