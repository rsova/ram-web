package app.beans

import microsoft.sql.DateTimeOffset;

class Agent {
	
	private String identifier
	private boolean active;
	private String email;
	private String firstName;
	private String fullName;
	private String lastName;
	private String middleInitial;
	private String phone1Description;
	private String phone1Number;
	private String phone2Description;
	private String phone2Number;
	private String salutation;
	private AgentType type;
	private String url;
	private Office office;
	private DateTimeOffset dateAdded;
	private String hash;
	private DateTimeOffset updateDate;
	private String preferredPhone;
	private String userCode;
	
	public String getIdentifier() {
		return identifier;
	}
	public String getActive() {
		return active;
	}
	public String getEmail() {
		return email;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getFullName() {
		return fullName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getMiddleInitial() {
		return middleInitial;
	}
	public String getOfficeId() {
		return officeId;
	}
	public String getPhone1Description() {
		return phone1Description;
	}
	public String getPhone1Number() {
		return phone1Number;
	}
	public String getPhone2Description() {
		return phone2Description;
	}
	public String getPhone2Number() {
		return phone2Number;
	}
	public String getSalutation() {
		return salutation;
	}
	public String getUrl() {
		return url;
	}
	public String getOfficeAbbreviation() {
		return officeAbbreviation;
	}
	public String getOfficeEmail() {
		return officeEmail;
	}
	public String getOfficeName() {
		return officeName;
	}
	public String getOfficePhone1Extension() {
		return officePhone1Extension;
	}
	public String getOfficePhone1Number() {
		return officePhone1Number;
	}
	public String getOfficeType() {
		return officeType;
	}
	public String getOfficeUrl() {
		return officeUrl;
	}
	public String getOfficePhone1Description() {
		return officePhone1Description;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public String getHash() {
		return hash;
	}
	public DateTimeOffset getUpdateDate() {
		return updateDate;
	}
	public String getPreferredPhone() {
		return preferredPhone;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public void setPhone1Description(String phone1Description) {
		this.phone1Description = phone1Description;
	}
	public void setPhone1Number(String phone1Number) {
		this.phone1Number = phone1Number;
	}
	public void setPhone2Description(String phone2Description) {
		this.phone2Description = phone2Description;
	}
	public void setPhone2Number(String phone2Number) {
		this.phone2Number = phone2Number;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setOfficeAbbreviation(String officeAbbreviation) {
		this.officeAbbreviation = officeAbbreviation;
	}
	public void setOfficeEmail(String officeEmail) {
		this.officeEmail = officeEmail;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public void setOfficePhone1Extension(String officePhone1Extension) {
		this.officePhone1Extension = officePhone1Extension;
	}
	public void setOfficePhone1Number(String officePhone1Number) {
		this.officePhone1Number = officePhone1Number;
	}
	public void setOfficeType(String officeType) {
		this.officeType = officeType;
	}
	public void setOfficeUrl(String officeUrl) {
		this.officeUrl = officeUrl;
	}
	public void setOfficePhone1Description(String officePhone1Description) {
		this.officePhone1Description = officePhone1Description;
	}
	public void setDateAdded(DateTimeOffset dateAdded) {
		this.dateAdded = dateAdded;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public void setUpdateDate(DateTimeOffset updateDate) {
		this.updateDate = updateDate;
	}
	public void setPreferredPhone(String preferredPhone) {
		this.preferredPhone = preferredPhone;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public void setType(AgentType type) {
		this.type = type;
	}
	public void setOffice(Office office) {
		this.office = office;
	}
	
}
