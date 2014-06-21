Feature: Bean manipulator
  In order to change part of the state of an object
  As a bean2bean api user
  I want to be able to manipulate object properties

  Scenario: Object creation as conversion
    Given A default configured bean2bean type converter
    	And a creation mapping configured
    When I convert null to destination type 
    Then I should obtain a new object created of expected type
    	And the object creator should be called
    
  Scenario: Object elimination as conversion
    Given A default configured bean2bean type converter
    	And an elimination mapping configured
    When I convert the object to Void type
    Then I should obtain null as result
    	And the object eliminator should be called

  Scenario: Object elimination as conversion
    Given A default configured bean2bean type converter
    	And an elimination mapping configured
    When I convert the object to Void type
    Then I should obtain null as result
    	And the object eliminator should be called
    	
    
