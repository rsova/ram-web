package app.beans


class Property {
	
	public static final String ATTR_MLS = "MLS";
	public static final String ATTR_CLASS = "Class";
	public static final String ATTR_TYPE = "Type";
	public static final String ATTR_DISTRICT = "District";
	public static final String ATTR_LIST_PRICE = "ListPrice";
	public static final String ATTR_ADDRESS_NUMBER = "AddressNumber";
	public static final String ATTR_ADDRESS_DIRECTION = "AddressDirection";
	public static final String ATTR_ADDRESS_STREET = "AddressStreet";
	public static final String ATTR_LOT_UNIT = "AddressLotUnit";
	public static final String ATTR_CITY = "City";
	public static final String ATTR_STATE = "State";
	public static final String ATTR_ZIP = "Zip";
	public static final String ATTR_STATUS = "Status";
	public static final String ATTR_WATER_FRONT = "WaterFront";
	public static final String ATTR_VIEW = "View";
	public static final String ATTR_AGENT_ID = "AgentID";
	public static final String ATTR_AGENT_NAME = "AgentName";
	public static final String ATTR_AGENT_PHONE = "AgentPhone";
	public static final String ATTR_OFFICE1_ID = "ListingOffice1_ID";
	public static final String ATTR_OFFICE1_NAME = "ListingOffice1_Name";
	public static final String ATTR_OFFICE1_PHONE = "ListingOffice1_Phone";
	public static final String ATTR_COLIST_AGENT_ID = "CoListingAgent_ID";
	public static final String ATTR_COLIST_AGENT_NAME = "CoListingAgent_Name";
	public static final String ATTR_COLIST_AGENT_PHONE = "CoListingAgent_Phone";
	public static final String ATTR_OFFICE2_ID = "ListingOffice2_ID";
	public static final String ATTR_OFFICE2_NAME = "ListingOffice2_Name";
	public static final String ATTR_OFFICE2_PHONE = "ListingOffice2_Phone";
	public static final String ATTR_LIST_DATE = "ListDate";
	public static final String ATTR_VIRTUAL_TOUR = "VirtualTour";
	public static final String ATTR_DIV = "Div";
	public static final String ATTR_ZONE = "Zone";
	public static final String ATTR_SEC = "Sec";
	public static final String ATTR_PLAT = "Plat";
	public static final String ATTR_PAR = "Par";
	public static final String ATTR_UNIT = "Unit";
	public static final String ATTR_CPR = "CPR";
	public static final String ATTR_HOUSE_CARPORT = "HouseCarport";
	public static final String ATTR_HOUSE_GARAGE = "HouseGarage";
	public static final String ATTR_OHANA_CARPORT = "OhanaCarport";
	public static final String ATTR_OHANA_GARAGE = "OhanaGarage";
	public static final String ATTR_LT = "LT";
	public static final String ATTR_PARTIAL_OWNERSHIP = "PartialOwnership";
	public static final String ATTR_PERCENT_OWNERSHIP = "PercentOwnership";
	public static final String ATTR_BUILDING_NAME = "BuildingName";
	public static final String ATTR_FLOOR_LEVEL = "FloorLevel";
	public static final String ATTR_BEDS = "Beds";
	public static final String ATTR_BATHS = "Baths";
	public static final String ATTR_LIV_SQFT = "LivSQFT";
	public static final String ATTR_LAND_SQFT = "LandSQFT";
	public static final String ATTR_LAND_ACRES = "LandAcres";
	public static final String ATTR_MAINT_FEE = "MaintFee";
	public static final String ATTR_PUBLIC_REMARKS = "PublicRemarks";
	public static final String ATTR_OHANA_BEDS = "Ohana_Beds";
	public static final String ATTR_OHANA_BATHS = "Ohana_Baths";
	public static final String ATTR_OHANA_LIV_SQFT = "Ohana_LivSQFT";
	public static final String ATTR_FIXED_BEGIN_DATE = "FixedBeginDate";
	public static final String ATTR_FIXED_END_DATE = "FixedEndDate";
	public static final String ATTR_RES_CONDO = "Res_Condo";
	public static final String ATTR_VOW_ADDRESS = "VOWAddress";
	public static final String ATTR_VOW_COMMENT = "VOWComment";
	public static final String ATTR_VOW_AVM = "VOWAVM";
	public static final String ATTR_VOW_INCLUDE = "VOWInclude";
	public static final String ATTR_POT_SHORT_SALE = "PotentialShortSale";
	public static final String ATTR_REO = "REO";
	public static final String ATTR_AGENT_EMAIL = "AgentEmail";
	public static final String ATTR_AGENT_FAX = "AgentFax";
	public static final String ATTR_AGENT_WEB_PAGE = "AgentWebPage";
	public static final String ATTR_FULL_NAME = "AgentFullName";
	public static final String ATTR_OFFICE1_EMAIL = "ListingOffice1_Email";
	public static final String ATTR_OFFICE1_FAX = "ListingOffice1_Fax";
	public static final String ATTR_OFFICE1_WEB_PAGE = "ListingOffice1_WebPage";
	public static final String ATTR_COLIST_AGENT_EMAIL = "CoListingAgent_Email";
	public static final String ATTR_COLIST_AGENT_FAX = "CoListingAgent_Fax";
	public static final String ATTR_COLIST_AGENT_WEB_PAGE = "CoListingAgentWebPage";
	public static final String ATTR_COLIST_AGENT_FULL_NAME = "CoListingAgentFullName";
	public static final String ATTR_OFFICE2_EMAIL = "ListingOffice2_Email";
	public static final String ATTR_OFFICE2_FAX = "ListingOffice2_Fax";
	public static final String ATTR_OFFICE2_WEB_PAGE = "ListingOffice2_WebPage";
	public static final String ATTR_LAT = "Lat";
	public static final String ATTR_LON = "Lon";
	public static final String ATTR_POOLYN = "PoolYN";
	public static final String ATTR_PV_INSTALLED = "PV_Installed";
	public static final String ATTR_PVO_INSTALLED = "PVO_Installed";
	public static final String ATTR_PV_OWNERSHIP = "PV_Ownership";
	public static final String ATTR_PVO_OWNERSHIP = "PVO_Ownership";
	public static final String ATTR_IMAGES = "Images";
	
	
	private String mls;
	private PropertyClass clazz;
	private PropertyType type;
	private District district;
	private Double listPrice;
	private String closingDate;
	private PropertyAddress address;
	private Status status;
	private Agent agent;
	private PropertyOffice listingOffice1;
	private Agent colistingAgent;
	private PropertyOffice listingOffice2;
	private String listDate;
	private String virtualTour;
	private String div;
	private String zone;
	private String sec;
	private String plat;
	private String par;
	private String unit;
	private String cpr;
	private String houseCarport;
	private String houseGarage;
	private PropertyOhana ohana;
	private LandTenure lt;
	private String partialOwnership;
	private String percentOwnership;
	private String buildingName;
	private String floorLevel;
	private String beds;
	private String baths;
	private String livSQFT;
	private String landSQFT;
	private String landAcres;
	private String maintFee;
	private String publicRemarks;
	private String fixedBeginDate;
	private String fixedEndDate;
	private String resCondo;
	private String vowAddress;
	private String vowComment;
	private String vowAVM;
	private String vowInclude;	
	private String potentialShortSale;
	private String reo;
	private String lat;
	private String lon;
	private String poolYN;
	private View view;
	private Waterfront waterfront;
	
