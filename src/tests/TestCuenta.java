package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DecimalFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clases.Cuenta;

/**
 * Tests unitarios de la clase Cuenta.
 * Escritos para ejecutarse con Junit 5.
 * 
 * @author Raul Sanchez Galan
 * @version 1.0
 *
 */
class TestCuenta {
	//Atributos
	private Cuenta cuentaNormal=new Cuenta(IBAN_CUENTA_NORMAL, NUMERO_CUENTA_NORMAL, SALDO_INICIAL_CUENTA_NORMAL);
	//Representa una cuenta con saldo positivo
	private Cuenta cuentaDescubierta=new Cuenta(IBAN_CUENTA_DESCUBIERTA, NUMERO_CUENTA_DESCUBIERTA, SALDO_INICIAL_CUENTA_DESCUBIERTA);
	//Representa una cuenta con saldo negativo
	
	//Constantes
	private static final String IBAN_CUENTA_NORMAL="ES6621000418401234567891";
	private static final int NUMERO_CUENTA_NORMAL=123;
	private static final double SALDO_INICIAL_CUENTA_NORMAL=0;
	private static final int INTERES_MENSUAL=0;
	
	private static final String IBAN_CUENTA_DESCUBIERTA="ES6621000418401234567894";
	private static final int NUMERO_CUENTA_DESCUBIERTA=12341234;
	private static final double SALDO_INICIAL_CUENTA_DESCUBIERTA=-10;

	

	@BeforeEach
	void setUp() {
		//Cuenta normal es una cuenta con saldo 0
		cuentaNormal.setIban(IBAN_CUENTA_NORMAL);
		cuentaNormal.setNumeroCuenta(NUMERO_CUENTA_NORMAL);
		cuentaNormal.setInteresMensual(INTERES_MENSUAL);
		cuentaNormal.setSaldo(SALDO_INICIAL_CUENTA_NORMAL);
		
		//Cuenta normal es una cuenta con saldo -10
		cuentaDescubierta.setIban(IBAN_CUENTA_DESCUBIERTA);
		cuentaDescubierta.setNumeroCuenta(12341234);
		cuentaDescubierta.setInteresMensual(INTERES_MENSUAL);
		cuentaDescubierta.setSaldo(SALDO_INICIAL_CUENTA_DESCUBIERTA);
	}

	

	
	@Test
	//Queremos comprobar si el metodo getDescubierta
	//devuelve true cuando una cuenta tiene saldo negativo
	//Caso de prueba: saldo=-1
	//Resultado esperado: return=true
	void testGetDescubiertaSaldoNegativo() {
		cuentaNormal.setSaldo(-1);
		assertEquals(true, cuentaNormal.getDescubierta());
	}
	
	@Test
	//Queremos comprobar si el metodo getDescubierta
	//devuelve false cuando una cuenta no tiene saldo negativo
	//Caso de prueba: saldo=0
	//Resultado esperado: return=false
	void testGetDescubiertaSaldoNoNegativo() {
		assertEquals(false, cuentaNormal.getDescubierta());
	}
	
	@Test
	//Queremos comprobar si el metodo ingresar
	//Incrementa el saldo 
	//Caso de prueba: saldo=0, dinero=5
	//Resultado esperado: return=5
	void testIngresarDineroPositivo() {
		cuentaNormal.ingresar(5);
		assertEquals(5, cuentaNormal.getSaldo());
	}
	
	@Test
	//Queremos comprobar si el metodo ingresar
	//no incrementa el saldo, si la cantidad es negativa 
	//Caso de prueba: saldo=0, dinero=-5
	//Resultado esperado: return=0
	void testIngresarDineroNegativo() {
		cuentaNormal.ingresar(-5);
		assertEquals(SALDO_INICIAL_CUENTA_NORMAL, cuentaNormal.getSaldo());
	}
	
	@Test
	//Queremos comprobar si el metodo retirar
	//no decrementa el saldo, si la cuenta esta descubierta
	//y devuelve false.
	//Caso de prueba: saldo=-10, dinero=20
	//Resultado esperado: saldo=-10, return=false
	void testRetirarDeCuentaDescubierta() {
		assertEquals(false,cuentaDescubierta.retirar(20));
		assertEquals(SALDO_INICIAL_CUENTA_DESCUBIERTA, cuentaDescubierta.getSaldo());
	}
	
	@Test
	//Queremos comprobar si el metodo retirar
	//no decrementa el saldo, si la cantidad es negativa
	//y devuelve false.
	//Caso de prueba: saldo=0, dinero=20
	//Resultado esperado: saldo=0, return=false
	void testRetirarCantidadNegativa() {
		assertEquals(false,cuentaNormal.retirar(-20));
		assertEquals(SALDO_INICIAL_CUENTA_NORMAL, cuentaNormal.getSaldo());
	}
	
	@Test
	//Queremos comprobar si el metodo retirar
	//decrementa el saldo, si la cantidad es positiva
	//y la cuenta no esta descubierta.
	//Caso de prueba: saldo=20, dinero=5
	//Resultado esperado: saldo=15, return=true
	void testRetirarCantidadPositiva() {
		cuentaNormal.setSaldo(20);
		assertEquals(true,cuentaNormal.retirar(5));
		assertEquals(15, cuentaNormal.getSaldo());
	}
	
