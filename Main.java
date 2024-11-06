import controller.Controller;
import model.ADT.MyDictionary;
import model.ADT.MyList;
import model.ADT.MyStack;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.state.PrgState;
import model.statements.*;
import model.type.IntType;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.Repository;
import view.TextMenu;
import view.View;
import view.commands.ExitCommand;
import view.commands.RunExample;

import java.io.BufferedReader;


public class Main {
    public static void main(String[] args) {
//        IStatement ex1 = new CompStatement(new VarDeclStatement("v",new IntType()),
//                new CompStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))),
//                        new PrintStatement(new VariableExpression("v"))));
//
//        PrgState prg1 = new PrgState(ex1, new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
//                new MyList<String>(), new MyDictionary<StringValue, BufferedReader>());
        Repository repo = new Repository("logFile.txt");
//        repo.addState(prg1);

        Controller controller = new Controller(repo);

        View view = new View(controller);

        view.display();
//        TextMenu textMenu = new TextMenu();
//        textMenu.addCommand(new ExitCommand("0", "Exit"));
//        textMenu.addCommand(new RunExample("1", ex1.toString(), controller));
//
//        textMenu.show();
    }
}