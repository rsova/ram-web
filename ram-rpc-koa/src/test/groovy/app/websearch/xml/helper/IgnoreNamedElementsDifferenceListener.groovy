package app.websearch.xml.helper
import org.custommonkey.xmlunit.Difference
import org.custommonkey.xmlunit.DifferenceConstants
import org.custommonkey.xmlunit.DifferenceListener
import org.w3c.dom.Node;

public class IgnoreNamedElementsDifferenceListener implements DifferenceListener {
    private Set<String> blackList = new HashSet<String>();
    Set<String> missingValues = [];

	public IgnoreNamedElementsDifferenceListener(List elementNames) {
		for (String name : elementNames) {
			blackList.add(name);
		}
	}

	@Override
	public int differenceFound(Difference difference) {
		
		if (difference.getId() in [DifferenceConstants.HAS_CHILD_NODES_ID]){
			def missingVal =  difference.controlNodeDetail?.node?.parentNode?.parentNode?.firstChild?.firstChild?.nodeValue ?: difference.testNodeDetail?.node?.parentNode?.parentNode?.firstChild?.firstChild?.nodeValue
			System.err.println 'Value is missing in: ' << missingVal
			missingValues.add(missingVal)
			return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL
		}
		
        if (difference.getId() in [DifferenceConstants.TEXT_VALUE_ID, DifferenceConstants.ELEMENT_TAG_NAME_ID ]) {
// 			println difference.controlNodeDetail.xpathLocation
//			println blackList
//			println difference.controlNodeDetail.node?.parentNode?.nodeName
//			println difference.controlNodeDetail?.value
//			println difference.testNodeDetail?.value
			
			if (blackList.contains(difference.controlNodeDetail.xpathLocation)) {
				return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL
			}

			//compare doubles
			if(difference.controlNodeDetail.node.parentNode.nodeName == 'double'){
				def cont = difference.controlNodeDetail?.value?.toDouble()
				def test = difference.testNodeDetail?.value?.toDouble()
				if(test == cont){
					return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL
				}
			}
			
			//some strings contain numeric values, check them as numbers
			if(difference.controlNodeDetail.node.parentNode.nodeName == 'string'){
				if(difference.controlNodeDetail?.value?.isNumber() && difference.testNodeDetail?.value?.isNumber()){
					def cont = difference.controlNodeDetail?.value?.toDouble()		
					def test = difference.testNodeDetail?.value?.toDouble()
					if(test == cont){
						return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL
					}
				}
			}

			
			//check latitude and longitude as a numeric value
			if(difference.controlNodeDetail?.node?.parentNode?.parentNode?.parentNode?.firstChild?.firstChild?.nodeValue in ['Lat','Lon']){
				println "control: ${difference.controlNodeDetail.value}, test: ${difference.testNodeDetail.value} "
				if(difference.controlNodeDetail.value.toDouble() == difference.testNodeDetail.value.toDouble() ){
					return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL
				}
						
			}
			
        }
        return DifferenceListener.RETURN_ACCEPT_DIFFERENCE
	}

	@Override
	public void skippedComparison(Node control, Node test) {
		
	}
 
	
}