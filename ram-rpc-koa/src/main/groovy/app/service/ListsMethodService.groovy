package app.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import app.beans.Agent
import app.beans.Condo
import app.beans.District
import app.beans.Office;
import app.repository.AgentRepository
import app.repository.CondoRepository
import app.repository.DistrictRepository
import app.repository.OfficeRepository
import app.websearch.rpc.helper.XmlRpcHelper

@Service
class ListsMethodService {
	
	public static final String METHOD_NAME = 'lists';
	
	public static final String LIST_NAME_PARAM = 'getlist';
	
	public static final String DISTRICTS_RESPONSE_NAME = 'District';	
	public static final String CONDOS_RESPONSE_NAME    = 'Condo';
	public static final String OFFICES_RESPONSE_NAME   = 'Office';
	public static final String AGENTS_RESPONSE_NAME    = 'Agent';
	
	
	@Autowired
	private AgentRepository agentRepo;
	
	@Autowired
	private DistrictRepository districtRepo;
	
	@Autowired
	private OfficeRepository officeRepo;

	@Autowired
	private CondoRepository condoRepo;
	
	
	public List getList (Map params) {
		String listType = params.get(LIST_NAME_PARAM);
		switch (listType) {
			case 'agent':
				return getAgentList();
				break;
			case 'district':
				return getDistrictList();
				break;
			case 'condo':
				return getCondoList();
				break;
			case 'office':
				return getOfficeList();
				break;
			default:
				return [];
		}
		
	}
	
	protected List getAgentList () {
		long start = Date.getMillisOf(new Date());
		List<Agent> list = agentRepo.listAgents();
		//list = XmlRpcHelper.escapeXml(list);
		//def responseList = list*.values();
		List responseList = [];
		for (Agent a : list) {
			responseList.add([a.getUserCode(),a.getFullName()]);
		}
		double time = (Date.getMillisOf(new Date()) - start);
		return [AGENTS_RESPONSE_NAME, time, responseList];
	}
	
	protected List getDistrictList () {
		long start = Date.getMillisOf(new Date());
		List<District> list = districtRepo.listDistricts();
//		list = XmlRpcHelper.escapeXml(list);
//		def listOfCollections = list*.values();
//		def listOfStrings = listOfCollections.flatten();
		List responseList = [];
		for (District d : list) {
			responseList.add(d.getName());
		}
		double time = (Date.getMillisOf(new Date()) - start);
		return [DISTRICTS_RESPONSE_NAME, time, responseList];
	}
	
	protected List getCondoList () {
		long start = Date.getMillisOf(new Date());
		List<Condo> list = condoRepo.listCondos();
//		list = XmlRpcHelper.escapeXml(list);
//		def listOfCollections = list*.values();
//		def listOfStrings = listOfCollections.flatten();
		List responseList = [];
		for (Condo c : list) {
			responseList.add(c.getName());
		}
		double time = (Date.getMillisOf(new Date()) - start);
		return [CONDOS_RESPONSE_NAME, time, responseList];
	}
	
	protected List getOfficeList () {
		long start = Date.getMillisOf(new Date());
		List<Office> list = officeRepo.listOffices();
//		list = XmlRpcHelper.escapeXml(list);		
//		def responseList = list*.values();
		def responseList = [];
		for (Office o : list) {
			responseList.add([o.getAbbreviation(),o.getName()]);
		}
		double time = (Date.getMillisOf(new Date()) - start);
		return [OFFICES_RESPONSE_NAME, time, responseList];
	}

}
