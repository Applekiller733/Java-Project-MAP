import controller.Controller;
import model.ADT.*;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.state.PrgState;
import model.statements.*;
import model.type.IntType;
import model.type.StringType;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.MyIRepository;
import repository.Repository;
import view.TextMenu;
//import view.View;
import view.commands.ExitCommand;
import view.commands.RunExample;

import java.io.BufferedReader;

//
//public class Main {
//    public static void main(String[] args) {
////        IStatement ex1 = new CompStatement(new VarDeclStatement("v",new IntType()),
////                new CompStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))),
////                        new PrintStatement(new VariableExpression("v"))));
////
////        PrgState prg1 = new PrgState(ex1, new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
////                new MyList<String>(), new MyDictionary<StringValue, BufferedReader>());
//        Repository repo = new Repository("logFile.txt");
////        repo.addState(prg1);
//
//        Controller controller = new Controller(repo);
//
//        View view = new View(controller);
//
//        view.display();
////        TextMenu textMenu = new TextMenu();
////        textMenu.addCommand(new ExitCommand("0", "Exit"));
////        textMenu.addCommand(new RunExample("1", ex1.toString(), controller));
////
////        textMenu.show();
//    }
//}

public class Interpreter{
    public static void main(String[] args) {
        IStatement ex1 = new CompStatement(new VarDeclStatement("v",new IntType()),
                new CompStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));

        PrgState state1 = new PrgState(ex1, new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>());
        MyIRepository repo1 = new Repository( "log1.txt");
        repo1.addState(state1);
        Controller ctr1 = new Controller(repo1);

        IStatement test = new CompStatement(
                new VarDeclStatement("varf", new StringType()),
                new CompStatement(new AssignStatement("varf",
                        new ValueExpression(new StringValue("src/text.in"))),
                        new CompStatement(new OpenFileStatement(new VariableExpression("varf")),
                                new CompStatement(new VarDeclStatement("varc", new IntType()),
                                        new CompStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseFileStatement(new VariableExpression("varf"))))))))));
        MyIStack<IStatement> st = new MyStack<IStatement>();
        MyIDictionary<String, IValue> sTable= new MyDictionary<String, IValue>();
        MyIList<IValue> o= new MyList<IValue>();
        MyIDictionary<StringValue, BufferedReader> f= new MyDictionary<>();
        PrgState p= new PrgState(test,st,sTable, o, f);
        MyIRepository r= new Repository("src/logFile.txt");
        r.addState(p);
        Controller ctrfiletest= new Controller(r);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(),ctr1));
        menu.addCommand(new RunExample("2", test.toString(), ctrfiletest));

        menu.show();
    }
}