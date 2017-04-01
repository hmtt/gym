@web
Feature: Navigation optimised for mobile devices

  @scenarioConfiguredDriver @wip
  Scenario: Can navigate around the site using mobile optimised navigation
    Given a user who has booked classes
      | name         | session                |
      | Body Balance | Body Balance Thu 06.10 |
      | Metafit      | Metafit Tue 9:00       |
    When the user logs onto the mobile site
    Then clicks on <link> and navigates to <url>
      | link     | url           |
      | home     | /gym          |
      | schedule | /gym/schedule |
      | logout   | /login?logout |
