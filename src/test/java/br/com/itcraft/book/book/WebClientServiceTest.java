package br.com.itcraft.book.book;

import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.itcraft.book.exception.BookServiceException;
import br.com.itcraft.book.service.IWebClientService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WebClientServiceTest {

	@Autowired
	private IWebClientService webClientService;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void getBookApiSaraivaSuccess() throws BookServiceException, JSONException{		
		String jsonBook = this.webClientService.getBySku(9742327l);
		JSONObject json = new JSONObject(jsonBook);
		Assertions.assertThat(json.getString("brand")).isEqualTo("SparkPoint Studio (IPS) (Livros Digitais)");
	}
}
