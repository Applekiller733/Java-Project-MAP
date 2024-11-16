//package view;
//
//import controller.Controller;
//import exceptions.ControllerException;
//import model.ADT.*;
//import model.expressions.ArithmeticExpression;
//import model.expressions.ArithmeticOperation;
//import model.expressions.ValueExpression;
//import model.expressions.VariableExpression;
//import model.state.PrgState;
//import model.statements.*;
//import model.type.BoolType;
//import model.type.IntType;
//import model.value.BoolValue;
//import model.value.IValue;
//import model.value.IntValue;
//import model.value.StringValue;
//
//import java.io.BufferedReader;
//import java.util.Scanner;
//
//public class View {
//    private Controller controller;
//    public View(Controller controller) {
//        this.controller = controller;
//    }
//    public void display(){
//        while (true){
//            MyIStack<IStatement> stack = new MyStack<IStatement>();
//            MyIDictionary<String, IValue> symtbl = new MyDictionary<String, IValue>();
//            MyIList<String> outlist = new MyList<String>();
//            MyIDictionary<StringValue, BufferedReader> filetbl = new MyDictionary<StringValue,
//                                                                    BufferedReader>();
//
//            System.out.println("1.input a program\n2.complete execution of program\n9.exit\n");
//            Scanner sc = new Scanner(System.in);
//            int choice = sc.nextInt();
//            switch(choice){
//                case 1:
//                    System.out.println("1.choose prebuilt program\n2.write program\n");
//                    int choice2 = sc.nextInt();
//                    if (choice2 == 1){
//                        System.out.println("1.Program1: int v; v=2;Print(v)\n" +
//                                "2.Program2:int a;int b; a=2+3*5;b=a+1;Print(b)\n" +
//                                "3.Program3:bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)");
//                        int choice3 = sc.nextInt();
//                        IStatement ex;
//                        if (choice3 == 1){
//                            ex= new CompStatement(new VarDeclStatement("v",new IntType()),
//                                    new CompStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))),
//                                            new PrintStatement(new VariableExpression("v"))));
//                        }
//                        else if (choice3 == 2){
//                            ex = new CompStatement( new VarDeclStatement("a",new IntType()),
//                                    new CompStatement(new VarDeclStatement("b",new IntType()),
//                                            new CompStatement(new AssignStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)),
//                                                    ArithmeticOperation.PLUS,
//                                                    new ArithmeticExpression(new ValueExpression(new IntValue(3)),
//                                                            ArithmeticOperation.MULTIPLY,
//                                                            new ValueExpression(new IntValue(5))))),
//                                                    new CompStatement(new AssignStatement("b",
//                                                            new ArithmeticExpression(new VariableExpression("a"), ArithmeticOperation.PLUS,
//                                                                    new ValueExpression(new IntValue(1)))),
//                                                            new PrintStatement(new VariableExpression("b"))))));
//                        }
//                        else {
//                            ex = new CompStatement(new VarDeclStatement("a",new BoolType()),
//                                    new CompStatement(new VarDeclStatement("v", new IntType()),
//                                            new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
//                                                    new CompStatement(new IfStatement(new VariableExpression("a"),
//                                                            new AssignStatement("v",
//                                                                    new ValueExpression(new IntValue(2))),
//                                                            new AssignStatement("v",
//                                                                    new ValueExpression(new IntValue(3)))),
//                                                            new PrintStatement(new VariableExpression("v"))))));
//                        }
////                        stack.push(ex);
////                        PrgState initstate = new PrgState(ex, stack, symtbl, outlist, filetbl);
//                        PrgState initstate = new PrgState(ex, stack, symtbl, outlist, filetbl);
////                        System.out.println(initstate.toString());
//                        controller.addState(initstate);
//                    }
//                    else{
//
//                    }
//                    break;
//                case 2:
//                    try {
//                        controller.allStep();
//                    } catch (ControllerException e) {
//                        System.out.println(e.getMessage());
//                    }
////                    System.out.println("Program ran successfully!\n");
//                    break;
//                    case 9:
//                        System.exit(0);
//                        break;
//                        default:
//                            System.out.println("Invalid choice");
//                            break;
//            }
//        }
//    }
//}
