package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import model.Fine;
import model.Librarian;
import model.Payment;
import model.Person;
import model.Transactions;

/**
 * Controller for the transactions view.
 * 
 * @author Oliver Harris.
 */
public class TransactionsController {

    private final double MIN_PAY = 0.01;

    private Transactions transactions;
    private ArrayList<Fine> fines;
    private TableView<Payment> tableTransaction = new TableView<>();
    private TableView<Fine> tableFine = new TableView<>();
    Person user = ScreenManager.getCurrentUser();

    private boolean isStaff = false;

    @FXML
    private TextField dateSearch;

    ObservableList<Fine> finesData = FXCollections.observableArrayList();
    FilteredList<Fine> finesList = new FilteredList<>(finesData);

    ObservableList<Payment> transactionData = FXCollections.observableArrayList();
    FilteredList<Payment> transactionsList = new FilteredList<>(transactionData);

    @FXML
    private SplitPane transactionsSplit;

    @FXML
    private SplitPane finesSplit;

    /**
     * Searches the objects that are being displayed.
     * 
     * @param event The key change.
     */
    @FXML
    void transactionSearch(KeyEvent event) {
        if (isStaff) {
            finesList.setPredicate(s -> s.containsUser(dateSearch.getText()));

        }
        else {
            finesList.setPredicate(s -> s.contains(dateSearch.getText()));
            transactionsList.setPredicate(s -> s.contains(dateSearch.getText()));
        }
    }

    /**
     * Generate the fines table.
     */
    @SuppressWarnings("unchecked") // A bug in FXML
    private void setupFines() {

        if (isStaff) {
            fines = Fine.getFines();
        }
        else {
            fines = Fine.getFines(user.getUsername());
        }

        finesData = FXCollections.observableArrayList();
        for (Fine fine : fines) {
            finesData.add(fine);
        }

        finesList = new FilteredList<>(finesData);

        // create the table
        TableColumn<Fine,
            String> fineCol = new TableColumn<Fine, String>("Fine");
        fineCol.setCellValueFactory(new PropertyValueFactory<>("fineId"));

        TableColumn<Fine,
            String> personCol = new TableColumn<Fine, String>("Person");
        personCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Fine,
            String> amountCol = new TableColumn<Fine, String>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Fine, String> forCol = new TableColumn<Fine, String>("For");
        forCol.setCellValueFactory(
            cd -> new SimpleStringProperty(cd.getValue().getResourceName()));

        TableColumn<Fine,
            String> overCol = new TableColumn<Fine, String>("Days over");
        overCol.setCellValueFactory(new PropertyValueFactory<>("daysOver"));

        TableColumn<Fine,
            String> whenCol = new TableColumn<Fine, String>("When");
        whenCol.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        TableColumn<Fine,
            String> paidCol = new TableColumn<Fine, String>("Paid");
        paidCol.setCellValueFactory(new PropertyValueFactory<>("paid"));

        tableFine.setItems(finesList);

        // If they're staff - let them double click to pay
        if (isStaff) {
            tableFine.setRowFactory(tv -> {
                TableRow<Fine> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Fine rowData = row.getItem();
                        confirmPay(rowData);
                    }
                });
                return row;
            });
        }

        tableFine.getColumns().addAll(fineCol, personCol, amountCol, overCol,
            forCol, whenCol, paidCol);
        tableFine.autosize();

        finesSplit.getItems().add(tableFine);

    }

    /**
     * Generate the popup confirmation.
     * 
     * @param fine The fine that is being paid.
     */
    private void confirmPay(Fine fine) {

        TextInputDialog dialog = new TextInputDialog(
            String.valueOf(fine.getAmount()));
        dialog.setTitle("Confirm payment");
        dialog.setHeaderText(
            "Enter a value beween " + MIN_PAY + " and " + fine.getAmount());
        dialog.setContentText("Enter payment: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            boolean goAhead = true;
            try {
                Float.valueOf(result.get());
            }
            catch (NumberFormatException e) {
                alertDone("Invalid input");
                goAhead = false;
            }

            if (Float.valueOf(result.get()) > MIN_PAY &&
                Float.valueOf(result.get()) <= fine.getAmount() && goAhead) {
                System.out.println("paying.. " + result.get());
                if (Payment.makePayment(fine.getUsername(),
                    Float.valueOf(result.get()), fine.getFineId(), (Float
                        .valueOf(result.get()) == fine.getAmount())) != null) {
                    alertDone("Fine has been paid");
                    
                    //Removes the old table
                    finesSplit.getItems().remove(tableFine); 
                    
                    tableFine = new TableView<>();
                    setupFines();
                }
                else {
                    alertDone("Fine was not able to be paid");
                }
            }
            else {
                alertDone("Value entered was wrong");
            }
        }

    }

    /**
     * Generate a popup.
     * 
     * @param text The text to be displayed.
     */
    private void alertDone(String text) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    /**
     * Setup the transactions table.
     */
    @SuppressWarnings("unchecked")
    private void setupTransactions() {

        transactions = Transactions.getTransactions(user.getUsername());
        for (Payment payment : transactions.getPayments()) {
            transactionData.add(payment);
        }

        // create the table
        TableColumn<Payment,
            String> transCol = new TableColumn<Payment, String>("Transaction");
        transCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));

        TableColumn<Payment,
            String> amountCol = new TableColumn<Payment, String>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Payment,
            String> whenCol = new TableColumn<Payment, String>("When");
        whenCol.setCellValueFactory(new PropertyValueFactory<>("stamp"));

        tableTransaction.setItems(transactionsList);

        tableTransaction.getColumns().addAll(transCol, amountCol, whenCol);
        tableTransaction.autosize();

        transactionsSplit.getItems().add(tableTransaction);

    }

    /**
     * Setup the Balance view.
     */
    private void setupBalance() {
        Pane newLoadedPane;
        try {
            newLoadedPane = FXMLLoader
                .load(getClass().getResource("/fxml/staffBalance.fxml"));
            transactionsSplit.getItems().add(newLoadedPane);
        }
        catch (IOException e) {
            // File not found
            e.printStackTrace();
        }

    }

    /**
     * Initialise the view.
     */
    @FXML
    public void initialize() {
        isStaff = user instanceof Librarian;

        setupFines();

        if (!isStaff) {
            setupTransactions();
        }
        else {
           // setupBalance();
        }
    }
}
