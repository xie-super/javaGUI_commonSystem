package scaffolding.gui.dal.config;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * 数据库连接器:工厂方法论模式，Product
 * @author superxie
 */
public interface DatabaseConnector {
    Connection getConnection() throws IOException;
    void closeConnection();
    void closeQuietly(AutoCloseable... closeable);
    <T> boolean insert(T entity);

    // 通用的 update 方法，传入的是实体以及字段名（根据该字段找数据）
    <T> boolean update(T entity, String fieldName);

    /**
     * 通用 select 方法，传入类型实例，以及 where后的限制字段
     * select * from (entity的类名) where fieldName = (entity实例的fieldName值)返回传入类型的链表 (AND...)
     * 若field 为null 则返回表中所有记录
     */
    <T> List<T> select(T entity, String... fieldNames);

    /**
     * 通用 delete 方法，传入类型实例，以及 where后的限制字段
     * delete from (entity的类名) where fieldName = (entity实例的fieldName值)返回传入类型的链表 (AND...)
     * 若field 为null 则返回表中所有记录
     */
    <T> boolean delete(T entity, String fieldName);


}