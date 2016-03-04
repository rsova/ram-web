package app.websearch.xml.diff

import java.util.List;
import java.util.Map;

import redstone.xmlrpc.XmlRpcArray;
import redstone.xmlrpc.XmlRpcStruct;

abstract class PropertySearchResponseDiff extends XmlRpcArrayDiff {

	public PropertySearchResponseDiff(Integer dataIndex) {
		super(dataIndex);
	}

	@Override
	public boolean diff (XmlRpcArray newArray, XmlRpcArray oldArray) {
		boolean areDifferent = false;
		if (diffElementCount(newArray, oldArray)) {
			areDifferent = true;
		}
		if (diffData(newArray, oldArray)) {
			areDifferent = true;
		}
		return areDifferent;
	}
	
	protected String craftDiffAttrMsg (def attrName, def val1, def val2) {
		return String.format("%-15s: NEW: '%s' vs OLD: '%s'", attrName, val1, val2);
	}
	
	protected String craftMissingAttrMsg (def attrName) {
		return String.format("Attribute is MISSING entirely in NEW feed: %-15s", attrName);
	}
	
	protected abstract Object toElement (XmlRpcStruct struct);
	
	protected boolean diffAttributes (XmlRpcArray newData, XmlRpcArray oldData, String codeAttrName) {
		boolean areDifferent = false;
		Map<String,List<XmlRpcStruct>> indexByCode = new HashMap();
		for (def e : oldData) {
			if (e && e instanceof XmlRpcStruct) {
				String code = e.getString(codeAttrName);
				List<XmlRpcStruct> matches = indexByCode.get(code);
				if (matches == null) {
					matches = new ArrayList<>();
					indexByCode.put(code, matches);
				}
				matches.add(e);
			}
		}
		
		// For each element of new set ...
		for (def x : newData) {
			if (x && x instanceof XmlRpcStruct) {
				if (diffByCode(indexByCode, x, codeAttrName)) {
					areDifferent = true;
				}
			}
		}
		return areDifferent;
	}
	
	protected boolean diffByCode(Map<String, List<XmlRpcStruct>> indexByCode, XmlRpcStruct x, String codeAttrName) {
		boolean areDifferent = false;
		String code = x.getString(codeAttrName);
		// If the code is the empty string, then skip
		if (!code.equals("")) {
			List<XmlRpcStruct> matches = indexByCode.get(code);
			if (matches && matches.size() > 0) {
				for (XmlRpcStruct y : matches) {
					List<String> diffsForElem = new ArrayList<>();
					for (Map.Entry e : y) {
						String name = e.getKey();
						def valY = e.getValue();
						def valX = x.get(name);
						if (valY != null && valX == null) {
							String msg = craftMissingAttrMsg(name);
							diffsForElem.add(msg);
						} else if (valY != x.get(name)) {
							if (valY instanceof Number || valY instanceof String) {
								String msg = craftDiffAttrMsg(name, valX, valY);
								diffsForElem.add(msg);
							}
							// TODO compare structs
						}
					}

					if (!diffsForElem.isEmpty()) {
						areDifferent = true;
						String msg = "ERROR: Properties have same ${codeAttrName} '${code} but different attributes:";
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
	protected boolean setDiff (List newSet, List oldSet) {
		boolean areDifferent = false;
		List diffs = diffElements(newSet, oldSet);
		if (!diffs.isEmpty()) {
			areDifferent = true;
			String msg = "ERROR: The following NEW properties were not found in OLD feed:";
			for (def d : diffs) {
				msg += "\n\t${d}";
			}
			addDiffMessage(msg);
		}
		
		diffs = diffElements(oldSet, newSet);
		if (!diffs.isEmpty()) {
			areDifferent = true;
			String msg = "ERROR: The following OLD properties were not found in NEW feed:";
			for (def d : diffs) {
				msg += "\n\t${d}";
			}
			addDiffMessage(msg);
		}
		return areDifferent;
	}
}
