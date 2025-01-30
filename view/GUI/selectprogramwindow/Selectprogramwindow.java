package view.GUI.selectprogramwindow;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.ADT.*;
import model.expressions.*;
import model.state.PrgState;
import model.statements.*;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.MyIRepository;
import repository.Repository;
import view.GUI.mainwindow.Mainwindow;
import view.commands.RunExample;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Selectprogramwindow implements Initializable {

    private Mainwindow mainwindow;
    private ArrayList<IStatement> programsList;
    private IStatement selectedProgram;

    @FXML
    private ListView<IStatement> selectableProgramsList;

    @FXML
    private Button selectProgramButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeProgramsList();
        for (IStatement program : programsList) {
            selectableProgramsList.getItems().add(program);
        }

    }

    public void initializeProgramsList(){
        programsList = new ArrayList<>();

        IStatement ex1 = new CompStatement(new VarDeclStatement("v",new IntType()),
                new CompStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
        programsList.add(ex1);

        IStatement ex2 = new CompStatement(
                new VarDeclStatement("varf", new StringType()),
                new CompStatement(new AssignStatement("varf",
                        new ValueExpression(new StringValue("src/log2.txt"))),
                        new CompStatement(new OpenFileStatement(new VariableExpression("varf")),
                                new CompStatement(new VarDeclStatement("varc", new IntType()),
                                        new CompStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseFileStatement(new VariableExpression("varf"))))))))));
        programsList.add(ex2);

        IStatement ex3 = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a"))
                                        )
                                )
                        )
                )
        );
        programsList.add(ex3);

        IStatement ex4 = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                new CompStatement(
                                        new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(
                                                new ArithmeticExpression(
                                                        new ReadHeapExpression(new VariableExpression("v")),
                                                        ArithmeticOperation.PLUS,
                                                        new ValueExpression(new IntValue(5))
                                                )
                                        )
                                )
                        )
                )
        );
        programsList.add(ex4);


        IStatement ex5 = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompStatement(
                                                new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(
                                                        new ReadHeapExpression(
                                                                new ReadHeapExpression(new VariableExpression("a"))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        programsList.add(ex5);


        IStatement ex6 = new CompStatement(
                new VarDeclStatement("v", new IntType()),
                new CompStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompStatement(
                                new WhileStatement(
                                        new RelationalExpression(
                                                new VariableExpression("v"),
                                                RelationalOperation.GREATER,
                                                new ValueExpression(new IntValue(0))
                                        ),
                                        new CompStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignStatement("v",
                                                        new ArithmeticExpression(
                                                                new VariableExpression("v"),
                                                                ArithmeticOperation.MINUS,
                                                                new ValueExpression(new IntValue(1))
                                                        )
                                                )
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );
        programsList.add(ex6);


        IStatement ex7= new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new VarDeclStatement("a", new RefType(new IntType())),
                        new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompStatement(new HeapAllocationStatement("a",new ValueExpression(new IntValue(22))),
                                        new CompStatement(
                                                new ForkStatement(
                                                        new CompStatement(new WriteHeapStatement("a",new ValueExpression(new IntValue(30))),
                                                                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                                        new CompStatement(
                                                                                new PrintStatement(new VariableExpression("v")),
                                                                                new PrintStatement(new ReadHeapExpression
                                                                                        (new VariableExpression("a")))
                                                                        )
                                                                )

                                                        )),
                                                new CompStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))
                                        )
                                )
                        )
                )
        );

       programsList.add(ex7);
