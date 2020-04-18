package org.tea.saleman.repository;

import java.util.List;

import org.tea.saleman.domain.Status;

public interface StatusRepository {
	List<Status> listAll();
}
