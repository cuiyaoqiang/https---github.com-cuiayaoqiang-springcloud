package com.bh.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bh.entity.AccountEntity;

/**Spring Data :提供了一整套数据访问层(DAO)的解决方案，致力于减少数据访问层(DAO)的开发量。它使用一个叫作Repository的接口类为基础，它被定义为访问底层数据模型的超级接口。
 * 而对于某种具体的数据访问操作，则在其子接口中定义。 
 * 所有继承这个接口的interface都被spring所管理，此接口作为标识接口，功能就是用来控制domain模型的。 
 * Spring Data可以让我们只定义接口，只要遵循spring data的规范，就无需写实现类。 
 * @author bh
 *
 */
@Repository
public interface AccountDao {

	 /**
     * 新增一个用户
     * @param username
     * @param password
     */
    void create(String username, String password);

    /**
     * 根据name删除一个用户高
     * @param username
     */
    void deleteByName(String username);

    /**
     * 获取用户总量
     */
    List<AccountEntity> getAllAccounts();

    /**
     * 删除所有用户
     */
    void deleteAllAccounts();
}
