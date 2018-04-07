package agh.mwo.three.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import agh.mwo.three.data.BookDetails;
import agh.mwo.three.repository.BookDetailsRepository;

import java.util.List;

@Controller
@RestController
@RequestMapping("/booksdet")
public class BookDetailsController {

    @Autowired
    private BookDetailsRepository bookDetailsRepository;

    @RequestMapping("/test/hello")
    public String hallo() {
        return "Hello world";
    }

    @RequestMapping("/all")
    public List<BookDetails> findAllBooks() {
        return bookDetailsRepository.findAll();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BookDetails save(@RequestBody BookDetails bookDetails) {

        bookDetailsRepository.save(bookDetails);

        return bookDetails;
    }

}
