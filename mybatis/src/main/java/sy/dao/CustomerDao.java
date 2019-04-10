package sy.dao;

import sy.model.Customer;

public interface CustomerDao {

	public Customer queryById(String id);

}