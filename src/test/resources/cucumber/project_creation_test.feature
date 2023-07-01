Feature: Crear nuevo proyecto

  Scenario: Crear nuevo proyecto exitosamente
    Given Quiero crear un nuevo proyecto
    When Ingreso los datos de nombre, descripcion, fecha de inicio, fecha de fin, versión del producto asociado y responsable del proyecto
    Then Se crea el proyecto exitosamente

  Scenario: Crear nuevo proyecto devuelve error si no ingreso todos los datos solicitados
    Given Quiero crear un nuevo proyecto
    When Ingreso los datos de nombre y descripcion y ningun otro dato mas
    Then No se puede crear el proyecto por error de datos invalidos

  Scenario: Crear nuevo proyecto devuelve error si la fecha de fin es menor a la fecha de inicio
    Given Quiero crear un nuevo proyecto
    When Ingreso los todos los datos solicitados con una fecha de fin menor a la fecha de inicio
    Then No se puede crear el proyecto por error de fecha de fin menor a fecha de inicio

  Scenario: Crear nuevo proyecto devuelve error si ingreso una version de producto que ya esta asociado a otro proyecto
    Given Quiero crear un nuevo proyecto
    When Ingreso todos los datos solicitados y una versión de producto que ya esta asociado a otro proyecto
    Then No se puede crear el proyecto por error version ya asociada a otro proyecto