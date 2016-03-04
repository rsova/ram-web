package app.websearch.xml.diff;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import app.websearch.xml.diff.dataelements.OfficeElement;


import org.apache.commons.lang.StringEscapeUtils;

import app.websearch.xml.diff.dataelements.OfficeElement;
import redstone.xmlrpc.XmlRpcArray;

public class OfficeListDiff extends GetListsResponseDiff {
	
	private static final Integer DATA_INDEX = 2;
	
	private boolean areDifferent;
	
	public OfficeListDiff() {
		super(DATA_INDEX);
	}
	
	@Override
	protected boolean diffData (XmlRpcArray newArray, XmlRpcArray oldArray) {
		XmlRpcArray newData = newArray.getArray(getDataIndex());
		XmlRpcArray oldData = oldArray.getArray(getDataIndex());
		
		List<OfficeElement> newSet = new ArrayList<>(newData.size());
		for (def d : newData) {
			if (d instanceof XmlRpcArray) {
				OfficeElement e = new OfficeElement(d);
				newSet.add(e);
			}
		}
		
		List<OfficeElement> oldSet = new ArrayList<>(oldData.size());
		for (def d : oldData) {
			if (d instanceof XmlRpcArray) {
				OfficeElement e = new OfficeElement(d);
				oldSet.add(e);
			}
		}
		
		diffAttributes(newSet, oldSet);
		setDiff(newSet, oldSet);

		return areDifferent;
	}
	
	private void diffAttributes (List<OfficeElement> newSet, List<OfficeElement> oldSet) {
		Map<String,List<OfficeElement>> indexByCode = new HashMap();
		for (OfficeElement e : oldSet) {
			List<OfficeElement> offices = indexByCode.get(e.code);
			if (offices == null) {
				offices = new ArrayList<>();
				indexByCode.put(e.code, offices);
			}
			offices.add(e);
		}
		
		Map<String,List<OfficeElement>> indexByName = new HashMap();
		for (OfficeElement e : oldSet) {
			List<OfficeElement> offices = indexByName.get(e.name);
			if (offices == null) {
				offices = new ArrayList<>();
				indexByName.put(e.name, offices);
			}
			offices.add(e);
		}
		
		// For each office of new set ...
		for (OfficeElement x : newSet) {
			boolean matchFound = diffByCode(indexByCode, x);
			// If match is already found, then skip name search
			if (!matchFound) {
				matchFound = diffByName(indexByName, x);
			}
		}
	}
	
	private boolean diffByCode (Map<String,List<OfficeElement>> indexByCode, OfficeElement x) {
		boolean matchFound = false;
		// If the office's code is the empty string, then skip
		if (!x.code.equals("")) {
			List<OfficeElement> matches = indexByCode.get(x.code);
			if (matches && matches.size() > 0) {
				matchFound = true;
				for (OfficeElement y : matches) {
					// We unescape XML first, in case one service used name to escape and other service used unicode point code
					String unescapedX = StringEscapeUtils.unescapeXml(x.name);
					String unescapedY = StringEscapeUtils.unescapeXml(y.name);
					if (!unescapedX.equals(unescapedY)) {
						areDifferent = true;
						addDiffMessage("ERROR: Offices have same code '${x.code}' but different names; NEW: '${x.name}' vs OLD: '${y.name}'!");
					}
				}
			}
		}
		return matchFound;
	}
	
	private boolean diffByName (Map<String,List<OfficeElement>> indexByName, OfficeElement x) {
		boolean matchFound = false;
		// If the office's name is the empty string, then skip
		if (!x.name.equals("")) {
			List<OfficeElement> matches = indexByName.get(x.name);
			if (matches && matches.size() > 0) {
				matchFound = true;
				for (OfficeElement y : matches) {
					// We unescape XML first, in case one service used name to escape and other service used unicode point code
					if (!x.code.equals(y.code)) {
						areDifferent = true;
						addDiffMessage("ERROR: Offices have same name '${x.name}' but different codes; NEW: '${x.code}' vs OLD: '${y.code}'!");
					}
				}
			}
		}
		return matchFound;
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
	private void setDiff (List<OfficeElement> newSet, List<OfficeElement> oldSet) {
		List<OfficeElement> diffs = diffElements(newSet, oldSet);
		if (!diffs.isEmpty()) {
			areDifferent = true;
			addDiffMessage("ERROR: NEW offices '${diffs}' were not found in OLD feed!");
		}
		
		diffs = diffElements(oldSet, newSet);
		if (!diffs.isEmpty()) {
			areDifferent = true;
			addDiffMessage("ERROR: OLD offices '${diffs}' were not found in NEW feed!");
		}
	}
	
	private List<OfficeElement> diffElements (List<OfficeElement> set1, List<OfficeElement> set2) {
		List<OfficeElement> diffs = new ArrayList<>();
		
		Map<String,List<OfficeElement>> indexByCode = new HashMap();
		for (OfficeElement e : set2) {
			List<OfficeElement> offices = indexByCode.get(e.code);
			if (offices == null) {
				offices = new ArrayList<>();
				indexByCode.put(e.code, offices);
			}
			offices.add(e);
		}
		
		Map<String,List<OfficeElement>> indexByName = new HashMap();
		for (OfficeElement e : set2) {
			List<OfficeElement> offices = indexByName.get(e.name);
			if (offices == null) {
				offices = new ArrayList<>();
				indexByName.put(e.name, offices);
			}
			offices.add(e);
		}
		
		
		// For each office of set1 ...
		for (OfficeElement x : set1) {
			boolean matchFound = false;
			// If the office's code is the empty string, then skip
			if (!x.code.equals("")) {
				List<OfficeElement> matches = indexByCode.get(x.code);
				if (matches && matches.size() > 0) {
					matchFound = true;
				}
			}
			if (!matchFound) {
				// If the office's name is the empty string, then skip
				if (!x.name.equals("")) {
					List<OfficeElement> matches = indexByName.get(x.name);
					if (matches && matches.size() > 0) {
						matchFound = true;
					}
				}
			}
			if (!matchFound) {
				diffs.add(x);
			}
		}
		
		return diffs;
	}

}
