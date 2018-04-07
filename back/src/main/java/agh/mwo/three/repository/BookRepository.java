package agh.mwo.three.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import agh.mwo.three.data.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    public List<Book> findByNameLike(String name);

}
