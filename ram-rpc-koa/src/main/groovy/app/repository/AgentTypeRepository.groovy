package app.repository

import javax.annotation.PostConstruct

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

import app.beans.AgentType

@Service
class AgentTypeRepository {
	
	private final static Map<Integer,String> STATIC_LIST = [
		0: 'REALTOR',
		1: 'REALTORASSOCIATE',
		2: 'DESIGNATEDREALTOR',
		3: 'APPRAISERS',
		4: 'AFFILIATE',
		5: 'SECRETARY',
		6: 'NONMEMBER',
		7: 'ADMINISTRATOR',
		8: 'LOCALBDSGENPUBLIC',
		9: 'REALTORSALESPERSON',
		10: 'REALTORBROKER',
		11: 'RETSVendor User'
		
	];

	private final static String ALL_ELEMENTS_SQL = 
		"SELECT * FROM Enum_AgentType";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<Object,AgentType> cache;

	@PostConstruct
	public init () {
		cache = new HashMap<>();
		List rows = jdbcTemplate.queryForList(ALL_ELEMENTS_SQL);
		for (Map row : rows) {
			AgentType type = new AgentType();
			type.setId(row.get("Id"));
			type.setName(row.get("Name"));
			cache.put(type.getId(), type);
		}
	}

	public AgentType findAgentType (Object id) {
		return cache.get(id);
	}

}
