public class Laptop extends Resource {
	private String manufacturer;
	private String model;
	private String OS;
	
	public Laptop(int uniqueID, String title, int year, String manufacturer, String model, String OS) {
		super(uniqueID, title, year);
		this.manufacturer = manufacturer;
		this.model = model;
		this.OS = OS;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOS() {
		return OS;
	}

	public void setOS(String oS) {
		OS = oS;
	}

}
