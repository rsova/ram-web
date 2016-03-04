package app.websearch.xml.diff.dataelements

import redstone.xmlrpc.XmlRpcArray;


public class OfficeElement {
	
	private static final Integer CODE_INDEX = 0;
	private static final Integer NAME_INDEX = 1;
	
	private String code;
	private String name;
	
	public OfficeElement (XmlRpcArray array) {
		super();
		this.code = array.getString(CODE_INDEX);
		this.name = array.getString(NAME_INDEX);
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "{" + code + "," + name + "}";
	}
	
}
