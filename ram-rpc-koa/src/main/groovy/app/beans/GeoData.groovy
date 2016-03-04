package app.beans

import microsoft.sql.DateTimeOffset;

class GeoData {
	
	private String addressLine;
	private String latitude;
	private String longitude;
	private String matchCode;
	private String matchedMethod;
	private String postalCode;
	private String primaryCity;
	private String quality;
	private String secondaryCity;
	private String subdivision;
	private DateTimeOffset updateTimestamp;
	private String zoomLevel;
	public String getAddressLine() {
		return addressLine;
	}
	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getMatchCode() {
		return matchCode;
	}
	public void setMatchCode(String matchCode) {
		this.matchCode = matchCode;
	}
	public String getMatchedMethod() {
		return matchedMethod;
	}
	public void setMatchedMethod(String matchedMethod) {
		this.matchedMethod = matchedMethod;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getPrimaryCity() {
		return primaryCity;
	}
	public void setPrimaryCity(String primaryCity) {
		this.primaryCity = primaryCity;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public String getSecondaryCity() {
		return secondaryCity;
	}
	public void setSecondaryCity(String secondaryCity) {
		this.secondaryCity = secondaryCity;
	}
	public String getSubdivision() {
		return subdivision;
	}
	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}
	public DateTimeOffset getUpdateTimestamp() {
		return updateTimestamp;
	}
	public void setUpdateTimestamp(DateTimeOffset updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}
	public String getZoomLevel() {
		return zoomLevel;
	}
	public void setZoomLevel(String zoomLevel) {
		this.zoomLevel = zoomLevel;
	}
	
	

}
