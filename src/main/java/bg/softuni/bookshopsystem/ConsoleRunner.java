package bg.softuni.bookshopsystem;

import bg.softuni.bookshopsystem.services.AuthorService;
import bg.softuni.bookshopsystem.services.BookService;
import bg.softuni.bookshopsystem.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public ConsoleRunner(SeedService seedService, BookService bookService, AuthorService authorService) {
        this.seedService = seedService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Override
    //@Transactional - ако сложим тази анотация значи че си разширяваме сесията и не е необходимо да слагаме
    // FetchType.EAGER в @OneToMany на полето books в класа Author
    public void run(String... args) throws Exception {
        seedService.seedAllData();
        //задачи от Spring Data intro ex
        //Task 1
//        bookService.getAllBooksAfterGivenYear(2000).forEach(book ->
//                System.out.println(book.getTitle() + " -> " + book.getReleaseDate()));

        //Task 2
//        authorService.getAuthorsReleasedBooksBeforeGivenYear(2000)
//                .forEach(author -> System.out.println(author.getFullName()));

        //Task 3
//        System.out.println(authorService.getAllAuthorsNamesAndBookCount());

        //Task 4
//        authorService.getBooksInfoByAuthorFullName("George", "Powell");

        //Task 5 -> Books Titles by Age Restriction
        //bookService.findBooksByAgeRestriction("teEN").forEach(book -> System.out.println(book.getTitle()));
       
        //Task 6 -> Golden Books
//        bookService.findGoldenEditionBookTitlesThatHaveCopiesLessThan(5000).forEach(System.out::println);

        //Task 7 -> Books by Price
//        bookService.printTitlesAndPricesLowerAndHigherThan(5, 40);

        //Task 8 -> Not Released Books
//        bookService.printTitlesOfBooksNotReleasedInYear(1998);

        //Task 9 -> Books Released Before Date
//        bookService.printBooksReleasedBeforeDate("30-12-1989");

        //Task 10 -> Authors Search
//        authorService.printAuthorFullNameEndingWith("dy");

        //Task 11 -> Books Search
//        bookService.printBookTitlesContainingString("WOR");

        //Task 12 -> Book Titles Search
//        bookService.printTitlesAndAuthorsWithLastNameStarting("gr");

        //Task 13 -> Count Books
//        bookService.printCountOfTitlesLongerThan(40);

        //Task 14 -> Total Book Copies
//        authorService.printTotalBookCopiesPerAuthor();

        //Task 15 ->  Reduced Book
//        bookService.printBookInformation("Recalled to Life"); 

        //Task 16 -> Increase Book Copies
//        System.out.println(bookService.returnTotalAddedCopiesOfBooksReleasedAfter("06 Jun 2013", 44));

        //Task 17 -> Remove Books
//        bookService.printCountOfDeletedBooksHavingCopiesLessThan(206);

        //Task 18 -> Stored Procedure
        authorService.printCountOfBooksWrittenByAuthor("Amanda Rice");
    }
}
