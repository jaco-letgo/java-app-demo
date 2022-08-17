Feature: Find books containing title

  Scenario: I want to find all books with title containing a certain word
    Given a book titled "Moby Dick" with id "6cdc1f93-f684-40c6-8581-c225e5c6bce6"
    And a book titled "Dick Tracy" with id "c0603ca3-2b65-4840-9633-90aebc12fd36"
    And a book titled "Treasure's Island" with id "97034bbe-e03d-4d4d-9386-003f7603a4fe"
    And the endpoint "/books/containing/title/dick"
    When I make a "GET" http call
    Then response has 200 status code
    And response has body
    """
      {
        "books": [
          {
            "id": "c0603ca3-2b65-4840-9633-90aebc12fd36",
            "title": "Dick Tracy",
            "isEdited": false
          },
          {
            "id": "6cdc1f93-f684-40c6-8581-c225e5c6bce6",
            "title": "Moby Dick",
            "isEdited": false
          }
        ]
      }
    """
