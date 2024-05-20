package bg.softuni.bookshopsystem.services;

import bg.softuni.bookshopsystem.domain.models.Author;
import bg.softuni.bookshopsystem.domain.models.Book;
import bg.softuni.bookshopsystem.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final BookService bookService;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookService = bookService;
    }

    @Override
    public boolean isDataSeeded() {
        return authorRepository.count() > 0;
    }

    @Override
    public void seedAuthors(List<Author> authors) {
        authorRepository.saveAllAndFlush(authors);
    }


    @Override
    public Author getRandomAuthor() {
        long authorsCount = this.authorRepository.count();

        if (authorsCount > 0) {
            long randomId = new Random().nextLong(1L, authorsCount + 1L);

            return this.authorRepository
                    .findById(randomId)
                    .orElseThrow(() -> new IllegalArgumentException("No such author"));
        }

        throw new RuntimeException();
    }

    @Override
    public void updateAuthors(List<Author> authors) {
        authorRepository.saveAllAndFlush(authors);
    }

    @Override
    public void updateAuthor(Author author) {
        authorRepository.saveAndFlush(author);
    }

    public List<Author> getAuthorsReleasedBooksBeforeGivenYear(int year) {
        return this.bookService
                .getAllBooksBeforeGivenYear(year)
                .stream()
                .map(Book::getAuthor)
                .distinct()
                .toList()
                ;
    }

    @Override
    public String getAllAuthorsNamesAndBookCount() {
        return authorRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparingInt(Author::getBookCount).reversed())
                .map(author -> author.getFullName() + " -> count of books " + author.getBookCount())
                .collect(Collectors.joining("\n"));
    }

    @Override
    public List<Book> getAllBooksByAuthorFullName(String firstName, String lastName) {
        try {
            return authorRepository
                    .findByFirstNameAndLastName(firstName, lastName)
                    .getBooks()
                    .stream()
                    .sorted(Comparator.comparing(Book::getReleaseDate).reversed()
                            .thenComparing(Book::getTitle))
                    .toList();
        } catch (NullPointerException e) {
            System.out.println();
        }

        throw new NullPointerException("NO SUCH AUTHOR");
    }

    public void getBooksInfoByAuthorFullName(String firstName, String lastName) {
        getAllBooksByAuthorFullName(firstName, lastName)
                .forEach(book -> System.out.printf("Title: %s \nRelease date: %s \nCopies: %d\n\n",
                        book.getTitle(),
                        book.getReleaseDate().toString(),
                        book.getCopies()
                ));
    }

    @Override
    public void printAuthorFullNameEndingWith(String ending) {
        authorRepository.findAuthorsNamesEndingWithSomeString(ending).forEach(System.out::println);
    }

    @Override
    public void printTotalBookCopiesPerAuthor() {
        authorRepository.findTotalBookCopiesPerAuthor().forEach(System.out::println);
    }

    @Override
    public void printCountOfBooksWrittenByAuthor(String authorFullName) {
        System.out.println(authorRepository.returnAuthorBooksCount(authorFullName));
    }

}

