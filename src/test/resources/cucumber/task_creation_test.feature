Feature: Crear nueva tarea

  Scenario: Crear una nueva tarea exitosamente
    Given Quiero crear una nueva tarea en un proyecto al que pertenezco
    When Ingreso los datos de titulo, descripcion, tiempo estimado, tipo de tarea, responsable de la tarea y prioridad
    Then Se crea la tarea exitosamente

  Scenario: Crear una nueva tarea devuelve error si ingreso un legajo de responsable invalido
    Given Quiero crear una nueva tarea en un proyecto al que pertenezco
    When Ingreso los datos de titulo, descripcion, tiempo estimado, tipo de tarea, prioridad y un legajo de responsable que no es valido
    Then No se puede crear la tarea por error de legajo invalido

  Scenario: Crear una nueva tarea devuelve error si no ingreso todos los datos solicitados
    Given Quiero crear una nueva tarea en un proyecto al que pertenezco
    When Ingreso los datos de titulo, descripcion y ningun otro dato mas
    Then No se puede crear la tarea por error de datos invalidos