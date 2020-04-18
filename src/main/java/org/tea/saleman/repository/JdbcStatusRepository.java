package org.tea.saleman.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.tea.saleman.domain.Status;

@Repository
public class JdbcStatusRepository implements StatusRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcStatusRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Status> listAll() {
		final String SELECT_ALL_STATUSES = "SELECT value, display, description FROM status";
		return jdbcTemplate.query(SELECT_ALL_STATUSES, new StatusMapper());
	}
	
	private static final class StatusMapper implements RowMapper<Status> {

		@Override
		public Status mapRow(ResultSet rs, int rowNum) throws SQLException {
			Status status = new Status();
			status.setValue(rs.getInt("value"));
			status.setDisplay(rs.getString("display"));
			status.setDescription(rs.getString("description"));
			return status;
		}
		
	}
}
