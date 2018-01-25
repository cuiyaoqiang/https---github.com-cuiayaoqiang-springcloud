package server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.bh.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JdbcTest {

    @Value("${local.server.port}")
    private int port;
	/**
	 * 继承JpaRepository的基本查询
	 */
	@Test
	public void JpaRepositoryTest() {
		ResponseEntity<String> response = new TestRestTemplate().getForEntity(
				"http://localhost:" + port+ "/MySpringBoot/account/findAll",
				null, String.class);
		System.out.println(response.getBody());
	}
}
