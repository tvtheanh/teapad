package org.tea.saleman.repository;

import java.util.List;
import org.tea.saleman.domain.Giveaway;

public interface GiveawayRepository {
	List<Giveaway> listAll();
	Giveaway findById(int id);
	Giveaway add(Giveaway newGiveaway);
	Giveaway update(int id, Giveaway updatedGiveaway);
	public void delete(int id);
}
