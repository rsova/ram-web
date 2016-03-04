package app.websearch.xml.diff.dataelements

import redstone.xmlrpc.XmlRpcStruct;

public class SoldElement {

	public static final String ATTR_MLS = "MLS";
	public static final String ATTR_CLASS = "Class";
	public static final String ATTR_DISTRICT = "District";
	public static final String ATTR_CLOSING_DATE = "ClosingDate";
	public static final String ATTR_AGENT_ID = "AgentID";
	public static final String ATTR_OFFICE1_ID = "ListingOffice1_ID";
	public static final String ATTR_LIST_PRICE = "ListPrice";
	public static final String ATTR_SOLD_PRICE= "SoldPrice";
	public static final String ATTR_VOW_ADDRESS = "VOWAddress";
	public static final String ATTR_ADDRESS_NUMBER = "AddressNumber";
	public static final String ATTR_ADDRESS_DIRECTION = "AddressDirection";
	public static final String ATTR_ADDRESS_STREET = "AddressStreet";
	public static final String ATTR_BEDS = "Beds";
	public static final String ATTR_BATHS = "Baths";
	public static final String ATTR_LT = "LT";
	public static final String ATTR_LIV_SQFT = "LivSQFT";
	public static final String ATTR_LAND_SQFT = "LandSQFT";
	public static final String ATTR_VIEW = "View";
	public static final String ATTR_WATER_FRONT = "WaterFront";
	public static final String ATTR_COLIST_AGENT_ID = "CoListingAgent_ID";
	public static final String ATTR_OFFICE2_ID = "ListingOffice2_ID";
	public static final String ATTR_IMAGES = "Images";
	
	private String mls;
	private String sClass;
	private String district;
	private String closingDate;
	private String agentId;
	private String listingOffice1Id;
	private Double listPrice;
	private String soldPrice;
	private String vowAddress;
	private String addressNumber;
	private String addressDirection;
	private String addressStreet;
	private String beds;
	private String baths;
	private String lt;
	private Double livSQFT;
	private Double landSQFT;
	private String view;
	private String waterFront;
	private String colistAgentId;
	private String listingOffice2Id;
	
	// TODO populate this
	private Map<String,SoldImagesElement> images;
	
	
	public SoldElement (XmlRpcStruct struct) {
		super();
		this.mls = struct.getString(ATTR_MLS);
		this.sClass = struct.getString(ATTR_CLASS);
		this.district = struct.getString(ATTR_DISTRICT);
		this.closingDate = struct.getString(ATTR_CLOSING_DATE);
		this.agentId = struct.getString(ATTR_AGENT_ID);
		this.listingOffice1Id = struct.getString(ATTR_OFFICE1_ID);
		this.listPrice = struct.getDouble(ATTR_LIST_PRICE);
		this.soldPrice = struct.getString(ATTR_SOLD_PRICE);
		this.vowAddress = struct.getString(ATTR_VOW_ADDRESS);	
		this.addressNumber = struct.getString(ATTR_ADDRESS_NUMBER);
		this.addressDirection = struct.getString(ATTR_ADDRESS_DIRECTION);
		this.addressStreet = struct.getString(ATTR_ADDRESS_STREET);
		this.beds = struct.getString(ATTR_BEDS);
		this.baths = struct.getString(ATTR_BATHS);
		this.lt = struct.getString(ATTR_LT);
		this.livSQFT = struct.getDouble(ATTR_LIV_SQFT);
		this.landSQFT = struct.getDouble(ATTR_LAND_SQFT);	
		this.view = struct.getString(ATTR_VIEW);
		this.waterFront = struct.getString(ATTR_WATER_FRONT);
		this.colistAgentId = struct.getString(ATTR_COLIST_AGENT_ID);
		this.listingOffice2Id = struct.getString(ATTR_OFFICE2_ID);
	}

	@Override
	public int hashCode() {
		return mls.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SoldElement) {
			SoldElement cast = (SoldElement) obj;
			if (this.mls.equals(cast.mls)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "{$addressNumber $addressStreet, $district [$sClass, $listingOffice1Id, $agentId]}"
	}

	public String getMls() {
		return mls;
	}

	public String getsClass() {
		return sClass;
	}

	public String getDistrict() {
		return district;
	}

	public String getClosingDate() {
		return closingDate;
	}

	public String getAgentId() {
		return agentId;
	}

	public String getListingOffice1Id() {
		return listingOffice1Id;
	}

	public Double getListPrice() {
		return listPrice;
	}

	public String getSoldPrice() {
		return soldPrice;
	}

	public String getVowAddress() {
		return vowAddress;
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

	public String getBeds() {
		return beds;
	}

	public String getBaths() {
		return baths;
	}

	public String getLt() {
		return lt;
	}

	public Double getLivSQFT() {
		return livSQFT;
	}

	public Double getLandSQFT() {
		return landSQFT;
	}

	public String getView() {
		return view;
	}

	public String getWaterFront() {
		return waterFront;
	}

	public String getColistAgentId() {
		return colistAgentId;
	}

	public String getListingOffice2Id() {
		return listingOffice2Id;
	}

	public Map<String, SoldImagesElement> getImages() {
		return images;
	}

	
	
}
