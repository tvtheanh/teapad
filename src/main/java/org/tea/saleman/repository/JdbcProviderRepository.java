package org.tea.saleman.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.tea.saleman.domain.Provider;

@Repository
public class JdbcProviderRepository implements ProviderRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcProviderRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Provider> listAll() {
		final String SELECT_ALL_PROVIDERS =
				"SELECT id, name FROM provider WHERE del=false";
		
		return jdbcTemplate.query(SELECT_ALL_PROVIDERS, 
				this::mapRowToProvider);
	}


	private Provider mapRowToProvider(ResultSet rs, int rowNum) throws SQLException {
		Provider provider = new Provider();
		provider.setId(rs.getInt("id"));
		provider.setName(rs.getString("name"));
		
		return provider;
	}
		
	
	@Override
	public Provider findById(int id) {
		final String SELECT_PROVIDER_BY_ID = 
				"SELECT id, name FROM provider WHERE id=? AND del=false ";
		
		return jdbcTemplate.queryForObject(SELECT_PROVIDER_BY_ID, 
				this::mapRowToProvider, id);
	}

	@Override
	public Provider add(Provider newProvider) {
		final String INSERT_NEW_PROVIDER = 
				"INSERT INTO provider (name) VALUES (?)";
		jdbcTemplate.update(INSERT_NEW_PROVIDER,
				newProvider.getName());
		
		return newProvider;
	}

	@Override
	public Provider update(int id, Provider provider) {
		final String UPDATE_PROVIDER =
				"UPDATE provider SET name=? WHERE id=?";
		jdbcTemplate.update(UPDATE_PROVIDER,
				provider.getName(), provider.getId());
		
		return provider;
	}
	
	@Override
	public void delete(int id) {
		final String DELETE_PROVIDER =
				"UPDATE provider SET del=true WHERE id=?";
		jdbcTemplate.update(DELETE_PROVIDER, id);
	}
	
}
