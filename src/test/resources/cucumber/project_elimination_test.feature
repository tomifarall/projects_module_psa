Feature: Eliminar proyecto

  Scenario: Eliminar un proyecto exitosamente
    Given Quiero eliminar un proyecto existente
    When Ingreso el id del proyecto que quiero eliminar
    Then Se elimina el proyecto exitosamente
