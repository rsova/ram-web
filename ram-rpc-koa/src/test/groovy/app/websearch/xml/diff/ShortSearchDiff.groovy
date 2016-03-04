package app.websearch.xml.diff

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import redstone.xmlrpc.XmlRpcArray;
import redstone.xmlrpc.XmlRpcStruct;
import app.websearch.xml.diff.dataelements.ShortSearchElement;
import static app.websearch.xml.diff.dataelements.ShortSearchElement.*;

public class ShortSearchDiff extends PropertySearchResponseDiff {
	
	private static final Integer DATA_INDEX = 4;
	
	public ShortSearchDiff() {
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
		
		// TODO compare attributes
		if (diffAttributes(newData, oldData, ATTR_MLS)) {
			areDifferent = true;
		}
		
		List<ShortSearchElement> newSet = new ArrayList<>(newData.size());
		for (def d : newData) {
			if (d instanceof XmlRpcStruct) {
				ShortSearchElement e = toElement(d);
				newSet.add(e);
			}
		}
		
		List<ShortSearchElement> oldSet = new ArrayList<>(oldData.size());
		for (def d : oldData) {
			if (d instanceof XmlRpcStruct) {
				ShortSearchElement e = toElement(d);
				oldSet.add(e);
			}
		}
		
		if (setDiff(newSet, oldSet)) {
			areDifferent = true;
		}

		return areDifferent;
	}
	
	protected List<ShortSearchElement> diffElements (List<ShortSearchElement> set1, List<ShortSearchElement> set2) {
		List<ShortSearchElement> diffs = new ArrayList<>();
		
		Map<String,List<ShortSearchElement>> indexByCode = new HashMap();
		for (ShortSearchElement e : set2) {
			List<ShortSearchElement> matches = indexByCode.get(e.mls);
			if (matches == null) {
				matches = new ArrayList<>();
				indexByCode.put(e.mls, matches);
			}
			matches.add(e);
		}
		
		// For each element of set1 ...
		for (ShortSearchElement x : set1) {
			boolean matchFound = false;
			// If the element's code is the empty string, then skip
			if (!x.mls.equals("")) {
				List<ShortSearchElement> matches = indexByCode.get(x.mls);
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
	protected ShortSearchElement toElement (XmlRpcStruct struct) {
		ShortSearchElement agent = new ShortSearchElement(struct);
		return agent;
	}

}