	@Test
	//Queremos comprobar si el metodo hacerTransferenciaA
	// devuelve false, y no transfiere el dinero si se realiza
	// desde una cuenta descubierta.
	//Caso de prueba: saldo(Cuenta origen)=-1, saldo (Cuenta destin0)=0, dinero=1
	//Resultado esperado: saldo(Cuenta origen)=-1, saldo (Cuenta destin0)=0, return=false
	void testHacerTransferenciaDeCuentaDescubierta() {
		assertEquals(false,cuentaDescubierta.hacerTransferenciaA(cuentaNormal, 1));
		assertEquals(SALDO_INICIAL_CUENTA_DESCUBIERTA, cuentaDescubierta.getSaldo());
		assertEquals(SALDO_INICIAL_CUENTA_NORMAL, cuentaNormal.getSaldo());
	}
	
	@Test
	//Queremos comprobar si el metodo hacerTransferenciaA
	// devuelve false, y no transfiere el dinero si se realiza
	// con una suma de dinero negativa.
	//Caso de prueba: saldo(Cuenta origen)=-1, saldo (Cuenta destin0)=0, dinero=1
	//Resultado esperado: saldo(Cuenta origen)=-1, saldo (Cuenta destin0)=0, return=false
	void testHacerTransferenciaCantidadNegativa() {
		assertEquals(false,cuentaNormal.hacerTransferenciaA(cuentaDescubierta,-20));
		assertEquals(SALDO_INICIAL_CUENTA_NORMAL, cuentaNormal.getSaldo());
		assertEquals(SALDO_INICIAL_CUENTA_DESCUBIERTA, cuentaDescubierta.getSaldo());
	}
	
	@Test
	//Queremos comprobar si el metodo hacerTransferenciaA
	// devuelve true, y transfiere el dinero si se realiza
	// desde una cuenta no descubierta, con una suma de dinero positiva.
	//Caso de prueba: saldo(Cuenta origen)=-1, saldo (Cuenta destin0)=0, dinero=1
	//Resultado esperado: saldo(Cuenta origen)=-1, saldo (Cuenta destin0)=0, return=false
	void  testHacerTransferenciaCantidadPositiva() {
		assertEquals(true,cuentaNormal.hacerTransferenciaA(cuentaDescubierta,1));
		assertEquals(SALDO_INICIAL_CUENTA_NORMAL-1, cuentaNormal.getSaldo());
		assertEquals(SALDO_INICIAL_CUENTA_DESCUBIERTA+1, cuentaDescubierta.getSaldo());
	}
	
	
	@Test
	//Queremos comprobar si el metodo beneficiosFuturos
	// devuelve0, si la cuenta esta descubierta.
	//Caso de prueba: saldo=-1, interesMensual=0.05, numMeses=1
	//Resultado esperado: return=0
	void testBeneficiosFuturosCuentaDescubierta() {
		cuentaDescubierta.setInteresMensual(0.05);
		assertEquals(0, cuentaDescubierta.beneficiosFuturos(1));
	}
	
	@Test
	//Queremos comprobar si el metodo beneficiosFuturos
	// devuelve0, si la cuenta esta descubierta.
	//Caso de prueba: saldo=-1, interesMensual=0.05, numMeses=1
	//Resultado esperado: return=0
	void testBeneficiosFuturosMesesNegativos() {
		cuentaNormal.setInteresMensual(0.05);
		assertEquals(0, cuentaNormal.beneficiosFuturos(1));
	}
	
	@Test
	//Queremos comprobar si el metodo beneficiosFuturos
	// devuelve el  resultado del interes compuesto correcto
	//con saldo cero.
	//Caso de prueba: saldo=0, interesMensual=0.05, numMeses=1
	//Resultado esperado: return=0
	void testBeneficiosFuturosUnMesSaldoCero() {
		double interes=0.05;
		cuentaNormal.setInteresMensual(interes);
		assertEquals(0, cuentaNormal.beneficiosFuturos(1));
	}
	
	@Test
	//Queremos comprobar si el metodo beneficiosFuturos
	// devuelve el  resultado del interes compuesto correcto
	//con saldo positivo.
	//Caso de prueba: saldo=10, interesMensual=0.05, numMeses=1
	//Resultado esperado: return=0.5
	void testBeneficiosFuturosUnMesSaldoPositivo() {
		double interes=0.05;
		double saldo=10;
		cuentaNormal.setSaldo(saldo);
		cuentaNormal.setInteresMensual(interes);
		assertEquals(saldo*interes, cuentaNormal.beneficiosFuturos(1));
	}
	
	@Test
	//Queremos comprobar si el metodo beneficiosFuturos
	// devuelve el resultado del interes compuesto correcto
	//con saldo positivo, aplicado en 5 meses.
	//Caso de prueba: saldo=10, interesMensual=0.05, numMeses=5
	//Resultado esperado: return=22.76281563
	void testBeneficiosFuturosCincoMeses() {
		double interes=0.05;
		int numeroMeses=5;
		double saldo=10;
		double saldoTotal=saldo;
		DecimalFormat df = new DecimalFormat("###.##");//Debido a que Java
		//no hace los calculos de forma precisa cuando
		//trabaja con double, y por tanto introduce pequeñas imprecisiones 
		//en los calculos, vamos a tomar
		//los 2 primeros decimales del numero. Para ello utilizamos DecimalFormat.
		//NOTA: Si se tomasen los 8 primeros decimales, darian
		//resultados diferentes
		cuentaNormal.setSaldo(saldo);
		for(int i=1;i<=numeroMeses;i++) {//Calculamos el interes compuesto, paso a paso
			//para ver si coincide con la aplicación de la formula.
			saldoTotal=saldoTotal+saldoTotal*interes;
		}
		cuentaNormal.setInteresMensual(interes);
		//Comprobamos que el beneficio generado mes a mes, es el mismo que devuelve
		//el metodo beneficiosFuturos. Tomando los 2 primeros decimales con DecimalFormat.
		assertEquals(df.format(saldoTotal-saldo), df.format(cuentaNormal.beneficiosFuturos(numeroMeses)));
	}
	

}
