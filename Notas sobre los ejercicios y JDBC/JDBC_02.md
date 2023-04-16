# 3 Capas

Tendremos 3 capas

Data -> Acceder a la base de datos para recuperar información o modificar.

Logic -> Toda la lógica del negocio

UI -> Presentación visual

Cada una de las capas tendrá un package independiente. Y para poder intercambiar información entre estas capas, lograr que sean independientes y que no haya un acoplamiento entre ellas vamos a utilizar un conjunto de entidades (el otro package) aquí tendremos los objetos del sistema que serán intercambiados entre distintas capas.

<image src="img/mvc.png" alt="Patrón MVC">

Para realizar un login en un sistema web vamos a tener que el usuario va ingresar a la pagina de login, esto se envia a un servlet de login, el servlet mapea los parametros de la pagina html con el usuario y contraseña, crea un nuevo objeto Persona con usuario y contraseña y se le envía a la capa de lógica. Se aplican las reglas de negocio.

Pero no envíamos solo usuario y contraseña sino que se crea el objeto persona porque el dia de mañana podría ser que ser reciba otra cosa como un token ademas. Y como persona es Persona vale siempre.

Si quisieramos que el metodo login recibiera un usuario y contraseña y el dia de mañana existe un cambio por ejemplo email y contraseña tendría que crear otro metodo login o modificar el metodo existente.

En cambio si solo mandamos Persona no se verá reflejado en la llamada del metodo y quizá ningun cambio en el objeto del login.

Habría un cambio en la capa de data donde cambia el select nomas

El objeto dataPersona será el encargado de recibir los datos de las tablas y mapearlo a objeto para devolverlo al controlador.

Una vez recibido el objeto persona desde el servlet login será rederigido a la página que corresponde.

Desde la UI recibimos los datos planos -> lo pasamos al objetos del modelo de dominio -> llega a la logica como objeto -> si se requiere se accede a la capa de data -> y luego lo convertimos en una respuesta visible al usuario, regresa a la ui.

Lógica -> cifrar la contraseña para que no se encuentre en texto plano, "validar si se encuentra disponible la habitacion 001, si hay cupo"

Solo se utiliza los elementos necesarios en cada lugar.

En la capa de acceso a base de datos vamos a recibir el objeto persona y desde la lógica nos dirán que acción realizar.

La capa de data determinará si la baja es fisica o lógica. Update o Delete.

Toda la lógica del acceso a la base de datos, connection string y demas quedan restringidos a la capa de acceso de datos.

De misma forma la lógica no deberia estar en la interfaz grafica y ni siquiera en los servlets, los servlets deberia mapar los datos planos a objetos.

Las flechas azules son el uso que realizan las capas. Todas las capas hacen uso de las entidades, la ui usa la logica la logica usa la data, la data la bd.

Podemos utilizar un objeto controlador por cada caso de uso de usuario, en algunos casos habrá algo que se repita.

Existirá un objeto data por cada entiedad de negocio.

Las interfaces varían el como desarrollarla, no es lo mismo web que consola.




