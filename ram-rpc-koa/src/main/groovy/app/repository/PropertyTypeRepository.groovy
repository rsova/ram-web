package app.repository

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service

import app.beans.PropertyType;

import app.beans.PropertyType;
import app.beans.PropertyType

@Service
class PropertyTypeRepository {
	
	private final static String ALL_ELEMENTS_SQL =
		"SELECT * FROM Enum_PropertyType";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<Object,PropertyType> cache;

	@PostConstruct
	public init () {
		cache = new HashMap<>();
		List rows = jdbcTemplate.queryForList(ALL_ELEMENTS_SQL);
		for (Map row : rows) {
			PropertyType elem = new PropertyType();
			elem.setId(row.get("Id"));
			elem.setName(row.get("Name"));
			cache.put(elem.getId(), elem);
		}
	}

	public PropertyType findPropertyType (Object id) {
		return cache.get(id);
	}
	
	public List<PropertyType> listPropertyTypes () {
		List list = [];
		for (PropertyType e : cache.values()) {
			list.add(e);
		}
		return list;
	}

}
