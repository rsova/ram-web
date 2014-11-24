package app.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.EnableCaching
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import app.service.odata.Info
import app.service.odata.RamOdataService
import app.websearch.rpc.helper.XmlRpcHelper
import app.websearch.transformer.TransformationService


@RestController
class WebsearchController {
	static final String DBID ='dbid'
	@Autowired RamOdataService oService
	@Autowired TransformationService transformer	
	
	@RequestMapping(value="/websearch/{name}", method = RequestMethod.POST)// consumes="application/json")
	def handleWebsearch(@RequestBody String xml, @PathVariable String name) {
		def params = []
		Map serviceParams = generateServiceParameterMap(xml)
		if(name.equals('lists')){ //condo, district, agent, office
			params = executeList(serviceParams)
		}else{
			params = executeSearch(serviceParams)
		}
		XmlRpcHelper.toXmlResponse(params)
	}

	protected List executeSearch( Map serviceParams) {
		def odataParams = transformer.fromXmlrpcToOdata(serviceParams)
		def start = Date.getMillisOf(new Date())
		def json = oService.execute(odataParams)
		double total = (Date.getMillisOf(new Date()) - start)/1000 //seconds
		def properties = transformer.transform(json)
		def data = ['data':[properties, Info.warning]]
		
		int cnt1 = 655
		double cnt2 = 654		
		return [cnt1, oService.top, cnt2, total, data]
	}
	
	protected List executeList( Map serviceParams) {
		def start = Date.getMillisOf(new Date())
		def map = oService.getLists(serviceParams)
		double total = (Date.getMillisOf(new Date()) - start)/1000 //seconds
		return [total, map.type, map.data]
	}

	protected Map generateServiceParameterMap(String xmlRpc) {
		def xmlRpcMethodParams = XmlRpcHelper.xmlRpcToCollection(xmlRpc)
		xmlRpcMethodParams.remove(DBID)
		return xmlRpcMethodParams
	}

	@RequestMapping("/")
	def websearch() {
		[message: "Hello World"]
	}

}
