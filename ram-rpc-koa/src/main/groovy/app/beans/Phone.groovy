package app.beans

class Phone {
	
	private String number;
	private String description;
	private String extension;
	
	public String getNumber() {
		return number;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String setNumber(String number) {
		return this.number = number;
	}
	
	public String setDescription(String description) {
		return this.description = description;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	

}
