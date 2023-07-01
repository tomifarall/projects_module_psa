Feature: Eliminar tarea

  Scenario: Eliminar una tarea exitosamente
    Given Quiero eliminar una tarea existente asociada a un proyecto
    When Ingreso el id de la tarea que quiero eliminar
    Then Se elimina la tarea exitosamente
