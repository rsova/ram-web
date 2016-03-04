package app.websearch.xml.diff

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import redstone.xmlrpc.XmlRpcArray;
import redstone.xmlrpc.XmlRpcStruct;
import app.websearch.xml.diff.dataelements.SoldElement;
import static app.websearch.xml.diff.dataelements.SoldElement.*;

public abstract class SoldResponseDiff extends PropertySearchResponseDiff {
	
	private static final Integer DATA_INDEX = 2;
	
	public SoldResponseDiff() {
		super(DATA_INDEX);
	}
	
	@Override
	protected boolean diffData (XmlRpcArray newArray, XmlRpcArray oldArray) {	
		boolean areDifferent = false;
		
		def newData = newArray.get(getDataIndex());
		// If there is feed data in the new response ...
		if (!(newData instanceof XmlRpcArray)) {
			// ... Then use an empty array
			newData = new XmlRpcArray();
		}
		
		def oldData = oldArray.get(getDataIndex());
		// If old data is not an array (happens when there is no feed data in the old response) ...
		if (!(oldData instanceof XmlRpcArray)) {
			// ... Then use an empty array
			oldData = new XmlRpcArray();	
		}
		
		List<SoldElement> newSet = new ArrayList<>(newData.size());
		for (def d : newData) {
			if (d instanceof XmlRpcStruct) {
				SoldElement e = toElement(d);
				newSet.add(e);
			}
		}
		
		List<SoldElement> oldSet = new ArrayList<>(oldData.size());
		for (def d : oldData) {
			if (d instanceof XmlRpcStruct) {
				SoldElement e = toElement(d);
				oldSet.add(e);
			}
		}
		
		if (diffAttributes(newSet, oldSet)) {
			areDifferent = true;
		}
		if (setDiff(newSet, oldSet)) {
			areDifferent = true;
		}

		return areDifferent;
	}
	
	protected boolean diffAttributes (List<SoldElement> newSet, List<SoldElement> oldSet) {
		boolean areDifferent = false;
		Map<String,List<SoldElement>> indexByCode = new HashMap();
		for (SoldElement e : oldSet) {
			List<SoldElement> matches = indexByCode.get(e.mls);
			if (matches == null) {
				matches = new ArrayList<>();
				indexByCode.put(e.mls, matches);
			}
			matches.add(e);
		}
		
		// For each element of new set ...
		for (SoldElement x : newSet) {
			if (diffByCode(indexByCode, x)) {
				areDifferent = true;
			}
		}
		return areDifferent;
	}
	
	protected List<SoldElement> diffElements (List<SoldElement> set1, List<SoldElement> set2) {
		List<SoldElement> diffs = new ArrayList<>();
		
		Map<String,List<SoldElement>> indexByCode = new HashMap();
		for (SoldElement e : set2) {
			List<SoldElement> matches = indexByCode.get(e.mls);
			if (matches == null) {
				matches = new ArrayList<>();
				indexByCode.put(e.mls, matches);
			}
			matches.add(e);
		}
		
		// For each element of set1 ...
		for (SoldElement x : set1) {
			boolean matchFound = false;
			// If the element's code is the empty string, then skip
			if (!x.mls.equals("")) {
				List<SoldElement> matches = indexByCode.get(x.mls);
				if (matches && matches.size() > 0) {
					matchFound = true;
				}
			}
			if (!matchFound) {
				diffs.add(x);
			}
		}
		
		return diffs;
	}
	
	protected String craftDiffAttrMsg (def attrName, def val1, def val2) {
		return String.format("%-20s: NEW= '%s' vs OLD= '%s'", attrName, val1, val2);
	}
	
