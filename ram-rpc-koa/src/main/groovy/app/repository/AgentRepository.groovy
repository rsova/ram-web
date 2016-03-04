package app.repository

import javax.annotation.PostConstruct

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

import app.beans.Agent
import app.beans.AgentType
import app.beans.Office
import app.beans.OfficeType
import app.beans.Phone

// TODO normalize
@Service
class AgentRepository {
	
//	private final static String ALL_ELEMENTS_SQL = "SELECT * FROM Agent ORDER BY LastName ASC";
	private final static String ALL_ELEMENTS_SQL = "SELECT * FROM Agent where Active = 'true' ORDER BY LastName ASC";

	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AgentTypeRepository typeRepo;
	
	@Autowired 
	private OfficeTypeRepository officeTypeRepo;
	
	private Map<Object,Office> byIdentifier;
	
	private Map<Object,Office> byUserCode;
	
	
	@PostConstruct
	public init () {
		byIdentifier = new HashMap<>();
		byUserCode = new HashMap<>();
		List<Agent> list = listAgents();
		for (Agent a : list) {
			byIdentifier.put(a.getIdentifier(), a);	
			byUserCode.put(a.getUserCode(), a);
		}
	}
	
	public List<Agent> listAgents () {
		List agents = [];
		List rows = jdbcTemplate.queryForList(ALL_ELEMENTS_SQL);
		for (Map row : rows) {
			Agent agent = new Agent();
			agent.setIdentifier(row.get("Identifier"));
			agent.setActive(row.get("Active"));
			agent.setEmail(row.get("Email"));
			agent.setFirstName(row.get("FirstName"));
			agent.setFullName(row.get("FullName"));
			agent.setLastName(row.get("LastName"));
			agent.setMiddleInitial(row.get("MiddleInitial"));
			agent.setPhone1Description(row.get("Phone1Description"));
			agent.setPhone1Number(row.get("Phone1Number"));
			agent.setPhone2Description(row.get("Phone2Description"));
			agent.setPhone2Number(row.get("Phone2Number"));
			agent.setSalutation("Salutation");
			
			AgentType type = typeRepo.findAgentType(row.get("AgentType"));
			agent.setType(type);
			
			agent.setUrl("Url");
			
			Office office = new Office();
			office.setIdentifier(row.get("OfficeId"));
			office.setAbbreviation(row.get("OfficeAbbreviation"));
			office.setEmail(row.get("OfficeEmail"));
			office.setName(row.get("OfficeName"));
			office.setUrl(row.get("OfficeUrl"));
			
			Phone op = new Phone();
			op.setDescription(row.get("OfficePhone1Description"));
			op.setNumber(row.get("OfficePhone1Number"));
			op.setExtension(row.get("OfficePhone1Extension"));
			office.setPhone1(op);
			
			OfficeType officeType = officeTypeRepo.findOfficeType(row.get("OfficeId"));
			office.setType(officeType);
			agent.setOffice(office);
			
			agent.setDateAdded(row.get("DateAdded"));
			agent.setHash(row.get("Hash"));
			agent.setUpdateDate(row.get("UpdateDate"));
			agent.setPreferredPhone(row.get("PreferredPhone"));
			agent.setUserCode(row.get("UserCode"));
			
			agents.add(agent);
		}
		
		return agents;
	}
	
	public Agent findAgent (Object id) {
		Agent a = byIdentifier.get(id);
		return a;
	}
	
	public Agent findAgentByUserCode (Object userCode) {
		Agent a = byUserCode.get(userCode);
		return a;
	}

}
