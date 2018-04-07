package agh.mwo.three.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import agh.mwo.three.data.Book;
import agh.mwo.three.data.BookDetails;

import java.util.List;

public interface BookDetailsRepository extends JpaRepository<BookDetails, Integer> {

    public List<Book> findByBookId (int id);

}
