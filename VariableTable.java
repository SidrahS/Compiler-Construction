/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semmentic;

import java.util.LinkedList;

/**
 *
 * @author Dell
 */
 public class VariableTable
{
     static LinkedList variables = new LinkedList();
    
     public static String lookUp(String name, int Scope, String Class)
    {
         for (int i = 0; i < variables.size(); i++)
        {
             Variable v = (Variable) variables.get(i);
             
             if(v.getName().compareTo(name)==0)
             {
                 if(v.getScope()==Scope)
                 {
                     if(v.getClass_().compareTo(Class)==0)
                     {
                         return v.getDT();
                     }
                 }
             }
        }
         return null;
    }
     
     public static void Insert(String name, int Scope, String DT, String AM, String Class)
    {
         Variable NEW  = new Variable(name, Scope, DT, AM, Class);
         variables.add(NEW);
    }
         
     private static class Variable
    {
         private String name;
         private int Scope;
         private String DT;
         private String AM;
         private String Class;
         
         
         public Variable(String name, int Scope, String DT, String AM, String Class)
         {
             this.name = name;
             this.Scope = Scope;
             this.DT = DT;
             this.AM = AM;
             this.Class = Class;
         }

        public String getName() {
            return name;
        }

        public int getScope() {
            return Scope;
        }

        public String getDT() {
            return DT;
        }

        public String getAM() {
            return AM;
        }

        public String getClass_() {
            return Class;
        }
     }
    
}
