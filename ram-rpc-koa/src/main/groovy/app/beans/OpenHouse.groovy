package app.beans

import microsoft.sql.DateTimeOffset;

class OpenHouse {
	
	private String openHouseId;
	private PropertyAddress address;
	private Agent agent;
	private PropertyClass propertyClass;
	private Agent coListingAgent;
	private Office coListingOffice;
	private String comments;
	private DateTimeOffset createDateTime;
	private District district;
	private String docTimestamp;
	private String endTime;
	private DateTimeOffset eventEndDateTime;
	private GeoData geoData;
	private String idxInclude;
	private Office listingOffice1;
	private String mlsNumber;
	private String searchPrice;
	private String startDate;
	private DateTimeOffset startDateTime;
	private String startTime;
	private Status status;
	private StatusCategory statusCategory;
	private String statusDate;
	private String statusDetail;
	private PropertyType propertyType;
	private String uniqueID;
	private String udpateDate;
	private DateTimeOffset updateDateTime;
	private vowAddress;
	private vowAVM;
	private vowComment;
	private vowInclude;
	private String discriminator;
	private String image;
	
	public String getOpenHouseId() {
		return openHouseId;
	}
	public void setOpenHouseId(String openHouseId) {
		this.openHouseId = openHouseId;
	}
	public PropertyAddress getAddress() {
		return address;
	}
	public void setAddress(PropertyAddress address) {
		this.address = address;
	}
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public PropertyClass getPropertyClass() {
		return propertyClass;
	}
	public void setPropertyClass(PropertyClass propertyClass) {
		this.propertyClass = propertyClass;
	}
	public Agent getCoListingAgent() {
		return coListingAgent;
	}
	public void setCoListingAgent(Agent coListingAgent) {
		this.coListingAgent = coListingAgent;
	}
	public Office getCoListingOffice() {
		return coListingOffice;
	}
	public void setCoListingOffice(Office coListingOffice) {
		this.coListingOffice = coListingOffice;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public DateTimeOffset getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(DateTimeOffset createDateTime) {
		this.createDateTime = createDateTime;
	}
	public District getDistrict() {
		return district;
	}
	public void setDistrict(District district) {
		this.district = district;
	}
	public String getDocTimestamp() {
		return docTimestamp;
	}
	public void setDocTimestamp(String docTimestamp) {
		this.docTimestamp = docTimestamp;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public DateTimeOffset getEventEndDateTime() {
		return eventEndDateTime;
	}
	public void setEventEndDateTime(DateTimeOffset eventEndDateTime) {
		this.eventEndDateTime = eventEndDateTime;
	}
	public GeoData getGeoData() {
		return geoData;
	}
	public void setGeoData(GeoData geoData) {
		this.geoData = geoData;
	}
	public String getIdxInclude() {
		return idxInclude;
	}
	public void setIdxInclude(String idxInclude) {
		this.idxInclude = idxInclude;
	}
	public Office getListingOffice1() {
		return listingOffice1;
	}
	public void setListingOffice1(Office listingOffice1) {
		this.listingOffice1 = listingOffice1;
	}
	public String getMlsNumber() {
		return mlsNumber;
	}
	public void setMlsNumber(String mlsNumber) {
		this.mlsNumber = mlsNumber;
	}
	public String getSearchPrice() {
		return searchPrice;
	}
	public void setSearchPrice(String searchPrice) {
		this.searchPrice = searchPrice;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public DateTimeOffset getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(DateTimeOffset startDateTime) {
		this.startDateTime = startDateTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public StatusCategory getStatusCategory() {
		return statusCategory;
	}
	public void setStatusCategory(StatusCategory statusCategory) {
		this.statusCategory = statusCategory;
	}
	public String getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}
	public String getStatusDetail() {
		return statusDetail;
	}
	public void setStatusDetail(String statusDetail) {
		this.statusDetail = statusDetail;
	}
	public PropertyType getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}
	public String getUniqueID() {
		return uniqueID;
	}
	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}
	public String getUdpateDate() {
		return udpateDate;
	}
	public void setUdpateDate(String udpateDate) {
		this.udpateDate = udpateDate;
	}
	public DateTimeOffset getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(DateTimeOffset updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	public java.lang.Object getVowAddress() {
		return vowAddress;
	}
	public void setVowAddress(java.lang.Object vowAddress) {
		this.vowAddress = vowAddress;
	}
	public java.lang.Object getVowAVM() {
		return vowAVM;
	}
	public void setVowAVM(java.lang.Object vowAVM) {
		this.vowAVM = vowAVM;
	}
	public java.lang.Object getVowComment() {
		return vowComment;
	}
	public void setVowComment(java.lang.Object vowComment) {
		this.vowComment = vowComment;
	}
	public java.lang.Object getVowInclude() {
		return vowInclude;
	}
	public void setVowInclude(java.lang.Object vowInclude) {
		this.vowInclude = vowInclude;
	}
	public String getDiscriminator() {
		return discriminator;
	}
	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
	}
	public String getImage() {
			String mls = mlsNumber
			def mlsFolder = mls.replaceFirst(/.{3}$/, "000")
//			<string>http://www.ramidx.com/MLSImages/368000/200/maui368580-1.jpg</string>
			return 'http://www.ramidx.com/MLSImages/' + mlsFolder + '/200/maui' + mls + '-1.jpg'		
	}
	
	public void setImage(String image) {}	
}
