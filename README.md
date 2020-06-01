# ATM

## Grupo: LosBorbotones2
-----------------------------------------------------------------------------------------------------
##### Integrantes: 
- Aguilar, Leandro
- Rojas, Leonardo
- Servile, Luciano
- Wernecke, Matías
-----------------------------------------------------------------------------------------------------
##### Desiciones de diseño:
Se decidió utilizar la clase BigDecimal en vez del tipo de datos double, ya que, con el primero, se logra una mayor precisión a
la hora de realizar cálculos con decimales. También se decidió utilizar una jerarquía de transacciones con el fin de dejar preparado
a futuro la correcta implenentación de la interfaz Reversible. Para las clases que manejan dólares se decidió utilizar el precio dólar
de 1 dólar = 68 pesos.

-----------------------------------------------------------------------------------------------------
##### Descripción de los archivos:
- App: Clase que contiene el método principal y que le permite al usuario interactuar con la ATM mediante la consola. 
- ATM: Posee un lector de tarjetas y la capacidad de imprimir un ticket. Implementa las clases Cuenta y Transacciones. Comienza cada día con una cierta cantidad de dinero.
- Transacciones: una clase abstracta que posee un monto y una cuenta que serán utilizadas por sus subclases. También posee un metodo
generarMovimiento el cual genera un movimiento luego de realizar una treansacción.
- RetirarEfectivo: subclase de Transacciones, permite retirar efectivo de una cuenta siempre y cuando ésta tenga el saldo suficiente
y la ATM tenga billetes.
- ComprarDolares: subclase de Transacciones, permite al usuario comprar una cierta cantidad de dólares utilizando pesos. 
Éstos son guardados en una caja de ahorro en dólares.
- VenderDolares: subclase de Transacciones, permite al usuario vender dólares de su caja de ahorro en dólares por su equivalente en pesos.
- Depositar: subclase de Transacciones, permite depositar pesos o dólares dependiendo del tipo de cuenta a la que se le desee depositar dinero.
- Transferir: subclase de Transacciones, permiter transferir dinero entre cuentas en pesos. Éstas pueden hacerse con el alias de la cuenta destino. Implementa la interfaz Reversible.
- Reversible: interfaz que posee al método revertir. Éste permite al usuario revertir una transacción inmediatamente despues de realizarla si así lo desea.
- Cuenta: clase abstracta que posee un alias, un saldo, un cliente y una lista de movimientos. Permite al usuario ingresar o retirar efectivo de ésta, además, le permite consultar los últimos diez movimientos realizados.
- CuentaCorriente: subclase de Cuenta, posee un descubierto que le da al usuario un piso de saldo negativo que le permite seguir retirando una hasta una cierta cantidad de dinero.
- CajaDeAhorroEnPesos: subclase de Cuenta, permite al usuario depositar pesos.
- CajaDeAhorroEnDolares: subclase de Cuenta, permite al usuario depositar dólares, teniendo en cuenta la cotización del dólar y el impuesto país. 
- Movimientos: clase que contiene los atributos fecha, conceptos, importe y cuenta, además de sus respectivos getter y setter. 
- Cliente: esta clase posee una lista de tarjetas y un cuit de 11 digitos. Además de poseer los getter y los setter necesarios.
- Tarjeta: ésta clase posee un numeroDeTarjeta, el cual es un entero de 8 digitos que no se repiten; y un pin, un entero de 4 digitos. Posee además los getter y setter necesarios.

-----------------------------------------------------------------------------------------------------
##### Conclusión: 

En conclusión, este trabajo nos permitió afianzar varios de los temas vistos a lo largo del cuatrimestre, como por ejemplo, herencia y polimorfismo, el uso de interfaces o la implementación de listas y diccionarios.
