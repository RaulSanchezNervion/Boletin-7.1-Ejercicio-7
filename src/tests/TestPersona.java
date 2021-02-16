package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clases.Persona;

/**
 * Tests unitarios de la clase Persona.
 * Escritos para ejecutarse con Junit 5.
 * 
 * @author Raul Sanchez Galan
 * @version 1.0
 *
 */
class TestPersona {
	//Atributos
	private Persona personaNormal=new Persona();//Persona con una cuenta no descubierta
	private Persona personaConCuentaDescubierta=new Persona();//Persona con una cuenta descubierta
	private Persona personaSinCuenta=new Persona();//Persona con una cuenta a null

	//Constantes
	private static final String IBAN_CUENTA_NORMAL="ES6621000418401234567891";
	private static final int NUMERO_CUENTA_NORMAL=123;
	private static final double SALDO_CUENTA_NORMAL=0;
	private static final int INTERES_MENSUAL=0;

	private static final String IBAN_CUENTA_DESCUBIERTA="ES6621000418401234567894";
	private static final int NUMERO_CUENTA_DESCUBIERTA=12341234;
	private static final int SALDO_CUENTA_DESCUBIERTA=-1;

	private static final char SEXO_MUJER='f';
	private static final char SEXO_HOMBRE='m';
	private static final char SEXO_INDETERMINADO='i';
	private static final String MENSAJE_PARA_MOSTRAR_SALDO = "Saldo actual: ";
	private static final int EDAD_PERSONA_NORMAL = 23;
	private static final String MENSAJE_FALLO_NULL = "El metodo no debería fallar si no hay cuenta";

	//Atributos utilizados para controlar el flujo de impresion en 
	//pantalla con Junit. Para mas informacion visitar: https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;




	@BeforeEach
	void setUp(){
		//Inicializamos los casos de prueba

		//Inicializamos la persona con cuenta con saldo a 0
		personaNormal.setNombre("Carmen");
		personaNormal.setEdad(EDAD_PERSONA_NORMAL);
		personaNormal.setApellidos("Lopez Gonzalez");
		personaNormal.setSexo(SEXO_MUJER);
		personaNormal.crearCuentaCorriente(IBAN_CUENTA_NORMAL, NUMERO_CUENTA_NORMAL, INTERES_MENSUAL);

		//Inicializamos la persona con cuenta con saldo a -1
		personaConCuentaDescubierta.setNombre("Paco");
		personaConCuentaDescubierta.setEdad(45);
		personaConCuentaDescubierta.setApellidos("Roldan Justo");
		personaConCuentaDescubierta.setSexo(SEXO_HOMBRE);
		personaConCuentaDescubierta.crearCuentaCorriente(IBAN_CUENTA_DESCUBIERTA, NUMERO_CUENTA_DESCUBIERTA, INTERES_MENSUAL);
		personaConCuentaDescubierta.sacarDinero(-SALDO_CUENTA_DESCUBIERTA);

		//Inicializamos la persona sin cuenta
		personaSinCuenta.setNombre("Alejandro");
		personaSinCuenta.setEdad(20);
		personaSinCuenta.setApellidos("Garcia Ruiz");
		personaSinCuenta.setSexo(SEXO_HOMBRE);

		//Se inicia lo que se muestra por pantalla, para las evaluacion de las
		//pruebas.
		System.setOut(new PrintStream(outContent));
	}

	@AfterEach
	void tearDown(){
		//Se reinicia lo que se muestra por pantalla, para las evaluacion de las
		//pruebas.
		System.setOut(originalOut);
	}

	@Test
	//Queremos comprobar si el metodo setSexo
	//no modifica el sexo si se introduce un sexo invalido
	//Caso de prueba: this.sexo='m', sexo='o'
	//Resultado esperado: this.sexo='m'
	void testSetSexoInvalido() {
		personaNormal.setSexo('o');
		assertEquals(SEXO_MUJER, personaNormal.getSexo());
	}

