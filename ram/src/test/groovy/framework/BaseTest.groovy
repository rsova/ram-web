package framework;

import org.apache.olingo.client.core.v4.ODataClientImpl
import org.custommonkey.xmlunit.Diff
import org.custommonkey.xmlunit.DifferenceListener
import org.custommonkey.xmlunit.XMLTestCase
import org.custommonkey.xmlunit.XMLUnit

import app.service.mongo.MongoService
import app.service.odata.RamOdataService
import app.service.odata.resolver.MetadataResolver
import app.websearch.xml.helper.IgnoreNamedElementsDifferenceListener

class BaseTest  extends XMLTestCase {
	final static def WS='http://websearch.ramidx.com/smartframe/ramxml.php'
	static final String SRC_TEST_RESOURCES = './src/test/resources/'
	static final String TIME_TO_RUN_DOUBLE = '/methodResponse[1]/params[1]/param[1]/value[1]/array[1]/data[1]/value[2]/double[1]/text()[1]'
	static final String DBID = 'dbid1139259934'//dbid1142571627
	def service

	//un-comment this to skip xml compare
	//final boolean SKIP_CMP
	def public websearch_raidx = []

	//	@Before
	void setUp(){
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreWhitespace(true);
		//XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true);

		websearch_raidx = [
			'curl',
			'-H','Cookie: WebSearchID=r2so30lip7g5rp8ibonnej9ha2, D_SID=74.123.217.146:3rK08PPRKVcCOWmibSyxVs9T5+e0ovpplUhtdFEsY+4; D_PID=3119DF0B-3C06-308A-88B4-6118E4B86D16; D_IID=0B90D182-B361-3247-BBB8-F4E25374A8DD; D_UID=2DF8F0F0-54E8-3AC8-9B9F-566FA9407FDA; D_HID=D/r38/ISyXBwlPemp21MktzpUBkVEfdJwsLQeFu/JaM',
			'-H','Content-Type:text/xml',
			'-H','User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0',
			//'data',
			WS
		]
		
		 //service = getKoaService()
		 service = new RamOdataService()
		 service.resolver = new MetadataResolver()
		 service.v4Client = new ODataClientImpl()
 
		
	}

	//@After
	void tearDown() {
		websearch_raidx = null
		servicre = null
	}

	protected compareXml(String actual, String control) {
		//create control file with the name similar to the test
		//def test = getName().replaceFirst('test_','')
		//check if xml compare is enabled
		if(this.hasProperty( 'SKIP_CMP' )){println '~~~~> Skipped XML check for similarity';return}
		println '------> Checking XML for similarity'
		//saveToFileIfNotExist(SRC_TEST_RESOURCES + "$METHOD/$test"+".xml", actual)
		//String expectedXml = new File(SRC_TEST_RESOURCES + "$METHOD/$test"+".xml").getText()
		String expectedXml = control
		DifferenceListener listener = new IgnoreNamedElementsDifferenceListener(TIME_TO_RUN_DOUBLE);
		Diff diff = new Diff(expectedXml, actual);
		diff.overrideDifferenceListener(listener);
		assert diff.similar()
		assert diff.identical()
	}

	protected void saveToFileIfNotExist(String fileName, String response) {
		if(! new File(fileName).exists()){
			saveToFile(fileName, response)
			fail("Control File Created, re-run the test")
		}
	}

	protected saveToFile(String fileName, String response) {
		def w = new File(fileName).newWriter()
		w<<response
		w.close()
	}

	protected MongoService getKoaService() {
//		ConfigObject config = new ConfigSlurper().parse(new File("./src/ratpack/Config.groovy").getText()).app
//		MongoClient client = new MongoClient(config.mongo.host as String, config.mongo.port)
//		DB db = client.getDB(config.mongo.db)
//		db.authenticate(config.mongo.user, config.mongo.pass as char[])
//		MongoService service = new MongoService()
//		service.db = db
//		service.collection = 'websearch'
		return null
	}
}
