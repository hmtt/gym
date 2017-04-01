@web
Feature: User logon

  @logon
  Scenario: User is authorised to use the site
    Given a user that is authorised to use the site
    When the user logs onto the site
    Then authorisation is "successful"

  Scenario: User enters incorrect pin number
    Given a user that is authorised to use the site
    When the user logs onto the site with an incorrect pin number
    Then authorisation is "un-successful"

  Scenario: User is authorised to use the site and is using it for the first time
    Given a user that is authorised to use the site
    When the user logs onto the site
    Then authorisation is "successful"
    And the user account is updated to indicate that they are an active user

  Scenario: User has previously entered an incorrect pin
    Given a user that is authorised to use the site but has previously entered incorrect details
    When the user logs onto the site
    Then authorisation is "successful"
    And the user account is updated to reset the failed logon count to "0"

  Scenario: User logs out of site
    Given a user that is authorised to use the site
    When the user logs onto the site
    And clicks the logout link
    Then the user cannot access site
