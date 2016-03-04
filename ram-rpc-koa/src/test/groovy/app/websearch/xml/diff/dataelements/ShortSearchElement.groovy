package app.websearch.xml.diff.dataelements

import java.util.Map;

import redstone.xmlrpc.XmlRpcStruct;

class ShortSearchElement {

	public static final String ATTR_MLS = "MLS";
	public static final String ATTR_CLASS = "Class";
	public static final String ATTR_STATUS = "Status";
	public static final String ATTR_LIST_PRICE = "ListPrice";
	public static final String ATTR_LT = "LT";
	public static final String ATTR_LIST_DATE = "ListDate";
	public static final String ATTR_LIV_SQFT = "LivSQFT";
	public static final String ATTR_LAND_SQFT = "LandSQFT";
	public static final String ATTR_AGENT_ID = "AgentID";
	public static final String ATTR_OFFICE1_ID = "ListingOffice1_ID";
	public static final String ATTR_COLIST_AGENT_ID = "CoListingAgent_ID";
	public static final String ATTR_OFFICE2_ID = "ListingOffice2_ID";
	public static final String ATTR_ADDRESS_NUMBER = "AddressNumber";
	public static final String ATTR_ADDRESS_DIRECTION = "AddressDirection";
	public static final String ATTR_ADDRESS_STREET = "AddressStreet";
	public static final String ATTR_LOT_UNIT = "AddressLotUnit";
	public static final String ATTR_DISTRICT = "District";
	public static final String ATTR_BUILDING_NAME = "BuildingName";
	public static final String ATTR_UNIT = "Unit";
	public static final String ATTR_BEDS = "Beds";
	public static final String ATTR_BATHS = "Baths";
	public static final String ATTR_POT_SHORT_SALE = "PotentialShortSale";
	public static final String ATTR_REO = "REO";
	public static final String ATTR_LAT = "Lat";
	public static final String ATTR_LON = "Lon";
	public static final String ATTR_IMAGE = "Image";
	
	private String mls;
	private String sClass;
	private String status;
	private Double listPrice;
	private String lt;
	private String listDate;
	private String livSQFT;
	private String landSQFT;
	private String agentId;
	private String listingOffice1Id;
	private String coListingAgentId;
	private String listingOffice2Id;
	private String addressNumber;
	private String addressDirection;
	private String addressStreet;
	private String addressLotUnit;
	private String district;
	private String buildingName;
	private String unit;
	private String beds;
	private String baths;
	private String potentialShortSale;
	private String reo;
	private String lat;
	private String lon;
	private ImagesElement image;
	
	public ShortSearchElement(XmlRpcStruct struct) {
		super();
		this.mls = struct.getString(ATTR_MLS);
		this.sClass = struct.getString(ATTR_CLASS);
		this.status = struct.getString(ATTR_STATUS);
		this.listPrice = struct.getDouble(ATTR_LIST_PRICE);
		this.lt = struct.getString(ATTR_LT);
		this.listDate = struct.getString(ATTR_LIST_DATE);
		this.livSQFT = struct.getString(ATTR_LIV_SQFT);
		this.landSQFT = struct.getString(ATTR_LAND_SQFT);
		this.listDate = struct.getString(ATTR_LIST_DATE);
		this.agentId = struct.getString(ATTR_AGENT_ID);
		this.listingOffice1Id = struct.getString(ATTR_OFFICE1_ID);
		this.coListingAgentId = struct.getString(ATTR_COLIST_AGENT_ID);
		this.listingOffice2Id = struct.getString(ATTR_OFFICE2_ID);
		this.addressNumber = struct.getString(ATTR_ADDRESS_NUMBER);
		this.addressDirection = struct.getString(ATTR_ADDRESS_DIRECTION);
		this.addressStreet = struct.getString(ATTR_ADDRESS_STREET);
		this.addressLotUnit = struct.getString(ATTR_LOT_UNIT);
		this.district = struct.getString(ATTR_DISTRICT);	
		this.buildingName = struct.getString(ATTR_BUILDING_NAME);
		this.unit = struct.getString(ATTR_UNIT);
		this.beds = struct.getString(ATTR_BEDS);
		this.baths = struct.getString(ATTR_BATHS);
		this.potentialShortSale = struct.getString(ATTR_POT_SHORT_SALE);
		this.reo = struct.getString(ATTR_REO);
		this.lat = struct.getString(ATTR_LAT);
//		this.lat = struct.getDouble(ATTR_LAT);
//		if (this.lat) {
//			this.lat = String.format("%.15f", new Double(this.lat));
//		} 
		this.lon = struct.getString(ATTR_LON);
//		this.lon = struct.getDouble(ATTR_LON);
//		if (this.lon) {
//			this.lon = String.format("%.15f", new Double(this.lon));
//		}
		// TODO set image element
	}

