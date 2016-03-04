package app.websearch.xml.diff

import java.util.List;

import redstone.xmlrpc.XmlRpcArray;

public class DistrictListDiff extends GetListsResponseDiff {
	
	private static final Integer DATA_INDEX = 2;
	
	public DistrictListDiff() {
		super(DATA_INDEX);
	}
	
	@Override
	protected boolean diffData (XmlRpcArray newArray, XmlRpcArray oldArray) {
		boolean areDifferent = false;
		XmlRpcArray newData = newArray.getArray(getDataIndex());
		XmlRpcArray oldData = oldArray.getArray(getDataIndex());
		List<String> diffs = difference(newData, oldData)
		if (!diffs.isEmpty()) {
			areDifferent = true;
			addDiffMessage("ERROR: New districts '${diffs}' were not found in old feed!");
		}
		diffs = difference(oldData, newData)
		if (!diffs.isEmpty()) {
			areDifferent = true;
			addDiffMessage("ERROR: Old districts '${diffs}' were not found in new feed!");
		}
		return areDifferent;
	}
	
	private List<String> difference (XmlRpcArray data1, XmlRpcArray data2) {
		// Add all elements of data2 to a set for easy lookup
		Set<String> set = new HashSet<>();
		for (def d : data2) {
			if (d instanceof String) {
				set.add(d);
			}
		}
		
		List<String> diffs = new ArrayList<>();
		// For each element of data1 ...
		for (def d : data1) {
			if (d instanceof String) {
				// ... If the element is not in data2 ...
				if (!set.contains(d)) {
					// ... Then it is a difference
					diffs.add(d);
				}
			}
		}
		return diffs;
	}

}
