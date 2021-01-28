package clases;

/**
 * La clase Cuenta es utilizada para representar
 * una cuenta bancaria. Como propiedades basicas
 * tiene:
 * <ul>
 * 		<li>iban: tipo cadena, consultable y modificable</li>
 * 		<li>numeroCuenta: tipo int, consultable y modificable</li>
 * 		<li>saldo: tipo real, consultable y modificable</li>
 * 		<li>interesMensual: tipo real, consultable y modificable</li>
 * </ul>
 * 
 * Propiedades compartidas: No hay
 * Propiedades derivadas: Descubierta - tipo real, consultable 
 * (El diagrama UML del ejercicio, especifica que se ponga como
 * atributo de la clase, aunque no seria necesario)
 * 
 * Restricciones: el iban y el numero de cuenta proporcionado en los
 * metodos seran validos.
 * (NOTA: Se permite interes negativo, ya que puede darse esa circunstancia.)
 * 
 * Su interfaz es la siguiente:
 * <ul>
 * 		<li>Getter y Setter de las propiedades consultables y modificables</li>
 * 		<li> public void ingresar(double)</li>
 * 		<li> public boolean retirar(double)</li>
 * 		<li> public boolean hacerTransferenciaA(Cuenta,double)</li>
 * 		<li> public double beneficiosFuturos(int)</li>
 * </ul>
 * 
 * 
 * @author Raul Sanchez Galan
 * @version 1.0
 *
 */
public class Cuenta {

	//Atributos
	private String iban;
	private int numeroCuenta;
	private double saldo;
	private double interesMensual;
	private boolean descubierta;//Este atributo, realmente podria
	//quitarse de la clase, ya que se puede calcular evaluando el saldo.
	//Pero es lo que especificaba el diagrama UML.
	
	//Metodos
	
	//Constructor con parametros
	public Cuenta(String iban, int nc, double interesMensual) {
		this.iban=iban;
		this.numeroCuenta=nc;
		this.interesMensual=interesMensual;
		descubierta=false;
	}
	
	//Getters y Setters
	public boolean getDescubierta() {
		return descubierta;//Habria sido un mejor disenio,
		//no tener el atributo descubierta
		//y en este metodo tener: return saldo<0
	}

	public String getIban() {
		return iban;
	}

	public int getNumeroCuenta() {
		return numeroCuenta;
	}

	public double getSaldo() {
		return saldo;
	}

	public double getInteresMensual() {
		return interesMensual;
	}

	//El iban proporcionado como entrada, debe ser un iban valido
	public void setIban(String iban) {
		this.iban = iban;
	}

	//El numero de cuenta proporcionado como entrada, debe ser un valor valido
	public void setNumeroCuenta(int numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
		checkDescubierta();//Actualiza el atributo descubierta
	}

	public void setInteresMensual(double interesMensual) {
		this.interesMensual = interesMensual;
	}
	
	
	
	@Override
	public String toString() {
		return getIban()+", "+getInteresMensual()+", "+getNumeroCuenta()+
				", "+getSaldo();
	}
	
	/** Metodo utilizado para ingresar dinero en la cuenta
	 * bancaria.
	 * 
	 * <b>Precondicion:</b> Ninguna
	 * <b>Postcondicion:</b> Si dinero>0 incrementa el saldo actual
	 * con el valor del parametro dinero, e imprime por pantalla
	 * a continuacion el saldo resultante con la cadena "Saldo actual: 'saldo'"
	 * En caso contrario, solo imprime el mensaje.
	 * 
	 *  Si la cuenta estaba descubierta, y su saldo pasa a ser positivo o cero,
	 * descubierta pasa a valer false.
	 * 
	 * Entrada: dinero
	 * Salida: Ninguna
	 * 
	 * @param dinero double que representa el dinero que se desea
	 * ingresar en la cuenta.
	 */
	
	public void ingresar(double dinero) {
		if(dinero>0){//Si dinero es positivo
			saldo+=dinero;//Se incrementa el saldo
			checkDescubierta();//Actualiza el atributo descubierta
		}
		imprimeSaldo();//Mostramos por pantalla el saldo actual
		
	}
	
	

