package app.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import app.service.odata.RamoService
import app.websearch.rpc.helper.XmlRpcHelper
import app.websearch.transformer.TransformationService

import com.google.gson.Gson


@RestController
class WebsearchController {
	static final String DBID ='dbid'
	
	@Autowired
	RamoService oService
	@Autowired
	TransformationService transformer

	@RequestMapping("/")
	def websearch() {
		[message: "Hello World"]
	}

	@RequestMapping(value="/websearch/{name}", method = RequestMethod.POST)// consumes="application/json")
	def handleWebsearch(@RequestBody String xml, @PathVariable String name) {
		def responseMap = [:]
		// transformer =  new XmlRpcToOdataParameterTransformer()
		Map serviceParams = generateServiceParameterMap(xml)
		if(name.equals('lists')){
			//			service.collection = COLLECTION_LIST
			//			wsResponseData = service.findOne(serviceParams)
			//			method = serviceParams.get(METHOD_NAME_KEY).capitalize()
		}else{
			responseMap = executeSearch(serviceParams)
		}
		XmlRpcHelper.toXmlResponse([responseMap.cnt1,responseMap.top, responseMap.cnt2, responseMap.total, responseMap.data])
	}

	protected Map executeSearch( Map serviceParams) {
		def odataParams = transformer.fromXmlrpcToOdata(serviceParams)
		def start = Date.getMillisOf(new Date())
		def json = oService.execute(odataParams)
		double total = (Date.getMillisOf(new Date()) - start)/1000 //seconds
		int cnt1 = 655L
		double cnt2 = 654
		return transformer.transform(json).plus(['total':total,'cnt1':cnt1,'cnt2':cnt2,'top':oService.top.toLong()])
	}


	protected Map generateServiceParameterMap(String xmlRpc) {
		def xmlRpcMethodParams = XmlRpcHelper.xmlRpcToCollection(xmlRpc)
		xmlRpcMethodParams.remove(DBID)
		return xmlRpcMethodParams
	}

}
