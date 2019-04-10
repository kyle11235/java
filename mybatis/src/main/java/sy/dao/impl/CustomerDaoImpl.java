package sy.dao.impl;

import org.springframework.stereotype.Repository;

import sy.dao.CustomerDao;
import sy.dao.base.impl.MybatisDao;
import sy.model.Customer;;

@Repository("customerDao")
public class CustomerDaoImpl extends MybatisDao implements CustomerDao {

    @Override
    public Customer queryById(String id) {
        return super.selectOne(id, "queryById", id);
    }

}