	/** Metodo utilizado para retirar dinero de la cuenta
	 * bancaria.
	 * 
	 * <b>Precondicion:</b> Ninguna
	 * <b>Postcondicion:</b> Si dinero>0 y saldo>=0 decrementa el saldo actual
	 * con el valor del parametro dinero, e imprime por pantalla
	 * a continuacion el saldo resultante con la cadena "Saldo actual: 'saldo'"
	 * Si la cuenta estaba descubierta, y su saldo pasa a ser positivo,
	 * descubierta pasa a valer false.
	 * En caso contrario, solo imprime el mensaje.
	 * 
	 *  Si la cuenta no estaba descubierta, y su saldo pasa a ser negativo,
	 * descubierta pasa a valer true.
	 * 
	 * Entrada: dinero
	 * Salida: boolean
	 * 
	 * @param dinero double que representa el dinero que se desea
	 * retirar de la cuenta.
	 * @return devuelve true si se ha podido disminuir el saldo, y false en
	 * caso contrario
	 */
	public boolean retirar(double dinero) {
	boolean operacion=false;
		if(!this.getDescubierta() && dinero>0){//si no esta descubierta y
			//dinero es positivo
			saldo-=dinero;
			operacion=true;
			checkDescubierta();//Actualiza el atributo descubierta
		}
		imprimeSaldo();
		return operacion;
	}
	
	/** Metodo utilizado para transferir dinero de una cuenta
	 * a otra cuenta.
	 * 
	 * <b>Precondicion:</b> Ninguna
	 * <b>Postcondicion:</b> Si cantidad>0 y saldo>=0, y cuenta no es null, se
	 * decrementa cantidad del saldo y se incrementa cantidad del saldo
	 * del objeto c. Adicionalmente, se imprime por pantalla
	 * el saldo resultante con la cadena "Saldo actual: 'saldo'".
	 * En caso contrario, solo se imprime el mensaje por pantalla.
	 *  
	 *  Si la cuenta destino estaba descubierta, y su saldo pasa a ser positivo,
	 *  descubierta pasa a valer false, y al contrario para la cuenta origen.
	 *  
	 * Entrada: cantidad
	 * Entrada/salida: c
	 * Salida: boolean
	 * 
	 * @param cantidad double que representa el dinero que se desea
	 * transferir de la cuenta this hacia la cuenta c.
	 * @param c cuenta destino hacia la que se desea transferir el dinero.
	 * @return devuelve true si se ha transferido el dinero y false
	 * en caso contrario.
	 */
	public boolean hacerTransferenciaA(Cuenta c,double cantidad) {
		boolean operacion=false;
		if(!this.getDescubierta() && cantidad>0 && c!=null){//si no esta descubierta
			//cantidad es positivo, y c no es null
			saldo-=cantidad;//Se decrementa cantidad de la cuenta origen
			c.saldo+=cantidad;//Se incrementa cantidad en la cuenta destino
			operacion=true;//Operacion es true
			checkDescubierta();
			c.checkDescubierta();
		}
		imprimeSaldo();//Mostramos el saldo actual de la cuenta origen
		return operacion;//devolvemos si se ha realizado la transaccion
	}
	

	/** Metodo utilizado para conocer los beneficios que se obtendran
	 * de la cuenta bancaria, aplicando para ello un interes compuesto.
	 * @see <a href="https://es.wikipedia.org/wiki/Inter%C3%A9s_compuesto">Informacion sobre interes compuesto</a>
	 * 
	 * <b>Precondicion:</b> Ninguna
	 * <b>Postcondicion:</b> Si numeroMeses>0 y saldo>=0, Se devuelve el 
	 * beneficio obtenido con un interes compuesto, para el saldo actual, aplicando
	 * durante numeroMeses meses el interes indicado por interesMensual. La
	 * formula es la siguiente: beneficio final=(Cantidad final - saldo)
	 * Cantidad final=saldo*(1+interesMensual)^numMeses
	 *  
	 * Entrada: numMeses
	 * Salida: double
	 * 
	 * @param numMeses numero de meses para el que se quiere saber
	 * el beneficio que se obtendria con los interes.
	 * @return devuelve el beneficio que se va a obtener en los meses
	 * indicados por numMeses con el saldo, aplicandole el interes compuesto
	 * marcado por interesMensual.
	 */
	public double beneficiosFuturos(int numMeses) {
		//La formula del interes compuesto es ...
		double beneficioFinalConInteresCompuesto=0;
		if(!getDescubierta() && numMeses>0)
			beneficioFinalConInteresCompuesto=(saldo*Math.pow((1+interesMensual),numMeses)-saldo);
		return beneficioFinalConInteresCompuesto;
	}
	
	//Metodos privados
	
	//Metodo privado utilizado para imprimir por pantalla el saldo actual de
	//la cuenta
	private void imprimeSaldo() {
		System.out.println("Saldo actual: "+saldo);
		
	}
	
	//Metodo privado que comprueba si la cuenta esta descubierta o no.
	//El atributo descubierta sera true si el saldo es negativo, true
	//en caso contrario.
	private void checkDescubierta() {
		if(saldo<0)
			descubierta=true;
		else
			descubierta=false;
	}
}
