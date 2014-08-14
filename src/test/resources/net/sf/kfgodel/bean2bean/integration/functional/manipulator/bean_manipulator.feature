Feature: Bean manipulator
  In order to change part of the state of a system
  As a bean2bean api user
  I want to be able to manipulate objects and properties

  Scenario: Object creation as conversion
    Given A default configured bean2bean type converter
    	And an object creator mapping configured
    When I convert null to a non nullable destination type
    Then I should obtain a new object created for the expected type
    	And the object creator should have been called
    
  Scenario: Object elimination as conversion
    Given A default configured bean2bean type converter
    	And an object eliminator mapping configured
    When I convert the object to Void type
    Then I should obtain null as result
    	And the object eliminator should have been called

  Scenario: Property getter
    Given An object with a state value
    And a getter expression on that state
    When I execute the getter expression
    Then I should obtain the state value

  Scenario: Property setter
    Given An object without a state value
    And a setter expression on that state
    When I execute the setter expression with a value
    Then the object should have the setted value
