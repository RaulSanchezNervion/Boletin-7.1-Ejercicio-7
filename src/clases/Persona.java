package clases;

/**
 * La clase Persona es utilizada para representar una persona
 * y las acciones que puede realizar, en un contexto bancario.
 *  
 * Como propiedades basicas tiene:
 * <ul>
 * 		<li>nombre: tipo cadena, consultable y modificable</li>
 * 		<li>apellidos: tipo cadena, consultable y modificable</li>
 * 		<li>edad: tipo int, consultable y modificable</li>
 * 		<li>sexo: tipo char, consultable y modificable</li>
 * 		<li>cuenta: tipo Cuenta, no consultable, ni modificable</li>
 * </ul>
 * 
 * Propiedades compartidas: No hay
 * Propiedades derivadas: No hay
 * 
 * Restricciones: edad>=0, sexo puede ser 'm' (para mujer), 'h'(para hombre) o 'i'(indeterminado).
 * NOTA: Como se indicaba en el boletin, la clase ha asumido la responsabilidad de validar los datos. 
 * Para ello, los metodos aceptan estos valores como entrada, ignorando las posibles modificaciones.
 * Otra posible solución habria sido aniadir las restricciones como precondiciones.
 * 
 * Su interfaz es la siguiente:
 * <ul>
 * 		<li> Getter y Setter de las propiedades consultables y modificables</li>
 * 		<li> public void saludar()</li>
 * 		<li> public void crearCuentaCorriente(String, int, double)</li>
 * 		<li> public boolean sacarDinero(double)</li>
 * 		<li> public boolean ingresarDinero(double)</li>
 * 		<li> public boolean hacerTransferenciaA(Persona,double)</li>
 * 		<li> public void cerrarCuenta()</li>
 * </ul>
 * 
 * 
 * @author Raul Sanchez Galan
 * @version 1.0
 *
 */
public class Persona {
	//Atributos
	private String nombre;
	private String apellidos;
	private int edad;
	private char sexo;
	private Cuenta cuenta;
	
	//Metodos
	
	//Constructor sin parametros
	public Persona() {
		this.nombre = "";
		this.apellidos = "";
		this.edad = 1;
		this.sexo = 'm';
	}
	
