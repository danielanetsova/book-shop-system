package bg.softuni.bookshopsystem.repositories;

import bg.softuni.bookshopsystem.domain.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByFirstNameAndLastName(String firstName, String lastName);

    //когато правим заявка с LIKE задължително пишем @Param дори и аргументът да е със същото име като параметъра
    @Query("SELECT CONCAT_WS(' ', a.firstName, a.lastName) FROM Author a WHERE a.firstName LIKE %:ending")
    List<String> findAuthorsNamesEndingWithSomeString(@Param("ending") String ending);

    @Query("SELECT CONCAT(CONCAT(a.firstName, ' ', a.lastName), " +
                        "' -> ', " +
                        "SUM(b.copies)) " +
            "FROM Author a " +
            "JOIN a.books b " +
            "GROUP BY a.id " +
            "ORDER BY SUM(b.copies) DESC")
    List<String> findTotalBookCopiesPerAuthor();

    //DELIMITER $$
    //CREATE PROCEDURE usp_return_author_books_count(author_full_name VARCHAR(100))
    //BEGIN
    //
    //SELECT COUNT(*)
    //FROM authors as a
    //JOIN authors_books as ab ON a.id = ab.author_id
    //WHERE CONCAT(a.first_name, ' ', a.last_name) = author_full_name
    //GROUP BY author_full_name;
    //
    //
    //END $$
    //DELIMITER ;
    @Query(value = "CALL usp_return_author_books_count(:authorFullName);", nativeQuery = true)
    int returnAuthorBooksCount(@Param("authorFullName") String authorFullName);
}
