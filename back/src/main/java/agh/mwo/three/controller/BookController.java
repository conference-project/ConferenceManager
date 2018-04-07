package agh.mwo.three.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import agh.mwo.three.data.Book;
import agh.mwo.three.data.BookDetails;
import agh.mwo.three.repository.BookDetailsRepository;
import agh.mwo.three.repository.BookRepository;

import java.util.List;

@Controller
@RestController
@RequestMapping("/books")
@CrossOrigin
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping("/test/hello")
    public String hallo() {
        return "Hello world";
    }

    @RequestMapping("/{id}")
    public Book loadPerson(@PathVariable("id") Integer id) {

        System.out.println("id = " + id);

        return bookRepository.getOne(id);
    }

    @RequestMapping("/all")
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Book save(@RequestBody Book book) {
        bookRepository.save(book);
        return book;
    }

    @RequestMapping("/name/{name}")
    public List<Book> findBookByName(@PathVariable String name) {
        return bookRepository.findByNameLike(name);
    }

}
