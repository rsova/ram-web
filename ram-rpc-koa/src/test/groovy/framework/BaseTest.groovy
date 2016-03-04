package framework;

import static org.junit.Assert.*
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

import org.custommonkey.xmlunit.Diff
import org.custommonkey.xmlunit.DifferenceListener
import org.custommonkey.xmlunit.XMLTestCase
import org.custommonkey.xmlunit.XMLUnit
import org.junit.After
import org.junit.AfterClass;
import org.junit.Before
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redstone.xmlrpc.XmlRpcArray;
import redstone.xmlrpc.XmlRpcStruct;
import app.RamRpc;
import app.websearch.rpc.helper.XmlRpcHelper
import app.websearch.xml.diff.XmlRpcArrayDiff;
import app.websearch.xml.helper.IgnoreNamedElementsDifferenceListener

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RamRpc.class)
class BaseTest extends XMLTestCase {
	
	static final String SRC_TEST_RESOURCES = './src/test/resources/'
	static final String TIME_TO_RUN_DOUBLE = '/methodResponse[1]/params[1]/param[1]/value[1]/array[1]/data[1]/value[2]/double[1]/text()[1]'
	
//	protected static final def WEBSEARCH_RAMXML_URL = 'http://websearch.ramidx.com/ramxml.php';
	protected static final def WEBSEARCH_RAMXML_URL = 'http://websearch.ramidx.com/smartframe/ramxml.php'
	
	
	protected static final def WEBSEARCH_END_SESSION_URL = 'http://websearch.ramidx.com/index.php?Task=Logout';
	
//	protected static final def DBID = 'dbid1422054536';
//	protected static final def DBID = 'dbid1139259934';
	protected static final def DBID = 'dbid1422054460'; //Mike
//	curl 'http://websearch.ramaui.com/smartframe/search.php?Task=Search&dbid=dbid1142571627&WhatPropType%5b%5d=Condominium&WhatCondo%5b%5d=Grand+Champions&WhatSortType1=ListPrice&WhatSortDirection1=ASC' -H 'Pragma: no-cache' -H 'DNT: 1' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: en-US,en;q=0.8,ru;q=0.6' -H 'Upgrade-Insecure-Requests: 1' -H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8' -H 'Cache-Control: no-cache' -H 'Referer: http://www.itsmaui.com/Search/wmCondos.htm' -H 'Cookie: __utma=76334742.223577015.1456793412.1456871186.1456877736.6; __utmc=76334742; __utmz=76334742.1456793412.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)' -H 'Connection: keep-alive' --compressed
	protected static final def CURL_CMD = 'curl';
	
	protected static def requestHeaders = [
		'Accept': 'text/xml,application/xml',
		'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36',
		'Accept-Language': 'en-US,en;q=0.5',
		'Referer': 'http://websearch.ramidx.com/',
		'Connection': 'close',
		'Cache-Control': 'no-cache'
	]
	
	protected static String sessionCookie;
	
	//un-comment this to skip xml compare
	//final boolean SKIP_CMP
	
	protected def websearch_raidx;
	
	
	@BeforeClass
	public static void beginSession () {
		
		//sessionCookie = "D_HID=7V+hK4rE1MEwih5QfOcOrIjBQB5udaoDAphnyOCodm4; D_IID=D3944FF9-BC4D-3BF6-9872-1F3F25A3E70E; D_PID=1E1F7BE5-C578-31A0-A45A-42D248887EEA; D_SID=66.75.72.214:G+yYY6TqDtJ7dmXVMhvWNTERA9IFyYrKnOtCB19CIpI; D_UID=C2C20332-48C9-3EB6-979B-5ED59A647B01; WebSearchID=nvaqf8ls0d4t0i64vmpjouh515	websearch.ramidx.com"
		sessionCookie = "WebSearchID=cl1nv175sugck09he39fsegks7; D_SID=74.123.217.146:3rK08PPRKVcCOWmibSyxVs9T5+e0ovpplUhtdFEsY+4; RAMID=01gi1v10idj6p6li4qpr4ssm45; D_PID=1E1F7BE5-C578-31A0-A45A-42D248887EEA; D_IID=84BC35A0-17CA-37E9-8A26-230D6C6E2936; D_UID=5C0D6FA3-04F3-359C-A74D-7BCB2D1584BB; D_HID=1TRSRu3Nd1d8Ns0kfOGEFbg6J3B/liDyThOKNoVokHE"
		requestHeaders.put('Cookie', sessionCookie);
		requestHeaders.put('Authorization', 'Basic MjUwOTgtMjpOWU5zZDJi');
		
		println "Begin session ...";
	}

	@Before
	void setUp(){
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreWhitespace(true);
		//XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true);
		
		websearch_raidx = [
			CURL_CMD,
			'-X', 'POST',
			WEBSEARCH_RAMXML_URL
		];
		for (e in requestHeaders) {
			websearch_raidx.add('-H')
			websearch_raidx.add("${e.key}: ${e.value}")
		};	
	}

	@After
	void tearDown() {
		websearch_raidx = null;
	}
	
	@AfterClass
	public static void endSession() {
		def endSessionCmd = [
			CURL_CMD,
			'-X', 'GET',
			WEBSEARCH_END_SESSION_URL
		];
		for (e in requestHeaders) {
			endSessionCmd.add('-H')
			endSessionCmd.add("${e.key}: ${e.value}")
		}
		endSessionCmd.execute();
		
		println "... End session.";
	}

