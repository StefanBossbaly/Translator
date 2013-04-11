
public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LISPtoCTranslator translator = new LISPtoCTranslator();
		
		System.out.println(translator.translateStatment("(ADD 1 (ADD 2 1))"));

	}

}
