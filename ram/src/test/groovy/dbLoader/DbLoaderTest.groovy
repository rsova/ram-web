package dbLoader;

import static org.junit.Assert.*
import app.service.mongo.MongoService
import app.websearch.rpc.helper.XmlRpcHelper

import com.mongodb.WriteResult

import framework.BaseTest
import groovyx.gpars.GParsPool

class DbLoaderTest  extends BaseTest {

	final static def METHOD ='lists'
	def service

	//un-comment this to skip xml compare
	//final boolean SKIP_CMP

	//	@Before
	void setUp(){
		 service = getKoaService()	
		 super.setUp()	
	}

	public void test_condo() throws Exception{
		def list = 'condo'
		def wsResponseData = service.findOne(['method':'lists', 'getlist':list])
		def smartWsResponse = XmlRpcHelper.toXmlResponse(['Condo', wsResponseData.total, wsResponseData.data])

		websearch_raidx.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'getlist':list]))
		def currentResponse = websearch_raidx.execute().text

		compareXml(currentResponse, smartWsResponse)
	}

	public void test_district() throws Exception{
		def list = 'district'
		def wsResponseData = getKoaService().findOne(['method':'lists', 'getlist':list])
		def smartWsResponse = XmlRpcHelper.toXmlResponse([ 'District', wsResponseData.total, wsResponseData.data])

		websearch_raidx.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'getlist':list]))
		def currentResponse = websearch_raidx.execute().text

		compareXml(currentResponse, smartWsResponse)
	}

	public void test_agent() throws Exception{
		def list = 'agent'
		def wsResponseData = getKoaService().findOne(['method':'lists', 'getlist':list])
		def smartWsResponse = XmlRpcHelper.toXmlResponse(['Agent',wsResponseData.total, wsResponseData.data])

		websearch_raidx.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'getlist':list]))
		def currentResponse = websearch_raidx.execute().text

		compareXml(currentResponse, smartWsResponse)
	}

	public void test_office() throws Exception{
		def list = 'office'
		def wsResponseData = getKoaService().findOne(['method':'lists', 'getlist':list])
		
		def smartWsResponse = XmlRpcHelper.toXmlResponse(['Office',wsResponseData.total, wsResponseData.data])

		websearch_raidx.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'getlist':list]))
		def currentResponse = websearch_raidx.execute().text

		compareXml(currentResponse, smartWsResponse)
	}

	public void test_loadLists() {

		String list = 'district'
		def wsResponse = service.findOne(['method':'lists', 'getlist':list])
		if(!wsResponse){
			println list <<' success: ' << (fetchAndLoad(service, list).getError() == null)
		}
		setUp()
		list = 'condo'
		wsResponse = service.findOne(['method':'lists', 'getlist':list])
		if(!wsResponse){
			println list <<' success: ' << (fetchAndLoad(service, list.toLowerCase()).getError() == null)
		}
		setUp()

		list = 'agent'
		wsResponse = service.findOne(['method':'lists', 'getlist':list])
		if(!wsResponse){
			println list <<' success: ' << (fetchAndLoad(service, list).getError() == null)
		}
		setUp()

		list = 'office'
		wsResponse = service.findOne(['method':'lists', 'getlist':list])
		if(!wsResponse){
			println list <<' success: ' << (fetchAndLoad(service, list).getError() == null)
		}
	}
	
	
	private WriteResult fetchAndLoad(MongoService koaService, String list) {
		websearch_raidx.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'getlist':list]))
		def currentXmlResponse = websearch_raidx.execute().text
		//println currentXmlResponse

		//Process response into collection
		def methodResponse = new XmlSlurper().parseText(currentXmlResponse)
		def data = methodResponse.params.param.value.array.data.value[2].array.data.value
		
		List<String> collection
		if( data[0].children()?.first()?.name()?.equals('array')){
			def results =  data.array.data.value.collect{node -> node.text() }
			//Parallel processing of collection
			GParsPool.withPool {collection = results.collate(2).eachParallel{}}
		}else{
			collection = data.collect{ node -> node.text() }
		}

		//Save collection into koa database
		return koaService.save(['method':'lists', 'getlist':list, 'data':collection])
	}
	
}
