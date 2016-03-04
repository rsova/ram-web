package app.beans

import microsoft.sql.DateTimeOffset;

class Office {

	private String identifier;
	private DateTimeOffset dateAdded;
	private Broker broker;
	private Office mainOffice;
	private String abbreviation;
	private String email;
	private String name;
	private Phone phone1;
	private Phone phone2;
	private OfficeType type;
	private String url;
	private DateTimeOffset updateDate;
	private String state;
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public void setDateAdded(DateTimeOffset dateAdded) {
		this.dateAdded = dateAdded;
	}
	public void setMainOffice(Office mainOffice) {
		this.mainOffice = mainOffice;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setType(OfficeType type) {
		this.type = type;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setUpdateDate(DateTimeOffset updateDate) {
		this.updateDate = updateDate;
	}
	public String getIdentifier() {
		return identifier;
	}
	public DateTimeOffset getDateAdded() {
		return dateAdded;
	}
	public Office getMainOffice() {
		return mainOffice;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public String getEmail() {
		return email;
	}
	public String getName() {
		return name;
	}
	public OfficeType getType() {
		return type;
	}
	public String getUrl() {
		return url;
	}
	public DateTimeOffset getUpdateDate() {
		return updateDate;
	}
	public Broker getBroker() {
		return broker;
	}
	public void setBroker(Broker broker) {
		this.broker = broker;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Phone getPhone1() {
		return phone1;
	}
	public void setPhone1(Phone phone1) {
		this.phone1 = phone1;
	}
	public Phone getPhone2() {
		return phone2;
	}
	public void setPhone2(Phone phone2) {
		this.phone2 = phone2;
	}
	
	
}
