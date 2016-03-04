package service;

import static org.junit.Assert.*
import framework.BaseTest

class AppServiceHandlerTest extends BaseTest{
	
	final static def METHOD ='lists'
	static final String KOA_SMARTSEARCH_URL = 'http://localhost:5050/koa/smartsearch/lists'
	
	public void testCompareCondo() throws Exception {
		
		//Compare xmlRpcRequest crafted by the new app matches the current xml. 
		String controlRequestXml = getClass().getClassLoader().getResourceAsStream('requests/lists-condo.xml').getText()		
		def xmlRpc = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'getlist':'condo']) //<-- Create xmlRpc for Condo Request
		assertXMLEqual(controlRequestXml, xmlRpc)
		
		//Call to the current websearch radix service to get list of offices
		websearch_raidx.add("-d " + xmlRpc ) //<---- Post xmlRpc
		def currentResponse = websearch_raidx.execute().text

		//Make the same request to koiIT webservice. It expects same request xml!!!
		def koi_it_smartws = ['curl', KOA_SMARTSEARCH_URL]
		koi_it_smartws.add("-d " + xmlRpc ) //<---- Post xmlRpc
		def smartWsResponse = koi_it_smartws.execute().text
		
		compareXml(currentResponse, smartWsResponse)
	}
	
	
	public void testCompareDistricts() throws Exception {
		//Compare xmlRpcRequest crafted by the new app matches the current xml.
		String controlRequestXml = getClass().getClassLoader().getResourceAsStream('requests/lists-district.xml').getText()
		def xmlRpc = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'getlist':'district']) //<-- Create xmlRpc for District Request
		assertXMLEqual(controlRequestXml, xmlRpc)

		//Call to the current websearch radix service to get list of offices
		websearch_raidx.add("-d " + xmlRpc ) //<---- Post xmlRpc
		def currentResponse = websearch_raidx.execute().text

		//Same request to koiIT webservice. It expects same request xml!!!
		def koi_it_smartws = ['curl',KOA_SMARTSEARCH_URL]
		koi_it_smartws.add("-d " + xmlRpc ) //<---- Post xmlRpc
		def smartWsResponse = koi_it_smartws.execute().text

		compareXml(currentResponse, smartWsResponse)
	}
	
	public void testCompareOffices() throws Exception {
		//Compare xmlRpcRequest crafted by the new app matches the current xml.
		String controlRequestXml = getClass().getClassLoader().getResourceAsStream('requests/lists-office.xml').getText()
		def xmlRpc = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'getlist':'office']) //<-- Create xmlRpc for Office Request
		assertXMLEqual(controlRequestXml, xmlRpc)

		//Call to the current websearch radix service to get list of offices
		websearch_raidx.add("-d " + xmlRpc ) //<---- Post xmlRpc
		def currentResponse = websearch_raidx.execute().text

		//Same request to koiIT webservice. It expects same request xml!!!
		def koi_it_smartws = ['curl',KOA_SMARTSEARCH_URL]
		koi_it_smartws.add("-d " + xmlRpc ) //<---- Post xmlRpc
		def smartWsResponse = koi_it_smartws.execute().text

		compareXml(currentResponse, smartWsResponse)
	}
	
	public void testCompareAgent() throws Exception {
		//Compare xmlRpcRequest crafted by the new app matches the current xml.
		String controlRequestXml = getClass().getClassLoader().getResourceAsStream('requests/lists-agent.xml').getText()
		def xmlRpc = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'getlist':'agent']) //<-- Create xmlRpc for Agent Request
		assertXMLEqual(controlRequestXml, xmlRpc)

		//Call to the current websearch radix service to get list of offices
		websearch_raidx.add("-d " + xmlRpc ) //<---- Post xmlRpc
		def currentResponse = websearch_raidx.execute().text

		//Same request to koiIT webservice. It expects same request xml!!!
		def koi_it_smartws = ['curl',KOA_SMARTSEARCH_URL]
		koi_it_smartws.add("-d " + xmlRpc ) //<---- Post xmlRpc
		def smartWsResponse = koi_it_smartws.execute().text

		compareXml(currentResponse, smartWsResponse)
	}


}
