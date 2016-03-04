package app.websearch.xml.diff.dataelements

import redstone.xmlrpc.XmlRpcStruct;

public class OpenHouseElement {
	
	public static final String ATTR_AGENT1_ID = "agent1_id";
	public static final String ATTR_AGENT2_ID = "agent2_id";
	public static final String ATTR_OFFICE1_ID = "office1_id";
	public static final String ATTR_OFFICE2_ID = "office2_id";
	public static final String ATTR_LISTING_ID = "L_ListingID";
	public static final String ATTR_CLASS = "L_Class";
	public static final String ATTR_AREA = "L_Area";
	public static final String ATTR_OH_START_DATE = "OH_StartDate";
	public static final String ATTR_OH_START_TIME = "OH_StartTime";
	public static final String ATTR_OH_END_TIME = "OH_EndTime";
	public static final String ATTR_IMAGE = "image";
	public static final String ATTR_TYPE = "L_Type_";
	public static final String ATTR_STATUS = "L_Status";
	public static final String ATTR_ADDRESS_NUMBER = "L_AddressNumber";
	public static final String ATTR_ADDRESS_DIRECTION = "L_AddressDirection";
	public static final String ATTR_ADDRESS_STREET = "L_AddressStreet";
	public static final String ATTR_CITY = "L_City";
	public static final String ATTR_STATE = "L_State";
	public static final String ATTR_ZIP = "L_Zip";
	public static final String ATTR_OH_COMMENTS = "OH_Comments";
	
	private String agent1Id;
	private String agent2Id;
	private String office1Id;
	private String office2Id;
	private String lListingId;
	private String lClass;
	private String lArea;
	private String ohStartDate;
	private String ohStartTime;
	private String ohEndTime;
	private String image;
	private String lType;
	private String lStatus;
	private String lAddressNumber;
	private String lAddressDirection;
	private String lAddressStreet;
	private String lCity;
	private String lState;
	private String lZip;
	private String ohComments;

	public OpenHouseElement (XmlRpcStruct struct) {
		super();
		this.agent1Id = struct.getString(ATTR_AGENT1_ID);
		this.agent2Id = struct.getString(ATTR_AGENT2_ID);
		this.office1Id = struct.getString(ATTR_OFFICE1_ID);
		this.office2Id = struct.getString(ATTR_OFFICE2_ID);
		this.lListingId = struct.getString(ATTR_LISTING_ID);
		this.lClass = struct.getString(ATTR_CLASS);
		this.lArea = struct.getString(ATTR_AREA);
		this.ohStartDate = struct.getString(ATTR_OH_START_DATE);
		this.ohStartTime = struct.getString(ATTR_OH_START_TIME);
		this.ohEndTime = struct.getString(ATTR_OH_END_TIME);
		this.image = struct.getString(ATTR_IMAGE);
		this.lType = struct.getString(ATTR_TYPE);
		this.lStatus = struct.getString(ATTR_STATUS);
		this.lAddressNumber = struct.getString(ATTR_ADDRESS_NUMBER);
		this.lAddressDirection = struct.getString(ATTR_ADDRESS_DIRECTION);
		this.lAddressStreet = struct.getString(ATTR_ADDRESS_STREET);
		this.lCity = struct.getString(ATTR_CITY);
		this.lState = struct.getString(ATTR_STATE);
		this.lZip = struct.getString(ATTR_ZIP);
		this.ohComments = struct.getString(ATTR_OH_COMMENTS);
	}

	@Override
	public int hashCode() {
		return lListingId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof OpenHouseElement) {
			OpenHouseElement cast = (OpenHouseElement) obj;
			if (this.lListingId.equals(cast.lListingId)) {
				return true;
			}
		}
		return false;
	}

	

	@Override
	public String toString() {
		String str = "OpenHouseElement [" +
			"lListingId=${lListingId}, " +
			"lClass=${lClass}, " +
			"lArea=${lArea}, " +
			"ohStartDate=${ohStartDate}, " +
			"ohStartTime=${ohStartTime}, " +
			"ohEndTime=${ohEndTime}, " +
			"lStatus=${lStatus}, " + 
			"lAddressNumber=${lAddressNumber}, " +
			"lAddressDirection=${lAddressDirection}, " + 
			"lAddressStreet=${lAddressStreet}" +
		"]";
		return str;
	}

	public String getAgent1Id() {
		return agent1Id;
	}

	public String getAgent2Id() {
		return agent2Id;
	}

	public String getOffice1Id() {
		return office1Id;
	}

	public String getOffice2Id() {
		return office2Id;
	}

	public String getlListingId() {
		return lListingId;
	}

	public String getlClass() {
		return lClass;
	}

	public String getlArea() {
		return lArea;
	}

	public String getOhStartDate() {
		return ohStartDate;
	}

	public String getOhStartTime() {
		return ohStartTime;
	}

	public String getOhEndTime() {
		return ohEndTime;
	}

	public String getImage() {
		return image;
	}

	public String getlType() {
		return lType;
	}

	public String getlStatus() {
		return lStatus;
	}

	public String getlAddressNumber() {
		return lAddressNumber;
	}

	public String getlAddressDirection() {
		return lAddressDirection;
	}

	public String getlAddressStreet() {
		return lAddressStreet;
	}

	public String getlCity() {
		return lCity;
	}

	public String getlState() {
		return lState;
	}

	public String getlZip() {
		return lZip;
	}

	public String getOhComments() {
		return ohComments;
	}

	
	
}
