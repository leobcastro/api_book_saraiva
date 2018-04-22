package br.com.itcraft.book.service;

import org.springframework.web.client.RestClientException;

public interface IWebClientService {

	public String getBySku(long sku) throws RestClientException;
}
