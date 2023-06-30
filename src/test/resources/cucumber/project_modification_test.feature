Feature: Modificar proyecto

  Scenario: Modificar un proyecto exitosamente
    Given Quiero modificar el nombre y la descripcion de un proyecto existente
    When Ingreso los datos del nombre y la descripcion
    Then Se actualiza la informacion del proyecto con los datos ingresados

  Scenario: Modificar el estado de un proyecto exitosamente
    Given Quiero modificar el estado de un proyecto existente
    When Ingreso el estado al que quiero que transicione
    Then Se actualiza el estado del proyecto exitosamente

  Scenario: Modificar un proyecto devuelve error si la fecha de fin es menor a la fecha de inicio
    Given Quiero modificar las fechas de comienzo y finalizacion de un proyecto existente
    When Ingreso una fecha de fin la cual es menor a la fecha de inicio
    Then No se puede modificar el proyecto por error de fecha de fin menor a fecha de inicio

  Scenario: Modificar un proyecto devuelve error si ingreso un legajo de responsable invalido
    Given Quiero modificar el responsable de un proyecto existente
    When Ingreso un legajo de responsable que no es valido
    Then No se puede crear el proyecto por error de legajo invalido

  Scenario: Crear nuevo proyecto devuelve error si ingreso una version de producto que ya esta asociado a otro proyecto
    Given Quiero crear un nuevo proyecto
    When Ingreso todos los datos solicitados y una versión de producto que ya esta asociado a otro proyecto
    Then No se puede crear el proyecto por error version ya asociada a otro proyecto