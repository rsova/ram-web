package app.websearch.xml.diff

import static app.websearch.xml.diff.dataelements.OpenHouseElement.*
import redstone.xmlrpc.XmlRpcArray
import redstone.xmlrpc.XmlRpcStruct
import app.websearch.xml.diff.dataelements.OpenHouseElement

class OpenHouseListDiff extends PropertySearchResponseDiff {
	
	private static final Integer DATA_INDEX = 2;
	
	public OpenHouseListDiff() {
		super(DATA_INDEX);
	}
	
	@Override
	protected boolean diffData (XmlRpcArray newArray, XmlRpcArray oldArray) {
		boolean areDifferent = false;

		def oMls =[]
		oldArray[DATA_INDEX].each {entry -> if (entry instanceof Map) {oMls += entry.mlsNumber}}
		def nMls = []
		newArray[DATA_INDEX].each {entry -> if (entry instanceof Map ) {nMls += entry.mlsNumber}}

		if(oMls != nMls){
			System.err.println 'Results are different '
			System.err.println "Old MSL size ${oMls.size()} : ${oMls}"
			System.err.println "New MSL: size ${nMls.size()} : ${nMls}"
			System.err.println "Not in new : ${oMls - nMls}"
			System.err.println "Not in old : ${nMls - oMls}"
		}

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
		
		if (diffAttributes(newData, oldData, ATTR_LISTING_ID)) {
			areDifferent = true;
		}
		
		List<OpenHouseElement> newSet = new ArrayList<>(newData.size());
		for (def d : newData) {
			if (d instanceof XmlRpcStruct) {
				OpenHouseElement e = toElement(d);
				newSet.add(e);
			}
		}
		
		List<OpenHouseElement> oldSet = new ArrayList<>(oldData.size());
		for (def d : oldData) {
			if (d instanceof XmlRpcStruct) {
				OpenHouseElement e = toElement(d);
				oldSet.add(e);
			}
		}
		
		if (setDiff(newSet, oldSet)) {
			areDifferent = true;
		}

		return areDifferent;
	}
	
	private List<OpenHouseElement> diffElements (List<OpenHouseElement> set1, List<OpenHouseElement> set2) {
		List<OpenHouseElement> diffs = new ArrayList<>();
		
		Map<String,List<OpenHouseElement>> indexByCode = new HashMap();
		for (OpenHouseElement e : set2) {
			List<OpenHouseElement> matches = indexByCode.get(e.lListingId);
			if (matches == null) {
				matches = new ArrayList<>();
				indexByCode.put(e.lListingId, matches);
			}
			matches.add(e);
		}	
		
		// For each element of set1 ...
		for (OpenHouseElement x : set1) {
			boolean matchFound = false;
			// If the element's code is the empty string, then skip
			if (!x.lListingId.equals("")) {
				List<OpenHouseElement> matches = indexByCode.get(x.lListingId);
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

	@Override
	protected OpenHouseElement toElement(XmlRpcStruct struct) {
		return new OpenHouseElement(struct);
	}
	
	

}
