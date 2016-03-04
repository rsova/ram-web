package app.websearch.xml.diff

import app.websearch.xml.diff.dataelements.SoldElement
import app.websearch.xml.diff.dataelements.SoldResidentialElement
import redstone.xmlrpc.XmlRpcStruct
import static app.websearch.xml.diff.dataelements.SoldElement.*;
import static app.websearch.xml.diff.dataelements.SoldResidentialElement.*;

class SoldResidentialDiff extends SoldResponseDiff {

	public SoldResidentialDiff() {
		super();
	}

	@Override
	protected boolean diffByCode(Map<String, List<SoldElement>> indexByCode, SoldElement x) {
		boolean areDifferent = super.diffByCode(indexByCode, x);
		// If the code is the empty string, then skip
		if (!x.mls.equals("")) {
			SoldResidentialElement cast = (SoldResidentialElement) x;
			List<SoldElement> matches = indexByCode.get(cast.mls);
			if (matches && matches.size() > 0) {
				for (SoldResidentialElement y : matches) {
					List<String> diffsForElem = new ArrayList<>();
					
					if (cast.ohanaBeds != y.ohanaBeds) {
						String msg = craftDiffAttrMsg(ATTR_OHANA_BEDS, cast.ohanaBeds, y.ohanaBeds);
						diffsForElem.add(msg);
					}
					if (cast.ohanaBaths != y.ohanaBaths) {
						String msg = craftDiffAttrMsg(ATTR_OHANA_BATHS, cast.ohanaBaths, y.ohanaBaths);
						diffsForElem.add(msg);
					}
					if (cast.ohanaLivSQFT != y.ohanaLivSQFT) {
						String msg = craftDiffAttrMsg(ATTR_OHANA_LIV_SQFT, cast.ohanaLivSQFT, y.ohanaLivSQFT);
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
		return new SoldResidentialElement(struct);
	}

	
	
}
