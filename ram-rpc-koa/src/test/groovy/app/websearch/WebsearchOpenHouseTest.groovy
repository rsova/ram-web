package app.websearch;

//import org.custommonkey.xmlunit.*
import java.util.Map;

import org.junit.*
import org.junit.rules.TestName;
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import app.RamRpc
import app.controller.WebsearchController
import app.websearch.rpc.helper.XmlRpcHelper
import app.websearch.xml.diff.OpenHouseListDiff;
import framework.BaseTest
import groovy.xml.XmlUtil

//	WhatOffice	 Array Office ID Numbers
//	WhatAgent	 Array Agent ID Numbers
//	WhatType	 Array Residential, Vacant Land, Condominium
//	WhatDistrict Array Use district

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RamRpc.class)

class WebsearchOpenHouseTest extends BaseTest {
	
	@Autowired
	WebsearchController controller
	
	// Used to identify the test name to save the xml from idx in resources
	@Rule public TestName test = new TestName();
	
	final static def METHOD = 'openhouse'

	//un-comment this to skip xml compare
	//final boolean SKIP_CMP
	private void openHouseRunner(rpcXml){
		
//				def map = UrlHelper.urlToMap(url)
//				map.put('dbid',DBID)
//		
				println XmlUtil.serialize(rpcXml)
				def oldPath = SRC_TEST_RESOURCES + "$METHOD/${test.getMethodName().replaceFirst('test_', '')}"+".old.xml"
				File oldFile = new File(oldPath)
				def oldXml
				if(!oldFile.exists()){
					websearch_raidx.add("-d " + rpcXml)
					oldXml = websearch_raidx.execute().text
					saveToFile(oldPath, oldXml);
				}else{
					 oldXml = oldFile.text
				}
				
				def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
				
				OpenHouseListDiff differ = new OpenHouseListDiff();
				compareData(differ, oldXml, newXml);
			}
		
	@Test
	public void test_type_residential() throws Exception{
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatType':['Residential']])
		openHouseRunner(rpcXml)
	}

	
	@Test
	void test_office(){
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID, 'WhatOffice':[8700]])
		openHouseRunner(rpcXml)
	}

	@Test
	void test_type_condominium() {
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatType':['Condominium']])
		openHouseRunner(rpcXml)
	}
	
	@Test
	void test_type_land() {
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatType':['Vacant Land']]);
		openHouseRunner(rpcXml)
	}
	
	@Test
	public void test_type_office() throws Exception{
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatType':['Residential'], 'WhatOffice':['8700']])
		openHouseRunner(rpcXml)
	}

	@Test
	void test_agent_office(){
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatOffice':['8700'],'WhatAgent':['11395']])
		openHouseRunner(rpcXml)
	}
	
	@Test
	void test_agent(){
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatAgent':['22052']])
		openHouseRunner(rpcXml)
	}
	
	@Test
	void test_district(){
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatDistrict':['Kihei']])
		openHouseRunner(rpcXml)
	}
	
	@Test
	void test_office_agent_district(){
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatOffice':['8700'],'WhatAgent':['22052'],'WhatDistrict':['Kihei']])
		openHouseRunner(rpcXml)
	}
	
	@Test
	void test_all(){
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
		
		def newXml = controller.handleWebsearch(rpcXml)
		//println newXml
//		compareXml(oldXml, newXml)
		
		OpenHouseListDiff differ = new OpenHouseListDiff();
		compareData(differ, oldXml, newXml);
	}

}