/*
       //sleep example
        IStatement ex8 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                        new CompStatement(new ForkStatement(new CompStatement(new AssignStatement("v",
                                new ArithmeticExpression( new VariableExpression("v"), ArithmeticOperation.MINUS, new ValueExpression(new IntValue(1)))),
                                new CompStatement(new AssignStatement("v", new ArithmeticExpression( new VariableExpression("v"), ArithmeticOperation.MINUS, new ValueExpression(new IntValue(1)))),
                                        new PrintStatement(new VariableExpression("v"))))),
                                new CompStatement(new SleepStatement(10), new PrintStatement(new ArithmeticExpression(new VariableExpression("v"), ArithmeticOperation.PLUS, new ValueExpression(new IntValue(10))))))));

        programsList.add(ex8);

        //switch example
        // switch example
        IStatement ex9 = new CompStatement(new VarDeclStatement("a", new IntType()),
                new CompStatement(new VarDeclStatement("b", new IntType()),
                        new CompStatement(new VarDeclStatement("c", new IntType()),
                                new CompStatement(new AssignStatement("a", new ValueExpression(new IntValue(1))),
                                        new CompStatement(new AssignStatement("b", new ValueExpression(new IntValue(2))),
                                                new CompStatement(new AssignStatement("c", new ValueExpression(new IntValue(5))),
                                                        new CompStatement(new SwitchStatement(
                                                                new ArithmeticExpression(new VariableExpression("a"), ArithmeticOperation.MULTIPLY, new ValueExpression(new IntValue(10))),
                                                                new ArithmeticExpression(new VariableExpression("b"), ArithmeticOperation.MULTIPLY ,new VariableExpression("c")),
                                                                new CompStatement(new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b"))),
                                                                new ValueExpression(new IntValue(10)),
                                                                new CompStatement(new PrintStatement(new ValueExpression(new IntValue(100))), new PrintStatement(new ValueExpression(new IntValue(200)))),
                                                                new PrintStatement(new ValueExpression(new IntValue(300)))
                                                        ), new PrintStatement(new ValueExpression(new IntValue(300))))))))));

        programsList.add(ex9);

        //for example
        // for example
        IStatement ex10 = new CompStatement(new VarDeclStatement("a", new RefType(new IntType())),
                new CompStatement(new HeapAllocationStatement("a", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("v", new IntType()),
                                new CompStatement(new ForStatement(new ForkStatement(
                                        new CompStatement(new PrintStatement(new VariableExpression("v")),
                                                new AssignStatement("v", new ArithmeticExpression(new VariableExpression("v"), ArithmeticOperation.MULTIPLY, new ReadHeapExpression(new VariableExpression("a")))))),
                                        "v", new ValueExpression(new IntValue(0)), new ValueExpression(new IntValue(3)),
                                        new ArithmeticExpression(new VariableExpression("v"), ArithmeticOperation.PLUS,new ValueExpression(new IntValue(1)))),
                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))));

        programsList.add(ex10);

        //wait example
        // wait example
        IStatement ex11 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new PrintStatement(new VariableExpression("v")),
                                new CompStatement(new WaitStatement(10),
                                        new PrintStatement(new ArithmeticExpression(new VariableExpression("v"), ArithmeticOperation.MULTIPLY,new ValueExpression(new IntValue(10))))))));

        programsList.add(ex11);
*/
        //conditional assignment example
        // cond ass example
        IStatement ex12 = new CompStatement(
                new VarDeclStatement("a", new RefType(new IntType())),
                new CompStatement(
                        new VarDeclStatement("b", new RefType(new IntType())),
                        new CompStatement(
                                new VarDeclStatement("v", new IntType()),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new ValueExpression(new IntValue(0))),
                                        new CompStatement(
                                                new HeapAllocationStatement("b", new ValueExpression(new IntValue(0))),
                                                new CompStatement(
                                                        new WriteHeapStatement("a", new ValueExpression(new IntValue(1))),
                                                        new CompStatement(
                                                                new WriteHeapStatement("b", new ValueExpression(new IntValue(2))),
                                                                new CompStatement(
                                                                        new ConditionalAssignmentStatement(
                                                                                "v",
                                                                                new RelationalExpression( new ReadHeapExpression(new VariableExpression("a")), RelationalOperation.LOWER, new ReadHeapExpression(new VariableExpression("b"))),
                                                                                new ValueExpression(new IntValue(100)),
                                                                                new ValueExpression(new IntValue(200))
                                                                        ),
                                                                        new CompStatement(
                                                                                new PrintStatement(new VariableExpression("v")),
                                                                                new CompStatement(
                                                                                        new ConditionalAssignmentStatement(
                                                                                                "v",
                                                                                                new RelationalExpression(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("b")), ArithmeticOperation.MINUS,new ValueExpression(new IntValue(2))), RelationalOperation.GREATER, new ReadHeapExpression(new VariableExpression("a"))),
                                                                                                new ValueExpression(new IntValue(100)),
                                                                                                new ValueExpression(new IntValue(200))
                                                                                        ),
                                                                                        new PrintStatement(new VariableExpression("v"))
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        programsList.add(ex12);


        IStatement ex13 = new CompStatement(new VarDeclStatement("v1", new RefType(new IntType())),
                new CompStatement(new VarDeclStatement("v2", new RefType(new IntType())),
                        new CompStatement(new VarDeclStatement("v3", new RefType(new IntType())),
                                new CompStatement(new VarDeclStatement("cnt", new IntType()),
                                        new CompStatement(new HeapAllocationStatement("v1", new ValueExpression(new IntValue(2))),
                                                new CompStatement(new HeapAllocationStatement("v2", new ValueExpression(new IntValue(3))),
                                                        new CompStatement(new HeapAllocationStatement("v3", new ValueExpression(new IntValue(4))),
                                                                new CompStatement(new NewLatchStatement("cnt", new ReadHeapExpression(new VariableExpression("v2"))),
                                                                        new CompStatement(new ForkStatement(new CompStatement(new WriteHeapStatement("v1", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")), ArithmeticOperation.MULTIPLY ,new ValueExpression(new IntValue(10)))),
                                                                                new CompStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
                                                                                        new CompStatement(new CountDownStatement("cnt"),
                                                                                                new ForkStatement(new CompStatement(new WriteHeapStatement("v2", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v2")), ArithmeticOperation.MULTIPLY,new ValueExpression(new IntValue(10)))),
                                                                                                        new CompStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v2"))),
                                                                                                                new CompStatement(new CountDownStatement("cnt"),
                                                                                                                        new ForkStatement(new CompStatement(new WriteHeapStatement("v3", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v3")), ArithmeticOperation.MULTIPLY,new ValueExpression(new IntValue(10)))),
                                                                                                                                new CompStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v3"))),
                                                                                                                                        new CountDownStatement("cnt")
                                                                                                                                )
                                                                                                                        )
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                        ),
                                                                                new CompStatement(new AwaitStatement("cnt"),
                                                                                        new CompStatement(new PrintStatement(new ValueExpression(new IntValue(100))),
                                                                                                new CompStatement(new CountDownStatement("cnt"),
                                                                                                        new PrintStatement(new ValueExpression(new IntValue(100)))
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        programsList.add(ex13);
    }

    @FXML
    public void onSelectProgramButtonPress(ActionEvent event) {
        int selectedIndex = this.selectableProgramsList.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No program selected!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        else
        {
            selectedProgram = this.programsList.get(selectedIndex);
            try {
                selectedProgram.typecheck(new MyDictionary<>());

            }
            catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error while typechecking:" + e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
                return;
            }
            PrgState programstate = new PrgState(selectedProgram, new MyStack<>(), new MyDictionary<>(),
                    new MyList<>(), new MyDictionary<>(), new Heap(), new MyLatchTable());
            MyIRepository repository = new Repository("log" + selectedIndex +".txt");
            repository.addState(programstate);
            Controller controller = new Controller(repository);
            try {
                openMainWindow(controller);
            }
            catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Could not open main window " + e.getMessage(), ButtonType.OK);
                alert.showAndWait();
                return;
            }
        }
    }

    public void openMainWindow(Controller cont) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../mainwindow/mainwindow.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Mainwindow mainwindow = loader.getController();
        mainwindow.setController(cont);
        mainwindow.initialize(selectedProgram);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(500);

        stage.setTitle("Java Toy Language Interpreter - Program Execution Window");
        stage.show();
    }
}
