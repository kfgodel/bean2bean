Feature: Conversor universal
  In order to transform an object to any other kind
  As a bean2bean user
  I want to be able to convert values with one method call

  Scenario Outline: Numeros Enteros a Strings
    Given Un conversor ya setupeado
    When Convierto el numero entero <numero> en String
    Then Deberia obtener el valor "<convertido>"

	Examples:
		| numero | convertido |
		|  0   |  0  |
		|  1   |  1  |
		|  10000  |  10000  |
		|  -5   |  -5  |
        
  Scenario Outline: Numeros decimales a Strings
    Given Un conversor ya setupeado
    When Convierto el numero decimal <numero> en String
    Then Deberia obtener el valor "<convertido>"

	Examples:
		| numero | convertido |
		|  0   |  0.0  |
		|  1   |  1.0  |
		|  10000  |  10000.0  |
		|  -5   |  -5.0  |
		|  -0.01   |  -0.01  |
		|  8.01   |  8.01  |

  Scenario: Objeto tipico en TO
    Given Un conversor ya setupeado
    	And mapeo de TO definido
    When Convierto el objeto tipico en TO
    Then Deberia obtener el TO con los valores populados
    
  Scenario: Lista de objetos en array de TOs
    Given Un conversor ya setupeado
    	And mapeo de TO definido
    When Convierto el la lista de objetos tipicos en array de Tos
    Then Deberia obtener el array de TOs con los valores populados por cada elemento
    
        