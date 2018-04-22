package br.com.itcraft.book.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class WebClientServiceImpl implements IWebClientService{

	private static final String URL_SARAIVA = "https://api.saraiva.com.br/sc/produto/pdp/#/0/0/1/";
	RestTemplate rest = new RestTemplate();		

	public String getBySku(long sku) throws RestClientException {
		String jsonStr  = null;
	
			try {
				jsonStr = rest.getForObject(new URI(URL_SARAIVA.replaceFirst("#", String.valueOf(sku))), String.class);
			} catch (RestClientException | URISyntaxException e) {
				throw new RestClientException("Erro de requisição");
			}
		
		return jsonStr;
	}
}
