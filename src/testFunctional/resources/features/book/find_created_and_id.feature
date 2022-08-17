Feature: Find all created books and the one with the given id

  Scenario: I want to find all created books and the one with the given id
    Given a book titled "Moby Dick" with id "6cdc1f93-f684-40c6-8581-c225e5c6bce6"
    And an edited book titled "Dick Tracy" with id "c0603ca3-2b65-4840-9633-90aebc12fd36"
    And an edited book titled "Treasure's Island" with id "97034bbe-e03d-4d4d-9386-003f7603a4fe"
    And the endpoint "/books/with/id/97034bbe-e03d-4d4d-9386-003f7603a4fe"
    When I make a "GET" http call
    Then response has 200 status code
    And response has body
    """
      {
        "books": [
          {
            "id": "6cdc1f93-f684-40c6-8581-c225e5c6bce6",
            "title": "Moby Dick",
            "isEdited": false
          },
          {
            "id": "97034bbe-e03d-4d4d-9386-003f7603a4fe",
            "title": "Treasure's Island",
            "isEdited": true
          }
        ]
      }
    """