	private String pvInstalled;
	private String pvoInstalled;
	private String pvOwnership;
	private String pvoOwnership;
	private String photoCount;
	
	private List<PropertyImage> images;

	public String getMls() {
		return mls;
	}

	public void setMls(String mls) {
		this.mls = mls;
	}

	public PropertyClass getClazz() {
		return clazz;
	}

	public void setClazz(PropertyClass clazz) {
		this.clazz = clazz;
	}

	public PropertyType getType() {
		return type;
	}

	public void setType(PropertyType type) {
		this.type = type;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public Double getListPrice() {
		return listPrice;
	}

	public void setListPrice(Double listPrice) {
		this.listPrice = listPrice;
	}

	public String getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}

	public PropertyAddress getAddress() {
		return address;
	}

	public void setAddress(PropertyAddress address) {
		this.address = address;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public PropertyOffice getListingOffice1() {
		return listingOffice1;
	}

	public void setListingOffice1(PropertyOffice listingOffice1) {
		this.listingOffice1 = listingOffice1;
	}

	public Agent getColistingAgent() {
		return colistingAgent;
	}

	public void setColistingAgent(Agent colistingAgent) {
		this.colistingAgent = colistingAgent;
	}

	public PropertyOffice getListingOffice2() {
		return listingOffice2;
	}

	public void setListingOffice2(PropertyOffice listingOffice2) {
		this.listingOffice2 = listingOffice2;
	}

	public String getListDate() {
		return listDate;
	}

	public void setListDate(String listDate) {
		this.listDate = listDate;
	}

	public String getVirtualTour() {
		return virtualTour;
	}

	public void setVirtualTour(String virtualTour) {
		this.virtualTour = virtualTour;
	}

	public String getDiv() {
		return div;
	}

	public void setDiv(String div) {
		this.div = div;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getSec() {
		return sec;
	}

	public void setSec(String sec) {
		this.sec = sec;
	}

	public String getPlat() {
		return plat;
	}

	public void setPlat(String plat) {
		this.plat = plat;
	}

	public String getPar() {
		return par;
	}

	public void setPar(String par) {
		this.par = par;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCpr() {
		return cpr;
	}

	public void setCpr(String cpr) {
		this.cpr = cpr;
	}

	public String getHouseCarport() {
		return houseCarport;
	}

	public void setHouseCarport(String houseCarport) {
		this.houseCarport = houseCarport;
	}

	public String getHouseGarage() {
		return houseGarage;
	}

	public void setHouseGarage(String houseGarage) {
		this.houseGarage = houseGarage;
	}

	public PropertyOhana getOhana() {
		return ohana;
	}

	public void setOhana(PropertyOhana ohana) {
		this.ohana = ohana;
	}

	public LandTenure getLt() {
		return lt;
	}

	public void setLt(LandTenure lt) {
		this.lt = lt;
	}

	public String getPartialOwnership() {
		return partialOwnership;
	}

	public void setPartialOwnership(String partialOwnership) {
		this.partialOwnership = partialOwnership;
	}

	public String getPercentOwnership() {
		return percentOwnership;
	}

	public void setPercentOwnership(String percentOwnership) {
		this.percentOwnership = percentOwnership;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getFloorLevel() {
		return floorLevel;
	}

	public void setFloorLevel(String floorLevel) {
		this.floorLevel = floorLevel;
	}

	public String getBeds() {
		return beds;
	}

	public void setBeds(String beds) {
		this.beds = beds;
	}

	public String getBaths() {
		return baths;
	}

	public void setBaths(String baths) {
		this.baths = baths;
	}

	public String getLivSQFT() {
		return livSQFT;
	}

	public void setLivSQFT(String livSQFT) {
		this.livSQFT = livSQFT;
	}

	public String getLandSQFT() {
		return landSQFT;
	}

	public void setLandSQFT(String landSQFT) {
		this.landSQFT = landSQFT;
	}

	public String getLandAcres() {
		return landAcres;
	}

	public void setLandAcres(String landAcres) {
		this.landAcres = landAcres;
	}

	public String getMaintFee() {
		return maintFee;
	}

	public void setMaintFee(String maintFee) {
		this.maintFee = maintFee;
	}

	public String getPublicRemarks() {
		return publicRemarks;
	}

	public void setPublicRemarks(String publicRemarks) {
		this.publicRemarks = publicRemarks;
	}

	public String getFixedBeginDate() {
		return fixedBeginDate;
	}

	public void setFixedBeginDate(String fixedBeginDate) {
		this.fixedBeginDate = fixedBeginDate;
	}

	public String getFixedEndDate() {
		return fixedEndDate;
	}

	public void setFixedEndDate(String fixedEndDate) {
		this.fixedEndDate = fixedEndDate;
	}

	public String getResCondo() {
		return resCondo;
	}

	public void setResCondo(String resCondo) {
		this.resCondo = resCondo;
	}

	public String getVowAddress() {
		return vowAddress;
	}

	public void setVowAddress(String vowAddress) {
		this.vowAddress = vowAddress;
	}

	public String getVowComment() {
		return vowComment;
	}

	public void setVowComment(String vowComment) {
		this.vowComment = vowComment;
	}

	public String getVowAVM() {
		return vowAVM;
	}

	public void setVowAVM(String vowAVM) {
		this.vowAVM = vowAVM;
	}

	public String getVowInclude() {
		return vowInclude;
	}

	public void setVowInclude(String vowInclude) {
		this.vowInclude = vowInclude;
	}

	public String getPotentialShortSale() {
		return potentialShortSale;
	}

	public void setPotentialShortSale(String potentialShortSale) {
		this.potentialShortSale = potentialShortSale;
	}

	public String getReo() {
		return reo;
	}

	public void setReo(String reo) {
		this.reo = reo;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getPoolYN() {
		return poolYN;
	}

	public void setPoolYN(String poolYN) {
		this.poolYN = poolYN;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Waterfront getWaterfront() {
		return waterfront;
	}

	public void setWaterfront(Waterfront waterfront) {
		this.waterfront = waterfront;
	}

	public String getPvInstalled() {
		return pvInstalled;
	}

	public void setPvInstalled(String pvInstalled) {
		this.pvInstalled = pvInstalled;
	}

	public String getPvoInstalled() {
		return pvoInstalled;
	}

	public void setPvoInstalled(String pvoInstalled) {
		this.pvoInstalled = pvoInstalled;
	}

	public String getPvOwnership() {
		return pvOwnership;
	}

	public void setPvOwnership(String pvOwnership) {
		this.pvOwnership = pvOwnership;
	}

	public String getPvoOwnership() {
		return pvoOwnership;
	}

	public void setPvoOwnership(String pvoOwnership) {
		this.pvoOwnership = pvoOwnership;
	}

	public String getPhotoCount() {
		return photoCount;
	}

	public void setPhotoCount(String photoCount) {
		this.photoCount = photoCount;
	}

	public List<PropertyImage> getImages() {
		return images;
	}

	public void setImages(List<PropertyImage> images = null) {
		this.images = images;
	}

	

}
