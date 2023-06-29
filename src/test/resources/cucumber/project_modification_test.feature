Feature: Modificar proyecto

  Scenario: Modificar un proyecto exitosamente
    Given Quiero modificar el nombre y la descripcion de un proyecto existente al que pertenezco
    When Ingreso los datos del nombre y la descripcion
    Then Se actualiza la informacion del proyecto con los datos ingresados

  Scenario: Modificar el estado de un proyecto exitosamente
    Given Quiero modificar el estado de un proyecto existente al que pertenezco
    When Ingreso el estado al que quiero que transicione
    Then Se actualiza el estado del proyecto exitosamente

  Scenario: Modificar un proyecto devuelve error si la fecha de fin es menor a la fecha de inicio
    Given Quiero modificar un proyecto existente al que pertenezco
    When Ingreso una fecha de fin la cual es menor a la fecha de inicio
    Then No se puede modificar el proyecto por error de fecha de fin menor a fecha de inicio