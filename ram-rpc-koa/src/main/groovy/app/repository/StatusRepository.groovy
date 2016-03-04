package app.repository

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service

import app.beans.Status;

@Service
class StatusRepository {

	private final static String ALL_ELEMENTS_SQL =
	"SELECT * FROM Enum_Status";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<Object,Status> cache;

	@PostConstruct
	public init () {
		cache = new HashMap<>();
		List rows = jdbcTemplate.queryForList(ALL_ELEMENTS_SQL);
		for (Map row : rows) {
			Status elem = new Status();
			elem.setId(row.get("Id"));
			elem.setName(row.get("Name"));
			cache.put(elem.getId(), elem);
		}
	}

	public Status findStatus (Object id) {
		return cache.get(id);
	}

	public List<Status> listStatuss () {
		List list = [];
		for (Status e : cache.values()) {
			list.add(e);
		}
		return list;
	}
}
