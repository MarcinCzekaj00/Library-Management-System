package org.library.service;

import org.library.messages.NextID;

public class QueryHelper {

    private static String addReader = "INSERT INTO readers (readers_id,name,surname,date_of_birth) values (" + NextID.getNextID("SELECT max(readers_id) FROM readers") +
            ",?,?,?);";

    private static String addBook = "INSERT INTO books (books_id,title,category,author,release_date) values (" + NextID.getNextID("SELECT max(books_id) FROM books") +
            ",?,?,?,?);";

    private static String borrowBook = "INSERT INTO rentals (book_id,reader_id,due_date) values (?,?,?)";

    //------------------------------------------------------------------------------------------------------------------------------------------------------
    private static String maxReadersID = "SELECT max(readers_id) FROM readers";

    private static String maxBooksID = "SELECT max(books_id) FROM books";

    //------------------------------------------------------------------------------------------------------------------------------------------------------

    private static String selectFromRentals = "select * from rentals";

    private static String selectFromBooks = "select * from books";

    private static String selectFromReaders = "select * from readers";

    private static String selectFromCategories = "select * from categories";

    private static String selectFromBooksWhereID = "select * from books where books_id=";

    private static String selectFromReadersWhereID = "select * from readers where readers_id=";

    private static String selectTitleWhereID = "select title from books where books_id=";

    private static String selectNameReaderWhereID = "select name from readers where readers_id=";

    private static String selectSurnameReaderWhereID = "select surname from readers where readers_id=";
    //------------------------------------------------------------------------------------------------------------------------------------------------------

    private static String ifBookExist = "SELECT * FROM books,readers where books_id=? AND readers_id=? ;";

    private static String ifUserExist = "SELECT * FROM users WHERE username=?;";

    //------------------------------------------------------------------------------------------------------------------------------------------------------

    private static String updateRentals = "update rentals set book_id=?,reader_id=?,due_date=? where rental_id=?;";

    private static String updateBooks = "update books set books_id=?,title=?,category=?,author=?,release_date=? where books_id=?;";

    private static String updateReaders = "update readers set readers_id=?,name=?,surname=?,date_of_birth=? where readers_id=?;";

    //------------------------------------------------------------------------------------------------------------------------------------------------------

    private static String deleteFromBooksWhereID = "delete from books where books_id = ?";

    private static String deleteFromReadersWhereID = "delete from readers where readers_id = ?";

    private static String deleteFromRentals = "DELETE FROM rentals WHERE book_id=? AND reader_id=? ;";

    //------------------------------------------------------------------------------------------------------------------------------------------------------

    private static String dueDate = "select due_date from rentals where reader_id=? AND book_id=?;";

    //------------------------------------------------------------------------------------------------------------------------------------------------------




    public static String getSelectNameReaderWhereID() {
        return selectNameReaderWhereID;
    }

    public static String getSelectSurnameReaderWhereID() {
        return selectSurnameReaderWhereID;
    }
    public static String getSelectTitleWhereID() {
        return selectTitleWhereID;
    }

    public static String getSelectFromReaders() {
        return selectFromReaders;
    }

    public static String getSelectFromCategories() {
        return selectFromCategories;
    }

    public static String getSelectFromBooks() {
        return selectFromBooks;
    }

    public static String getDeleteFromReadersWhereID() {
        return deleteFromReadersWhereID;
    }

    public static String getDeleteFromBooksWhereID() {
        return deleteFromBooksWhereID;
    }

    public static String getSelectFromBooksWhereID() {
        return selectFromBooksWhereID;
    }

    public static String getSelectFromReadersWhereID() {
        return selectFromReadersWhereID;
    }

    public static String getUpdateRentals() {
        return updateRentals;
    }

    public static String getUpdateBooks() {
        return updateBooks;
    }

    public static String getUpdateReaders() {
        return updateReaders;
    }

    public static String getDueDate() {
        return dueDate;
    }

    public static String getDeleteFromRentals() {
        return deleteFromRentals;
    }

    public static String getIfUserExist() {
        return ifUserExist;
    }

    public static String getAddReader() {
        return addReader;
    }

    public static String getAddBook() {
        return addBook;
    }

    public static String getBorrowBook() {
        return borrowBook;
    }

    public static String getMaxReadersID() {
        return maxReadersID;
    }

    public static String getMaxBooksID() {
        return maxBooksID;
    }

    public static String getSelectFromRentals() {
        return selectFromRentals;
    }

    public static String getIfBookExist() {
        return ifBookExist;
    }
}
