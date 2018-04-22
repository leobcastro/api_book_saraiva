package br.com.itcraft.book.book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.itcraft.book.domain.Book;
import br.com.itcraft.book.exception.BookServiceException;
import br.com.itcraft.book.service.IBookService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookServiceTest {
	
	@Autowired
	private IBookService bookSerivce;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	
	@Test
	public void insertBookSuccess() throws BookServiceException{		
		this.bookSerivce.insert(9744442l);
		this.bookSerivce.insert(9744272l);
		this.bookSerivce.insert(9742327l);
		assertThatExceptionOfType(BookServiceException.class);
	}
	
	@Test
	public void insertBookNotExistsThrownBookExcepiton() throws BookServiceException  {		
		thrown.expect(BookServiceException.class);
		thrown.expectMessage("Ocorreu um erro de requisicao");
		this.bookSerivce.insert(99999991l);
	}
	
	@Test
	public void deleteBookSuccess() throws BookServiceException{		
		this.bookSerivce.insert(9744442l);
		this.bookSerivce.insert(9744272l);
		this.bookSerivce.insert(9742327l);
		
		this.bookSerivce.delete(9744272l);
		
		assertThatExceptionOfType(BookServiceException.class);
	}
		
	@Test
	public void selectByIdBookSuccess() throws BookServiceException{		
		this.bookSerivce.insert(9744442l);
		this.bookSerivce.insert(9744272l);
		this.bookSerivce.insert(9742327l);
		
		Book a = this.bookSerivce.findBySku(9744442l);
		assertThat(a.getName()).isEqualTo("Handbook of Research in School Consultation");		
	}
	
	@Test
	public void selectAllBookSuccess() throws BookServiceException{		
		this.bookSerivce.insert(9744442l);
		this.bookSerivce.insert(9744272l);
		this.bookSerivce.insert(9742327l);
		
		List<Book> books = this.bookSerivce.list(null, null);
		assertThat(books.size()).isEqualTo(3);
		assertThat(books.get(1).getName()).isEqualTo("Meeting Customer Needs");
	}
	
	@Test
	public void selectBooksPriceLimitSuccess() throws BookServiceException{		
		
		BigDecimal priceLimit = new BigDecimal(168);
		
		this.bookSerivce.insert(9744442l);
		this.bookSerivce.insert(9744272l);
		this.bookSerivce.insert(9742327l);
		
		List<Book> books = this.bookSerivce.list(priceLimit, null);

		for (Book book : books) {
			assertThat(book.getPrice()).isLessThanOrEqualTo(priceLimit);
		}

	}
	
	@Test
	public void selectBooksLimitSuccess() throws BookServiceException{		
		
		int limit = 2;
		
		this.bookSerivce.insert(9744442l);
		this.bookSerivce.insert(9744272l);
		this.bookSerivce.insert(9742327l);
		
		List<Book> books = this.bookSerivce.list(null, limit);
		assertThat(books.size()).isEqualTo(limit);
	}
	
	@Test
	public void selectBooksPriceLimitAndLimitSuccess() throws BookServiceException{		
		
		BigDecimal priceLimit = new BigDecimal(168);
		int limit = 1;
		
		this.bookSerivce.insert(9744442l);
		this.bookSerivce.insert(9744272l);
		this.bookSerivce.insert(9742327l);
		
		List<Book> books = this.bookSerivce.list(priceLimit, limit);

		for (Book book : books) {
			assertThat(book.getPrice()).isLessThanOrEqualTo(priceLimit);
		}
		assertThat(books.size()).isEqualTo(limit);
	}
	

	
}
