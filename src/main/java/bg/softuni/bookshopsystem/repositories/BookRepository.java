package bg.softuni.bookshopsystem.repositories;

import bg.softuni.bookshopsystem.domain.enums.AgeRestriction;
import bg.softuni.bookshopsystem.domain.models.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("FROM Book WHERE YEAR(releaseDate) > :year")
    List<Book> findAllByReleaseDateYearAfter(@Param("year") int year);

    @Query("FROM Book WHERE YEAR(releaseDate) < :year")
    List<Book> findAllByReleaseDateYearBefore(@Param("year") int year);

    List<Book> findByAgeRestriction(AgeRestriction ageRestriction);

    @Query("SELECT b.title FROM Book b WHERE b.editionType = 'GOLD' AND b.copies < :copiesCount")
    List<String> findGoldenEditionBookTitlesThatHaveCopiesLessThan(int copiesCount);

    List<Book> findByPriceLessThanOrPriceGreaterThan(double lowerThan, double higherThan);

    @Query("SELECT b.title FROM Book b WHERE YEAR(b.releaseDate) != :year")
    List<String> findTitlesOfBooksNotReleasedInYear(int year);

    List<Book> findByReleaseDateBefore(LocalDate date);
    List<Book> findByReleaseDateAfter(LocalDate date);

    @Query("SELECT b.title FROM Book b WHERE b.title LIKE %:s%" )
    List<String> getBookTitlesContainingString(@Param("s") String s);

    List<Book> findByAuthorLastNameStartingWith(String start);

    @Query("SELECT COUNT(*) FROM Book b WHERE LENGTH(b.title) > :number")
    int findCountOfTitlesLongerThan(int number);

    @Query("SELECT CONCAT_WS(' ', b.title, b.editionType, b.ageRestriction, b.price) " +
            "FROM Book b WHERE b.title = :title")
    List<String> findInformationAboutBook(String title);

    @Modifying
    @Transactional
    @Query("UPDATE Book b " +
            "SET b.copies = b.copies + :number " +
            "WHERE b IN :books")
    int increaseBookCopiesWith(int number, List<Book> books);

    List<Book> findByCopiesLessThan(int number);
    @Modifying
    @Transactional
    @Query("DELETE Book b WHERE b IN :books")
    int deleteBooksWithCopiesLessThan(List<Book> books);
    //може и int deleteByCopiesLessThan(int amount) - когато се прави без @Query можем да изпуснем @Modifying

}
