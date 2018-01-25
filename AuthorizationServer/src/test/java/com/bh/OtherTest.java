package com.bh;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class OtherTest {
	public static void main(String[] args) {
		try {
			String access_token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic3ByaW5nLWJvb3QtYXBwbGljYXRpb24iXSwidXNlcl9uYW1lIjoibGVmdHNvIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sInJvbGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImV4cCI6MTUxMjYyMTIxNiwidXNlck5hbWUiOiJsZWZ0c28iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiZjlkNzI4NjMtNWMzMi00YzM4LWE2MDgtY2RkY2M4ZWM1NTZhIiwiY2xpZW50X2lkIjoibm9ybWFsLWFwcCJ9.XQYE-pxaBPqERbMJ6mI1y8JRv_ur8en94RdcrAzdq9g";
			System.out.println("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic3ByaW5nLWJvb3QtYXBwbGljYXRpb24iXSwidXNlcl9uYW1lIjoibGVmdHNvIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sInJvbGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImV4cCI6MTUxMjYyMTIxNiwidXNlck5hbWUiOiJsZWZ0c28iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiZjlkNzI4NjMtNWMzMi00YzM4LWE2MDgtY2RkY2M4ZWM1NTZhIiwiY2xpZW50X2lkIjoibm9ybWFsLWFwcCJ9.XQYE-pxaBPqERbMJ6mI1y8JRv_ur8en94RdcrAzdq9g".equals(access_token));
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + access_token);
			ResponseEntity<String> response = new TestRestTemplate().exchange("http://localhost:" + 8080 + "/resources/roles", HttpMethod.GET,
					new HttpEntity<>(null, headers), String.class);
			System.out.println(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
