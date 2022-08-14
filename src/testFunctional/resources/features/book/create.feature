Feature: Create a book
  Scenario: User wants to create a book
    Given the endpoint "/books"
    And the headers
    | Content-Type | application/json |
    And the body
    """
      {
        "id": "6cdc1f93-f684-40c6-8581-c225e5c6bce6",
        "title": "Moby Dick"
      }
    """
    When the user makes a POST call
    And we wait for the messages to be processed
    Then the user receives a 201 status code
    And a book with id "6cdc1f93-f684-40c6-8581-c225e5c6bce6" exists
    And its title is "MOBY DICK"

  Scenario: User wants to create a book
    Given there is no book titled "MOBY DICK"
    When the user creates a book titled "Moby Dick"
    Then a book titled "MOBY DICK" is created
