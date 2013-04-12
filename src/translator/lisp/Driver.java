package translator.lisp;


public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LISPtoCTranslator translator = new LISPtoCTranslator();
		System.out.println(translator.translateStatment("(ADD 2 2)"));
	}

}
