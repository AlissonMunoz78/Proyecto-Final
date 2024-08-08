# Proyecto-Final
Objetivo General
Desarrollar un sistema de gestión de consultorios médicos llamado MediCare que optimice la administración de citas, historiales médicos, resultados de exámenes y tratamientos. El sistema está diseñado para mejorar la eficiencia en la gestión de información médica y proporcionar una mejor experiencia tanto para el personal médico como para los pacientes.

Objetivos Específicos
Gestión de Citas:

Implementar una interfaz para que el personal médico pueda registrar, modificar y cancelar citas.
Permitir a los pacientes solicitar citas y visualizar el historial de citas.
Administración de Historiales Médicos:

Implementar un módulo para registrar y actualizar los historiales médicos de los pacientes.
Facilitar el acceso a los historiales médicos para el personal médico autorizado.
Resultados de Exámenes:

Crear una funcionalidad para registrar y consultar los resultados de exámenes médicos.
Permitir la asignación de tratamientos y medicamentos basados en los resultados de los exámenes.
Gestión de Pacientes y Personal Médico:

Desarrollar herramientas para la gestión de pacientes y personal médico, incluyendo la capacidad de agregar, actualizar y eliminar registros.
Implementar una sección para la visualización de reportes estadísticos relacionados con la ocupación de citas y especialidades.
Seguridad y Acceso:

Implementar un sistema de autenticación para el acceso de personal médico y administradores.
Asegurar la protección de la información médica confidencial mediante encriptación y control de acceso.
Justificación
La implementación de MediCare es esencial en la modernización de la gestión médica, buscando optimizar tanto la administración como la experiencia del usuario en el entorno de atención médica. Este sistema ofrece múltiples beneficios:

Mejora de la Experiencia del Usuario:
Acceso y Gestión de Citas: Los pacientes pueden solicitar y gestionar sus citas de manera sencilla, evitando largas esperas y mejorando la accesibilidad.
Acceso a Historiales Médicos: El personal médico tiene acceso rápido a la información relevante para una mejor atención y seguimiento de los pacientes.
Optimización Operativa:
Gestión Eficiente: El sistema facilita la gestión de citas, historiales médicos y resultados de exámenes, reduciendo la carga administrativa y mejorando la eficiencia operativa.
Automatización de Procesos: La automatización de la gestión de citas y resultados reduce los errores y mejora la precisión de la información.
Seguridad y Confianza:
Protección de Datos: La implementación de medidas de seguridad como encriptación y control de acceso garantiza la confidencialidad de la información médica.
Acceso Autorizado: Solo el personal médico y los administradores autorizados pueden acceder a la información sensible.
Análisis y Mejora:
Reportes Estadísticos: La generación de reportes permite a los administradores y personal médico realizar análisis detallados para mejorar la gestión y la atención.
En resumen, MediCare no solo responde a la necesidad de modernizar la gestión médica, sino que también proporciona una herramienta eficiente y segura para mejorar la atención al paciente y la administración del consultorio médico.

Herramientas Swing Utilizadas
JComboBox: Para seleccionar especialidades y otros valores predeterminados.
JTextField: Para la entrada de datos como nombre, dirección y otros detalles del paciente y personal médico.
JButton: Para realizar acciones como registrar citas, guardar datos y navegar por el sistema.
JLabel: Para mostrar textos y guías en las interfaces de usuario.
JPanel: Para organizar y contener otros componentes dentro de las ventanas.
JScrollPane: Para la navegación en tablas con datos extensos.
JTable: Para mostrar información de pacientes, citas y resultados en formato tabular.
JPasswordField: Para la entrada segura de contraseñas durante el proceso de inicio de sesión.
JCheckBox: Para seleccionar opciones como la disponibilidad de horarios.
JFrame: Para crear las ventanas principales del sistema.
Desarrollo y Pruebas
LOGIN
Para la implementación del sistema de login, se consideran los tipos de usuario: Administrador y Personal Médico. La clase Login en Java Swing maneja el diseño y las funcionalidades de autenticación.

