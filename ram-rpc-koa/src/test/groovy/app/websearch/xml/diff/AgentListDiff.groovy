package app.websearch.xml.diff

import org.apache.commons.lang.StringEscapeUtils

import com.sun.xml.internal.ws.util.StringUtils;

import redstone.xmlrpc.XmlRpcArray
import app.websearch.xml.diff.dataelements.AgentElement

public class AgentListDiff extends GetListsResponseDiff {
	
	private static final Integer DATA_INDEX = 2;
	
	private boolean areDifferent;
	
	public AgentListDiff() {
		super(DATA_INDEX);
	}
	
	@Override
	protected boolean diffData (XmlRpcArray newArray, XmlRpcArray oldArray) {	
		areDifferent = false;
		XmlRpcArray newData = newArray.getArray(getDataIndex());
		XmlRpcArray oldData = oldArray.getArray(getDataIndex());
				
		List<AgentElement> newSet = new ArrayList<>(newData.size());
		for (def d : newData) {
			if (d instanceof XmlRpcArray) {
				AgentElement e = new AgentElement(d);
				newSet.add(e);
			}
		}
		
		List<AgentElement> oldSet = new ArrayList<>(oldData.size());
		for (def d : oldData) {
			if (d instanceof XmlRpcArray) {
				AgentElement e = new AgentElement(d);
				oldSet.add(e);
			}
		}
		
		diffAttributes(newSet, oldSet);
		setDiff(newSet, oldSet);

		return areDifferent;
	}
	
	private void diffAttributes (List<AgentElement> newSet, List<AgentElement> oldSet) {
		Map<String,List<AgentElement>> indexByCode = new HashMap();
		for (AgentElement e : oldSet) {
			List<AgentElement> agents = indexByCode.get(e.code);
			if (agents == null) {
				agents = new ArrayList<>();
				indexByCode.put(e.code, agents);
			}
			agents.add(e);
		}
		
		Map<String,List<AgentElement>> indexByName = new HashMap();
		for (AgentElement e : oldSet) {
			List<AgentElement> agents = indexByName.get(e.name);
			if (agents == null) {
				agents = new ArrayList<>();
				indexByName.put(e.name, agents);
			}
			agents.add(e);
		}
		
		// For each agent of new set ...
		for (AgentElement x : newSet) {
			boolean matchFound = diffByCode(indexByCode, x);
			// If match is already found, then skip name search
			if (!matchFound) {
				matchFound = diffByName(indexByName, x);
			}
		}
	}
	
	private boolean diffByCode (Map<String,List<AgentElement>> indexByCode, AgentElement x) {
		boolean matchFound = false;
		// If the agent's code is the empty string, then skip
		if (!x.code.equals("")) {
			List<AgentElement> matches = indexByCode.get(x.code);
			if (matches && matches.size() > 0) {
				matchFound = true;
				for (AgentElement y : matches) {
					// We unescape XML first, in case one service used name to escape and other service used unicode point code
					String unescapedX = StringEscapeUtils.unescapeXml(x.name);
					String unescapedY = StringEscapeUtils.unescapeXml(y.name);
					if (!unescapedX.equals(unescapedY)) {
						areDifferent = true;
						addDiffMessage("ERROR: Agents have same code '${x.code}' but different names; NEW: '${x.name}' vs OLD: '${y.name}'!");
					}
				}
			}
		}
		return matchFound;
	}
	
	private boolean diffByName (Map<String,List<AgentElement>> indexByName, AgentElement x) {
		boolean matchFound = false;
		// If the agent's name is the empty string, then skip
		if (!x.name.equals("")) {
			List<AgentElement> matches = indexByName.get(x.name);
			if (matches && matches.size() > 0) {
				matchFound = true;
				for (AgentElement y : matches) {
					if (!x.code.equals(y.code)) {
						areDifferent = true;
						addDiffMessage("ERROR: Agents have same name '${x.name}' but different codes; NEW: '${x.code}' vs OLD: '${y.code}'!");
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
	private void setDiff (List<AgentElement> newSet, List<AgentElement> oldSet) {
		List<AgentElement> diffs = diffElements(newSet, oldSet);
		if (!diffs.isEmpty()) {
			areDifferent = true;
			addDiffMessage("ERROR: NEW agents '${diffs}' were not found in OLD feed!");
		}
		
		diffs = diffElements(oldSet, newSet);
		if (!diffs.isEmpty()) {
			areDifferent = true;
			addDiffMessage("ERROR: OLD agents '${diffs}' were not found in NEW feed!");
		}
	}
	
	private List<AgentElement> diffElements (List<AgentElement> set1, List<AgentElement> set2) {
		List<AgentElement> diffs = new ArrayList<>();
		
		Map<String,List<AgentElement>> indexByCode = new HashMap();
		for (AgentElement e : set2) {
			List<AgentElement> agents = indexByCode.get(e.code);
			if (agents == null) {
				agents = new ArrayList<>();
				indexByCode.put(e.code, agents);
			}
			agents.add(e);
		}
		
		Map<String,List<AgentElement>> indexByName = new HashMap();
		for (AgentElement e : set2) {
			String unescapedName = StringEscapeUtils.unescapeXml(e.name);
			List<AgentElement> agents = indexByName.get(unescapedName);
			if (agents == null) {
				agents = new ArrayList<>();
				indexByName.put(unescapedName, agents);
			}
			agents.add(e);
		}
		
		
		// For each agent of set1 ...
		for (AgentElement x : set1) {
			boolean matchFound = false;
			// If the agent's code is the empty string, then skip
			if (!x.code.equals("")) {
				List<AgentElement> matches = indexByCode.get(x.code);
				if (matches && matches.size() > 0) {
					matchFound = true;
				}
			}
			if (!matchFound) {
				// If the agent's name is the empty string, then skip
				if (!x.name.equals("")) {
					String unescapedName = StringEscapeUtils.unescapeXml(x.name);
					List<AgentElement> matches = indexByName.get(unescapedName);
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
