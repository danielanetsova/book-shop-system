package bg.softuni.bookshopsystem.services;

import bg.softuni.bookshopsystem.domain.enums.AgeRestriction;
import bg.softuni.bookshopsystem.domain.models.Author;
import bg.softuni.bookshopsystem.domain.models.Book;
import bg.softuni.bookshopsystem.repositories.AuthorRepository;
import bg.softuni.bookshopsystem.repositories.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public boolean isDataSeeded() {
        return bookRepository.count() > 0;
    }

    @Override
    public void seedBooks(List<Book> books) {
        bookRepository.saveAllAndFlush(books);
    }

    @Override
    public List<Book> getAllBooksAfterGivenYear(int year) {
        return bookRepository.findAllByReleaseDateYearAfter(year);
    }

    @Override
    public List<Book> getAllBooksBeforeGivenYear(int year) {
        return bookRepository.findAllByReleaseDateYearBefore(year);
    }

    public List<Book> findBooksByAgeRestriction(String ageRestriction) {
        return bookRepository.findByAgeRestriction(AgeRestriction.valueOf(ageRestriction.toUpperCase()));
    }

    @Override
    public List<String> findGoldenEditionBookTitlesThatHaveCopiesLessThan(int copiesCount) {
        return bookRepository.findGoldenEditionBookTitlesThatHaveCopiesLessThan(copiesCount);
    }

    @Override
    public void printTitlesAndPricesLowerAndHigherThan(double lowerThan, double higherThan) {
        bookRepository.findByPriceLessThanOrPriceGreaterThan(lowerThan, higherThan)
                .forEach(book -> System.out.printf("%s - $%.2f\n", book.getTitle(), book.getPrice()));
    }

    @Override
    public void printTitlesOfBooksNotReleasedInYear(int year) {
        bookRepository.findTitlesOfBooksNotReleasedInYear(year).forEach(System.out::println);
    }

    @Override
    public void printBooksReleasedBeforeDate(String s) {
        LocalDate date = LocalDate.parse(s, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        bookRepository.findByReleaseDateBefore(date)
                .forEach(book -> System.out.printf("%s %s %.2f\n",
                        book.getTitle(), book.getEditionType(), book.getPrice()));
    }

    @Override
    public void printBookTitlesContainingString(String s) {
        bookRepository.getBookTitlesContainingString(s).forEach(System.out::println);
    }

    @Override
    public void printTitlesAndAuthorsWithLastNameStarting(String start) {
        bookRepository.findByAuthorLastNameStartingWith(start)
                .forEach(book -> System.out.printf("%s (%s)\n", book.getTitle(), book.getAuthor().getFullName()));
    }

    @Override
    public void printCountOfTitlesLongerThan(int number) {
        System.out.printf("There are %d books with longer titles than %d symbols.\n",
                bookRepository.findCountOfTitlesLongerThan(number), number);
    }

    @Override
    public void printBookInformation(String title) {
        bookRepository.findInformationAboutBook(title).forEach(System.out::println);
    }

    @Override
    public int returnTotalAddedCopiesOfBooksReleasedAfter(String stringDate, int number) {
        LocalDate date = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("dd MMM yyyy"));
        List<Book> byReleaseDateAfter = bookRepository.findByReleaseDateAfter(date);
        int updatedBooks = bookRepository.increaseBookCopiesWith(number, byReleaseDateAfter);
        return updatedBooks * number;
    }

    @Override
    @Transactional
    public void printCountOfDeletedBooksHavingCopiesLessThan(int number) {
        List<Book> byCopiesLessThan = bookRepository.findByCopiesLessThan(number);
        byCopiesLessThan.forEach(book -> {
            Author author = book.getAuthor();
            author.removeBook(book);
            authorRepository.saveAndFlush(author);
        });

        System.out.println(bookRepository.deleteBooksWithCopiesLessThan(byCopiesLessThan));
    }


}
