Feature: Change a book's title
  Scenario: User wants to change a book's title
    Given a book titled "Island's Treasure" with id "6cdc1f93-f684-40c6-8581-c225e5c6bce6"
    And the endpoint "/books/6cdc1f93-f684-40c6-8581-c225e5c6bce6"
    And the http headers
    | Content-Type | application/json |
    And the json body
    """
      {
        "title": "Moby Dick"
      }
    """
    When I make a "PUT" http call
    And I wait for the messages to be processed
    Then response has 202 status code
    And a book with id "6cdc1f93-f684-40c6-8581-c225e5c6bce6" exists
    And it has been edited
    And its title is "Moby Dick"

  Scenario: User wants to change a book's title
    Given there is a book titled "Island's Treasure"
    When the user changes its title to "Moby Dick"
    Then the book is titled "Moby Dick"

  Scenario: App returns accepted response even when book is not found for given id
    Given the endpoint "/books/6cdc1f93-f684-40c6-8581-c225e5c6bce6"
    And the http headers
      | Content-Type | application/json |
    And the json body
    """
      {
        "title": "Moby Dick"
      }
    """
    When I make a "PUT" http call
    And I wait for the messages to be processed
    Then response has 202 status code
    But a book with id "6cdc1f93-f684-40c6-8581-c225e5c6bce6" does not exist
