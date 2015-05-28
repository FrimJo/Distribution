package distribution;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;


public class TableViewSample {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Value,String> firstnameCol;

    @FXML
    private TableColumn<Value,String> lastNameCol;
    
    @FXML
    private TableView<Value> table = new TableView<Value>();

    private final ObservableList<Value> data =
            FXCollections.observableArrayList(
                new Value("Jacob", "Smith", "jacob.smith@example.com"),
                new Value("Isabella", "Johnson", "isabella.johnson@example.com"),
                new Value("Ethan", "Williams", "ethan.williams@example.com"),
                new Value("Emma", "Jones", "emma.jones@example.com"),
                new Value("Michael", "Brown", "michael.brown@example.com")
            );
    
    @FXML
    void initialize() {
        assert firstnameCol != null : "fx:id=\"firstnameCol\" was not injected: check your FXML file 'Main.fxml'.";
        assert lastNameCol != null : "fx:id=\"lastNameCol\" was not injected: check your FXML file 'Main.fxml'.";

        
        
        this.table.setEditable(true);
        this.table.setItems(this.data);
        
        Callback<TableColumn<Value,String>, TableCell<Value,String>> cellFactory =
                new Callback<TableColumn<Value,String>, TableCell<Value,String>>() {
                    public TableCell<Value,String> call(TableColumn<Value,String> p) {
                       return new EditingCell();
                    }
                };
                
        this.firstnameCol.setCellValueFactory(new PropertyValueFactory<Value,String>("firstName"));

        firstnameCol.setCellFactory(cellFactory);
        firstnameCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Value, String>>() {
                @Override
                public void handle(CellEditEvent<Value, String> t) {
                    ((Value) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setFirstName(t.getNewValue());
                }
             }
        );
        
        this.lastNameCol.setCellValueFactory(new PropertyValueFactory<Value,String>("lastName"));
        

    }
    
    public static class Value {
    	 
        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;
 
        private Value(String fName, String lName, String email) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
        }
 
        public String getFirstName() {
            return firstName.get();
        }
 
        public void setFirstName(String fName) {
            firstName.set(fName);
        }
 
        public String getLastName() {
            return lastName.get();
        }
 
        public void setLastName(String fName) {
            lastName.set(fName);
        }
 
        public String getEmail() {
            return email.get();
        }
 
        public void setEmail(String fName) {
            email.set(fName);
        }
    }
    
    class EditingCell extends TableCell<Value, String> {
    	 
        private TextField textField;
 
        public EditingCell() {
        }
 
        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }
 
        @Override
        public void cancelEdit() {
            super.cancelEdit();
 
            setText((String) getItem());
            setGraphic(null);
        }
 
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }
 
        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
            textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, 
                    Boolean arg1, Boolean arg2) {
                        if (!arg2) {
                            commitEdit(textField.getText());
                        }
                }
            });
        }
 
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

}
