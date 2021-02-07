package org.tea.saleman.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.tea.saleman.domain.Giveaway;

@Repository
public class JdbcGiveawayRepository implements GiveawayRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	@Override
	public List<Giveaway> listAll() {
		final String SELECT_ALL_GIVEAWAYS = 
				"SELECT id, givename, givecontent  " + 
				"FROM giveaway  " + 
				"WHERE del=false";
		return jdbcTemplate.query(SELECT_ALL_GIVEAWAYS, 
				new GiveawayMapper());
		
	}
	
	private static final class GiveawayMapper implements RowMapper<Giveaway> {

		@Override
		public Giveaway mapRow(ResultSet rs, int rowNum) throws SQLException {
			Giveaway giveaway = new Giveaway();
			giveaway.setId(rs.getInt("id"));
			giveaway.setGivename(rs.getString("givename"));
			giveaway.setGivecontent(rs.getString("givecontent"));
			return giveaway;
		}
		
	}

	
	@Override
	public Giveaway findById(int id) {
		final String SELECT_GIVEAWAY_BY_ID = 
				"SELECT id, givename, givecontent  " + 
				"FROM giveaway  " + 
				"WHERE id=? AND del=false";
		return jdbcTemplate.queryForObject(SELECT_GIVEAWAY_BY_ID,
				new GiveawayMapper(), id);
	}


	@Override
	public Giveaway add(Giveaway newGiveaway) {
		final String INSERT_NEW_GIVEAWAY = 
				"INSERT INTO GIVEAWAY (givename, givecontent) " + 
				"VALUES (?, ?)";
		jdbcTemplate.update(INSERT_NEW_GIVEAWAY,
				newGiveaway.getGivename(),
				newGiveaway.getGivecontent());
		return newGiveaway;
	}


	@Override
	public Giveaway update(int id, Giveaway updatedGiveaway) {
		final String UPDATE_GIVEAWAY = 
				"UPDATE GIVEAWAY " + 
				"SET givename=?, givecontent=?   " +
				"WHERE id=?";
		jdbcTemplate.update(UPDATE_GIVEAWAY,
				updatedGiveaway.getGivename(),
				updatedGiveaway.getGivecontent(),
				id);
		return updatedGiveaway;
	}


	@Override
	public void delete(int id) {
		final String DELETE_GIVEAWAY =
				"UPDATE GIVEAWAY SET del=true WHERE id=? ";
		jdbcTemplate.update(DELETE_GIVEAWAY, id);
	}



	
}
