package app.repository

import javax.annotation.PostConstruct

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

import app.beans.Broker
import app.beans.Office
import app.beans.OfficeType
import app.beans.Phone

// TODO normalize
@Service
class OfficeRepository {
	
	private final static String ALL_ELEMENTS_SQL = "SELECT * FROM Office";
	
	private final static String GETLIST_SQL = "SELECT * FROM Office ORDER BY MainOfficeName ASC";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private OfficeTypeRepository typeRepo;
	
	private Map<Object,Office> byIdentifier;
	
	private Map<Object,Office> byAbbreviation;
	
	@PostConstruct
	public init () {
		byIdentifier = new HashMap<>();
		byAbbreviation = new HashMap<>();
		List rows = jdbcTemplate.queryForList(ALL_ELEMENTS_SQL);
		for (Map row : rows) {
			Office obj = new Office();
			String id = row.get("Identifier");
			obj.setIdentifier(id);
			obj.setDateAdded(row.get("DateAdded"));
			
			Broker broker = new Broker();
			broker.setIdentifier(row.get("BrokerId"));
			obj.setBroker(broker);
			
			obj.setAbbreviation(row.get("Abbreviation"));
			obj.setEmail(row.get("Email"));
			obj.setName(row.get("Name"));
			
			Phone p1 = new Phone();
			p1.setNumber(row.get("Phone1Number"));
			p1.setDescription(row.get("Phone1Desc"));
			obj.setPhone1(p1);
			
			Phone p2 = new Phone();
			p2.setNumber(row.get("Phone2Number"));
			p2.setDescription(row.get("Phone2Desc"));
			obj.setPhone2(p2);
			
			OfficeType type = typeRepo.findOfficeType(row.get("Type"));
			obj.setType(type);
			
			obj.setUrl(row.get("Url"));
			obj.setUpdateDate(row.get("UpdateDate"));
			
			byIdentifier.put(obj.getIdentifier(), obj);
			byAbbreviation.put(obj.getAbbreviation(), obj);
			
			Office mainOffice = new Office();
			String mOId = row.get("MainOfficeId");
			String mOAb = row.get("MainOfficeAbbreviation")
			mainOffice.setIdentifier(mOId);
			mainOffice.setAbbreviation(mOAb);
			mainOffice.setEmail(row.get("MainOfficeEmail"));
			mainOffice.setName(row.get("MainOfficeName"));
			mainOffice.setState(row.get("MainOfficeState"));
			mainOffice.setUrl(row.get("MainOfficeUrl"));
			
			Phone mOP = new Phone();
			mOP.setNumber(row.get("MainOfficePhoneNumber"));
			mOP.setDescription(row.get("MainOfficePhoneDesc"));
			mainOffice.setPhone1(mOP);
			
			obj.setMainOffice(mainOffice);
			
			if (mOId != id) {
				if (!byIdentifier.containsKey(mOId)) {
					byIdentifier.put(mOId, mainOffice);
					byAbbreviation.put(mOAb, mainOffice);
				}
			}
			
		}
	}
	
	public List<Office> listOffices () {
		List offices = [];
		List rows = jdbcTemplate.queryForList(GETLIST_SQL);
		for (Map row : rows) {
			Office office = new Office();
			office.setAbbreviation(row.get("MainOfficeAbbreviation"));
			office.setName(row.get("MainOfficeName"));
			offices.add(office);
		}
		return offices;
	}
	
	public Office findOffice (Object id) {
		return byIdentifier.get(id);
	}
	
	public Office findOfficeByAbbrev (Object abbrev) {
		return byAbbreviation.get(abbrev);
	}
}
