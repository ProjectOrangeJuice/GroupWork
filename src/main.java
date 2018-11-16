import java.util.ArrayList;

public class main {

	public static void main(String[] args) {
		
		ArrayList<String> languages = new ArrayList<String>();
		languages.add("English");
		languages.add("French");
		languages.add("Romanian");
		
		Book Sapiens = new Book("Sapiens", 2017, "Noah", "Penguin Books", "111222333", "English");
		Book TheMartian = new Book("The Martian", 2015, "Andy Weir", "Kane Books", "333444555", "English");
		DVD Interstellar = new DVD("Interstellar", 2014, "Christopher Nolan", 169, "English", languages);
		
		System.out.println(Sapiens.getUniqueID());
		System.out.println(TheMartian.getUniqueID());
		System.out.println(Interstellar.getUniqueID());

	}

}
