Feature: Create new project
  Checking bank account operations

  Scenario: Crear nuevo proyecto exitosamente
    Given Quiero crear un nuevo proyecto
    When Ingreso los datos de nombre, descripcion, fecha de inicio y fecha de fin
    Then Se crea el proyecto exitosamente

  Scenario: Crear nuevo proyecto error fecha inicio mayor a fecha fin
    Given Quiero crear un nuevo proyecto
    When Ingreso los datos de nombre, descripcion, fecha de inicio y fecha de fin la cual es menor a la fecha de inicio
    Then No se puede crear el proyecto por fecha de inicio mayor a fecha de fin

  Scenario: Successfully deposit money when sum is not negative
    Given Account with a balance of 1000
    When Trying to deposit 500
    Then Account balance should be 1500

  Scenario: Cannot deposit money when sum is negative
    Given Account with a balance of 200
    When Trying to deposit -100
    Then Operation should be denied due to negative sum
    And Account balance should remain 200

  Scenario: Successfully promo applied, cap not reached.
    Given Account with a balance of 0
    When Trying to deposit 2000
    Then Account balance should be 2200

  Scenario: Successfully promo applied, cap reached.
    Given Account with a balance of 0
    When Trying to deposit 6000
    Then Account balance should be 6500

  Scenario: Promo not applied
    Given Account with a balance of 0
    When Trying to deposit 1500
    Then Account balance should be 1500