	protected boolean diffByCode(Map<String, List<SoldElement>> indexByCode, SoldElement x) {
		boolean areDifferent = false;
		// If the code is the empty string, then skip
		if (!x.mls.equals("")) {
			List<SoldElement> matches = indexByCode.get(x.mls);
			if (matches && matches.size() > 0) {
				for (SoldElement y : matches) {
					List<String> diffsForElem = new ArrayList<>();
					if (x.sClass != y.sClass) {
						String msg = craftDiffAttrMsg(ATTR_CLASS, x.sClass, y.sClass);
						diffsForElem.add(msg);
					}
					if (x.district != y.district) {
						String msg = craftDiffAttrMsg(ATTR_DISTRICT, x.district, y.district);
						diffsForElem.add(msg);
					}
					if (x.closingDate != y.closingDate) {
						String msg = craftDiffAttrMsg(ATTR_CLOSING_DATE, x.closingDate, y.closingDate);
						diffsForElem.add(msg);
					}
					if (x.agentId != y.agentId) {
						String msg = craftDiffAttrMsg(ATTR_AGENT_ID, x.agentId, y.agentId);
						diffsForElem.add(msg);
					}
					if (x.listingOffice1Id != y.listingOffice1Id) {
						String msg = craftDiffAttrMsg(ATTR_OFFICE1_ID, x.listingOffice1Id, y.listingOffice1Id);
						diffsForElem.add(msg);
					}
					if (x.listPrice != y.listPrice) {
						String msg = craftDiffAttrMsg(ATTR_LIST_PRICE, x.listPrice, y.listPrice);
						diffsForElem.add(msg);
					}
					if (x.soldPrice != y.soldPrice) {
						String msg = craftDiffAttrMsg(ATTR_SOLD_PRICE, x.soldPrice, y.soldPrice);
						diffsForElem.add(msg);
					}
					if (x.vowAddress != y.vowAddress) {
						String msg = craftDiffAttrMsg(ATTR_VOW_ADDRESS, x.vowAddress, y.vowAddress);
						diffsForElem.add(msg);
					}
					if (x.addressNumber != y.addressNumber) {
						String msg = craftDiffAttrMsg(ATTR_ADDRESS_NUMBER, x.addressNumber, y.addressNumber);
						diffsForElem.add(msg);
					}
					if (x.addressDirection != y.addressDirection) {
						String msg = craftDiffAttrMsg(ATTR_ADDRESS_DIRECTION, x.addressDirection, y.addressDirection);
						diffsForElem.add(msg);
					}
					if (x.addressStreet != y.addressStreet) {
						String msg = craftDiffAttrMsg(ATTR_ADDRESS_STREET, x.addressStreet, y.addressStreet);
						diffsForElem.add(msg);
					}
					if (x.beds != y.beds) {
						String msg = craftDiffAttrMsg(ATTR_BEDS, x.beds, y.beds);
						diffsForElem.add(msg);
					}
					if (x.baths != y.baths) {
						String msg = craftDiffAttrMsg(ATTR_BATHS, x.baths, y.baths);
						diffsForElem.add(msg);
					}
					if (x.lt != y.lt) {
						String msg = craftDiffAttrMsg(ATTR_LT, x.lt, y.lt);
						diffsForElem.add(msg);
					}
					if (x.livSQFT != y.livSQFT) {
						String msg = craftDiffAttrMsg(ATTR_LIV_SQFT, x.livSQFT, y.livSQFT);
						diffsForElem.add(msg);
					}
					if (x.landSQFT != y.landSQFT) {
						String msg = craftDiffAttrMsg(ATTR_LAND_SQFT, x.landSQFT, y.landSQFT);
						diffsForElem.add(msg);
					}
					if (x.view != y.view) {
						String msg = craftDiffAttrMsg(ATTR_VIEW, x.view, y.view);
						diffsForElem.add(msg);
					}
					if (x.waterFront != y.waterFront) {
						String msg = craftDiffAttrMsg(ATTR_WATER_FRONT, x.waterFront, y.waterFront);
						diffsForElem.add(msg);
					}
					if (x.colistAgentId != y.colistAgentId) {
						String msg = craftDiffAttrMsg(ATTR_COLIST_AGENT_ID, x.colistAgentId, y.colistAgentId);
						diffsForElem.add(msg);
					}
					if (x.listingOffice2Id != y.listingOffice2Id) {
						String msg = craftDiffAttrMsg(ATTR_OFFICE2_ID, x.listingOffice2Id, y.listingOffice2Id);
						diffsForElem.add(msg);
					}
					
					// TODO compare images
					if (!diffsForElem.isEmpty()) {
						areDifferent = true;
						String msg = "ERROR: Sold properties have same ${ATTR_MLS} '${x.mls} but different attributes:";
						for (String m : diffsForElem) {
							msg += "\n\t" + m;
						}
						addDiffMessage(msg);
					}
				}
				
			}
		}
		return areDifferent;
	}

}
