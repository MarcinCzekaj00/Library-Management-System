package controllers;

import DB.BorrowedBookDataFromDB;
import DB.DBConnection;
import data.BorrowedBookData;
import data.User;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import messages.BadValuesMessage;
import messages.ClearMessage;
import messages.SuccessMessage;
import org.library.App;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SearchBorrowedBookController implements Initializable {

    ObservableList<BorrowedBookData> list;
    ObservableList<BorrowedBookData> dataList;

    @FXML
    private Label usernameLabel,searchBorrowedBookMessage;

    @FXML
    private TextField searchBorrowedBookFilter,txt_book_id,txt_reader_id,txt_rental_id;

    @FXML
    private TableView<BorrowedBookData> table_borrowed_books;

    @FXML
    private TableColumn<BorrowedBookData, Integer> col_reader_id, col_book_id, col_rental_id;

    @FXML
    private TableColumn<BorrowedBookData, String> col_name, col_surname, col_title;

    @FXML
    private TableColumn<BorrowedBookData, Date> col_due_date;

    @FXML
    private DatePicker txt_date;

    int index = -1;

    //==============================================================================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        usernameLabel.setText(User.getUsername());

        updateTable();
        searchBorrowedBook();
        clearTextFields();
        ClearMessage.clear(searchBorrowedBookMessage);

    }

    @FXML
    public void addReaderButtonOnAction() throws IOException {
        App.setRoot("addReader");
    }

    @FXML
    public void addNewBookButtonOnAction() throws IOException {
        App.setRoot("addNewBook");
    }

    @FXML
    public void infoButtonOnAction() throws IOException {
        App.setRoot("info");
    }

    @FXML
    public void borrowBookButtonOnAction() throws IOException {
        App.setRoot("borrowBook");
    }

    @FXML
    public void returnBookButtonOnAction() throws IOException {
        App.setRoot("returnBook");
    }

    @FXML
    public void logoutButtonOnAction() throws IOException {
        App.setRoot("login");
    }


    //==============================================================================================================

    @FXML
    private void closeSearchOnAction() throws IOException {
        App.setRoot("info");
    }

    @FXML
    private void updateBorrowedBookOnAction() {

        DBConnection connect = new DBConnection();

        try (Connection connectDB = connect.getConnection()){

            if(ifBookNotExist(txt_book_id)) {
                throw new Exception("Book with this id does not exist");
            } else if(ifReaderNotExist(txt_reader_id)) {
                throw new Exception("Reader with this id does not exist");
            }
            String value = txt_rental_id.getText();
            String value1 = txt_book_id.getText();
            String value2 = txt_reader_id.getText();
            LocalDate value3 = txt_date.getValue();

            String query = "update rentals set book_id="+value1+",reader_id= '"+value2
                            +"',due_date= '"+value3+"' where rental_id="+value+";";

            Statement stm = connectDB.createStatement();
            stm.executeUpdate(query);

            SuccessMessage.getSuccessMessage(searchBorrowedBookMessage);
            updateTable();
            searchBorrowedBook();
            clearTextFields();

        } catch (Exception e) {
            e.printStackTrace();
            BadValuesMessage.getBadValuesMessage(searchBorrowedBookMessage);
        }
    }


    @FXML
    private void getSelected (){

        index = table_borrowed_books.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        txt_rental_id.setText(col_rental_id.getCellData(index).toString());
        txt_book_id.setText(col_book_id.getCellData(index).toString());
        txt_reader_id.setText(col_reader_id.getCellData(index).toString());
        txt_date.setValue(col_due_date.getCellData(index).toLocalDate());

    }


    @FXML
    private void searchBorrowedBook() {

        col_rental_id.setCellValueFactory(new PropertyValueFactory<>("rental_id"));
        col_book_id.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_reader_id.setCellValueFactory(new PropertyValueFactory<>("reader_id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        col_due_date.setCellValueFactory(new PropertyValueFactory<>("due_date"));

        dataList = BorrowedBookDataFromDB.getDataBorrowedBook();
        table_borrowed_books.setItems(dataList);
        FilteredList<BorrowedBookData> filteredData = new FilteredList<>(dataList, b -> true);
        searchBorrowedBookFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (book.getSurname().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (book.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (book.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(book.getRental_id()).contains(lowerCaseFilter)){
                    return true;
                } else if (String.valueOf(book.getReader_id()).contains(lowerCaseFilter)){
                    return true;
                } else if (String.valueOf(book.getBook_id()).contains(lowerCaseFilter)){
                    return true;
                } else return book.getDue_date().toString().contains(lowerCaseFilter);
            });
        });

        SortedList<BorrowedBookData> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_borrowed_books.comparatorProperty());
        table_borrowed_books.setItems(sortedData);
    }

    //==============================================================================================================


    private void updateTable(){

        col_rental_id.setCellValueFactory(new PropertyValueFactory<>("rental_id"));
        col_book_id.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_reader_id.setCellValueFactory(new PropertyValueFactory<>("reader_id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        col_due_date.setCellValueFactory(new PropertyValueFactory<>("due_date"));

        list = BorrowedBookDataFromDB.getDataBorrowedBook();
        table_borrowed_books.setItems(list);
    }

    private void clearTextFields(){
        txt_book_id.setText("");
        txt_reader_id.setText("");
        txt_date.setValue(null);
    }

    private boolean ifBookNotExist(TextField f){

        DBConnection connect = new DBConnection();

        try (Connection connectDB = connect.getConnection()){

            String query = "select * from books where books_id="+f.getText();
            Statement stm = connectDB.createStatement();
            ResultSet rs = stm.executeQuery(query);

            if(rs.next()) return false;
            else return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean ifReaderNotExist(TextField f){

        DBConnection connect = new DBConnection();

        try (Connection connectDB = connect.getConnection()){

            String query = "select * from readers where readers_id="+f.getText();
            Statement stm = connectDB.createStatement();
            ResultSet rs = stm.executeQuery(query);

            if(rs.next()) return false;
            else return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
