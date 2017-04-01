@web
Feature: User can schedule booking of classes

  Scenario: User books classes from a list of available classes
    Given an authorised user and available classes
      | name                     | session                  |
      | The Peak - Velocity Spin | Velocity Blast Wed 16:15 |
      | The Peak - Velocity Spin | Velocity Blast Fri 07:30 |
      | The Peak - RPM           | RPM Mon 10:15            |
    When the user logs onto the site
    And the user books class
      | name          | session                  |
      | Velocity Spin | Velocity Blast Wed 16:15 |
      | RPM           | RPM Mon 10:15            |
    Then the chosen classes are scheduled to be booked
      | name                     | session                  |
      | The Peak - Velocity Spin | Velocity Blast Wed 16:15 |
      | The Peak - RPM           | RPM Mon 10:15            |
    And the classes that were not chosen are not scheduled to be booked
      | name                     | session                  |
      | The Peak - Velocity Spin | Velocity Blast Fri 07:30 |

  @scenarioConfiguredDriver @wip
  Scenario: User views schedule to show booked classes
    Given a user with email "userOne@hmtt.co.uk" who has booked classes
      | name                    | session                |
      | The Peak - Body Balance | Body Balance Thu 06.10 |
      | The Peak - Body Balance | Body Balance Tue 20:00 |
      | The Peak - Metafit      | Metafit Tue 9:00       |
    And a user with email "userTwo@hmtt.co.uk" who has booked classes
      | name                     | session            |
      | The Peak - Velocity Spin | Velocity Mon 12.10 |
      | The Peak - Rpm Biking    | Rpm Tue 16:45      |
    When user with email "userOne@hmtt.co.uk" logs onto the site
    Then chosen classes are scheduled on the web site
      | name                    | session                |
      | The Peak - Body Balance | Body Balance Thu 06.10 |
      | The Peak - Body Balance | Body Balance Tue 20:00 |
      | The Peak - Metafit      | Metafit Tue 9:00       |
    When user with email "userTwo@hmtt.co.uk" logs onto the site
    Then chosen classes are scheduled on the web site
      | name                     | session            |
      | The Peak - Velocity Spin | Velocity Mon 12.10 |
      | The Peak - Rpm Biking    | Rpm Tue 16:45      |

  Scenario: User clicks through to activity from schedule list of booked activities
    Given a user with email "admin@hmtt.co.uk" who has booked classes
      | name                | session                  |
      | The Peak - Velocity | Velocity Blast Sat 08.10 |
    When the user logs onto the site
    And clicks on the schedule link for "The Peak - Velocity" for "Velocity Blast Sat 08.10"
    Then the user navigates to the booking screen for "The Peak - Velocity" for "Velocity Blast Sat 08.10"

  Scenario: User views schedule to show booked classes with exclusions
    Given a user who has booked classes with exclusions
      | name                    | session                  | exclusion  |
      | The Peak - Body Balance | Body Balance Thu 06.10   | 05/06/2016 |
      | The Peak - Body Balance | Body Balance Thu 06.10   | 12/06/2016 |
      | The Peak - Velocity     | Velocity Blast Sat 08.10 | 15/07/2016 |
    When the user logs onto the site
    Then the activities should be scheduled with exclusions
      | name                    | session                  | exclusion  |
      | The Peak - Body Balance | Body Balance Thu 06.10   | 05/06/2016 |
      | The Peak - Body Balance | Body Balance Thu 06.10   | 12/06/2016 |
      | The Peak - Velocity     | Velocity Blast Sat 08.10 | 15/07/2016 |

  Scenario: User adds an exclusion to current booking
    Given a user who has booked classes
      | name                | session                  |
      | The Peak - Velocity | Velocity Blast Sat 08.10 |
    When the user logs onto the site
    And adds an exclusion
      | displayName | name                | session                  | exclusion  |
      | Velocity    | The Peak - Velocity | Velocity Blast Sat 08.10 | 12/06/2017 |
    Then the exclusion is added to the users schedule
      | displayName | name                | session                  | exclusion  |
      | Velocity    | The Peak - Velocity | Velocity Blast Sat 08.10 | 12/06/2017 |