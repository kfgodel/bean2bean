Feature: Conversor universal
  In order to transform an object to any other kind
  As a bean2bean user
  I want to be able to convert values in one liner call

  Scenario Outline: Numeros a Strings
    Given Un conversor ya setupeado
    When Convierto el numero entero <numero> en String
    Then Deberia obtener el valor "<convertido>"

	Examples:
		| numero | convertido |
		|  0   |  0  |
		|  1   |  1  |
		|  10000  |  10000  |
		|  -5   |  -5  |
        
