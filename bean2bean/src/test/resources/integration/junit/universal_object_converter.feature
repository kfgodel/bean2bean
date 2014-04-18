Feature: Universal Object converter
  In order to get the type of object I need
  As a bean2bean api user
  I want to be able to any type of object to any other type

  Scenario: Typical domain object to Json String
    Given A default configured bean2bean type converter
    	And a configured mapping from any object to Json
    When I convert the domain object to String
    Then I should obtain a Json representation of the object
    
  Scenario: Typical domain object to toString String
    Given A default configured bean2bean type converter
    	And a configured mapping from any object to toString String
    When I convert the TO representation to the domain object
    Then I should obtain a toString representation of the object
    
