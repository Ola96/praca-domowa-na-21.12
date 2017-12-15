package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;


public class DaneOsobowe implements HierarchicalController<MainController> {

    public TextField imie;
    public TextField nazwisko;
    public TextField pesel;
    public TextField indeks;
    public TableView<Student> tabelka;
    private MainController parentController;

    public void dodaj(ActionEvent actionEvent) {
        Student st = new Student();
        st.setName(imie.getText());
        st.setSurname(nazwisko.getText());
        st.setPesel(pesel.getText());
        st.setIdx(indeks.getText());
        tabelka.getItems().add(st);
    }

    public void setParentController(MainController parentController) {
        this.parentController = parentController;
        tabelka.getItems().addAll(parentController.getDataContainer().getStudents());
        tabelka.setEditable(true);
    }

    public void usunZmiany() {
        tabelka.getItems().clear();
        tabelka.getItems().addAll(parentController.getDataContainer().getStudents());
    }

    public MainController getParentController() {
        return parentController;
    }

    public void initialize() {
        for (TableColumn<Student, ?> studentTableColumn : tabelka.getColumns()) {
            if ("imie".equals(studentTableColumn.getId())) {
                TableColumn<Student, String> imieColumn = (TableColumn<Student, String>) studentTableColumn;
                imieColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                imieColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                imieColumn.setOnEditCommit((val)->{
                    val.getTableView().getItems().get(val.getTablePosition().getRow()).setName(val.getNewValue());
                });
            } else if ("nazwisko".equals(studentTableColumn.getId())) {
                studentTableColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
                ((TableColumn<Student, String>) studentTableColumn).setCellFactory(TextFieldTableCell.forTableColumn());
            } else if ("pesel".equals(studentTableColumn.getId())) {
                studentTableColumn.setCellValueFactory(new PropertyValueFactory<>("pesel"));
                ((TableColumn<Student, String>) studentTableColumn).setCellFactory(TextFieldTableCell.forTableColumn());
            } else if ("indeks".equals(studentTableColumn.getId())) {
                studentTableColumn.setCellValueFactory(new PropertyValueFactory<>("idx"));
                ((TableColumn<Student, String>) studentTableColumn).setCellFactory(TextFieldTableCell.forTableColumn());
            }
        }

    }

  public void synchronizuj(ActionEvent actionEvent) {
        parentController.getDataContainer().setStudents(tabelka.getItems());
    }
    public void dodajJesliEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            dodaj(new ActionEvent(keyEvent.getSource(), keyEvent.getTarget()));
        }
    }
}
