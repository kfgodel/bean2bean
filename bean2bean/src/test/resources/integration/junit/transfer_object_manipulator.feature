Feature: Transfer Object manipulator
  In order to abstract the internal state representation of a system 
  As a bean2bean api user
  I want to represent its internal state as transfer objects

  Scenario: Typical domain object to TO
    Given A default configured bean2bean type converter
    	And a configured mapping from a typical domain object to its transfer object
    When I convert the domain object to its TO representation
    Then I should obtain a TO object with the state of the domain object
    
  Scenario: TO to Typical domain object
    Given A default configured bean2bean type converter
    	And a configured mapping to a typical domain object from its transfer object
    When I convert the TO representation to the domain object
    Then I should obtain a domain object with the state from the TO
    
        