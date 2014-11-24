package app.websearch;

import static org.junit.Assert.*

import org.custommonkey.xmlunit.*
import org.junit.*

import framework.BaseTest;
import app.service.odata.RamOdataService
import app.websearch.rpc.helper.XmlRpcHelper
import app.websearch.xml.helper.IgnoreNamedElementsDifferenceListener

//final static def METHOD ='<methodCall><methodName>lists</methodName><params><param><value><struct><member><name>dbid</name><value><string>dbid1139259934</string></value></member><member><name>getlist</name><value><string>$TOKEN</string></value></member></struct></value></param></params></methodCall>';
class WebsearchListsTest extends BaseTest {
	
	final static def METHOD ='lists'
	
	//un-comment this to skip xml compare
	//final boolean SKIP_CMP
	
	public void test_condo() throws Exception{
		websearch_raidx.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'getlist':'condo']))		
		def actual = websearch_raidx.execute().text
		println actual
		
		Map map = service.getLists(['getlists':'condo'])
		double total = 123
		String type = map.type
		map =['total':123.0,'type':'Condo', 'data':map.data]
		
		def xmlFromRam = XmlRpcHelper.toXmlResponse([map.type, total, map.data])
		println xmlFromRam

		compareXml(actual, xmlFromRam)
	}
	
	public void test_district() throws Exception{
		
		websearch_raidx.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'getlist':'district']))
		def actual = websearch_raidx.execute().text
		println actual

		Map map = service.getLists(['getlist':'district'])
		double total = 123
		map =['total':123.0,'type':map.type, 'data':map.data]
		
		def xmlFromRam = XmlRpcHelper.toXmlResponse([map.type, total, map.data])
		println xmlFromRam

		compareXml(actual, xmlFromRam)

	}
	
	public void test_agent() throws Exception{
		
		websearch_raidx.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'getlist':'agent']))	
		def actual = websearch_raidx.execute().text
		println actual
		
		compareXml(actual)
	}
	
	public void testOffice() throws Exception{
		
		websearch_raidx.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'getlist':'office']))		
		def actual = websearch_raidx.execute().text
		println actual
		compareXml(actual)
	}
	
}
