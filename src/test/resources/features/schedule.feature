Feature: Scheduled activities run

  Scenario: Activities available to book at gym are registered locally
    Given no activities are registered
    When the automated activity checker is run
    Then activities are registered
      | name                     | session                  |
      | The Peak - Metafit       | Metafit Mon 17:10        |
      | The Peak - Velocity Spin | Velocity Thu 12:10       |
      | The Peak - Velocity Spin | Velocity Blast Fri 07:15 |

  Scenario: User scheduled activities are booked if available
    Given a user with email "userOne@hmtt.co.uk" who has booked classes
      | name                     | session                  |
      | The Peak - Metafit       | Metafit Mon 17:10        |
      | The Peak - Velocity Spin | Velocity Thu 12:10       |
      | The Peak - Velocity Spin | Velocity Blast Fri 07:15 |
    Given a user with email "userTwo@hmtt.co.uk" who has booked classes
      | name                     | session                  |
      | The Peak - Metafit       | Metafit Mon 17:10        |
      | The Peak - Velocity Spin | Velocity Blast Fri 07:15 |
    When the automated booking function is run
    Then "userOne@hmtt.co.uk" classes are booked
      | name                     | session                  |
      | The Peak - Metafit       | Metafit Mon 17:10        |
      | The Peak - Velocity Spin | Velocity Thu 12:10       |
      | The Peak - Velocity Spin | Velocity Blast Fri 07:15 |
    Then "userTwo@hmtt.co.uk" classes are booked
      | name                     | session                  |
      | The Peak - Metafit       | Metafit Mon 17:10        |
      | The Peak - Velocity Spin | Velocity Blast Fri 07:15 |

  Scenario: User scheduled activities are booked if available and not excluded
    Given a user who has booked classes with exclusions
      | name                     | session            | exclusion  |
      | The Peak - Metafit       | Metafit Mon 17:10  | 22/02/2022 |
      | The Peak - Velocity Spin | Velocity Thu 12:10 | none       |
    When the automated booking function is run
    Then the chosen classes are booked
      | name                     | session            |
      | The Peak - Velocity Spin | Velocity Thu 12:10 |
    And the classes that were not chosen are not booked
      | name               | session           |
      | The Peak - Metafit | Metafit Mon 17:10 |