	@Override
	public int hashCode() {
		return mls.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ShortSearchElement) {
			ShortSearchElement cast = (ShortSearchElement) obj;
			if (this.mls.equals(cast.mls)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "{$addressNumber $addressDirection $addressStreet, $district}"
	}

	public static String getAttrClass() {
		return ATTR_CLASS;
	}

	public static String getAttrListPrice() {
		return ATTR_LIST_PRICE;
	}

	public static String getAttrListDate() {
		return ATTR_LIST_DATE;
	}

	public static String getAttrLivSqft() {
		return ATTR_LIV_SQFT;
	}

	public static String getAttrLandSqft() {
		return ATTR_LAND_SQFT;
	}

	public static String getAttrAgentId() {
		return ATTR_AGENT_ID;
	}

	public static String getAttrColistAgentId() {
		return ATTR_COLIST_AGENT_ID;
	}

	public static String getAttrAddressNumber() {
		return ATTR_ADDRESS_NUMBER;
	}

	public static String getAttrAddressDirection() {
		return ATTR_ADDRESS_DIRECTION;
	}

	public static String getAttrAddressStreet() {
		return ATTR_ADDRESS_STREET;
	}

	public static String getAttrDistrict() {
		return ATTR_DISTRICT;
	}

	public static String getAttrBuildingName() {
		return ATTR_BUILDING_NAME;
	}

	public static String getAttrBeds() {
		return ATTR_BEDS;
	}

	public static String getAttrBaths() {
		return ATTR_BATHS;
	}

	public static String getAttrLat() {
		return ATTR_LAT;
	}

	public static String getAttrLon() {
		return ATTR_LON;
	}

	public static String getAttrImage() {
		return ATTR_IMAGE;
	}

	public String getMls() {
		return mls;
	}

	public String getsClass() {
		return sClass;
	}

	public String getStatus() {
		return status;
	}

	public Double getListPrice() {
		return listPrice;
	}

	public String getLt() {
		return lt;
	}

	public String getListDate() {
		return listDate;
	}

	public String getLivSQFT() {
		return livSQFT;
	}

	public String getLandSQFT() {
		return landSQFT;
	}

	public String getAgentId() {
		return agentId;
	}

	public String getListingOffice1Id() {
		return listingOffice1Id;
	}

	public String getCoListingAgentId() {
		return coListingAgentId;
	}

	public String getListingOffice2Id() {
		return listingOffice2Id;
	}

	public String getAddressNumber() {
		return addressNumber;
	}

	public String getAddressDirection() {
		return addressDirection;
	}

	public String getAddressStreet() {
		return addressStreet;
	}

	public String getAddressLotUnit() {
		return addressLotUnit;
	}

	public String getDistrict() {
		return district;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public String getUnit() {
		return unit;
	}

	public String getBeds() {
		return beds;
	}

	public String getBaths() {
		return baths;
	}

	public String getPotentialShortSale() {
		return potentialShortSale;
	}

	public String getReo() {
		return reo;
	}

	public String getLat() {
		return lat;
	}

	public String getLon() {
		return lon;
	}

	public ImagesElement getImage() {
		return image;
	}
	
}
