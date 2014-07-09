Feature: Transfer Object Converter
  In order to abstract the internal state of a system
  As a bean2bean api user
  I want to represent a system state as a transfer objects

  Scenario: Typical domain object to TO
    Given A default configured bean2bean type converter
      And an implicit property name mapper between classes
      And a typical domain object instance
    When I convert the domain object to its TO representation
    Then I should obtain a TO object with the state of the domain object
    
  Scenario: TO to Typical domain object
    Given A default configured bean2bean type converter
    	And a configured mapping to a typical domain object from its transfer object
    When I convert the TO representation to the domain object
    Then I should obtain a domain object with the state from the TO
