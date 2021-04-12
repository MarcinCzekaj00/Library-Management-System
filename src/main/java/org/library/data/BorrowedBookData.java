package org.library.data;

import java.sql.Date;

public class BorrowedBookData {
    private int rental_id,book_id,reader_id;
    private String title,name,surname;
    private Date due_date;

    public BorrowedBookData(int rental_id,int book_id, String title, int reader_id, String name, String surname, Date due_date) {
        this.rental_id = rental_id;
        this.book_id = book_id;
        this.reader_id = reader_id;
        this.title = title;
        this.name = name;
        this.surname = surname;
        this.due_date = due_date;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getReader_id() {
        return reader_id;
    }

    public void setReader_id(int reader_id) {
        this.reader_id = reader_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public int getRental_id() {
        return rental_id;
    }

    public void setRental_id(int rental_id) {
        this.rental_id = rental_id;
    }
}