	/* Method to compare 2 xml results
	 * String actual - old xml served by ramidx
	 * String control - new xml served by the app
	 */
	protected compareXml(String oldXml, String newXml, List skipElements = [], Boolean stripImagesYes = false) {
		String[] o = getMlss(oldXml)
		String[] n = getMlss(newXml)
		
		if(stripImagesYes){
			oldXml = stripImages(oldXml)
			newXml = stripImages(newXml)
		}
		
		println "~~~~ OLD XML ~~~~ \n" + oldXml
		println "++++ NEW XML ++++++ \n" + newXml

		def cmpMls = "old: ${o} new : ${n}"
		println cmpMls
		assertArrayEquals(cmpMls,o,n)
		println "MLS numbers Match or absent !: ${cmpMls} "

		if (this.hasProperty( 'SKIP_CMP' )) {
			println '~~~~> Skipped XML check for similarity';
			return
		}
		
		println '------> Checking XML for similarity'
		try{
			saveToFileIfNotExist(SRC_TEST_RESOURCES + "$METHOD/${test.getMethodName().replaceFirst('test_', '')}"+".xml", oldXml)
		}catch (Exception e){
			println "Boom! while saving idx xml. Handled, Exception: " << e.message
		
		}
		
		skipElements.add(TIME_TO_RUN_DOUBLE)
		DifferenceListener listener = new IgnoreNamedElementsDifferenceListener(skipElements);
		
		// new xml is expected.
		Diff diff = new Diff(newXml, oldXml);
		diff.overrideDifferenceListener(listener);
		assert diff.similar()
		assert diff.identical()
		
		// Report ALL the values that are  
		def mv = (( IgnoreNamedElementsDifferenceListener) listener).missingValues
		assertTrue "XML is different some of the elements have missing values : ${mv.toString()}" , mv.isEmpty()
	}

	protected String[] getMlss(String xml){
		def list = []
		def resp = XmlRpcHelper.xmlRpcToCollection(xml)
		resp.each { struct ->
			if (struct instanceof Collection){
				for (el in struct) { 
					if (el instanceof Map) {
						list.add(el.MLS?:el.L_ListingID ) 
					}//blowing up when crap is coming back from the idx
				}
			}
		}
		return  list.toArray()
	}
	
	protected String stripImages(String xml){
		def response = new XmlSlurper().parseText(xml.replace('''<?xml version="1.0" ?>''', ''))
		response.params.param.value.array.data.value*.each{ x ->
			x.array.data.value*.struct.member*.each { y ->
				 if(y.name == 'Image' || y.name == 'Images'){ y.replaceNode {}}
			}
		}
		return XmlUtil.serialize(new StreamingMarkupBuilder().bind { mkp.yield response} )
	}
	
	protected void compareData (XmlRpcArrayDiff differ, String oldXml, String newXml) {
		saveToFile(SRC_TEST_RESOURCES + "$METHOD/${test.getMethodName().replaceFirst('test_', '')}"+".old.xml", oldXml);
		saveToFile(SRC_TEST_RESOURCES + "$METHOD/${test.getMethodName().replaceFirst('test_', '')}"+".new.xml", newXml);
		
		def oldArray = XmlRpcHelper.xmlRpcToCollection(oldXml);
		def newArray = XmlRpcHelper.xmlRpcToCollection(newXml);
		
		def oMls =[]
		oldArray[4].each {entry -> if (entry instanceof Map) {oMls += entry.MLS}}
		def nMls = []
		newArray[4].each {entry -> if (entry instanceof Map) {nMls += entry.MLS}}

		if(oMls != nMls){
			System.err.println 'Results are different '
			System.err.println "Old MSL size ${oMls.size()} : ${oMls}"
			System.err.println "New MSL: size ${nMls.size()} : ${nMls}"
			System.err.println "Not in new : ${oMls - nMls}"
			System.err.println "Not in old : ${nMls - oMls}"
		}
		
		boolean areDifferent = differ.diff(newArray, oldArray);
		String failMsg = createFailMsg(differ.getDiffMessages());
		if (failMsg.size() > 0) {
			System.err.println(failMsg);
		}
		assertFalse(failMsg, areDifferent);
	}

	
	protected void saveToFileIfNotExist(String fileName, String response) {
		if(! new File(fileName).exists()){
			saveToFile(fileName, response)
			//fail("Control File Created, re-run the test")
		}
	}

	protected saveToFile(String fileName, String response) {
		def w = new File(fileName).newWriter()
		w<<response
		w.close()
	}
	
	@Test
	public void checkSessionCookie() {
		def rpcXml = XmlRpcHelper.toXmlRpcRequest('lists',['dbid':DBID,'getlist':'condo'])
		websearch_raidx.add("-d " + rpcXml)
		def response = websearch_raidx.execute().text
		String sessionCookie = requestHeaders['Cookie'];
		String assertFailMsg = "It appears that your session cookie has expired or is invalid: ${sessionCookie}";
		String badResponse = 'The website websearch.ramidx.com requires that all visitors be running JavaScript';
		assertFalse(assertFailMsg, response.contains(badResponse));
	}
	
	@Test
	public void checkDbid () {
		def rpcXml = XmlRpcHelper.toXmlRpcRequest('lists',['dbid':DBID,'getlist':'condo']);
		websearch_raidx.add("-d " + rpcXml);
		def response = websearch_raidx.execute().text;
		String assertFailMsg = "It appears that your dbid is invalid: ${DBID}";
		String badResponse = '<fault>';
		assertFalse(assertFailMsg, response.contains(badResponse));
	}
	
	protected String createFailMsg (String[] diffMessages) {
		StringBuilder builder = new StringBuilder();
		for (String m : diffMessages) {
			builder.append("${m}\n");
		}
		if (builder.size() > 0) {
			// Remove the last newline char
			builder.delete(builder.size()-1,builder.size());
		}
		return builder.toString();
	}
	
}
