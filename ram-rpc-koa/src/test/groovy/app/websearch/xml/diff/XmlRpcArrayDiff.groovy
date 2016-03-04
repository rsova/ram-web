package app.websearch.xml.diff

import java.io.PrintStream;
import java.util.List;

import redstone.xmlrpc.XmlRpcArray;

public abstract class XmlRpcArrayDiff {

	private List<String> diffMessages;
	private Integer dataIndex;
	private Integer elementCount;
	
	public XmlRpcArrayDiff (Integer dataIndex) {
		this.dataIndex = dataIndex;
		diffMessages = new ArrayList<>();
		elementCount = 0;
	}
	
	protected void addDiffMessage(String message) {
		diffMessages.add(message);
	};
	
	public abstract boolean diff (XmlRpcArray newArray, XmlRpcArray oldArray);
	
	protected abstract boolean diffData (XmlRpcArray newArray, XmlRpcArray oldArray)

	protected boolean diffElementCount (XmlRpcArray newArray, XmlRpcArray oldArray) {
		boolean areDifferent = false;
		Integer newElCount = 0;
		def newElems = newArray.get(dataIndex);
		// If newElems is an array of values ...
		if (newElems instanceof XmlRpcArray) {
			for (def val : newElems) {
				// If value is not empty (sometimes new services return empty values) ...
				if (!isEmptyVal(val)) {
					// ... Then count it
					newElCount++;
				}
			}
		}
		def oldElems = oldArray.get(dataIndex);
		// If oldElems is an array of values (sometimes old services return empty string instead of expected array) ...
		if (oldElems instanceof XmlRpcArray) {
			for (def val : oldElems) {
				// If value is not empty (sometimes old services return empty values) ...
				if (!isEmptyVal(val)) {
					// ... Then count it
					elementCount++;
				}
			}
		}
		if (!newElCount.equals(elementCount)) {
			areDifferent = true;
			diffMessages.add("ERROR: Feed result count comparision failed! New count is '${newElCount}' and old is '${elementCount}'");
		}
		return areDifferent;
	}
	
	public Integer getDataIndex() {
		return dataIndex;
	}
	
	public String[] getDiffMessages() {
		return diffMessages.toArray();
	}

	public Integer getElementCount() {
		return elementCount;
	}
	
	public boolean isEmptyVal (def val) {
		boolean isEmpty = false;
		if (val == null) {
			isEmpty = true;
		} else if (val instanceof String) {
			if (val.equals("")) {
				isEmpty = true;
			}
		}
		return isEmpty;
	}

	public void setElementCount(Integer elementCount) {
		this.elementCount = elementCount;
	}

}
