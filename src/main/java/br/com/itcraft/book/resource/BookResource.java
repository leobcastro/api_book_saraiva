package br.com.itcraft.book.resource;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.itcraft.book.domain.Book;
import br.com.itcraft.book.exception.BookServiceException;
import br.com.itcraft.book.service.IBookService;

@RestController
@RequestMapping("/book")
public class BookResource {
	
	private static final Logger logger = Logger.getGlobal();
	
	@Autowired
	private IBookService bookService;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private @ResponseBody List<Book> list(@PathParam("price") BigDecimal price, @PathParam("limit") Integer limit){	
		return bookService.list(price, limit);
	}
	
	@RequestMapping(value="{sku}",method = RequestMethod.GET)
	private ResponseEntity<Book> find(@PathVariable(value="sku") long sku){
		Book book = null;
		try {
			book = bookService.findBySku(sku);
		} catch (BookServiceException e) {
			logger.info(e.getMessage());
		}
		return new ResponseEntity<Book>(book,HttpStatus.OK);
	}
	
	@RequestMapping(value="{sku}",method = RequestMethod.POST)
	private ResponseEntity<Void> insert(@PathVariable(value="sku") long sku){		
		try {
			bookService.insert(sku);
		} catch (BookServiceException e) {
			logger.info(e.getMessage());
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value="{sku}",method = RequestMethod.DELETE)
	private ResponseEntity<Void> delete(@PathVariable(value="sku") long sku){		
		try {
			bookService.delete(sku);
		} catch (BookServiceException e) {
			logger.info(e.getMessage());
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	
}
