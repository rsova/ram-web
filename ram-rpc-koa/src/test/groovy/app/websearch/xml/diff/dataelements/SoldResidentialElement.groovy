package app.websearch.xml.diff.dataelements

import redstone.xmlrpc.XmlRpcStruct;

class SoldResidentialElement extends SoldElement {

	public static final String ATTR_OHANA_BEDS = "Ohana_Beds";
	public static final String ATTR_OHANA_BATHS = "Ohana_Baths";
	public static final String ATTR_OHANA_LIV_SQFT = "Ohana_LivSQFT";
	
	private String ohanaBeds;
	private String ohanaBaths;
	private Double ohanaLivSQFT;
	
	public SoldResidentialElement (XmlRpcStruct struct) {
		super(struct);
		this.ohanaBeds = struct.getString(ATTR_OHANA_BEDS);
		this.ohanaBaths = struct.getString(ATTR_OHANA_BATHS);
		this.ohanaLivSQFT = struct.getDouble(ATTR_OHANA_LIV_SQFT);
	}

	public String getOhanaBeds() {
		return ohanaBeds;
	}

	public String getOhanaBaths() {
		return ohanaBaths;
	}

	public Double getOhanaLivSQFT() {
		return ohanaLivSQFT;
	}
	
}
