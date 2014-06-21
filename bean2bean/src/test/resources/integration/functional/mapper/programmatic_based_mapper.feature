Feature: Programmatic based object mapper
  In order to get the state of the object I need
  As a bean2bean api user
  I want to be able to map properties between objects using programmatic declarations

  Scenario: Mapping with declarations on the source object
    Given A default configured bean2bean type converter
    	And a programmatic mapped source object
    When I convert the source object to destination
    Then I should obtain the state from source on the destination
    
  Scenario: Mapping with declarations on the destination object
    Given A default configured bean2bean type converter
    	And a programmatic mapped destination object
    When I convert the source object to destination
    Then I should obtain the state from source on the destination
    
