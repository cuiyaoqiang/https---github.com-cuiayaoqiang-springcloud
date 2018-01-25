package server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import com.bh.Application;
import com.bh.dao.AccountDao;
import com.bh.dao.CustomizeDao;
import com.bh.entity.AccountEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JpaTest {

	@Autowired
	private AccountDao accountDao;
	@Autowired
	private CustomizeDao customizeDao;
	/**
	 * 继承JpaRepository的基本查询
	 */
	@Test
	public void JpaRepositoryTest() {

		AccountEntity accountEntity=new AccountEntity(1, "zs1", "zs",new Date(2013,12,25));
		List<AccountEntity> accounts = accountDao.findAll();
		System.out.println("findAll:"+accounts);
		AccountEntity account = accountDao.findOne(1);
		System.out.println("findOne:"+account);
		AccountEntity saveResult = accountDao.save(accountEntity);
		System.out.println("save:"+saveResult);
		accountDao.delete(accountEntity);
		System.out.println("delete...");
		long count = accountDao.count();
		System.out.println("count:"+count);
		boolean result = accountDao.exists(1);
		System.out.println("exists(1):"+result);
	}
	/**
	 * 自定义简单查询
	 */
	@Test
	public void customizeTest() {
		List<AccountEntity> accounts;
		accounts = customizeDao.findByUsername("zs");
		System.out.println("findByUsername:"+accounts);
		AccountEntity account = customizeDao.findByPassword("ls");
		System.out.println("findByPassword:"+account);
		
		AccountEntity result = customizeDao.findByUsernameOrPassword("ls","ls");
		System.out.println("findByUsernameOrPassword:"+result);
		
		customizeDao.deleteByUsername("zs1");
		System.out.println("deleteByUsername...");
		/*customizeDao.deleteByPassword("zs1");
		System.out.println("deleteByPassword...");*/

		accounts  = customizeDao.findByUsernameOrderByIdDesc("zs");
		System.out.println("findByUsernameOrderByIdDesc:"+accounts);
		
		AccountEntity accountEntity = customizeDao.findFirstByOrderByIdDesc();
		System.out.println("findFirstByOrderByIdDesc:"+accountEntity);
	}
	/**
	 * 自定义简单查询
	 */
	@Test
	public void customizeTest2() {

		List<AccountEntity> accountEntities;
		accountEntities  = customizeDao.findByBirthdayBetween(new Date(2012,12,25),new Date(2013,12,25));
		System.out.println("findByBirthdayBetween:"+accountEntities);
		accountEntities=customizeDao.findByBirthdayAfter(new Date(2012,12,25));
		System.out.println("findByBirthdayAfter:"+accountEntities);
		accountEntities= customizeDao.findByBirthdayBefore(new Date(2012,12,25));
		System.out.println("findByBirthdayBefore:"+accountEntities);
		
		int id=2;
		accountEntities= customizeDao.findByIdLessThan(id);
		System.out.println("findByIdLessThan:"+accountEntities);
		accountEntities= customizeDao.findByIdLessThanEqual(id);
		System.out.println("findByIdLessThanEqual:"+accountEntities);
		accountEntities= customizeDao.findByIdGreaterThan(id);
		System.out.println("findByIdGreaterThan:"+accountEntities);
		accountEntities= customizeDao.findByIdGreaterThanEqual(id);
		System.out.println("findByIdGreaterThanEqual:"+accountEntities);
		
		accountEntities= customizeDao.findByUsernameLike("%zs%");
		System.out.println("findByUsernameLike:"+accountEntities);
		accountEntities= customizeDao.findByUsernameAndPassword("ls","ls");
		System.out.println("findByUsernameAndPassword:"+accountEntities);
		
		accountEntities= customizeDao.findByManyParam("zs","%zs%",new Date(2012,12,25),new Date(2013,12,25));
		System.out.println("findByManyParam:"+accountEntities);

		int result = customizeDao.updateByUsername("zss","zs");
		System.out.println("updateByUsername"+result);
	}
	/**
	 * 分页查询
	 */
	@Test
	public void pageTest() {

		int page=0,size=10;
		Sort sort = new Sort(Direction.DESC, "id");
		Pageable pageable = new PageRequest(page,size,sort); 
		Page<AccountEntity> pageResult = customizeDao.findAll(pageable);
		System.out.println("pageResult:"+pageResult);
		System.out.println("findALL:"+pageResult.getSize()+"  "+pageResult.getTotalPages());
		Page<AccountEntity> pageResult2 =customizeDao.findByUsername("zs", pageable);
		System.out.println("getContent:"+pageResult2.getContent());
	}
	@Test
	public void pageTest2() {

		int page=1,size=10;
		//当前页数需要减1,默认是从0开始
		Pageable pageable = new PageRequest(page-1,size);
		String queueParam="zs";
		Specification<AccountEntity> specification = new Specification<AccountEntity>(){
			@Override
			public Predicate toPredicate(Root<AccountEntity> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();  
				list.add(criteriaBuilder.equal(root.get("username").as(String.class),queueParam));
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		};
		Page<AccountEntity> all = customizeDao.findAll(specification,pageable);

		System.out.println("accounts:"+all.getContent());
		System.out.println("pageCount:"+all.getTotalPages());
		System.out.println("count:"+all.getTotalElements());
	}
}
