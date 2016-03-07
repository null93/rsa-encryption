/**
 *
 */
public class RSA {

	/**
	 *
	 */
	public static void main ( String [] args ) {

		Decimal a = new Decimal ( "2" );
		Decimal b = new Decimal ( "5123232345234545624646234623462" );
		Decimal c = new Decimal ( "93456237612378461789345709324516345716458913475" );
		Decimal d = new Decimal ( "1236478264782364762378462374" );

		System.out.println ( "result: " + Operation.divide ( b, a ).stringify () );

	}

}