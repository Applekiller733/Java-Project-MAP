package view;

import controller.Controller;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.statements.*;
import model.type.IntType;
import model.value.IntValue;

import java.util.Scanner;

public class View {
    private Controller controller;
    public void display(){
        while (true){
            System.out.println("1.input a program\n2.complete execution of program\n9.exit\n");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch(choice){
                case 1:
                    System.out.println("1.choose prebuilt program\n2.write program\n");
                    int choice2 = sc.nextInt();
                    if (choice2 == 1){
                        System.out.println("1.Program1: int v; v=2;Print(v)\n" +
                                "2.Program2:int a;int b; a=2+3*5;b=a+1;Print(b)\n" +
                                "3.Program3:bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)");
                        int choice3 = sc.nextInt();
                        if (choice3 == 1){
                            IStatement ex1= new CompStatement(new VarDeclStatement("v",new IntType()),
                                    new CompStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))),
                                            new PrintStatement(new VariableExpression("v"))));
                        }
                        else if (choice3 == 2){

                        }
                        else if (choice3 == 3){

                        }
                    }
                    else{

                    }
                    break;
                case 2:
                    controller.allStep();
                    break;
                    case 9:
                        System.exit(0);
                        break;
                        default:
                            System.out.println("Invalid choice");
                            break;
            }
        }
    }
}
