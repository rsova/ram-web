package app.controller

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.EnableCaching
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import redstone.xmlrpc.XmlRpcServlet;
import app.repository.AgentRepository;
import app.service.ListsMethodService;
import app.service.OpenHouseMethodService
import app.service.SearchMethodService
import app.service.ShortMethodService;
import app.service.SoldsMethodService;
import app.service.sqlserver.RamSqlService;
import app.websearch.rpc.helper.XmlRpcHelper;


@RestController
class WebsearchController {
	
	private static final String DBID_PARAM_NAME = 'dbid';
	
	@Autowired 
	private RamSqlService sqlService;
	
	@Autowired 
	private ListsMethodService listsService;
	
	@Autowired
	private OpenHouseMethodService openHouseService;
	
	@Autowired
	private ShortMethodService shortService;
	
	@Autowired
	private SoldsMethodService soldsService;
	
	@Autowired
	private SearchMethodService searchService;
	
	@RequestMapping(value="/smartframe/ramxml.php", method = RequestMethod.POST)
	def handleWebsearch(@RequestBody String xml) {
		def response = [];
		Map serviceParams = generateServiceParameterMap(xml);
		def method = XmlRpcHelper.xmlRpcRequestMethodName(xml);
		switch(method){
			case OpenHouseMethodService.METHOD_NAME:
				response = executeOpenHouse(serviceParams);
				response = openHouseService.openHouse(serviceParams);
				break;
			case SoldsMethodService.METHOD_NAME:
//				response = executeSolds(serviceParams);
				response = soldsService.soldSearch(serviceParams);
				break;
			case ShortMethodService.METHOD_NAME:
//				response = executeShortSearch(serviceParams);	
				response = shortService.shortSearch(serviceParams);
				break;
			case SearchMethodService.METHOD_NAME:
				response = searchService.search(serviceParams);
//				response = executeSearch(serviceParams);
				break;
			case ListsMethodService.METHOD_NAME:
//				response = executeList(serviceParams)
				response = listsService.getList(serviceParams);
				break;
			default:
				break;
		}
		return XmlRpcHelper.toXmlResponse(response)
	}
	
	@Deprecated
	protected List executeSearch( Map serviceParams) {
		return sqlService.search(serviceParams);
	}
	
	@Deprecated
	protected List executeOpenHouse(Map serviceParams){
		return sqlService.openHouse(serviceParams)
	}
	
	@Deprecated
	protected List executeSolds(Map serviceParams){
		return sqlService.solds(serviceParams)
	}
	
	@Deprecated
	protected List executeShortSearch(Map serviceParams){
		return sqlService.shortSearch(serviceParams)
	}
	
	@Deprecated
	protected List executeList( Map serviceParams) {
		def method = serviceParams.'getlist'
		return sqlService.getList(method)
	}

	protected Map generateServiceParameterMap(String xmlRpc) {
		def xmlRpcMethodParams = XmlRpcHelper.xmlRpcToCollection(xmlRpc)
		xmlRpcMethodParams.remove(DBID_PARAM_NAME)
		return xmlRpcMethodParams
	}

	@RequestMapping("/hello")
	def websearch() {
		[message: "I am alive"]
	}
	

}
