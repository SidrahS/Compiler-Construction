/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SyntaxAnalyzer;


 public class Token
{
     public String CP;
     public String VP;
     public String lineNumber;
     
     public Token(String CP, String VP, String lineNumber)
    {
         this.CP = CP;
         this.VP = VP;
         this.lineNumber = lineNumber;          
    }
}
