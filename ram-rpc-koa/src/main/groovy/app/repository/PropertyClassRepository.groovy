package app.repository

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service

import app.beans.PropertyClass;
import app.beans.PropertyClass

@Service
class PropertyClassRepository {
	
	private final static String ALL_ELEMENTS_SQL =
		"SELECT * FROM Enum_PropertyClass";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<Object,PropertyClass> cache;

	@PostConstruct
	public init () {
		cache = new HashMap<>();
		List rows = jdbcTemplate.queryForList(ALL_ELEMENTS_SQL);
		for (Map row : rows) {
			PropertyClass type = new PropertyClass();
			type.setId(row.get("Id"));
			type.setName(row.get("Name"));
			cache.put(type.getId(), type);
		}
	}

	public PropertyClass findPropertyClass (Object id) {
		return cache.get(id);
	}

}
