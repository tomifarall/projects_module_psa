Feature: Modificar tarea

  Scenario: Modificar una tarea exitosamente
    Given Quiero modificar el titulo y la descripcion de una tarea existente asociada a un proyecto
    When Ingreso los datos del titulo y la descripcion
    Then Se actualiza la informacion de la tarea con los datos ingresados

  Scenario: Modificar el estado de una tarea devuelve error si ingreso un estado invalido
    Given Quiero modificar el estado de una tarea existente asociada a un proyecto
    When Ingreso un estado al que quiero que transicione la tarea que no es ninguno de los definidos por psa
    Then No se puede actualizar la tarea por error de estado invalido

  Scenario: Modificar una tarea devuelve error si ingreso un legajo de responsable invalido
    Given Quiero modificar el responsable de una tarea existente
    When Ingreso el legajo de un responsable que no es valido
    Then No se puede crear la tarea por error de legajo invalido