package sy.dao.base;

public interface ShardingSqlSession {
	
	<T> T selectOne(Object shardingKey, String statement, Object parameter);

}
