package app.websearch.xml.diff.dataelements


import java.util.Map;

import redstone.xmlrpc.XmlRpcStruct;

class FullSearchElement {

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
	private String sClass;
	private String district;
	private String closingDate;
	private String image;
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
	private String livSQFT;
	private String landSQFT;
	private String view;
	private String waterFront;
	private String ohanaBeds;
	private String ohanaBaths;
	private String ohanaLivSQFT;
	private Map<String,SoldImagesElement> images;
	
	
	public FullSearchElement(XmlRpcStruct struct) {
		super();
		this.mls = struct.getString(ATTR_MLS);
		this.addressNumber = struct.getString(ATTR_ADDRESS_NUMBER);
		this.addressDirection = struct.getString(ATTR_ADDRESS_DIRECTION);
		this.addressStreet = struct.getString(ATTR_ADDRESS_STREET);
		this.agentId = struct.getString(ATTR_AGENT_ID);
		this.sClass = struct.getString(ATTR_CLASS);
		this.district = struct.getString(ATTR_DISTRICT);
		this.listingOffice1Id = struct.getString(ATTR_OFFICE1_ID);
		this.listPrice = struct.getDouble(ATTR_LIST_PRICE);
	}

	@Override
	public int hashCode() {
		return mls.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FullSearchElement) {
			FullSearchElement cast = (FullSearchElement) obj;
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
	
	

}
