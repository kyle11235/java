
package sy.dao.base.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import sy.dao.base.ShardingSqlSession;

public class MybatisDao extends SqlSessionDaoSupport implements ShardingSqlSession{

	public static ThreadLocal<Object> KEYS = new ThreadLocal<Object>();

	@Autowired
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	@Override
	public <T> T selectOne(Object shardingKey, String statement, Object parameter) {
		KEYS.set(shardingKey);
		return super.getSqlSession().selectOne(statement, parameter);
	}

}
