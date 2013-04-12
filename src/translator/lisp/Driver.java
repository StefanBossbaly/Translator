package translator.lisp;


public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LISPtoCTranslator translator = new LISPtoCTranslator();
		System.out.println(translator.translateStatment("(SET x (ADD 1 (SUBTRACT 1 123)))"));
	}
}
