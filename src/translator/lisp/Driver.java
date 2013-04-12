package translator.lisp;


public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LISPtoCTranslator translator = new LISPtoCTranslator();
		System.out.println(translator.translateStatment("(SET x (NOT 1))"));
		System.out.println(translator.translateStatment("(SET x (ADD x 1))"));
	}
}
