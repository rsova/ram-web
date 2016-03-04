package app.repository

import javax.annotation.PostConstruct

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

import app.beans.LandTenure

@Service
class LandTenureRepository {
	
	private final static String ALL_ELEMENTS_SQL =
		"SELECT * FROM Enum_LandTenure";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<Object,LandTenure> cache;

	@PostConstruct
	public init () {
		cache = new HashMap<>();
		List rows = jdbcTemplate.queryForList(ALL_ELEMENTS_SQL);
		for (Map row : rows) {
			LandTenure type = new LandTenure();
			type.setId(row.get("Id"));
			type.setName(row.get("Name"));
			cache.put(type.getId(), type);
		}
	}

	public LandTenure findLandTenure (Object id) {
		return cache.get(id);
	}

}