	@Test
	//Queremos comprobar si el metodo setSexo
	// modifica el sexo de mujer a hombre
	//Caso de prueba: this.sexo='m', sexo='h'
	//Resultado esperado: this.sexo='h'
	void testSetSexoValidoHombre() {
		personaNormal.setSexo(SEXO_HOMBRE);
		assertEquals(SEXO_HOMBRE, personaNormal.getSexo());
	}

	@Test
	//Queremos comprobar si el metodo setSexo
	// modifica el sexo de hombre a mujer
	//Caso de prueba: this.sexo='h', sexo='m'
	//Resultado esperado: this.sexo='m'
	void testSetSexoValidoMujer() {
		personaConCuentaDescubierta.setSexo(SEXO_MUJER);
		assertEquals(SEXO_MUJER, personaConCuentaDescubierta.getSexo());
	}

	@Test
	//Queremos comprobar si el metodo setSexo
	// modifica el sexo de mujer a indeterminado
	//Caso de prueba: this.sexo='m', sexo='i'
	//Resultado esperado: this.sexo='i'
	void testSetSexoValidoIndeterminado() {
		personaNormal.setSexo(SEXO_INDETERMINADO);
		assertEquals(SEXO_INDETERMINADO, personaNormal.getSexo());
	}

	@Test
	//Queremos comprobar si el metodo setEdad
	//no modifica la edad, con una entrada negativa
	//Caso de prueba: this.edad=23, edad=-1
	//Resultado esperado: this.edad=23
	void testSetEdadInvalida() {
		personaNormal.setEdad(-1);
		assertEquals(EDAD_PERSONA_NORMAL, personaNormal.getEdad());
	}

	@Test
	//Queremos comprobar si el metodo setEdad
	//modifica la edad, a cero
	//Caso de prueba: this.edad=23, edad=0
	//Resultado esperado: this.edad=0
	void testSetEdadValida() {
		personaNormal.setEdad(0);
		assertEquals(0, personaNormal.getEdad());
	}


	@Test
	//Queremos comprobar si el metodo ingresarDinero
	//permite ingresar una cantidad de dinero
	//positiva
	//Caso de prueba: dinero=5, saldo=0
	//Resultado esperado:return=true, imprimir por pantalla 'Saldo: 5'
	void testIngresarDineroCuenta() {
		assertEquals(true,personaNormal.ingresarDinero(5));
		comprobarSaldoActualEs(5);//Debido a que la clase persona no tiene un metodo
		//publico para obtener el saldo, debemos comprobar el saldo actual evaluando
		//el mensaje que imprime por pantalla. 
	}


	@Test
	//Queremos comprobar si el metodo ingresarDinero
	//permite ingresar una cantidad de dinero
	//negativa
	//Caso de prueba: dinero=-5, saldo=0
	//Resultado esperado:return=false, No imprimir por pantalla 'Saldo: -5'
	void testIngresarDineroNegativo() {
		assertEquals(false,personaNormal.ingresarDinero(-5));
		comprobarSaldoActualNoEs(-5);//Debido a que la clase persona no tiene un metodo
		//publico para obtener el saldo, debemos comprobar el saldo actual evaluando
		//el mensaje que imprime por pantalla. 
	}

	@Test
	//Queremos comprobar si el metodo ingresarDinero
	//no lanza una excepcion cuando no existe una cuenta
	//Caso de prueba: cuenta=null
	//Resultado esperado:return=false, No se lanza una Excepcion.
	void testIngresarDineroSinCuentaCreada() {
		try {
			assertEquals(false,personaSinCuenta.ingresarDinero(6));
		}
		catch (Exception e) {
			fail(MENSAJE_FALLO_NULL);
		}

	}


	@Test
	//Queremos comprobar si el metodo sacarDinero
	//no permite sacar dinero si la cuenta esta descubierta
	//Caso de prueba: saldo=-1, dinero=20
	//Resultado esperado: return=false, No imprimir por pantalla 'Saldo: -21' 
	void testRetirarDeCuentaDescubierta() {
		assertEquals(false,personaConCuentaDescubierta.sacarDinero(20));
		comprobarSaldoActualNoEs(SALDO_CUENTA_DESCUBIERTA-20);//Debido a que la clase persona no tiene un metodo
		//publico para obtener el saldo, debemos comprobar el saldo actual evaluando
		//el mensaje que imprime por pantalla. 
	}


