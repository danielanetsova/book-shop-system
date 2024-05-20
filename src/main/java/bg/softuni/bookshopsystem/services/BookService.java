package bg.softuni.bookshopsystem.services;

import bg.softuni.bookshopsystem.domain.models.Book;

import java.util.List;

public interface BookService {
    boolean isDataSeeded();
    void seedBooks(List<Book> books);

    List<Book> getAllBooksAfterGivenYear(int year);
    List<Book> getAllBooksBeforeGivenYear(int year);

    List<Book> findBooksByAgeRestriction(String ageRestriction);

    List<String> findGoldenEditionBookTitlesThatHaveCopiesLessThan(int copiesCount);

    void printTitlesAndPricesLowerAndHigherThan(double lowerThan, double higherThan);
    void printTitlesOfBooksNotReleasedInYear(int year);

    void printBooksReleasedBeforeDate(String s);
    void printBookTitlesContainingString(String s);

    void printTitlesAndAuthorsWithLastNameStarting(String start);

    void printCountOfTitlesLongerThan(int number);

    void printBookInformation(String title);

    int returnTotalAddedCopiesOfBooksReleasedAfter(String stringDate, int number);

    void printCountOfDeletedBooksHavingCopiesLessThan(int number);

}
