import controller.Controller;
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
import view.TextMenu;
//import view.View;
import view.commands.ExitCommand;
import view.commands.RunExample;

import java.awt.*;
import java.io.BufferedReader;
import java.util.ArrayList;

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
                new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), new Heap());
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
        IHeap heap = new Heap();
        PrgState p= new PrgState(test,st,sTable, o, f, heap);
        MyIRepository r= new Repository("src/logFile.txt");
        r.addState(p);
        Controller ctrfiletest= new Controller(r);


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
        PrgState crtPrgState3 = new PrgState(ex3, new MyStack<>(), new MyDictionary<>(), new MyList<>(),new MyDictionary<>(), new Heap());
//        List<PrgState> list6 = new ArrayList<>();
//        list6.add(crtPrgState6);
        MyIRepository repo3 = new Repository("src/log3.txt");
        repo3.addState(crtPrgState3);
        Controller ctrl3 = new Controller(repo3);

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
        PrgState crtPrgState4 = new PrgState(ex4, new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new Heap());
//        List<PrgState> list7 = new ArrayList<>();
//        list7.add(crtPrgState7);
        MyIRepository repo4 = new Repository( "src/log4.txt");
        repo4.addState(crtPrgState4);
        Controller ctrl4 = new Controller(repo4);

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
        PrgState crtPrgState5 = new PrgState(ex5, new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new Heap());
//        List<PrgState> list8 = new ArrayList<>();
//        list8.add(crtPrgState8);
        MyIRepository repo5 = new Repository("src/log5.txt");
        repo5.addState(crtPrgState5);
        Controller ctrl5 = new Controller(repo5);

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
        PrgState crtPrgState6 = new PrgState(ex6, new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new Heap());
//        List<PrgState> list9 = new ArrayList<>();
//        list9.add(crtPrgState9);
        MyIRepository repo6 = new Repository("src/log6.txt");
        repo6.addState(crtPrgState6);
        Controller ctrl6 = new Controller(repo6);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(),ctr1));
        menu.addCommand(new RunExample("2", test.toString(), ctrfiletest));
        menu.addCommand(new RunExample("3", ex3.toString(), ctrl3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctrl4));
        menu.addCommand(new RunExample("5", ex5.toString(), ctrl5));
        menu.addCommand(new RunExample("6", ex6.toString(), ctrl6));

        menu.show();
    }
}