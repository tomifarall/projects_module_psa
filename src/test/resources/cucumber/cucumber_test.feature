Feature: Create new project

  Scenario: Crear nuevo proyecto exitosamente
    Given Quiero crear un nuevo proyecto
    When Ingreso los datos de nombre, descripcion, fecha de inicio y fecha de fin
    Then Se crea el proyecto exitosamente

