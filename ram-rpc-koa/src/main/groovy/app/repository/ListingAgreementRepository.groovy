package app.repository

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service

import app.beans.ListingAgreement;
import app.beans.ListingAgreement

@Service
class ListingAgreementRepository {

	private final static String LIST_ALL_SQL =
		"SELECT * FROM Enum_ListingAgreement";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<Object,ListingAgreement> cache;

	@PostConstruct
	public init () {
		cache = new HashMap<>();
		List rows = jdbcTemplate.queryForList(LIST_ALL_SQL);
		for (Map row : rows) {
			ListingAgreement type = new ListingAgreement();
			type.setId(row.get("Id"));
			type.setName(row.get("Name"));
			cache.put(type.getId(), type);
		}
	}

	public ListingAgreement findListingAgreement (Object id) {
		return cache.get(id);
	}


}
