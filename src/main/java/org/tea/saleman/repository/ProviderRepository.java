package org.tea.saleman.repository;

import java.util.List;

import org.tea.saleman.domain.Provider;

public interface ProviderRepository {
	List<Provider> listAll();
	Provider findById(int id);
	Provider add(Provider newProvider);
	Provider update(int id, Provider provider);
	void delete(int id);
}
