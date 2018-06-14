# Cities

Decisions made to improve performance:

* Use Array and indexes so it doesn't duplicate objects
* Create a `nameLowerCase` field on the model to avoid using `String.toLowerCase()` all the time
* Create a hashmap that holds all indexes for the first two characters, so the search on the beginning (when the list is bigger) is O(1)

To-Do:

* Improve UI all around
* Refactor the CityDetailFragment to use MVP and be testable
* Improve test coverage on the search
* Decouple more the DataSource and the Presenter on the list 
  * (it's kinda fuzzy that the presenter knows how the DataSource stores the indexes)