	@Test
	//Queremos comprobar si el metodo sacarDinero
	//no permite sacar una suma de dinero negativa
	//Caso de prueba: saldo=0, dinero=-7
	//Resultado esperado: return=false, No imprimir por pantalla 'Saldo: -7'
	void testRetirarCantidadNegativa() {
		assertEquals(false,personaNormal.sacarDinero(-7));
		comprobarSaldoActualNoEs(SALDO_CUENTA_NORMAL-7);//Debido a que la clase persona no tiene un metodo
		//publico para obtener el saldo, debemos comprobar el saldo actual evaluando
		//el mensaje que imprime por pantalla. 
	}

	@Test
	//Queremos comprobar si el metodo sacarDinero
	//permite sacar una suma de dinero positiva
	//Caso de prueba: saldo=0, dinero=6
	//Resultado esperado: return=true, imprimir por pantalla 'Saldo: -6'
	void testRetirarCantidadPositiva() {
		assertEquals(true,personaNormal.sacarDinero(6));
		comprobarSaldoActualEs(SALDO_CUENTA_NORMAL-6);//Debido a que la clase persona no tiene un metodo
		//publico para obtener el saldo, debemos comprobar el saldo actual evaluando
		//el mensaje que imprime por pantalla. 
	}

	@Test
	//Queremos comprobar si el metodo sacarDinero
	//no lanza una excepcion cuando no existe una cuenta
	//Caso de prueba: cuenta=null
	//Resultado esperado:return=false,No se lanza una Excepcion.
	void testRetirarCantidadSinCuentaCreada() {
		try {
			assertEquals(false,personaSinCuenta.sacarDinero(6));
		}
		catch (Exception e) {
			fail(MENSAJE_FALLO_NULL);
		}

	}


	@Test
	//Queremos comprobar si el metodo hacerTransferenciaA
	//no transfiere el dinero si la cuenta origen esta descubierta.
	//Caso de prueba: this.saldo=-1, dinero=1.
	//Resultado esperado: return=false, imprimir por pantalla 'Saldo: -1'
	void testHacerTransferenciaDeCuentaDescubierta() {
		assertEquals(false,personaConCuentaDescubierta.hacerTransfereciaA(personaNormal, 1));
		comprobarSaldoActualEs(SALDO_CUENTA_DESCUBIERTA);//Debido a que la clase persona no tiene un metodo
		//publico para obtener el saldo, debemos comprobar el saldo actual evaluando
		//el mensaje que imprime por pantalla. 
	}

	@Test
	//Queremos comprobar si el metodo hacerTransferenciaA
	//no transfiere el dinero si la cantidad a transferir
	// es negativa.
	//Caso de prueba: this.saldo=0, dinero=-1.
	//Resultado esperado: return=false, imprimir por pantalla 'Saldo: 0'
	void testHacerTransferenciaCantidadNegativa() {
		assertEquals(false,personaNormal.hacerTransfereciaA(personaConCuentaDescubierta, -1));
		comprobarSaldoActualEs(SALDO_CUENTA_NORMAL);//Debido a que la clase persona no tiene un metodo
		//publico para obtener el saldo, debemos comprobar el saldo actual evaluando
		//el mensaje que imprime por pantalla. 
	}

	@Test
	//Queremos comprobar si el metodo hacerTransferenciaA
	//transfiere el dinero si la cantidad a transferir
	// es positiva.
	//Caso de prueba: this.saldo=0, dinero=1, persona.saldo=-1.
	//Resultado esperado: return=true, imprimir por pantalla 'Saldo: -1'
	void  testHacerTransferenciaCantidadPositiva() {
		assertEquals(true, personaNormal.hacerTransfereciaA(personaConCuentaDescubierta, 1));
		comprobarSaldoActualEs(SALDO_CUENTA_NORMAL-1);//Debido a que la clase persona no tiene un metodo
		//publico para obtener el saldo, debemos comprobar el saldo actual evaluando
		//el mensaje que imprime por pantalla. 
	}

