package app.websearch;

import org.custommonkey.xmlunit.*
import org.junit.*

import app.websearch.rpc.helper.XmlRpcHelper
import app.websearch.xml.helper.IgnoreNamedElementsDifferenceListener

//	WhatOffice	 Array Office ID Numbers
//	WhatAgent	 Array Agent ID Numbers
//	WhatType	 Array Residential, Vacant Land, Condominium
//	WhatDistrict Array Use district

class WebsearchOpenHouseTest extends XMLTestCase {
	final static def WS='http://websearch.ramidx.com/smartframe/ramxml.php'
	//final static def METHOD ='<methodCall><methodName>short</methodName><params><param><value><struct><member><name>dbid</name><value><string>dbid1139259934</string></value></member><member><name>WhatPropType</name><value><array><data><value><string>Residential</string></value></data></array></value></member></struct></value></param></params></methodCall>';
	final static def METHOD = 'openhouse'
	static final String SRC_TEST_RESOURCES = 'src/test/resources/'

	static final String TIME_TO_RUN_DOUBLE = '/methodResponse[1]/params[1]/param[1]/value[1]/array[1]/data[1]/value[2]/double[1]/text()[1]'
	static final String DBID = 'dbid1139259934'//dbid1142571627
	//un-comment this to skip xml compare
	//final boolean SKIP_CMP

	
	def curl = []

	//	@Before
	void setUp(){
		curl = [
			'curl',
			'-H',
			'Cookie: WebSearchID=r2so30lip7g5rp8ibonnej9ha2, D_SID=74.123.217.146:3rK08PPRKVcCOWmibSyxVs9T5+e0ovpplUhtdFEsY+4; D_PID=3119DF0B-3C06-308A-88B4-6118E4B86D16; D_IID=0B90D182-B361-3247-BBB8-F4E25374A8DD; D_UID=2DF8F0F0-54E8-3AC8-9B9F-566FA9407FDA; D_HID=D/r38/ISyXBwlPemp21MktzpUBkVEfdJwsLQeFu/JaM',
			'-H',
			'Content-Type:text/xml',
			'-H',
			'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0',
			//'data',
			WS
		]
		super.setUp();
	}

	//@After
	void tearDown() {
		curl = null;
		super.tearDown();
	}

	void test_type_condominium() {
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':['Condominium']]))
		def actual = curl.execute().text
		
		println actual
		compareXml(actual)
	}

	void test_type_land() {
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':['Vacant Land']]))
		def actual = curl.execute().text

		println actual
		compareXml(actual)
	}

	public void test_type_vacant_office() throws Exception{
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':['Vacant Land','Residential'], 'WhatOffice':['8700', '9913']]))
		def actual = curl.execute().text

		println actual
		compareXml(actual)
	}

	public void test_type_residential() throws Exception{
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':['Residential']]))
		def actual = curl.execute().text

		println actual
		compareXml(actual)
	}

	void test_property_office(){
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':['Land'], 'WhatOffice':['8395', '358235']]))
		def actual = curl.execute().text

		println actual
		compareXml(actual)
	}

	void test_office(){
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatOffice':['8700', '9913']]))
		def actual = curl.execute().text

		println actual
		compareXml(actual)

	}
	
	void test_agent(){
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatAgent':['11156','22052',]]))
		def actual = curl.execute().text

		println actual
		compareXml(actual)
	}

	void test_district(){
		println XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatDistrict':['Kihei']])
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatDistrict':['Kihei']]))
		def actual = curl.execute().text

		println actual
		compareXml(actual)
	}

	void test_office_agent_type_district(){
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatOffice':['8395'],'WhatAgent':['11156','22052'],'WhatPropType':['Land'],'WhatDistrict':['Kihei']]))
		def actual = curl.execute().text

		println actual
		compareXml(actual)
		
	}

	private compareXml(String actual, String control = null) {
		//create control file with the name similar to the test
		def test = getName().replaceFirst('test_','')
		//check if xml compare is enabled
		if(this.hasProperty( 'SKIP_CMP' )){println '~~~~> Skipped XML check for similarity';return}
		println '------> Checking XML for similarity'
		saveToFileIfNotExist(SRC_TEST_RESOURCES + "$METHOD/$test"+".xml", actual)
		String expectedXml = new File(SRC_TEST_RESOURCES + "$METHOD/$test"+".xml").getText()
		DifferenceListener listener = new IgnoreNamedElementsDifferenceListener(TIME_TO_RUN_DOUBLE);
		Diff diff = new Diff(expectedXml, actual);
		diff.overrideDifferenceListener(listener);
		assert diff.similar()
		assert diff.identical()
	}

	private void saveToFileIfNotExist(String fileName, String response) {
		if(! new File(fileName).exists()){
			saveToFile(fileName, response)
			fail("Control File Created, re-run the test")
		}
	}

	private saveToFile(String fileName, String response) {
		def w = new File(fileName).newWriter()
		w<<response
		w.close()
	}

}
