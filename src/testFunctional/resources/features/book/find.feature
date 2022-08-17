Feature: Find a book

  Scenario: I want to find a book
    Given a book titled "Moby Dick" with id "6cdc1f93-f684-40c6-8581-c225e5c6bce6"
    And the endpoint "/books/6cdc1f93-f684-40c6-8581-c225e5c6bce6"
    When I make a "GET" http call
    Then response has 200 status code
    And response has body
    """
      {
        "id": "6cdc1f93-f684-40c6-8581-c225e5c6bce6",
        "title": "Moby Dick",
        "isEdited": false
      }
    """

  Scenario: User wants to find a book
    Given a book titled "Moby Dick" with id "6cdc1f93-f684-40c6-8581-c225e5c6bce6"
    When the user finds a book with id "6cdc1f93-f684-40c6-8581-c225e5c6bce6"
    Then a book titled "Moby Dick" is found

  Scenario: I try to find a book that not exists
    Given the endpoint "/books/6cdc1f93-f684-40c6-8581-c225e5c6bce6"
    When I make a "GET" http call
    Then response has 404 status code
    And response has no body