	@Test
	//Queremos comprobar si el metodo hacerTransferenciaA
	//no lanza una excepcion cuando no existe una cuenta origen
	//Caso de prueba: this.cuenta=null
	//Resultado esperado:return=false, No se lanza una Excepcion.
	void testHacerTransferenciaSinCuentaCreada() {
		try {
			assertEquals(false,personaSinCuenta.hacerTransfereciaA(personaConCuentaDescubierta, 1));
		}
		catch (Exception e) {
			fail(MENSAJE_FALLO_NULL);
		}
	}

	@Test
	//Queremos comprobar si el metodo hacerTransferenciaA
	//no lanza una excepcion cuando la persona es null
	//Caso de prueba: this.cuenta=null
	//Resultado esperado:return=false, No se lanza una Excepcion.
	void testHacerTransferenciaANullPersona() {
		try {
			assertEquals(false,personaSinCuenta.hacerTransfereciaA(null, 1));
		}
		catch (Exception e) {
			fail(MENSAJE_FALLO_NULL);
		}
	}



	@Test
	//Queremos comprobar si el metodo cerrar
	//cierra la cuenta, y por tanto, no se pueden hacer
	//transferencias desde la cuenta
	//Caso de prueba: this.cuenta=0
	//- Como segundo paso, se realiza una transferencia a: persona.saldo=-1,
	//dinero=1.
	//Resultado esperado:return (de hacer transferecia)=false, 
	//No se imprime por pantalla 'Saldo: -1',
	//No se lanza una excepcion Nullpointer
	void testCerrarYHacerTransferecia() {
		try {
			personaNormal.cerrarCuenta();
			personaNormal.hacerTransfereciaA(personaConCuentaDescubierta, 1);
			comprobarSaldoActualNoEs(SALDO_CUENTA_NORMAL-1);//Debido a que la clase persona no tiene un metodo
			//publico para obtener el saldo, debemos comprobar el saldo actual evaluando
			//el mensaje que imprime por pantalla. 
		}
		catch (Exception e) {
			fail(MENSAJE_FALLO_NULL);
		}
	}

	//TODO hacer metodos para getDescubierta():
	//que pasa de true a false--> saldo =-1 --> saldo=0
	//de false a false --> saldo=0 --> saldo 4
	// de false a true --> saldo=0 --> saldo=-1
	// y de true a true --> saldo=-1 --> saldo=-2

	//Metodos privados

	//Metodo utilizado para comprobar si se imprime por
	//pantalla que el saldo es igual a la entrada saldo
	private void comprobarSaldoActualEs(double saldo) {
		String resultado=MENSAJE_PARA_MOSTRAR_SALDO+saldo;//Se concatena el mensaje 
		//que se utiliza para mostrar el saldo, junto con la cantidad
		assertTrue(outContent.toString().contains(resultado));
		//Se comprueba si la salida estandar (outContent) contiene la cadena
		//resultado. No se utiliza equals ya que en en ese caso habría que
		//hacer que coincidiese exactamente, con decimales, y caracteres especiales
		//como /r, /n, etc.
	}

	//Metodo utilizado para comprobar si no se imprime por
	//pantalla que el saldo es igual a la entrada saldo
	private void comprobarSaldoActualNoEs(double saldo) {
		String resultado=MENSAJE_PARA_MOSTRAR_SALDO+saldo;//Se concatena el mensaje 
		//que se utiliza para mostrar el saldo, junto con la cantidad
		assertFalse(outContent.toString().contains(resultado));
		//Se comprueba si la salida estandar (outContent) no contiene la cadena
		//resultado. No se utiliza equals ya que en en ese caso habría que
		//hacer que coincidiese exactamente, con decimales, y caracteres especiales
		//como /r, /n, etc.
	}

}
