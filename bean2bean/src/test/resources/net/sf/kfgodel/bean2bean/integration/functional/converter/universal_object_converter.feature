Feature: Universal Object converter
  In order to get the type of object I need
  As a bean2bean api user
  I want to be able to convert any type of object to any other type

  Scenario: Typical domain object to Json String
    Given A default configured bean2bean type converter
    	And a configured mapping from any object to Json
    When I convert the domain object to String
    Then I should obtain a Json representation of the object

  Scenario: Json String to typical domain object
    Given A default configured bean2bean type converter
    And a configured mapping from Json to any object
    When I convert the String to a domain object
    Then I should obtain an Object representation of the Json

