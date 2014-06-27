Feature: Annotation based object mapper
  In order to move state from source object to destination object
  As a bean2bean api user
  I want to be able to map properties between objects using annotations

  Scenario: Mapping with annotations on the source object
    Given A default configured bean2bean type converter
    	And an annotation mapped source object
    When I convert the source object to destination object
    Then I should obtain the desired state on the destination
    
  Scenario: Mapping with annotations on the destination object
    Given A default configured bean2bean type converter
    	And an annotation mapped destination object
    When I convert the source object to destination object
    Then I should obtain the desired state on the destination
    
