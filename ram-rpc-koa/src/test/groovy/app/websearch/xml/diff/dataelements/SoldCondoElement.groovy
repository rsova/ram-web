package app.websearch.xml.diff.dataelements

import redstone.xmlrpc.XmlRpcStruct;

class SoldCondoElement extends SoldElement {
	
	public static final String ATTR_BUILDING = "Building";
	public static final String ATTR_UNIT = "Unit";
	public static final String ATTR_MAINT_FEE = "MaintFee";
	
	private String building;
	private String unit;
	private String maintFee;
	
	public SoldCondoElement (XmlRpcStruct struct) {
		super(struct);
		this.building = struct.getString(ATTR_BUILDING);
		this.unit = struct.getString(ATTR_UNIT);
		this.maintFee = struct.getString(ATTR_MAINT_FEE);
	}

	public String getBuilding() {
		return building;
	}

	public String getUnit() {
		return unit;
	}

	public String getMaintFee() {
		return maintFee;
	}
	
}
