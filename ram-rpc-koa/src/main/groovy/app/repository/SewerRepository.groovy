package app.repository

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import app.beans.Sewer;

@Service
class SewerRepository {

	private final static String ALL_ELEMENTS_SQL =
	"SELECT * FROM Enum_Sewer";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<Object,Sewer> cache;

	@PostConstruct
	public init () {
		cache = new HashMap<>();
		List rows = jdbcTemplate.queryForList(ALL_ELEMENTS_SQL);
		for (Map row : rows) {
			Sewer elem = new Sewer();
			elem.setId(row.get("Id"));
			elem.setName(row.get("Name"));
			cache.put(elem.getId(), elem);
		}
	}

	public Sewer findSewer (Object id) {
		return cache.get(id);
	}

	public List<Sewer> listSewers () {
		List list = [];
		for (Sewer e : cache.values()) {
			list.add(e);
		}
		return list;
	}
}
