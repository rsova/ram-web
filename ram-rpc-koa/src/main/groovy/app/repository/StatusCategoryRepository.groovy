package app.repository

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import app.beans.StatusCategory;

@Service
class StatusCategoryRepository {
	
	private final static String ALL_ELEMENTS_SQL =
	"SELECT * FROM Enum_StatusCategory";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<Object,StatusCategory> cache;

	@PostConstruct
	public init () {
		cache = new HashMap<>();
		List rows = jdbcTemplate.queryForList(ALL_ELEMENTS_SQL);
		for (Map row : rows) {
			StatusCategory elem = new StatusCategory();
			elem.setId(row.get("Id"));
			elem.setName(row.get("Name"));
			cache.put(elem.getId(), elem);
		}
	}

	public StatusCategory findStatusCategory (Object id) {
		return cache.get(id);
	}

	public List<StatusCategory> listStatusCategorys () {
		List list = [];
		for (StatusCategory e : cache.values()) {
			list.add(e);
		}
		return list;
	}

}
