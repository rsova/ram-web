package app.websearch.xml.diff

import java.util.List;
import java.util.Map;

import redstone.xmlrpc.XmlRpcStruct;
import app.websearch.xml.diff.dataelements.SoldCondoElement
import app.websearch.xml.diff.dataelements.SoldElement;
import static app.websearch.xml.diff.dataelements.SoldCondoElement.*;

class SoldCondoDiff extends SoldResponseDiff {

	public SoldCondoDiff() {
		super();
	}

	@Override
	protected boolean diffByCode(Map<String, List<SoldElement>> indexByCode, SoldElement x) {
		boolean areDifferent = super.diffByCode(indexByCode, x);
		// If the code is the empty string, then skip
		if (!x.mls.equals("")) {
			SoldCondoElement cast = (SoldCondoElement) x;
			List<SoldElement> matches = indexByCode.get(cast.mls);
			if (matches && matches.size() > 0) {
				for (SoldCondoElement y : matches) {
					List<String> diffsForElem = new ArrayList<>();
					
					if (cast.building != y.building) {
						String msg = craftDiffAttrMsg(ATTR_BUILDING, cast.building, y.building);
						diffsForElem.add(msg);
					}
					if (cast.unit != y.unit) {
						String msg = craftDiffAttrMsg(ATTR_UNIT, cast.unit, y.unit);
						diffsForElem.add(msg);
					}
					if (cast.maintFee != y.maintFee) {
						String msg = craftDiffAttrMsg(ATTR_MAINT_FEE, cast.maintFee, y.maintFee);
						diffsForElem.add(msg);
					}

					if (!diffsForElem.isEmpty()) {
						areDifferent = true;
						String msg = "ERROR: Sold properties have same ${ATTR_MLS} '${cast.mls} but different attributes:";
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

	@Override
	protected SoldElement toElement(XmlRpcStruct struct) {
		return new SoldCondoElement(struct);
	}

}