Funcionamiento
Al hacer clic en el botón "Iniciar Sesión", el sistema verifica las credenciales en la base de datos y permite el acceso a las funcionalidades correspondientes.
![image](https://github.com/user-attachments/assets/8946a35c-de89-4103-a642-890dc664dc5d)

Validaciones y Límites
Todos los campos deben ser llenados correctamente para validar la identidad.
El sistema muestra alertas en caso de credenciales incorrectas.
![image](https://github.com/user-attachments/assets/028b58c5-4fe8-420e-8218-8b2a39f9556a)

MENU ADMINISTRADOR
Para la implementacion de este menu parte desde el login el cual direcciona a la ventada del administrador que tiene las siguientes opciones Gestionar medicos, gestionar pacinete, reportes
![image](https://github.com/user-attachments/assets/28218f66-1ac7-43a0-b6d5-79aaf294e737)
![image](https://github.com/user-attachments/assets/0cab5a7d-1f4b-4f7e-9955-55e7e7824212)


 GESTIONAR MEDICOS
Permitir al administrador crear, actualizar, eliminar y buscar médicos en el sistema.
  
  Crear Médicos:
  Permite ingresar nuevos médicos en el sistema. Esto incluye datos como nombre, especialidad, horario de atención, y otros detalles relevantes.
  Interfaz: Un formulario con campos para ingresar la información del médico y un botón para guardar los datos.
  ![image](https://github.com/user-attachments/assets/5e8babe6-533d-483f-b3c5-f01416a1b19d)

  Actualizar Médicos:
  Permite modificar la información de médicos ya existentes. Se puede actualizar información como especialidad, horarios, y datos de contacto.
  Interfaz: Una ventana de búsqueda para localizar al médico a actualizar, seguida de un formulario editable con la información actual.
  ![image](https://github.com/user-attachments/assets/ad0e97af-3c72-4aae-b883-b253499dafe8)

  
  Eliminar Médicos:
  Permite eliminar médicos del sistema. Esta acción debe ser cuidadosa para no afectar la programación de citas existentes.
  Interfaz: Una opción de búsqueda y un botón para eliminar al médico seleccionado.
  ![image](https://github.com/user-attachments/assets/9800e78e-36f7-42e0-b586-ad0055510e78)

  
  Buscar Médicos:
  Permite ver la lista de médicos organizados por especialidad, horario de atención, y otros filtros relevantes.
  Interfaz: Una vista de lista o tabla que muestra médicos con opciones para filtrar por especialidad y otros criterios.
  ![image](https://github.com/user-attachments/assets/e68c178d-1095-4048-8604-0ae2aded638d)

  Validaciones y Límites
  Gestionar Pacientes
Objetivo:
Permitir al administrador crear, actualizar, eliminar y buscra pacientes en el sistema.

Funcionalidades Clave:

  Crear Pacientes:
  Permite ingresar nuevos pacientes en el sistema. Incluye datos como nombre, lugar de nacimiento, edad, género, y contacto.
  Interfaz: Un formulario con campos para ingresar los datos del paciente y un botón para guardar la información.
  ![image](https://github.com/user-attachments/assets/155010ee-459b-4b73-afc7-e5d707fba6d2)

  
  Actualizar Pacientes:
  Permite modificar la información de pacientes existentes. Esto puede incluir datos personales y de contacto.
  Interfaz: Una ventana de búsqueda para localizar al paciente a actualizar, seguida de un formulario editable.
  ![image](https://github.com/user-attachments/assets/bc2ea52c-2859-45b6-9a70-6c4d57a6c435)

  Eliminar Pacientes:
  Permite eliminar pacientes del sistema. Es importante considerar las citas y tratamientos asociados antes de eliminar.
  Interfaz: Una opción de búsqueda y un botón para eliminar al paciente seleccionado.
  ![image](https://github.com/user-attachments/assets/ebed9545-3d92-4252-bfc3-d6ffce33abc6)

  Buscar Pacientes:
  Permite ver una lista de pacientes con detalles relevantes. Se puede buscar por nombre, número de cédula, etc.
  Interfaz: Una vista de lista o tabla que muestra pacientes con opciones de búsqueda y filtrado.
  ![image](https://github.com/user-attachments/assets/4af7edb4-2047-401d-80f7-d1848cee9d17)

REPORTES
Permite al administrador crear reportes sobre diferentes aspectos como el número de citas por especialidad y los doctores por especialidad.
Visualizar Reporte Citas:
![image](https://github.com/user-attachments/assets/ac23e58a-667c-4670-80e7-b9978f614ca8)

Visualizar Reporte Medicos:
![image](https://github.com/user-attachments/assets/afadaedb-56a1-4fe5-84a1-613ad7659a70)


MENU MEDICOS
Para la implementacion de este menu parte desde el login el cual direcciona a la ventada del medico que tiene las siguientes opciones Gestionar citas, historial medico, resultados examenes, tratamiento
![image](https://github.com/user-attachments/assets/a6311f55-08f8-49b5-a565-78dddbe2f2e2)

  Gestión de Citas
  El sistema permite al personal médico registrar y gestionar citas a través de una interfaz gráfica.
  ![image](https://github.com/user-attachments/assets/7f4dc323-43fb-45ca-a63d-c77df1d5c9f2)

  Historial Médico
  Permite registrar y consultar los historiales médicos de los pacientes.
  ![image](https://github.com/user-attachments/assets/c745b33c-3f0f-479e-b767-f859e2df7121)

  Resultados de Exámenes
  El sistema permite la entrada y visualización de resultados de exámenes médicos.
  ![image](https://github.com/user-attachments/assets/a18a70a0-6241-4db8-91a0-ed074c5f7f46)

  Tratamiento
  El sistema permite ver el tratamiento del paciente
  ![image](https://github.com/user-attachments/assets/3428c1d3-8b03-469f-a729-d84c8f8b5eb7)

  Funcionamiento
  Se registran los resultados y se asignan tratamientos basados en los exámenes realizados.
  
  Validaciones y Límites
  Se valida la entrada de datos y la asignación correcta de tratamientos.
  
Conclusiones
La implementación de MediCare ha permitido aplicar conceptos avanzados de Programación Orientada a Objetos y crear un sistema integral para la gestión médica. El sistema facilita la administración eficiente de citas, historiales médicos, resultados y tratamientos, asegurando una experiencia de usuario mejorada y una operación más eficiente.

Proyección
Futuras versiones de MediCare pueden incluir integraciones adicionales, como la gestión de pagos en línea y mejoras en la visualización de datos estadísticos para una toma de decisiones más informada.
LINK DEL VIDEO: https://photos.app.goo.gl/iaeGgWZnZGfKrY8d7
