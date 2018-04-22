package br.com.itcraft.book.book;

import static org.assertj.core.api.Assertions.assertThat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Rollback
public class BookResourceTest {

	@Autowired
	private TestRestTemplate restTemplate;
	@LocalServerPort
	private int port;

	@Test	
	public void insertBookSuccess(){
		ResponseEntity<String> response = restTemplate.postForEntity("/book/9744442", null, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(201);
	}

	@Test
	public void deleteBookSuccess(){
		restTemplate.postForEntity("/book/9787948", null, String.class);
		ResponseEntity<String> responseDelete = restTemplate.exchange("/book/9787948", HttpMethod.DELETE, null, String.class);		
		assertThat(responseDelete.getStatusCodeValue()).isEqualTo(204);
	}

	@Test
	public void getBookBySkuSuccess() throws JSONException{
		restTemplate.postForEntity("/book/9744272", null, String.class);

		ResponseEntity<String> responseGet = restTemplate.getForEntity("/book/9744272", String.class);
		assertThat(responseGet.getStatusCodeValue()).isEqualTo(200);

		JSONObject jsonBook = new JSONObject(responseGet.getBody());
		assertThat(jsonBook.getString("name")).isEqualTo("Meeting Customer Needs");
	}

	@Test
	public void findAllBookSuccess() throws JSONException{
		restTemplate.postForEntity("/book/9742327", null, String.class);
		restTemplate.postForEntity("/book/9744272", null, String.class);
		restTemplate.postForEntity("/book/9787948", null, String.class);
		restTemplate.postForEntity("/book/9744442", null, String.class);

		ResponseEntity<String> responseGet = restTemplate.getForEntity("/book", String.class);
		assertThat(responseGet.getStatusCodeValue()).isEqualTo(200);

		JSONArray jsonBookArr = new JSONArray(responseGet.getBody());

		assertThat(jsonBookArr.length()).isEqualTo(4);
	}
	
	@Test
	public void findBooksPriceLimitAndLimitSuccess() throws JSONException{
						
		restTemplate.postForEntity("/book/9742327", null, String.class);
		restTemplate.postForEntity("/book/9744272", null, String.class);
		restTemplate.postForEntity("/book/9787948", null, String.class);
		restTemplate.postForEntity("/book/9744442", null, String.class);

		ResponseEntity<String> responseGet = restTemplate.getForEntity("/book?price=100&limit=2", String.class);
		assertThat(responseGet.getStatusCodeValue()).isEqualTo(200);

		JSONArray jsonBookArr = new JSONArray(responseGet.getBody());
		assertThat(jsonBookArr.length()).isEqualTo(2);
		assertThat(jsonBookArr.getJSONObject(0).getDouble("price")).isLessThanOrEqualTo(100);
		assertThat(jsonBookArr.getJSONObject(1).getDouble("price")).isLessThanOrEqualTo(100);
		
	}
}
