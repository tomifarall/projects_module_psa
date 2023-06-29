Feature: Crear nuevo proyecto

  Scenario: Crear nuevo proyecto exitosamente
    Given Quiero crear un nuevo proyecto
    When Ingreso los datos de nombre, descripcion, fecha de inicio y fecha de fin
    Then Se crea el proyecto exitosamente

  Scenario: Crear nuevo proyecto devuelve error si la fecha de fin es menor a la fecha de inicio
    Given Quiero crear un nuevo proyecto
    When Ingreso los datos de nombre, descripcion, fecha de inicio y fecha de fin la cual es menor a la fecha de inicio
    Then No se puede crear el proyecto por error de fecha de fin menor a fecha de inicio

  Scenario: Crear nuevo proyecto devuelve error si no ingreso todos los datos
    Given Quiero crear un nuevo proyecto
    When Ingreso los datos de nombre y descripcion y ningun otro dato mas
    Then No se puede crear el proyecto por error de datos invalidos