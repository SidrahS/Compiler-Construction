/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semmentic;

import java.util.LinkedList;


 public class FunctionTable
{    
     static LinkedList Functions = new LinkedList();
     
     public static String FunctionLookUp(String name, int Scope, String Class, LinkedList ArgsList)
    {
        System.out.println("FUNCTION HAVE TOTAL "+Functions.size());
         boolean allMatch = true;
         for (int i = 0; i < Functions.size(); i++)
        {
             Function f = (Function) Functions.get(i);
             System.out.println("FUNCTION RETRIVED IS "+Functions.get(i).toString());
             
             for (int j = 0; j < f.getArgList().size(); j++) {
                 System.out.println(j+" : "+ f.getArgList().get(j));
            }
             
             if(f.getName().compareTo(name)==0)
            {
                 System.out.println("SAME NAME FUNCTION");
                 if(f.getScope()==Scope)
                {
                     System.out.println("SCOPE IS SAME");
                     if(f.getClass_().compareTo(Class)==0)
                    {
                        System.out.println("CLASS IS SAME sending size "+ArgsList.size()+ " INNER SIZE "+f.getArgList().size());
                         if(f.getArgList().size() == ArgsList.size())
                        {
                            System.out.println("SAME NUMBER OF ARGS");
                             for (int j = 0; j < f.ArgList.size(); j++)
                            {
                                 String args  = (String) f.ArgList.get(j);     //Args of function from Function List
                                 String args2 = (String) ArgsList.get(j); 
                                 System.out.println("Cmparing "+args+" "+args2);//Args of the function that we have send for checking
                                 if(args.compareTo(args2)!=0)                //If all args are same 
                                {
                                     if(allMatch == true)
                                    {
                                         allMatch = true;
                                    }
                                     else
                                    {
                                         System.out.println("ALL MATCH BECOME FALSE");
                                         allMatch = false;
                                    }
                                }
                            }
                             System.out.println("ALL MATCH IS TRUE");
                             if(allMatch)
                            {
                                 System.out.println("RETURNING "+f.getRT());
                                 return f.getRT();
                            }
                        }
                    }
                }
            }
        }
         return null;
    }
     
     public static void FunctionInsert(String name, int Scope, String RT, String AM, String Class, LinkedList ArgList)
    {
         System.out.println("Inserted Function "+name+" "+Scope+" "+RT+" "+AM+" "+Class+ " with ");
         Function f = new Function(name, Scope, RT, AM, Class, ArgList);
         Functions.add(f);
         System.out.println("ID IS "+Functions.get(0).toString());
    }
     
     public static class Function
    {
         private String name;
         private int Scope;
         private String RT;
         private String AM;
         private String Class;
         private LinkedList ArgList;

        public Function(String name, int Scope, String RT, String AM, String Class, LinkedList ArgList) {
            this.name = name;
            this.Scope = Scope;
            this.RT = RT;
            this.AM = AM;
            this.Class = Class;
            this.ArgList = ArgList;
        }

        public String getName() {
            return name;
        }

        public int getScope() {
            return Scope;
        }

        public String getRT() {
            return RT;
        }

        public String getAM() {
            return AM;
        }

        public String getClass_() {
            return Class;
        }

        public LinkedList getArgList() {
            return ArgList;
        }
         
         
         
    }
}
