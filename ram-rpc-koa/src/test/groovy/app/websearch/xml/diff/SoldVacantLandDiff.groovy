package app.websearch.xml.diff

import java.util.List
import java.util.Map;

import redstone.xmlrpc.XmlRpcStruct;
import app.websearch.xml.diff.dataelements.SoldElement;
import app.websearch.xml.diff.dataelements.SoldVacantLandElement

class SoldVacantLandDiff extends SoldResponseDiff {

	public SoldVacantLandDiff() {
		super();
	}

	@Override
	protected SoldElement toElement(XmlRpcStruct struct) {
		return new SoldVacantLandElement(struct);
	}

}
