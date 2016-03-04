package app.websearch;

import static org.junit.Assert.*

import org.custommonkey.xmlunit.*
import org.junit.*
import org.junit.rules.TestName;
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import app.RamRpc
import app.controller.WebsearchController
import app.websearch.rpc.helper.XmlRpcHelper
import app.websearch.xml.diff.AgentListDiff;
import app.websearch.xml.diff.CondoListDiff;
import app.websearch.xml.diff.DistrictListDiff;
import app.websearch.xml.diff.OfficeListDiff;
import framework.BaseTest
import groovy.xml.XmlUtil

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RamRpc.class)
class WebsearchListsTest extends BaseTest {
	
	@Autowired
	WebsearchController controller

	final static def METHOD ='lists'
	
	// Used to identify the test name to save the xml from idx in resources
	@Rule public TestName test = new TestName();

	//un-comment this to skip xml compare
	//final boolean SKIP_CMP

	@Test
	public void test_condo() throws Exception{
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'getlist':'condo'])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
		println oldXml

		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
//		println newXml
//		compareXml(oldXml, newXml)
		
		CondoListDiff differ = new CondoListDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}

	@Test
	public void test_district() throws Exception{
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'getlist':'district'])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
//		println oldXml

		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
//		println newXml
//		compareXml(oldXml, newXml)
		
		DistrictListDiff differ = new DistrictListDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}

	@Test
	public void test_agent() throws Exception{
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'getlist':'agent'])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
//		println oldXml

		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
//		println newXml
//		compareXml(oldXml, newXml)
		
		AgentListDiff differ = new AgentListDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}

	@Test
	public void test_office() throws Exception{
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'getlist':'office'])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
//		println oldXml

		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
//		println newXml
//		compareXml(oldXml, newXml,['/methodResponse[1]/params[1]/param[1]/value[1]/array[1]/data[1]/value[3]/array[1]/data[1]/text()[1]'])
	
		OfficeListDiff differ = new OfficeListDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}

}
