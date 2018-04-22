package br.com.itcraft.book.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.itcraft.book.domain.Book;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long>{

	@Query("SELECT b FROM Book b WHERE b.sku = :sku" )
	public Book findBySku(@Param("sku") Long sku);
	
	@Query("SELECT b FROM Book b WHERE b.price <= :price" )
	public List<Book> findBySkuLimitPrice(@Param("price") BigDecimal priceLimit);
	
	@Query("SELECT b FROM Book b WHERE b.price <= :price" )
	public Page<Book> findBySkuLimitPricePage(@Param("price") BigDecimal priceLimit,Pageable pageable);
	
}
