/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerconstruction01;

import SyntaxAnalyzer.SyntaxAnalyzerMain;
import compilerconstruction01.reserveWords.DataType;
import compilerconstruction01.reserveWords.Repeat;
import compilerconstruction01.reserveWords.remainingWords;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import operator.*;
import punctuators.punctuators;
import semmentic.SemmenticMainClass;

public class CompilerConstruction01 {
    
    
    public static LinkedList tokens ;

    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        tokens = new LinkedList();
        Scanner inp = new Scanner(System.in);
//TAKING INPUT FILE NAME
        System.out.print("ENTER INPUT FILE NAME: ");
        //String fileName = inp.nextLine();
         String fileName = "s.3";
//TAKING OUTPUT FILE NAME
        System.out.print("ENTER OUTPUT FILE NAME: ");
        //String outFile = inp.nextLine();
        String outFile = "e";

        try {
            String content = "This is the content to write into file";

            File file = new File("outputs/" + outFile + ".txt");
          
            PushbackReader fileInput
                    = new PushbackReader(new FileReader("resources/" + fileName + ".txt"));
            FileReader fr = new FileReader("resources/" + fileName + ".txt");
            LineNumberReader lnr = new LineNumberReader(fr);

            if (file.createNewFile()) {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }
//bw WILL BE USED TO WRITE FILE INFO
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            //bw.write(content);

//char BY char FILE DATA IS TAKEN FROM FILE IN fileInp
            int fileInp;
            String temp = "", temp2 = "";

            boolean found = false, dFlag = false;

            punctuators puncObj = new punctuators();
            DataType dataType = new DataType();

            List<Character> escapeSeq = Arrays.asList('r', 't', 'n', '\\', '\'', 'f');

            lnr.readLine();
//while WILL LOOP UNTIL END OF FILE IS FOUND
            while ((fileInp = fileInput.read()) != -1) {

                dFlag = false;

//charInp HAS FILE DATA AS char
                char charInp = (char) fileInp;
                if (puncObj.getPunct().contains(charInp) || charInp == ' '  || charInp == '\r' || fileInp == 10 || isOperator(charInp)) {
//System.out.println("\n temp:" + temp);
//CHECCKING FOR FLOAT (decimal ) AND .
                    if (charInp == '.') {
                        //temp2 += '.';
//CHECK IF THE STRING BEHIND . IS NUMBER                        
                        if (isNumber(temp) || temp.equals("")) {
                            dFlag = true;
                            while ((fileInp = fileInput.read()) != -1) {
                                //            System.out.println("temp2: " + fileInp);
                                charInp = (char) fileInp;

                                if (puncObj.getPunct().contains(charInp) || charInp == ' ' || charInp == '.' || (charInp >= 'a' && charInp <= 'z' || charInp >= 'z' && charInp <= 'z')  || charInp == '\r' || isOperator(charInp) || fileInp == 10) {
//CHECK IF THE STRING AFTER . IS ALSO A NUMBER IF YES, PRINT FLOAT 
                                    fileInput.unread(fileInp);

                                    if (isNumber(temp2)) {
                                        //                 System.out.println("temp2: " + temp2);
                                        System.out.println("(float constant" + "," + temp + "." + temp2 + " ," + lnr.getLineNumber() + ")");
                                        tokens.add(new String("(float constant" + "," + temp + "." + temp2 + " ," + lnr.getLineNumber() + ")"));
                                    } else if (temp.equals("")) {
                                        System.out.println("(." + "," + " ," + lnr.getLineNumber() + ")");
                                        tokens.add(new String("(." + "," + " ," + lnr.getLineNumber() + ")"));
                                        if (isReserveWord(temp2)) {
                                            if (dataType.getDataType().contains(temp2)) {
                                                System.out.println("(DT," + temp2 + "," + lnr.getLineNumber() + ")");
                                                tokens.add(new String("(DT," + temp2 + "," + lnr.getLineNumber() + ")"));
                                            } else if (dataType.getAccessModifier().contains(temp2)) {
                                                System.out.println("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");
                                                tokens.add("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");
                                            } else {
                                                System.out.println("(" + temp2 + ", ," + lnr.getLineNumber() + ")");
                                                tokens.add("(" + temp2 + ", ," + lnr.getLineNumber() + ")");
                                            }
                                            
                                        } 
                                        else if (dataType.getAccessModifier().contains(temp2)) {
                                                System.out.println("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");
                                                tokens.add("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");}
                                    else if (dataType.getAccessModifier().contains(temp2)) {
                                                System.out.println("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");
                                                tokens.add("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");}
                                    
                                        else if (!temp2.equals("")) {
                                            System.out.println("(invalid lexemehh," + temp2 + " ," + lnr.getLineNumber() + ")");
                                        }
                                    }else if (dataType.getAccessModifier().contains(temp2)) {
                                                System.out.println("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");
                                                tokens.add("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");}
                                    else if (dataType.getAccessModifier().contains(temp)) {
                                                System.out.println("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");
                                                tokens.add("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");}
                                    
                                    else if(temp.matches("[a-zA-Z0-9]*") && !temp.equals("")){
                            System.out.println("(ID," + temp + "," + lnr.getLineNumber() + ")");
                            tokens.add("(ID," + temp + "," + lnr.getLineNumber() + ")");
                        }
                                    else if (!temp.equals("")) {
                                        System.out.println("(integer constant" + "," + temp + " ," + lnr.getLineNumber() + ")");
                                        tokens.add("(integer constant" + "," + temp + " ," + lnr.getLineNumber() + ")");
                                        System.out.println("(." + "," + " ," + lnr.getLineNumber() + ")");
                                        tokens.add("(." + "," + " ," + lnr.getLineNumber() + ")");
                                        if (isReserveWord(temp2)) {
                                            if (dataType.getDataType().contains(temp2)) {
                                                System.out.println("(DT," + temp2 + "," + lnr.getLineNumber() + ")");
                                                tokens.add("(DT," + temp2 + "," + lnr.getLineNumber() + ")");
                                            } else if (dataType.getAccessModifier().contains(temp2)) {
                                                System.out.println("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");
                                                tokens.add("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");
                                            } else {
                                                System.out.println("(" + temp2 + ", ," + lnr.getLineNumber() + ")");
                                                tokens.add("(" + temp2 + ", ," + lnr.getLineNumber() + ")");
                                            }
                                        } else if (dataType.getAccessModifier().contains(temp2)) {
                                                System.out.println("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");
                                                tokens.add("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");}
                                    else if (!temp2.equals("")) {
                                            System.out.println("(invalid lexemeh," + temp2 + "," + lnr.getLineNumber() + ")");
                                        }
                                    }
                                    break;
                                } else {
                                    temp2 += charInp;
                                }
                            }
                        } else if (isReserveWord(temp)) {
                            if (dataType.getDataType().contains(temp)) {
                                System.out.println("(DT," + temp + "," + lnr.getLineNumber() + ")");
                                tokens.add("(DT," + temp + "," + lnr.getLineNumber() + ")");
                            } else if (dataType.getAccessModifier().contains(temp)) {
                                System.out.println("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");
                                tokens.add("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");
                            } else {
                                System.out.println("(" + temp + ", ," + lnr.getLineNumber() + ")");
                                tokens.add("(" + temp + ", ," + lnr.getLineNumber() + ")");
                            }
                        } else if (dataType.getAccessModifier().contains(temp2)) {
                                                System.out.println("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");
                                                tokens.add("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");}
                                    else if (!temp.equals("")) {
                            System.out.println("(invalid_lexeme," + temp + " ," + lnr.getLineNumber() + ")");
                            tokens.add("(invalid_lexeme," + temp + " ," + lnr.getLineNumber() + ")");
                        }
                    } else if (charInp == '\'') {
                        if (isReserveWord(temp)) {
                            if (dataType.getDataType().contains(temp)) {
                                System.out.println("(DT," + temp + "," + lnr.getLineNumber() + ")");
                                tokens.add("(DT," + temp + "," + lnr.getLineNumber() + ")");
                            } else if (dataType.getAccessModifier().contains(temp)) {
                                System.out.println("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");
                                tokens.add("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");
                            } else {
                                System.out.println("(" + temp + ", ," + lnr.getLineNumber() + ")");
                                tokens.add("(" + temp + ", ," + lnr.getLineNumber() + ")");
                            }
                        } else if (isNumber(temp)) {
                            System.out.println("(integer constant" + "," + temp + " ," + lnr.getLineNumber() + ")");
                            tokens.add("(integer constant" + "," + temp + " ," + lnr.getLineNumber() + ")");
                        }else if (dataType.getAccessModifier().contains(temp)) {
                                                System.out.println("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");
                                                tokens.add("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");}
                          else if (dataType.getAccessModifier().contains(temp2)) {
                                                System.out.println("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");
                                                tokens.add("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");}
                                              
                        else if(temp.matches("[a-zA-Z0-9]*") && !temp.equals("")){
                            System.out.println("(ID," + temp + "," + lnr.getLineNumber() + ")");
                            tokens.add("(ID," + temp + "," + lnr.getLineNumber() + ")");
                        }else if (!temp.equals("")) {
                            //                    System.out.println("TEMP" + temp);
                            System.out.println("(invalid lexeme," + temp + "," + lnr.getLineNumber() + ")");
                        }
                        dFlag = true;
                        if ((fileInp = fileInput.read()) == '\\') {
                            charInp = (char) fileInp;
                            String charTemp = "" + charInp;
                            charTemp += (char) fileInput.read();
                            charInp = (char) fileInput.read();
                            if (charInp == '\'') {
                                System.out.println("(char_const," + charTemp + "," + lnr.getLineNumber() + ")");
                                tokens.add("(char_const," + charTemp + "," + lnr.getLineNumber() + ")");
                            } else if (dataType.getAccessModifier().contains(temp2)) {
                                                System.out.println("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");
                                                tokens.add("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");}
                                    else {
                                System.out.println("(invalid_lexeme," + charTemp + charInp + "," + lnr.getLineNumber() + ")");
                            }
                        } else {
                            charInp = (char) fileInp;
                            if ((char) (fileInp = fileInput.read()) == '\'') {
                                System.out.println("(char_const," + charInp + "," + lnr.getLineNumber() + ")");
                                tokens.add("(char_const," + charInp + "," + lnr.getLineNumber() + ")");
                            } else if (dataType.getAccessModifier().contains(temp2)) {
                                                System.out.println("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");
                                                tokens.add("(AccessModifier," + temp2 + "," + lnr.getLineNumber() + ")");}
                                    else {
                                System.out.println("(invalid_lexeme," + charInp + (char) fileInp + "," + lnr.getLineNumber() + ")");
                            }
                        }
                    } else if (charInp == '"') {
                        if (isReserveWord(temp)) {
                            if (dataType.getDataType().contains(temp)) {
                                System.out.println("(DT," + temp + "," + lnr.getLineNumber() + ")");
                                tokens.add("(DT," + temp + "," + lnr.getLineNumber() + ")");
                            } else if (dataType.getAccessModifier().contains(temp)) {
                                System.out.println("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");
                                tokens.add("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");
                            } else {
                                System.out.println("(" + temp + ", ," + lnr.getLineNumber() + ")");
                                tokens.add("(" + temp + ", ," + lnr.getLineNumber() + ")");
                            }
                        } else if (isNumber(temp)) {
                            System.out.println("(integer_constant" + "," + temp + " ," + lnr.getLineNumber() + ")");
                            tokens.add("(integer_constant" + "," + temp + " ," + lnr.getLineNumber() + ")");
                        }
                        
                        else if (dataType.getAccessModifier().contains(temp)) {
                                                System.out.println("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");
                                                tokens.add("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");}
                                    
                        
                        else if(temp.matches("[a-zA-Z0-9]*") && !temp.equals("")){
                            System.out.println("(ID," + temp + "," + lnr.getLineNumber() + ")");
                            tokens.add("(ID," + temp + "," + lnr.getLineNumber() + ")");
                        }else if (!temp.equals("")) {
                            //                    System.out.println("TEMP" + temp);
                            System.out.println("(invalid_lexeme," + temp + "," + lnr.getLineNumber() + ")");
                        }
                        dFlag = true;
                        String stringTemp = "";
                        char prevCharInp = 'n';
                        boolean strFlag = false;
                        while (fileInp != -1) {
                            if (((char) (fileInp = fileInput.read())) == '"') {
                                break;
                            }
                            charInp = (char) fileInp;
                            stringTemp += charInp;
                            prevCharInp = charInp;
                            //   System.out.println("prevs:" + prevCharInp + " curr:" + charInp);
                        }
                        if (((char) fileInp) == '"') {
                            //       System.out.println("fileinp:" + (char) fileInp);
                            if (prevCharInp == '\\') {
                                //           System.out.println("in \\ checking");
                                charInp = (char) fileInp;
                                stringTemp += charInp;
                                while (strFlag == false) {
                                    prevCharInp = charInp;
                                    while ((charInp = ((char) (fileInp = fileInput.read()))) != '"') {
                                        //             System.out.println("fileinp2:" + fileInp
                                        System.out.println("prevs:" + prevCharInp + " curr: " + (char) fileInp);

                                        if (fileInp == -1) {
                                            break;
                                        } else {
                                            //                 System.out.println("in else");
                                            // charInp = (char) fileInp;
                                            stringTemp += charInp;
                                            prevCharInp = charInp;
                                            //               System.out.println("prevv:" + prevCharInp + " curr:" + charInp);
                                        }
                                    }
                                    if (prevCharInp != '\\' && fileInp != -1) {
                                        System.out.println("prevv:" + prevCharInp + " curr: " + charInp);
                                        strFlag = true;
                                    }
                                }
                                if (fileInp != -1) {
                                    System.out.println("(String_const," + stringTemp + charInp + "," + lnr.getLineNumber() + ")");
                                    tokens.add("(String_const," + stringTemp + charInp + "," + lnr.getLineNumber() + ")");
                                } else {
                                    System.out.println("(Invalid_Lexeme," + stringTemp + "," + lnr.getLineNumber() + ")");
                         
                                }
                            } else {
                                System.out.println("(String_const," + stringTemp + "," + lnr.getLineNumber() + ")");
                                tokens.add("(String_const," + stringTemp + "," + lnr.getLineNumber() + ")");
                            }
                        }
                    } else if (isOperator(charInp)) {
                        if (isReserveWord(temp)) {
                            if (dataType.getDataType().contains(temp)) {
                                System.out.println("(DT," + temp + "," + lnr.getLineNumber() + ")");
                                tokens.add("(DT," + temp + "," + lnr.getLineNumber() + ")");
                            } else if (dataType.getAccessModifier().contains(temp)) {
                                System.out.println("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");
                                tokens.add(new String("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")"));
                            } else {
                                System.out.println("(" + temp + ", ," + lnr.getLineNumber() + ")");
                                tokens.add(new String("(" + temp + ", ," + lnr.getLineNumber() + ")"));
                            }
                        } else if (isNumber(temp)) {
                            System.out.println("(integer_constant" + "," + temp + " ," + lnr.getLineNumber() + ")");
                            tokens.add(new String("(integer_constant" + "," + temp + " ," + lnr.getLineNumber() + ")"));
                        } 
                        
                        else if (dataType.getAccessModifier().contains(temp)) {
                                                System.out.println("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");
                                                tokens.add("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");}
                                    
                        
                        else if(temp.matches("[a-zA-Z0-9]*") && !temp.equals("")){
                            System.out.println("(ID," + temp + "," + lnr.getLineNumber() + ")");
                            tokens.add("(ID," + temp + "," + lnr.getLineNumber() + ")");
                        }
                        else if (!temp.equals("")) {
                            //                    System.out.println("TEMP" + temp);
                            System.out.println("(invalid_lexeme," + temp + "," + lnr.getLineNumber() + ")");
                        }

                        dFlag = true;
                        Arithmetic arithmetic = new Arithmetic();
                        Logical logical = new Logical();

                        char charInp2 = (char) (fileInp = fileInput.read());

                        if (logical.and == charInp || logical.not == charInp || logical.or == charInp) {
                            System.out.println("(" + charInp + ",," + lnr.getLineNumber() + ")");
                            tokens.add("(" + charInp + ",," + lnr.getLineNumber() + ")");
                        } else if (arithmetic.getP_M().contains(charInp)) {
                            // fileInp = fileInput.read();
                            if (charInp == '+') {
                                switch (charInp2) {
                                    case '+':
                                        System.out.println("(inc_dec," + charInp + charInp2 + "," + lnr.getLineNumber() + ")");
                                        tokens.add("(inc_dec," + charInp + charInp2 + "," + lnr.getLineNumber() + ")");
                                        break;
                                    case '=':
                                        System.out.println("(As_Op," + charInp + charInp2 + "," + lnr.getLineNumber() + ")");
                                        tokens.add("(As_Op," + charInp + charInp2 + "," + lnr.getLineNumber() + ")");
                                        break;
                                    default:
                                        System.out.println("(" + charInp + ",," + lnr.getLineNumber() + ")");
                                        tokens.add("(" + charInp + ",," + lnr.getLineNumber() + ")");
                                        if (charInp != '\r' && charInp != ' ' && charInp != '.') {
                                            fileInput.unread(fileInp);
                                        }
                                        break;
                                }
                            } else if (charInp == '-') {
                                switch (charInp2) {
                                    case '-':
                                        System.out.println("(inc_dec," + charInp + charInp2 + "," + lnr.getLineNumber() + ")");
                                        tokens.add("(inc_dec," + charInp + charInp2 + "," + lnr.getLineNumber() + ")");
                                        break;
                                    case '=':
                                        System.out.println("(As_Op," + charInp + charInp2 + "," + lnr.getLineNumber() + ")");
                                        tokens.add("(As_Op," + charInp + charInp2 + "," + lnr.getLineNumber() + ")");
                                        break;
                                    default:
                                        System.out.println("(," + charInp + "," + lnr.getLineNumber() + ")");
                                        tokens.add("(," + charInp + "," + lnr.getLineNumber() + ")");
                                        if (charInp != '\r' && charInp != ' ' && charInp != '.') {
                                            fileInput.unread(fileInp);
                                        }
                                        break;
                                }
                            }
                        } else if (charInp == '<' || charInp == '>' || charInp == '=' || charInp == '!') {
                            switch (charInp2) {
                                case '=':
                                    System.out.println("(R_Op," + charInp + charInp2 + "," + lnr.getLineNumber() + ")");
                                    tokens.add("(R_Op," + charInp + charInp2 + "," + lnr.getLineNumber() + ")");
                                    break;
                                default:
                                    if (charInp == '=') {
                                        System.out.println("(As_Op," + charInp + "," + lnr.getLineNumber() + ")");
                                        tokens.add("(As_Op," + charInp + "," + lnr.getLineNumber() + ")");

                                    } else {
                                        System.out.println("(R_Op," + charInp + "," + lnr.getLineNumber() + ")");
                                        tokens.add("(R_Op," + charInp + "," + lnr.getLineNumber() + ")");
                                    }
                                    if (charInp != '\r' && charInp != ' ' && charInp != '.') {
                                        fileInput.unread(fileInp);
                                    }

                                    break;
                            }
                        } else if (arithmetic.getM_D_M().contains(charInp)) {
                            switch (charInp2) {
                                case '=':
                                    System.out.println("(As_Op," + charInp + charInp2 + "," + lnr.getLineNumber() + ")");
                                    tokens.add("(As_Op," + charInp + charInp2 + "," + lnr.getLineNumber() + ")");
                                    break;
                                default:
                                    System.out.println("(M_D_M," + charInp + "," + lnr.getLineNumber() + ")");
                                    tokens.add("(M_D_M," + charInp + "," + lnr.getLineNumber() + ")");
                                    if (charInp != '\r' && charInp != ' ' && charInp != '.') {
                                        fileInput.unread(fileInp);
                                    }
                                    break;
                            }
                        }
                    } else if (charInp >= 'a' && charInp <= 'z' || charInp >= 'A' && charInp <= 'Z') {
                        if (isReserveWord(temp)) {
                            if (dataType.getDataType().contains(temp)) {
                                System.out.println("(DT," + temp + "," + lnr.getLineNumber() + ")");
                                tokens.add("(DT," + temp + "," + lnr.getLineNumber() + ")");
                            } else if (dataType.getAccessModifier().contains(temp)) {
                                System.out.println("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");
                                tokens.add("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");
                            } else {
                                System.out.println("(" + temp + ", ," + lnr.getLineNumber() + ")");
                                tokens.add("(" + temp + ", ," + lnr.getLineNumber() + ")");
                            }
                        } else if (isNumber(temp)) {
                            System.out.println("(integer_constant" + "," + temp + " ," + lnr.getLineNumber() + ")");
                            tokens.add("(integer_constant" + "," + temp + " ," + lnr.getLineNumber() + ")");
                        }
                        else if (dataType.getAccessModifier().contains(temp)) {
                                                System.out.println("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");
                                                tokens.add("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");}
                                
                        else if(temp.matches("[a-zA-Z0-9]*") && !temp.equals("")){
                            System.out.println("(ID," + temp + "," + lnr.getLineNumber() + ")");
                            tokens.add("(ID," + temp + "," + lnr.getLineNumber() + ")");
                        }
                        else if (!temp.equals("")) {
                            //                    System.out.println("TEMP" + temp);
                            System.out.println("(invalid_lexeme," + temp + "," + lnr.getLineNumber() + ")");
                        }

                        dFlag = true;
//System.out.println("in #");
                        char preword = charInp;
                        while ((fileInp = fileInput.read()) != -1) {
                            
                            charInp = (char) fileInp;
                            if (puncObj.getPunct().contains(charInp) || charInp == ' ' || charInp == '\r' || isOperator(charInp) || charInp == '#' ) {
                                if (charInp != '\r' && charInp != ' ' && charInp != '.') {
                                    fileInput.unread(fileInp);
                                }
                                if (temp2.equals("")) {
                                    System.out.println("(invalid_lexeme," + temp2 + " ," + lnr.getLineNumber() + ")");
                                } else {                     
                                     System.out.println("(ID,"+preword+"" + temp2 + " ," + lnr.getLineNumber() + ")");
                                     tokens.add("(ID,"+preword+"" + temp2 + " ," + lnr.getLineNumber() + ")");
                                    //        System.out.println("on:" + (char) (fileInp = fileInput.read()) + "isOperator" + isOperator((char) fileInp));
                                    //fileInput.unread(fileInp);
                                    break;
                                }
//bw.write("Invalid_Lexeme, " + temp + ", " + lnr.getLineNumber() + ")" + "\r");
                            } else {
                                temp2 += charInp;
                            }
                        }
                    } else if (isReserveWord(temp)) {
                        if (dataType.getDataType().contains(temp)) {
                            System.out.println("(DT," + temp + "," + lnr.getLineNumber() + ")");
                            tokens.add("(DT," + temp + "," + lnr.getLineNumber() + ")");
                        } else if (dataType.getAccessModifier().contains(temp)) {
                            System.out.println("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");
                            tokens.add("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");
                        } else {
                            System.out.println("(" + temp + ", ," + lnr.getLineNumber() + ")");
                            tokens.add("(" + temp + ", ," + lnr.getLineNumber() + ")");
                        }
                    } else if (isNumber(temp)) {
                        System.out.println("(integer_constant" + "," + temp + " ," + lnr.getLineNumber() + ")");
                        tokens.add("(integer_constant" + "," + temp + " ," + lnr.getLineNumber() + ")");
                    }
                    else if (dataType.getAccessModifier().contains(temp)) {
                                                System.out.println("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");
                                                tokens.add("(AccessModifier," + temp + "," + lnr.getLineNumber() + ")");}
                                    
                    
                    else if(temp.matches("[a-zA-Z0-9]*") && !temp.equals("") ){
                            System.out.println("(ID," + temp + "," + lnr.getLineNumber() + ")");
                            tokens.add("(ID," + temp + "," + lnr.getLineNumber() + ")");
                        }else if (!temp.equals("") && charInp != '\r') {
                        //                    System.out.println("TEMP" + temp);
                        System.out.println("(invalid_lexeme," + temp + "," + lnr.getLineNumber() + ")");
                        
                    }
                    if ((charInp == '\r' || fileInp == 10) && dFlag == false) {
                        System.out.println("( \\r" + " , ," + lnr.getLineNumber() + ")");
                        //tokens.add("( \\r" + " , ," + lnr.getLineNumber() + ")");
                    } else if (charInp != ' ' && dFlag == false) {
                        System.out.println("(" + charInp + " , ," + lnr.getLineNumber() + ")");
                        tokens.add("(" + charInp + " , ," + lnr.getLineNumber() + ")");
                    }
                    temp = "";
                    temp2 = "";

                } else {
                    temp += charInp;
                }
                if(charInp=='\r'){
                    lnr.readLine();
                }
            }
            fileInput.close();
            bw.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        if(new SyntaxAnalyzerMain().Passed)
        {
            new SemmenticMainClass();
        }
    }

    public static boolean isReserveWord(String temp) {
        DataType obj = new DataType();
        remainingWords obj2 = new remainingWords();
        if (obj.getDataType().contains(temp)) {
//            System.out.print("is true");
            return true;
        } else if (obj2.getRemainingWords().contains(temp)) {
            return true;
        }
        return false;
    }

    public static boolean isNumber(String temp) {
        boolean flag = false;
        for (int a = 0; a < temp.length(); a++) {
            if (temp.charAt(a) <= '9' && temp.charAt(a) >= '0') {
                flag = true;
            } else {
                flag = false;
                break;
            }
        }
//        System.out.print("flag: " + flag);
        return flag;
    }

    public static boolean isOperator(char charInp) {
        Arithmetic arithmetic = new Arithmetic();
        Assignment assignment = new Assignment();
        Logical logical = new Logical();
        Unary unary = new Unary();

        if (arithmetic.getM_D_M().contains(charInp) || arithmetic.getP_M().contains(charInp)) {
            return true;
        } else if (assignment.ASOP1 == charInp) {
            return true;
        } else if (logical.and == charInp || logical.not == charInp || logical.or == charInp) {
            return true;
        } else if (charInp == '<' || charInp == '>') {
            return true;
        }
        return false;
    }
}
/*class token {

    String classpart, valuepart, lineno;

    public token(String classPart, String valuePart, String lineno) {
        this.classpart = classpart;
        this.valuepart = valuepart;
        this.lineno = lineno;
    }

}
class SA {

    static int i;
    public static ArrayList<token> tok = new ArrayList<>();

    SA() 
    {
        SA.tok = new ArrayList<>();
    }

    //decl cfg
    boolean decl() {

        //ye decl ka frst  
       // if ("DataType".compareTo(tok.get(i).classpart) == 0 || "ID".compareTo(tok.get(i).classpart) == 0) {
         //   i++;
            // ye i++ aega k nhi ?? 
            //i++;
            
                if (a()) {

                    //ye decl_2 ka frst ha 
                    //if ("ID".compareTo(tok.get(i).classpart) == 0) {
                    if (decl_2()) {

                        if (";".compareTo(tok.get(i).classpart) == 0) {
                            i++;
                            return true;
                        }

                    }
                
                //return true;
            
        }
        // else if ("ID".compareTo(tok.get(i).classpart) == 0)

        return false;
    }

    boolean decl_2() {
        if ("ID".compareTo(tok.get(i).classpart) == 0) {
            i++;
            //if ("[".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0) {
            if (Arr_decl()) {
                //if (",".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0) {
                if (init()) {
                    //      if (";".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0) {
                    if (multi_decl()) {
                        return true;

                    }
                    // return true;

                }

            }
        }
        return false;
    }

    boolean a() {
      
        if ("DataType".compareTo(tok.get(i).classpart) == 0)// || "ID".compareTo(tok.get(i).classpart) == 0)
        {
            i++;
            return true;
        } 
         // ye a k rules hain..
        else if ("ID".compareTo(tok.get(i).classpart) == 0) {
                i++;
                return true;
            }
        else if ("ID".compareTo(tok.get(i).classpart) == 0) {
            
                return true;
            }
        

        return false;

    }

    boolean Arr_decl() {
        // if ("[".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0) {
        if ("[".compareTo(tok.get(i).classpart) == 0) {
            i++;
            //if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0) {
            if (q()) {
                if ("]".compareTo(tok.get(i).classpart) == 0) {
                    i++;
                                    return true;

                }
            }
        } else if (",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0) {
            return true;
        }
        // return false;

        return false;
    }

    boolean q() {
       // if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0) {

            //if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {
            if (exp()) {

                return true;
            }
         else if ("]".compareTo(tok.get(i).classpart) == 0) {
            return true;
        }

        return false;
    }

    boolean init() {
        //ye init ka first 
        if ("=".compareTo(tok.get(i).classpart) == 0) {
            i++;
            {
              //  if ("=".compareTo(tok.get(i).classpart) == 0) {
                //    i++;
                    
                        // if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0 || "{".compareTo(tok.get(i).classpart) == 0 || "new".compareTo(tok.get(i).classpart) == 0) {
                        if (init_2()) {
                            //i++;
                            return true;
                        }

                    
                
            }
        } else if (",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0) {
            return true;
        }

        return false;
    }

    boolean init_2() {
       // if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0 || "{".compareTo(tok.get(i).classpart) == 0 || "new".compareTo(tok.get(i).classpart) == 0) {
          //  if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {
                if (exp()) {
                    
                        if (k())
                                {
                                                        return true;

                                }
                    
                }
             else if  ("{".compareTo(tok.get(i).classpart) == 0)
             {
                         i++;
             {
               // if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0 || "}".compareTo(tok.get(i).classpart) == 0) {
                    if (exp_2()) {
                        if ("}".compareTo(tok.get(i).classpart) == 0) {
                            i++;
                            return true;
                        }
                    }

                }
             }
             else if ("new".compareTo(tok.get(i).classpart) == 0)
             {
                     i++;
             {
                if ("ID".compareTo(tok.get(i).classpart) == 0) {
                    i++;
                    if ("(".compareTo(tok.get(i).classpart) == 0) {
                        i++;
                        //if ("DataType".compareTo(tok.get(i).classpart) == 0 || "ID".compareTo(tok.get(i).classpart) == 0) {
                            if (exp()) {
                              //  if ("ID".compareTo(tok.get(i).classpart) == 0) {
                                    if (")".compareTo(tok.get(i).classpart) == 0) {
                                        i++;
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
             
//             else if ("ID".compareTo(tok.get(i).classpart) == 0) {
//
//                if ("=".compareTo(tok.get(i).classpart) == 0 || "ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {
//
//                    if (k()) {
//                        i++;
//                        return true;
//                    }
//                }
//            }

        
        return false;
    }

    boolean k() {
       // if ("=".compareTo(tok.get(i).classpart) == 0 || "ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {

         //   if (",".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0) {
                if (init()) {

                    i++;
                    return true;
                }

             else if ("(".compareTo(tok.get(i).classpart) == 0 ){
//|| "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0 || "}".compareTo(tok.get(i).classpart) == 0) {
i++;
                {
                 if (exp_2()) {
                    if (")".compareTo(tok.get(i).classpart) == 0 )
                    {
                        i++;
                    return true;
                }
            
                }
                }
             }

        return false;
    }

    boolean exp_2() {

//        if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0 || "}".compareTo(tok.get(i).classpart) == 0) {

  //          if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {
                if (exp()) {

                    if (",".compareTo(tok.get(i).classpart) == 0) {
i++;
    //                    if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0 || "}".compareTo(tok.get(i).classpart) == 0) {
                            if (exp_2()) {
                                
                                return true;
                            }
                        }

                    }
                else if ("}".compareTo(tok.get(i).classpart) == 0|| "]".compareTo(tok.get(i).classpart) == 0)
return true;
            
        

        return false;
    }

    boolean multi_decl() {
        //if (";".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0) {
            if (",".compareTo(tok.get(i).classpart) == 0) {
          i++;
          {
              //if ("ID".compareTo(tok.get(i).classpart) == 0) {
                    if(decl_2())
                    {
                        
                    return true;
                }
            }

        }
            else if(";".compareTo(tok.get(i).classpart) == 0)
        
                return true;
            
            
            return false;

    }
//exp cfg
    boolean exp() {
      //  if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {
        //    if (exp()) {
          //      if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {
                    if (OE()) {
          
                        return true;
                    }
                

            
        

        return false;
    }

    boolean OE() {

        //if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {
          //  if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {
                if (AE()) {

            //        if ("%%".compareTo(tok.get(i).classpart) == 0 || "$".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || ")".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || "++".compareTo(tok.get(i).classpart) == 0 || "--".compareTo(tok.get(i).classpart) == 0) {
                        if (OE_1()) {
                            
                            return true;
                        }
                    }
                
            
        
        return false;
    }

    boolean OE_1() {
        //if ("%%".compareTo(tok.get(i).classpart) == 0 || "$".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || ")".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || "++".compareTo(tok.get(i).classpart) == 0 || "--".compareTo(tok.get(i).classpart) == 0) {
            if ("%%".compareTo(tok.get(i).classpart) == 0) {
                i++;
                //if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {

                    if (AE()) {
                  //      if ("%%".compareTo(tok.get(i).classpart) == 0 || "$".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || ")".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || "++".compareTo(tok.get(i).classpart) == 0 || "--".compareTo(tok.get(i).classpart) == 0) {
                            if (OE_1()) {
                              
                                return true;
                            }
                        }

                    }

            else if ( "$".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || ")".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || "++".compareTo(tok.get(i).classpart) == 0 || "--".compareTo(tok.get(i).classpart) == 0) 
return true;

        

        return false;

    }

    boolean AE() {

      //  if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {

        //    if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {

                if (RE()) {
          //          if ("??".compareTo(tok.get(i).classpart) == 0 || "%%".compareTo(tok.get(i).classpart) == 0 || "$".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || ")".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || "++".compareTo(tok.get(i).classpart) == 0 || "--".compareTo(tok.get(i).classpart) == 0) {
                        if (AE_1()) {
                           
                            return true;
                        }
                    }
                
            
        

        return false;

    }

    boolean AE_1() {
        //if ("??".compareTo(tok.get(i).classpart) == 0 || "%%".compareTo(tok.get(i).classpart) == 0 || "$".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || ")".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || "++".compareTo(tok.get(i).classpart) == 0 || "--".compareTo(tok.get(i).classpart) == 0) {

            if ("??".compareTo(tok.get(i).classpart) == 0) {
   i++;
   //if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {
                    if (RE()) {
     //                   if ("??".compareTo(tok.get(i).classpart) == 0 || "%%".compareTo(tok.get(i).classpart) == 0 || "$".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || ")".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || "++".compareTo(tok.get(i).classpart) == 0 || "--".compareTo(tok.get(i).classpart) == 0) {
                            if (AE_1()) {
                               
                                return true;
                            }
                        }

                    }

                

            
            else if ("%%".compareTo(tok.get(i).classpart) == 0 || "$".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || ")".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || "++".compareTo(tok.get(i).classpart) == 0 || "--".compareTo(tok.get(i).classpart) == 0) 
return true;

        return false;
    }

    boolean RE() {

        //if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {

          //  if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {

                if (E()) {
            //        if ("==".compareTo(tok.get(i).classpart) == 0 || "<".compareTo(tok.get(i).classpart) == 0 || "<".compareTo(tok.get(i).classpart) == 0 || "$".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || ")".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || "++".compareTo(tok.get(i).classpart) == 0 || "--".compareTo(tok.get(i).classpart) == 0 || "%%".compareTo(tok.get(i).classpart) == 0) {
                        if (RE_1()) {
                            
                            return true;
                        }
                    }

                
            
        

        return false;
    }

    boolean RE_1() {

        //if ("==".compareTo(tok.get(i).classpart) == 0 || "<".compareTo(tok.get(i).classpart) == 0 || "<".compareTo(tok.get(i).classpart) == 0 || "$".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || ")".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || "++".compareTo(tok.get(i).classpart) == 0 || "--".compareTo(tok.get(i).classpart) == 0 || "%%".compareTo(tok.get(i).classpart) == 0) {
        
            if ("==".compareTo(tok.get(i).classpart) == 0)
            {
                i++;
            }
            else if( "<".compareTo(tok.get(i).classpart) == 0)
            {
                i++;
            }
            else if(">".compareTo(tok.get(i).classpart) == 0) {
              i++;
            }
            //  if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {
                    if (E()) {
              //          if ("==".compareTo(tok.get(i).classpart) == 0 || "<".compareTo(tok.get(i).classpart) == 0 || ">".compareTo(tok.get(i).classpart) == 0 || "??".compareTo(tok.get(i).classpart) == 0 || "$".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || ")".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || "++".compareTo(tok.get(i).classpart) == 0 || "--".compareTo(tok.get(i).classpart) == 0 || "%%".compareTo(tok.get(i).classpart) == 0) {
                            if (RE_1()) {
                                
                                return true;
                            }
                        }
                    

                    else if ("??".compareTo(tok.get(i).classpart) == 0|| "%%".compareTo(tok.get(i).classpart) == 0 || "$".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || ")".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || "++".compareTo(tok.get(i).classpart) == 0 || "--".compareTo(tok.get(i).classpart) == 0) 
return true;         
            
        

        return false;
    }

    boolean E() {
     //   if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {
       //     if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {
                if (T()) {

         //           if ("-".compareTo(tok.get(i).classpart) == 0 || "+".compareTo(tok.get(i).classpart) == 0 || "==".compareTo(tok.get(i).classpart) == 0 || "<".compareTo(tok.get(i).classpart) == 0 || ">".compareTo(tok.get(i).classpart) == 0 || "$".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || ")".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || "++".compareTo(tok.get(i).classpart) == 0 || "--".compareTo(tok.get(i).classpart) == 0 || "%%".compareTo(tok.get(i).classpart) == 0 || "??".compareTo(tok.get(i).classpart) == 0) {
                        if (E_1()) {
                            
                            return true;
                        }
                    }

                

            
        

        return false;
    }

    boolean E_1() {
        //if ("-".compareTo(tok.get(i).classpart) == 0 || "+".compareTo(tok.get(i).classpart) == 0 || "==".compareTo(tok.get(i).classpart) == 0 || "<".compareTo(tok.get(i).classpart) == 0 || ">".compareTo(tok.get(i).classpart) == 0 || "$".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || ")".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || "++".compareTo(tok.get(i).classpart) == 0 || "--".compareTo(tok.get(i).classpart) == 0 || "%%".compareTo(tok.get(i).classpart) == 0 || "??".compareTo(tok.get(i).classpart) == 0) {

            if ("+".compareTo(tok.get(i).classpart) == 0 )
            {
                i++;
            }
            else if( "-".compareTo(tok.get(i).classpart) == 0) {
                i++;
            }
            
            //    if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {
                    if (T()) {
              //          if ("-".compareTo(tok.get(i).classpart) == 0 || "+".compareTo(tok.get(i).classpart) == 0 || "==".compareTo(tok.get(i).classpart) == 0 || "<".compareTo(tok.get(i).classpart) == 0 || ">".compareTo(tok.get(i).classpart) == 0 || "$".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || ")".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || "++".compareTo(tok.get(i).classpart) == 0 || "--".compareTo(tok.get(i).classpart) == 0 || "%%".compareTo(tok.get(i).classpart) == 0 || "??".compareTo(tok.get(i).classpart) == 0) {
                            if (E_1()) {
                               
                                return true;

                            }
                        }
                    
                    

                                    else if ("??".compareTo(tok.get(i).classpart) == 0|| "%%".compareTo(tok.get(i).classpart) == 0 || "$".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || ")".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || "++".compareTo(tok.get(i).classpart) == 0 || "--".compareTo(tok.get(i).classpart) == 0) 
return true;

            

        
        return false;
    }

    boolean T() {
        //if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {
          //  if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {
                if (F()) {
            //        if ("*".compareTo(tok.get(i).classpart) == 0 || "/".compareTo(tok.get(i).classpart) == 0 || "-".compareTo(tok.get(i).classpart) == 0 || "+".compareTo(tok.get(i).classpart) == 0 || "==".compareTo(tok.get(i).classpart) == 0 || "<".compareTo(tok.get(i).classpart) == 0 || ">".compareTo(tok.get(i).classpart) == 0 || "$".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || ")".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || "++".compareTo(tok.get(i).classpart) == 0 || "--".compareTo(tok.get(i).classpart) == 0 || "%%".compareTo(tok.get(i).classpart) == 0 || "??".compareTo(tok.get(i).classpart) == 0) {

                        if (T_1()) {
                            
                            return true;
                        }
                    }
                
            
        

        return false;
    }

    boolean T_1() {

        if ("*".compareTo(tok.get(i).classpart) == 0 )
        {
            i++;
        }
        else if( "/".compareTo(tok.get(i).classpart) == 0) {
            i++;
        }
        //    if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {
                if (F()) {
                    if(T_1())
                    {
                        return true;
                }

            }
        
                    else if ("+".compareTo(tok.get(i).classpart) == 0|| "-".compareTo(tok.get(i).classpart) == 0|| "<".compareTo(tok.get(i).classpart) == 0|| ">".compareTo(tok.get(i).classpart) == 0|| "==".compareTo(tok.get(i).classpart) == 0|| "??".compareTo(tok.get(i).classpart) == 0|| "%%".compareTo(tok.get(i).classpart) == 0 || "$".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || ")".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || "++".compareTo(tok.get(i).classpart) == 0 || "--".compareTo(tok.get(i).classpart) == 0) 
return true;
        return false;
    }

    boolean F() {
//        if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {
            if ("ID".compareTo(tok.get(i).classpart) == 0) {
 i++;
 if (k()) {
                    if(x())
                    {
                        return true;
                    }
                

            }
            }
          //  else if ("alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0) {
            else if(const1())
            {
                
                return true;
            } 
            else if ("~".compareTo(tok.get(i).classpart) == 0) {
                i++;
//                if ("ID".compareTo(tok.get(i).classpart) == 0 || "alpha".compareTo(tok.get(i).classpart) == 0 || "point".compareTo(tok.get(i).classpart) == 0 || "large".compareTo(tok.get(i).classpart) == 0 || "num".compareTo(tok.get(i).classpart) == 0 || "string".compareTo(tok.get(i).classpart) == 0 || "boolean".compareTo(tok.get(i).classpart) == 0 || "~".compareTo(tok.get(i).classpart) == 0) {
                    {
                    if (F()) {
                        
                        return true;
                    }
                }
                    }
            
        
        return false;

    }

    boolean x()
    {
        if(".".compareTo(tok.get(i).classpart) == 0)
        {
            i++;
            {
                if("ID".compareTo(tok.get(i).classpart) == 0)
                    i++;
                {
                    if(x())
                    {
                        return true;
                    }
                }
            }
        }
                
        else if ("[".compareTo(tok.get(i).classpart) == 0)
        {
            i++;
            if("]".compareTo(tok.get(i).classpart) == 0)
            {
        i++;
            {
                if(x())
                {
                    return true;
                }
            }
                }
        }
        
                            else if ("*".compareTo(tok.get(i).classpart) == 0||"/".compareTo(tok.get(i).classpart) == 0|| "+".compareTo(tok.get(i).classpart) == 0|| "-".compareTo(tok.get(i).classpart) == 0|| "<".compareTo(tok.get(i).classpart) == 0|| ">".compareTo(tok.get(i).classpart) == 0|| "==".compareTo(tok.get(i).classpart) == 0|| "??".compareTo(tok.get(i).classpart) == 0|| "%%".compareTo(tok.get(i).classpart) == 0 || "$".compareTo(tok.get(i).classpart) == 0 || "]".compareTo(tok.get(i).classpart) == 0 || ",".compareTo(tok.get(i).classpart) == 0 || ";".compareTo(tok.get(i).classpart) == 0 || ")".compareTo(tok.get(i).classpart) == 0 || "=".compareTo(tok.get(i).classpart) == 0 || "++".compareTo(tok.get(i).classpart) == 0 || "--".compareTo(tok.get(i).classpart) == 0) 
return true;
        
        
        return false;
    }
    //inc_dec cfg
    boolean inc_dec()
    {
        if(exp())
        {
            if(inc_dec1())
            {
                if(";".compareTo(tok.get(i).classpart) == 0)
                {
                    i++;
                    return true;
                }
            }
        }
        
        
        
        
        return false;
       
    }
    
    boolean inc_dec1()
    {
        if("++".compareTo(tok.get(i).classpart) == 0)
        {
            i++;
            return true;
        }
        else if ("--".compareTo(tok.get(i).classpart) == 0)
        {
            i++;
            return true;
        }
        
        return false;
    }
    
    //do while cfg
    boolean work()
    {
        if("work".compareTo(tok.get(i).classpart) == 0)
        {
            i++;
            if("{".compareTo(tok.get(i).classpart) == 0)
        i++;
            {
                if(body())
                    
                {
                    if("}".compareTo(tok.get(i).classpart) == 0)
                i++;
                    {
                        if(whenever())
                        {
                            if(";".compareTo(tok.get(i).classpart) == 0)
                            {
                                return true;
                            }
                                }
                    }
                        }
            }
                }
        
        
        return false;
    }
    //for cfg
    boolean to_st()
    {
        if("to".compareTo(tok.get(i).classpart) == 0)
                {
                    i++;
                    {
                        if("(".compareTo(tok.get(i).classpart) == 0)
                        {
                            if(to_1())
                            {
                                if(to_2())
                                {
                                    if(to_3())
                                    {
                                        if(")".compareTo(tok.get(i).classpart) == 0)
                                    i++;
                                        {
                                            if("{".compareTo(tok.get(i).classpart) == 0)
                                                i++;
                                            {
                                                if(body())
                                                {
                                                    if("}".compareTo(tok.get(i).classpart) == 0)
                                                i++;
                                                    return true;
                                                        }
                                            }
                                        }
                                            }
                                }
                            }
                        }
                    }
                }
        
        
        
        return false;
    }
    
    boolean to_1()
    {
        if(decl())
        {
            return true;
        }
        else if(Assign_st())
        {
            return true;
        }
        else if(";".compareTo(tok.get(i).classpart) == 0)
        {
            //ye puchna hai k i++ hoga k nhi ager ye khali or kr k rule me likha ha..??
            i++;
            return true;
        }
        
        
        return false;
    }
    
    boolean to_2()
    {
        if(exp())
        {
            if(";".compareTo(tok.get(i).classpart) == 0){
                i++;
            return true;
            }
        }
        else if(";".compareTo(tok.get(i).classpart) == 0)
        {
            i++;
            return true;
        }
        
        
        
        
        
        return false;
        
    }
    boolean to_3()
    {
        if(inc_dec())
        {
            return true;
        }
        else if (Assign_st())
        {
            return true;
        }
        else if(")".compareTo(tok.get(i).classpart) == 0)
            return true;
        
        
        
        
        return false;
    }
    boolean whenever()
    {
        if("whenever".compareTo(tok.get(i).classpart) == 0)
        {
            i++;
            {
                if("(".compareTo(tok.get(i).classpart) == 0)
                {
                    if(exp())
                    {
                        if(")".compareTo(tok.get(i).classpart) == 0)
                        {
                            i++;
                            {
                                if("{".compareTo(tok.get(i).classpart) == 0)
                                {
                                    if(body())
                                    {
                                        if("}".compareTo(tok.get(i).classpart) == 0)
                                        {
                                            i++;
                                            return true;
                                        }
                                    }
                                }
                                      
                            }
                        }
                    }
                }
            }
        }
        
        
        
        return false;
    }
    
    
    
    
  boolean loops()
  {
      if(to_st())
      {
          return true;
      }
      else if(work())
          return true;
      else if (whenever())
          return  true;
      
      
      
      
      
      
      return false;
  }
    
  boolean conditions()
  {
      if(switch_st())
      {
          return true;
      }
      else if (when())
          return true;
  
      
      
      
      
      
      return false;
  }
  //single statement cfg
  
  boolean sst()
  {
      if(loops())
          return true;
      else if(conditions())
          return true;
      else if(decl())
          return true;
      
      else if(F_D())
      {
      return true;
      }
      else if(inc_dec())
          return true;
      
      else if (Assign_st())
          return true;
      
      else if("move".compareTo(tok.get(i).classpart) == 0)
      {
          i++;
          {
              
          
          if(";".compareTo(tok.get(i).classpart) == 0)
          {
                 i++;
          return true;
      }
          } 
      }
      else if("cut".compareTo(tok.get(i).classpart) == 0)
      {
          i++;
          {
              if(";".compareTo(tok.get(i).classpart) == 0)
              {
                  i++;
                  return true;
              }
          }
      }
      
      else if("return".compareTo(tok.get(i).classpart) == 0)
      {
          i++;
          if(exp())
          {
              if(";".compareTo(tok.get(i).classpart) == 0)
              {
                  i++;
                  return true;
              }
          }
      }
      //selec set aega abi
     // else if()
          
      return false;
  }
  //multi statemnt
  boolean mst()
  {
      if(sst())
      {
          if(mst())
          {
              return true;
          }
      }
      //selec set aega abi
      //else if
      
      
      
      
      
      
      
      
      
      return false;
      
  }  
  //body cfg
  boolean body()
  {
      if(sst())
          return true;
      else if (mst())
          return true;
      
      else if("cut".compareTo(tok.get(i).classpart) == 0|| ";".compareTo(tok.get(i).classpart) == 0|| ")".compareTo(tok.get(i).classpart) == 0) 
      return true;
      
      
      
      
      return false;
  }
  
  boolean class1()

  {
      if("class".compareTo(tok.get(i).classpart) == 0)
      {
      i++;
      {
    if("ID".compareTo(tok.get(i).classpart) == 0)

          {
              i++;
              {
                  if("{".compareTo(tok.get(i).classpart) == 0)
                  {
                      i++;
                      if(body())
                      {
                          if("}".compareTo(tok.get(i).classpart) == 0)
                          {
                      i++;
                          return true;
                        
                              }
                      }  
                  }
              }
          }
      }
      }
      
      
      
      
      
      
      
      return false;
  }  
  
  
  boolean main1()
  {
      if("void".compareTo(tok.get(i).classpart) == 0)
      {
          i++;
          {
              if("main".compareTo(tok.get(i).classpart) == 0)
              {
                  i++;
                  if("(".compareTo(tok.get(i).classpart) == 0)
                  {
                      i++;
                      if(")".compareTo(tok.get(i).classpart) == 0)
                      {
                          i++;
                          if("{".compareTo(tok.get(i).classpart) == 0)
                          {
                              i++;
                              {
                                  if(body())
                                  {
                                      if("}".compareTo(tok.get(i).classpart) == 0)
                                      {
                                  i++;
                                      return true;
                                       
                                      }
                                  }
                              }
                          }
                                  
                      }
                  }
                 
              }
          }
      }
      
      
      
      
      
      
      return false;
  }
  
  boolean start1()
  {
      if(start_1())
      {
          if(main1())
          {
              if(start_1())
              {
                  return true;
              }
          }
      }
      
       
      
       
      
      return false;
  }
  boolean start_1()
  {
      if(class1())
          return true;
      else if ("void".compareTo(tok.get(i).classpart) == 0|| "$".compareTo(tok.get(i).classpart) == 0)
      return true;
      
      
      
      
      
      
      
      
      
      
      
      return false;
      
  }
  boolean Assign_st()
  {
      if(exp())
      {
          if("=".compareTo(tok.get(i).classpart) == 0)
          {
              i++;
              if(exp())
              {
                  if(";".compareTo(tok.get(i).classpart) == 0)
                  {
                      i++;
                      return true;
                  }
                      }
          }
              
              }
      return false;
  }
   boolean const1()
   {
       if("string".compareTo(tok.get(i).classpart) == 0)
       {
           i++;
           return true;
       }
       else if("num".compareTo(tok.get(i).classpart) == 0)
               {
                   i++;
                   return true;
               }
       else if ("point".compareTo(tok.get(i).classpart) == 0)
       {
           i++;
           return true;
       }
       else if ("alpha".compareTo(tok.get(i).classpart) == 0)
       {
           i++;
           return true;
       }       
       else if ("boolean".compareTo(tok.get(i).classpart) == 0)
       {
           i++;
           return true;
       }
       
       return false;
   }
  //switch cfg
  boolean switch_st()
  {
      if("switch".compareTo(tok.get(i).classpart) == 0)
      {
          i++;
          {
          if("(".compareTo(tok.get(i).classpart) == 0)
      i++;
          {
              if(exp_1())
              {
                  if(")".compareTo(tok.get(i).classpart) == 0)
                  {
                      i++;
                      {
                          if("{".compareTo(tok.get(i).classpart) == 0)
                      i++;
                          {
                              if(switch_body())
                              {
                                  if("}".compareTo(tok.get(i).classpart) == 0)
                                  {
                                      i++;
                                      return true;
                                  }
                              }
                          }
                              }
                  }
              }
          }   
              }
      }
      
      
      return false;
  }
  boolean exp_1()
  {
      if(exp())
          return true;
      else if (Assign_st())
          return true;
      
      return false;
  }
  
  boolean switch_body()
  {
      if(lock())
          return true;
      else if(default1())
      return true;
      
      
      
      return false;
  }
  boolean lock()
  {
      if("lock".compareTo(tok.get(i).classpart) == 0)
      {
          i++;
          {
              if(exp())
              {
                  if(";".compareTo(tok.get(i).classpart) == 0)
                  {
                      i++;
                      {
                          if(body())
                          {
                              if("cut".compareTo(tok.get(i).classpart) == 0)
                              {
                                  i++;
                                  {
                                      if(";".compareTo(tok.get(i).classpart) == 0)
                                      {
                                          i++;
                                          {
                                              if(lock())
                                              {
                                                  i++;
                                                  return true;
                                              }
                                          }
                                      }
                                          
                                  }
                              }
                          }
                      }
                  }
                      }
          }
      }
      else if("default".compareTo(tok.get(i).classpart) == 0|| "}".compareTo(tok.get(i).classpart) == 0)
          return true;
  
      
      return false;
  }
  boolean default1()
  {
      if("default".compareTo(tok.get(i).classpart) == 0)
      {
          i++;
          {
              if(body())
              {
                  i++;
                  return true;
              }
          }
      }
      else if ("}".compareTo(tok.get(i).classpart) == 0)
      return true;
      
      
      
      
      return false;
  }
  // if else cfg
  boolean when()
  {
      if("when".compareTo(tok.get(i).classpart) == 0)
      {
          if("(".compareTo(tok.get(i).classpart) == 0)
          {
              i++;
              {
                  if(exp_1())
                  {
                      if(")".compareTo(tok.get(i).classpart) == 0)
                      {
                          i++;
                          if("{".compareTo(tok.get(i).classpart) == 0)
                          {
                              i++;
                              {
                                  if(body())
                                  {
                                      if("}".compareTo(tok.get(i).classpart) == 0)
                                      {
                                          i++;
                                          if(or())
                                          {
                                              return true;
                                          }
                                      }
                                  }
                              }
                          }
                      }
                  }
              }
          }
      }
      return false;
  }
  boolean or()
  {
      if("or".compareTo(tok.get(i).classpart) == 0)
      {
          i++;
          {
              if("{".compareTo(tok.get(i).classpart) == 0)
              {
                  i++;
                  {
                  if(body())
                  {
                      return true;
                  }
              }
              }
          }
      }
      //or ka selec set aega abi
      //else if ()
      return false;
  }
  boolean F_D()
  {
      return false;
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
        //ye bracket nhi mitana!   
}

*/