package app.websearch.xml.diff

import java.util.List;

import app.websearch.xml.diff.dataelements.AgentElement;
import app.websearch.xml.diff.dataelements.DataElement;
import redstone.xmlrpc.XmlRpcArray;

abstract class GetListsResponseDiff extends XmlRpcArrayDiff {
	
	private int listNameIndex;

	public GetListsResponseDiff (Integer dataIndex) {
		super(dataIndex);
		listNameIndex = 0;
	}

	protected boolean diffFeedName (XmlRpcArray newArray, XmlRpcArray oldArray) {
		boolean areDifferent = false;
		String newName = newArray.getString(listNameIndex);
		String oldName = oldArray.getString(listNameIndex);
		if (!newName.equals(oldName)) {
			areDifferent = true;
			addDiffMessage("ERROR: List name comparision failed! New list name is '${newName}' and old is '${oldName}'");
		}
		return areDifferent;
	}
	
	@Override
	public boolean diff (XmlRpcArray newArray, XmlRpcArray oldArray) {
		boolean areDifferent = false;
		if (diffFeedName(newArray, oldArray)) {
			areDifferent = true;
		}
		if (diffElementCount(newArray, oldArray)) {
			areDifferent = true;
		}
		if (diffData(newArray, oldArray)) {
			areDifferent = true;
		}
		return areDifferent;
	}

	public String getListNameIndex() {
		return listNameIndex;
	}

}
