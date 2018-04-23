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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("sku", "9744442");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		
		ResponseEntity<String> response = restTemplate.postForEntity("/book", request, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(201);
	}

	@Test
	public void deleteBookSuccess(){
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("sku", "9787948");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);		
		restTemplate.postForEntity("/book", request, String.class);
		
		ResponseEntity<String> responseDelete = restTemplate.exchange("/book/9787948", HttpMethod.DELETE, null, String.class);		
		assertThat(responseDelete.getStatusCodeValue()).isEqualTo(204);
	}

	@Test
	public void getBookBySkuSuccess() throws JSONException{

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("sku", "9744272");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);		
		restTemplate.postForEntity("/book", request, String.class);

		ResponseEntity<String> responseGet = restTemplate.getForEntity("/book/9744272", String.class);
		assertThat(responseGet.getStatusCodeValue()).isEqualTo(200);

		JSONObject jsonBook = new JSONObject(responseGet.getBody());
		assertThat(jsonBook.getString("name")).isEqualTo("Meeting Customer Needs");
	}

	@Test
	public void findAllBookSuccess() throws JSONException{
		
		insertBooks();

		ResponseEntity<String> responseGet = restTemplate.getForEntity("/book", String.class);
		assertThat(responseGet.getStatusCodeValue()).isEqualTo(200);

		JSONArray jsonBookArr = new JSONArray(responseGet.getBody());

		assertThat(jsonBookArr.length()).isEqualTo(4);
	}
	
	@Test
	public void findBooksPriceLimitAndLimitSuccess() throws JSONException{
						
		insertBooks();

		ResponseEntity<String> responseGet = restTemplate.getForEntity("/book?price=100&limit=2", String.class);
		assertThat(responseGet.getStatusCodeValue()).isEqualTo(200);

		JSONArray jsonBookArr = new JSONArray(responseGet.getBody());
		assertThat(jsonBookArr.length()).isEqualTo(2);
		assertThat(jsonBookArr.getJSONObject(0).getDouble("price")).isLessThanOrEqualTo(100);
		assertThat(jsonBookArr.getJSONObject(1).getDouble("price")).isLessThanOrEqualTo(100);
		
	}
	
	public void insertBooks(){
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map1= new LinkedMultiValueMap<String, String>();
		map1.add("sku", "9742327");
		HttpEntity<MultiValueMap<String, String>> request1 = new HttpEntity<MultiValueMap<String, String>>(map1, headers);		
		
		restTemplate.postForEntity("/book", request1, String.class);
		
		MultiValueMap<String, String> map2= new LinkedMultiValueMap<String, String>();
		map2.add("sku", "9744272");
		HttpEntity<MultiValueMap<String, String>> request2 = new HttpEntity<MultiValueMap<String, String>>(map2, headers);		
		
		restTemplate.postForEntity("/book", request2, String.class);
		
		MultiValueMap<String, String> map3= new LinkedMultiValueMap<String, String>();
		map3.add("sku", "9787948");
		HttpEntity<MultiValueMap<String, String>> request3 = new HttpEntity<MultiValueMap<String, String>>(map3, headers);		
		
		restTemplate.postForEntity("/book", request3, String.class);
		
		MultiValueMap<String, String> map4= new LinkedMultiValueMap<String, String>();
		map4.add("sku", "9744442");
		HttpEntity<MultiValueMap<String, String>> request4 = new HttpEntity<MultiValueMap<String, String>>(map4, headers);		
		
		restTemplate.postForEntity("/book", request4, String.class);
	}
	
}
