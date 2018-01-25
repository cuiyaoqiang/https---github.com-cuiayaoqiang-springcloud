package com.bh.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bh.entity.AccountEntity;

/**自定义简单查询
 * @author bh
 */
@Repository
public interface CustomizeDao extends JpaRepository<AccountEntity,Integer>,JpaSpecificationExecutor<AccountEntity>{

	AccountEntity findByPassword(String password);
	List<AccountEntity> findByUsername(String username);
	List<AccountEntity> findByUsernameOrderByIdDesc(String username);
	
	AccountEntity findByUsernameOrPassword(String username,String password);
	void deleteByPassword(String password);
	
	AccountEntity findFirstByOrderByIdDesc();
	
	Page<AccountEntity> findAll(Pageable pageable);
	Page<AccountEntity> findByUsername(String username,Pageable pageable);
	
	
	List<AccountEntity>  findByBirthdayBetween(Date startDate,Date endDate);
	List<AccountEntity>  findByBirthdayAfter(Date date);
	List<AccountEntity>  findByBirthdayBefore(Date date);
	
	List<AccountEntity>  findByIdLessThan(int id);
	List<AccountEntity>  findByIdLessThanEqual(int id);
	List<AccountEntity>  findByIdGreaterThan(int id);
	List<AccountEntity>  findByIdGreaterThanEqual(int id);
	
	List<AccountEntity>  findByUsernameAndPassword(String username,String password);
	List<AccountEntity>  findByUsernameLike(String username);
	
	@Transactional
	@Modifying
	@Query("delete from account where username = ?1")
	void deleteByUsername(String username);
	
	@Query("select a from account a where a.username = ?1 and a.password like ?2 and a.birthday between ?3 and ?4")
	List<AccountEntity>  findByManyParam(String username,String password,Date startDate,Date endDate);
	
	@Transactional
	@Modifying
	@Query("update account set username=?2 where username = ?1")
	int updateByUsername(String username,String username2);
}