	//Constructor con parametros
	public Persona(String nombre, String apellidos, int edad, char sexo) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.setEdad(edad);
		this.setSexo(sexo);
	}
	
	//Getters y Setters
	//En caso de intentar setear un valor invalido, se ignora dicho cambio.
	public String getNombre() {
		return nombre;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public int getEdad() {
		return edad;
	}
	
	public char getSexo() {
		return sexo;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	/**
	 * <b>Precondicion:</b> Ninguna
	 * <b>Postcondicion:</b> Si edad es menor o igual que 0, se setea la edad.
	 * En caso contrario no se modifica la edad. 
	 * 
	 * Entrada: edad
	 * Salida: Ninguna
	 * 
	 * @param edad Edad que se quiere establecer.
	 */
	public void setEdad(int edad) {
		if(edad>=0)
			this.edad = edad;
	}
	
	/**
	 * <b>Precondicion:</b> Ninguna
	 * <b>Postcondicion:</b> Si sexo es igual a 'm', 'h' o 'i',
	 * se modifica el sexo por el parametro de entrada. En caso contrario,
	 * no se realiza ninguna modificacion. 
	 * 
	 * Entrada: sexo
	 * Salida: Ninguna
	 * 
	 * @param sexo Sexo que se quiere establecer.
	 */
	public void setSexo(char sexo) {
		if(sexoValido(sexo))
			this.sexo = sexo;
	}
	
	/** Metodo utilizado para mostrar un saludo por pantalla 
	 * 
	 * 
	 * <b>Precondicion:</b> Ninguna
	 * <b>Postcondicion:</b> Mostrar por pantalla "Buenas"
	 * 
	 * Entrada: Ninguna
	 * Salida: Ninguna
	 * 
	 */
	public void saludar() {
		//TODO poner un mensaje mas elaborado
		System.out.println("Buenas");
	}
	
	/** Metodo utilizado para que una persona cree su cuenta bancaria
	 * asociada.
	 * 
	 * 
	 * <b>Precondicion:</b> iban y nc deben ser valores correctos
	 * segun la logica bancaria.
	 * <b>Postcondicion:</b> Si la persona no tiene una cuenta bancaria,
	 * se le crea una cuenta bancaria nueva, con el iban, nc, e interesMensual
	 * proporcionado por los parametros, y con un saldo igual a cero. Si la persona
	 * ya tenia una cuenta bancaria, no se realiza ninguna accion.
	 * 
	 * Entrada: iban, nc, interesMensual
	 * Salida: Ninguna
	 * 
	 * @param iban Iban identificativo de la cuenta corriente que se quiere crear
	 * @param nc Numero de la cuenta que se quiere crear.
	 * @param interesMensual Interes compuesto que se va a aplicar a la cuenta, mes a mes.
	 * 
	 */
	public void crearCuentaCorriente(String iban, int nc, double interesMensual) {
		if(cuenta==null)//Si la persona no tiene una cuenta 
			cuenta=new Cuenta(iban, nc, interesMensual);
	}
	
	/** Metodo utilizado para retirar dinero de la cuenta bancaria de la persona.
	 * 
	 * <b>Precondicion:</b> Ninguna
	 * <b>Postcondicion:</b> Si la persona no tiene una cuenta, el metodo no hace nada.
	 * 
	 * En caso contrario (es decir, si si tiene una cuenta creada), si dinero>0 y saldo>=0 se decrementa el saldo actual
	 * de la cuenta, con el valor del parametro dinero, e imprime por pantalla el saldo resultante con la cadena "Saldo actual: 'saldo'"
	 * En caso contrario, solo imprime el mensaje.
	 * 
	 * Entrada: dinero
	 * Salida: boolean
	 * 
	 * @param dinero double que representa el dinero que se desea
	 * retirar de la cuenta.
	 * @return devuelve true si se ha podido disminuir el saldo de la cuenta, y false en
	 * caso contrario
	 */
	public boolean sacarDinero(double dinero) {
		return cuenta!=null? cuenta.retirar(dinero):false;//Operador ternario que comprueba si cuenta es distinto de null (es decir
		//si la persona tiene una cuenta creada). En caso afirmativo, realiza
		//la operacion de retirar dinero, y devuelve su resultado. En caso contrario,
		//no hace nada, y devuelve false. 
		//
		//Para tener mas informacion
		//sobre el operador ternario, ver: http://lineadecodigo.com/java/el-operador-ternario-en-java/
	}
	
	
	/** Metodo utilizado para ingresar dinero en la cuenta bancaria de la persona.
	 * 
	 * <b>Precondicion:</b> Ninguna
	 * <b>Postcondicion:</b> Si la persona no tiene una cuenta, el metodo no hace nada.
	 * 
	 * En caso contrario (es decir, si si tiene una cuenta creada), si dinero>0 incrementa el saldo actual
	 * con el valor del parametro dinero, e imprime por pantalla
	 * el saldo resultante con la cadena "Saldo actual: 'saldo'"
	 * En caso contrario, solo imprime el mensaje.
	 * 
	 * Entrada: cantidad
	 * Salida: boolean
	 * 
	 * @param cantidad double que representa el dinero que se desea
	 * ingresar en la cuenta.
	 * @return devuelve true si se ha podido incrementar el saldo de la cuenta, y false en
	 * caso contrario
	 */
	public boolean ingresarDinero(double cantidad) {
		boolean operacionRealizada=false;
		if(cuenta!=null && cantidad>0) {
			cuenta.ingresar(cantidad);
			operacionRealizada=true;
		}
		return operacionRealizada;
	}
	
	/** Metodo utilizado para transferir dinero de la cuenta de una persona
	 * a la cuenta de otra persona.
	 * 
	 * <b>Precondicion:</b> Ninguna
	 * <b>Postcondicion:</b> Si la persona no tiene una cuenta, el metodo no hace nada.
	 * 
	 * En caso contrario (es decir, si si tiene una cuenta creada), 
	 * si cantidad>0 y saldo>=0, y la persona p tiene una cuenta creada, se
	 * decrementa cantidad del saldo de la cuenta de this y se incrementa cantidad 
	 * en el saldo de la cuenta de la persona p. Adicionalmente, se imprime por pantalla
	 * el saldo resultante con la cadena "Saldo actual: 'saldo'".
	 * En caso contrario, solo se imprime el mensaje por pantalla.
	 *  
	 * Entrada: cantidad
	 * Entrada/salida: p
	 * Salida: boolean
	 * 
	 * @param cantidad double que representa el dinero que se desea
	 * transferir de la cuenta this hacia la cuenta de la persona p.
	 * @param p persona dueña de la cuenta destino hacia la que se desea transferir el dinero.
	 * @return devuelve true si se ha transferido el dinero y false
	 * en caso contrario.
	 */
	public boolean hacerTransfereciaA(Persona p, double cantidad) {
		return cuenta!=null? cuenta.hacerTransferenciaA(p.cuenta, cantidad):false;
	}
	
	
	/** Metodo utilizado para cerrar la cuenta de una persona
	 * 
	 * <b>Precondicion:</b> Ninguna
	 * <b>Postcondicion:</b> La persona deja de tener una cuenta bancaria
	 * 
	 *  
	 * Entrada: Ninguna
	 * Salida: Ninguna
	 * 
	 */
	public void cerrarCuenta() {
		cuenta=null;
	}
	
	
	//Metodos privados
	
	
	//Metodo privado, para validar si el char 
	//para indicar el sexo es correcto o no
	private boolean sexoValido(char sexo) {
		return sexo=='m' || sexo=='f' || sexo=='i';
	}


}
