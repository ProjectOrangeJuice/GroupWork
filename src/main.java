import java.util.ArrayList;

import javafx.scene.image.Image;

public class main {

	public static void main(String[] args) {
		
		ArrayList<String> languages = new ArrayList<String>();
		
		languages.add("English");
		languages.add("French");
		languages.add("Romanian");
		
		Book Sapiens = new Book(0, "Sapiens", 2017, null, "Noah", "Penguin Books", "educational", "111222333", "English");
		Book TheMartian = new Book(1, "The Martian", 2015, null, "Andy Weir", "Kane Books", "sci-fi", "333444555", "English");
		DVD Interstellar = new DVD(2, "Interstellar", 2014, null, "Christopher Nolan", 169, "English", languages);
		
		System.out.println(Sapiens.getUniqueID());
		System.out.println(TheMartian.getUniqueID());
		System.out.println(Interstellar.getUniqueID());

	}

}
