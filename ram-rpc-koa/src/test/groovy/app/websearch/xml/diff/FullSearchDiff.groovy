package app.websearch.xml.diff

import redstone.xmlrpc.XmlRpcArray
import redstone.xmlrpc.XmlRpcStruct
import app.websearch.xml.diff.dataelements.FullSearchElement
import static app.websearch.xml.diff.dataelements.FullSearchElement.*;

class FullSearchDiff extends PropertySearchResponseDiff {
	
	private static final Integer DATA_INDEX = 4;
	
	public FullSearchDiff() {
		super(DATA_INDEX);
	}
	
	@Override
	protected boolean diffData (XmlRpcArray newArray, XmlRpcArray oldArray) {
		boolean areDifferent = false;
		
		def newData = newArray.get(getDataIndex());
		// If old data is not an array (can happen when there is no feed data in the response) ...
		if (!(newData instanceof XmlRpcArray)) {
			// ... Then use an empty array
			newData = new XmlRpcArray();
		}
		
		def oldData = oldArray.get(getDataIndex());
		// If old data is not an array (can happen when there is no feed data in the response) ...
		if (!(oldData instanceof XmlRpcArray)) {
			// ... Then use an empty array
			oldData = new XmlRpcArray();	
		}
		
		if (diffAttributes(newData, oldData, ATTR_MLS)) {
			areDifferent = true;
		}
		

		List<FullSearchElement> newSet = new ArrayList<>(newData.size());
		for (def d : newData) {
			if (d instanceof XmlRpcStruct) {
				FullSearchElement e = toElement(d);
				newSet.add(e);
			}
		}
		
		List<FullSearchElement> oldSet = new ArrayList<>(oldData.size());
		for (def d : oldData) {
			if (d instanceof XmlRpcStruct) {
				FullSearchElement e = toElement(d);
				oldSet.add(e);
			}
		}
		
		if (setDiff(newSet, oldSet)) {
			areDifferent = true;
		}

		return areDifferent;
	}
	
	/**
	 * Identify the unique elements that are missing from each set by doing two set
	 * difference operations:
	 *
	 * newSet - oldSet
	 * oldSet - newSet
	 *
	 * @param newSet
	 * @param oldSet
	 * @return
	 */
	protected boolean setDiff (List<FullSearchElement> newSet, List<FullSearchElement> oldSet) {
		boolean areDifferent = false;
		List<FullSearchElement> diffs = diffElements(newSet, oldSet);
		if (!diffs.isEmpty()) {
			areDifferent = true;
			addDiffMessage("ERROR: NEW properties '${diffs}' were not found in OLD feed!");
		}
		
		diffs = diffElements(oldSet, newSet);
		if (!diffs.isEmpty()) {
			areDifferent = true;
			addDiffMessage("ERROR: OLD properties '${diffs}' were not found in NEW feed!");
		}
		return areDifferent;
	}
	
	protected List<FullSearchElement> diffElements (List<FullSearchElement> set1, List<FullSearchElement> set2) {
		List<FullSearchElement> diffs = new ArrayList<>();
		
		Map<String,List<FullSearchElement>> indexByCode = new HashMap();
		for (FullSearchElement e : set2) {
			List<FullSearchElement> matches = indexByCode.get(e.mls);
			if (matches == null) {
				matches = new ArrayList<>();
				indexByCode.put(e.mls, matches);
			}
			matches.add(e);
		}
		
		// For each element of set1 ...
		for (FullSearchElement x : set1) {
			boolean matchFound = false;
			// If the element's code is the empty string, then skip
			if (!x.mls.equals("")) {
				List<FullSearchElement> matches = indexByCode.get(x.mls);
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
	protected Object toElement (XmlRpcStruct struct) {
		FullSearchElement agent = new FullSearchElement(struct);
		return agent;
	}
}
