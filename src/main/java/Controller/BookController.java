package Controller;


import Entity.Book;
import Repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    LibraryRepository repo;

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> booksList = repo.findAll();
        return new ResponseEntity<>(booksList, HttpStatus.OK);
    }

    @GetMapping("/getAllBooks/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Optional<Book> book =  repo.findById(id);

        if(book.isPresent()){
            return new ResponseEntity<>(book.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        Book newBook = repo.save(book);
        return new ResponseEntity<>(newBook, HttpStatus.OK);
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book){
        Optional<Book> oldBook = repo.findById(id);

        if(oldBook.isPresent()){
            Book updatedBook = oldBook.get();
            updatedBook.setBookName(book.getBookName());
            updatedBook.setGenre(book.getGenre());

            Book bookObj = repo.save(updatedBook);
            return new ResponseEntity<>(bookObj, HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id){
        Book book = repo.findById(id).get();
        repo.delete(book);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
