package view.GUI.mainwindow;

import controller.Controller;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import model.ADT.MyIDictionary;
import model.ADT.MyIList;
import model.ADT.MyIStack;
import model.state.PrgState;
import model.statements.IStatement;
import model.value.IValue;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Mainwindow {

    @FXML
    private TextField NumOfPrgStates;

    @FXML
    private ListView<String> ExecStackListView;

    @FXML
    private TableView<Pair<String, IValue>> SymtableTableView;

    @FXML
    private TableColumn<Pair<String, IValue>, String> SymNameColumn;

    @FXML
    private TableColumn<Pair<String, IValue>, String> SymValueColumn;

    @FXML
    private TableView<Pair<Integer, IValue>> HeapTableView;

    @FXML
    private TableColumn<Pair<Integer, IValue>, Integer> HeapAddressColumn;

    @FXML
    private TableColumn<Pair<Integer, IValue>, String> HeapNameColumn;

    @FXML
    private ListView<String> FiletableListView;

    @FXML
    private ListView<Integer> PrgStatesListView;

    @FXML
    private ListView<String> OutListView;

    @FXML
    private Button OneStepButton;

//    @FXML
//    private Button AllStepButton;

    private Controller controller;


    public void initialize(IStatement selectedProgram) {
        PrgStatesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateExecStack();
                populateSymTable();
            }
        });
    }

    public PrgState getSelectedProgramState() {
        try{
            if (controller.getCurrentProgramStates().isEmpty()){
                return null;
            }
            int selectedIndex = PrgStatesListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1 || selectedIndex > controller.getCurrentProgramStates().size()){
                return controller.getCurrentProgramStates().get(0);
            }
            return controller.getCurrentProgramStates().get(selectedIndex);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
        if (controller != null) {
            populateAll();
        }
    }

    public void populateAll(){
        populateExecStack();
        populateSymTable();
        populateHeapTable();
        populateFileTableListView();
        populatePrgStatesTable();
        populateOutListView();
        populateNumOfPrgStates();
    }

    public void populateNumOfPrgStates() {
        try{
            NumOfPrgStates.setText(String.valueOf(controller.getCurrentProgramStates().size()));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            NumOfPrgStates.setText("0");
        }
    }

    public void populateExecStack(){
        PrgState currentProgramState = this.getSelectedProgramState();
        if (currentProgramState == null){
            ExecStackListView.getItems().clear();
            return ;
        }
        MyIStack<IStatement> execStack = currentProgramState.getExecStack();
        ArrayList<IStatement> execList = new ArrayList<>(execStack.getStack());
        ExecStackListView.getItems().clear();
        for (IStatement statement : execStack.getStack()){
            ExecStackListView.getItems().add(statement.toString());
        }
    }

    public void populateSymTable(){
        PrgState currentProgramState = this.getSelectedProgramState();
        if (currentProgramState == null){
            SymtableTableView.getItems().clear();
            return ;
        }
        int selectedIndex = PrgStatesListView.getSelectionModel().getSelectedIndex();

        MyIDictionary<String, IValue> symTable = currentProgramState.getSymTable();
        try {
            ObservableList<Pair<String, IValue>> symTableData = FXCollections.observableArrayList(
                    symTable.getAll().entrySet().stream()
                            .map(pair -> new Pair<>(pair.getKey(), pair.getValue()))
                            .toList()
            );
            SymNameColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getKey()));
            SymValueColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getValue().toString()));
            SymtableTableView.setItems(symTableData);
            SymtableTableView.refresh();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void populateHeapTable(){
        try {
            if (controller.getCurrentProgramStates().isEmpty())
            {
                System.out.println("No program states available");
                HeapTableView.getItems().clear();
                return ;
            }

            ObservableList<Pair<Integer, IValue>> heapTableData = FXCollections.observableArrayList(
                    controller.getCurrentProgramStates().get(0)
                            .getHeap().getContent().entrySet().
                            stream().map(pair -> new Pair<>(pair.getKey(), pair.getValue()))
                            .toList()
            );
            HeapAddressColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getKey()));
            HeapNameColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getValue().toString()));
            HeapTableView.setItems(heapTableData);
            HeapTableView.refresh();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void populateFileTableListView(){
        try{

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void populatePrgStatesTable() {
        try {
            if (controller.getCurrentProgramStates().isEmpty()){
                System.out.println("No program states available");
                PrgStatesListView.getItems().clear();
                return ;
            }
            PrgStatesListView.getItems().clear();
            for (PrgState state : controller.getCurrentProgramStates()){
                if (!state.getExecStack().isEmpty()){
                    PrgStatesListView.getItems().add(state.getId());
                }
            }

            if (PrgStatesListView.getSelectionModel().getSelectedIndex() == -1){
                PrgStatesListView.getSelectionModel().selectFirst();
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void populateOutListView() {
        PrgState currentProgramState = this.getSelectedProgramState();
        if (currentProgramState == null){
            OutListView.getItems().clear();
            return ;
        }
        MyIList<IValue> OutList = currentProgramState.getOutputList();
        ArrayList<String> outListContent = new ArrayList<>();
        for (IValue obj : OutList.getElements()){
            outListContent.add(obj.toString());
        }
        OutListView.getItems().clear();
        for (String str : outListContent){
            OutListView.getItems().add(str);
        }
    }

    public void OneStepButtonHandler(ActionEvent event) {
        try {
            if (controller.getCurrentProgramStates().isEmpty()){
                System.out.println("No program states available");
                return;
            }

            int selectedIndex = PrgStatesListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1 || selectedIndex >= controller.getCurrentProgramStates().size()){
                System.out.println("Invalid selected program state");
            }
            controller.oneStepForAllPrg(controller.getCurrentProgramStates());
            populateAll();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

//    public void AllStepButtonHandler(ActionEvent event){
//        try{
//            controller.allStep();
//            populateAll();
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//    }


}
