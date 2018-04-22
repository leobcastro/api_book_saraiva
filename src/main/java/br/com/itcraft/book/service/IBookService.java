package br.com.itcraft.book.service;

import java.math.BigDecimal;
import java.util.List;

import br.com.itcraft.book.domain.Book;
import br.com.itcraft.book.exception.BookServiceException;

public interface IBookService {

	public void insert(Long sku) throws BookServiceException;
	public void delete(Long sku) throws BookServiceException;
	public Book findBySku(Long sku) throws BookServiceException;
	public List<Book> list(BigDecimal priceLimit,Integer limit);
}
