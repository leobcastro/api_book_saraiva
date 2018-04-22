package br.com.itcraft.book.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.itcraft.book.domain.Book;
import br.com.itcraft.book.exception.BookServiceException;
import br.com.itcraft.book.repository.IBookRepository;
import br.com.itcraft.book.utils.NumberConverterUtils;

@Service
public class BookServiceImpl implements IBookService {

	@Autowired
	private IBookRepository bookRepository;
	
	@Autowired
	private IWebClientService webClientService;
	
	
	public void insert(Long sku) throws BookServiceException{
		try {
				
			if (findBySku(sku)!=null) {
				throw new BookServiceException("Livro já cadastrado.");
			}
			
			String bookInfo = webClientService.getBySku(sku);
						
			JSONObject json = new JSONObject(bookInfo);

			Book book = new Book();

			book.setSku(json.getLong("sku"));
			book.setBrand(json.getString("brand"));
			book.setName(json.getString("name"));

			BigDecimal price = new BigDecimal(NumberConverterUtils.convertToBR(json.getJSONObject("price_block").getJSONObject("price").getString("final"))).setScale(2, RoundingMode.HALF_EVEN);
			book.setPrice(price);
			
			bookRepository.save(book);
			

		} catch (JSONException e) {
			throw new BookServiceException("Ocorreu um erro de requisicao");
		} catch (ParseException e) {
			throw new BookServiceException("Preço inválido");			
		}
	}
	
	public void delete(Long sku) throws BookServiceException{
		Book book = findBySku(sku);
		if (book == null) {
			throw new BookServiceException("Livro não casdastrado");
		}
		bookRepository.delete(book);
	}
	
	
	public Book findBySku(Long sku) {	
		Book book = bookRepository.findBySku(sku);
		return book;
	}
	
	public List<Book> list(BigDecimal priceLimit,Integer limit){
		if (priceLimit != null) {
			return listPagination(priceLimit, limit);
		}
		return list(limit); 
		
	}
	
	private List<Book> listPagination(BigDecimal priceLimit,Integer limit){

		if(limit != null){
			return bookRepository.findBySkuLimitPricePage(priceLimit, PageRequest.of(0, limit)).getContent();			
		} 
		
		return bookRepository.findBySkuLimitPrice(priceLimit);
	}
	
	private List<Book> list(Integer limit){
		
		if(limit != null){
			return bookRepository.findAll(PageRequest.of(0, limit)).getContent();			
		} 
		return bookRepository.findAll();
	}

}
