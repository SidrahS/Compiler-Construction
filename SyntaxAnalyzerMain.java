/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SyntaxAnalyzer;
import static compilerconstruction01.CompilerConstruction01.tokens;

public class SyntaxAnalyzerMain
{
     private Token[] tokn;
     int i = 0;
     public boolean Passed = false;
    
    public SyntaxAnalyzerMain()
    {
         System.out.println("IN SYNTAX\n-------------------");
         tokn = new Token[tokens.size()];
         
         for (int i = 0; i < tokens.size(); i++)
        {
             String str = (String) tokens.get(i);
             String[] parts = str.split(",");
            
             String CP = parts[0].substring(1).trim();
             String VP = parts[1].trim();
             if(parts.length==4)
             {
                 CP = ",";
             }
             String lineNumber = parts[2].substring(0,parts[2].length()-1);
             tokn[i] = new Token(CP, VP, lineNumber);  
        }
         System.out.println("MAPPED ALL TOKENS");

         //breaking tokens
         for (int i = 0; i < tokn.length; i++)
        {
             System.out.println(tokn[i].CP+" , "+tokn[i].VP+" , "+tokn[i].lineNumber);
        } //breaking tokens
         System.out.println("PRINTED");
         if(S())
        {
             System.out.println("SUCCESSFULLY PARSED");
             Passed = true;
        }
         else
        {
             System.out.println("NOT_PARSED");
             Passed = false;
        }
         
    }

     private boolean S()
    {
        // System.out.println("S RECEIVED "+tokn[i].CP);
         
         if(tokn[i].CP.compareTo("AccessModifier")==0)
        {
            i++;
            System.out.println("CALLING class_link from S");
            if(class_link())
            {
                 return true;
            }
        }
        return false; 
    }
     
     private boolean class_link()
    {
         System.out.println("CLASS LINK RECEIVE "+tokn[i].CP);
         if(tokn[i].CP.compareTo("ID")==0)
        {
             i++;
             System.out.println("CALLING class_base()");
             if(class_base())
            {
                 return true;
            }    
        }
          return false;
    }
     private boolean class_base()
    {
         System.out.println("class-base Receive "+tokn[i].CP);
         if(tokn[i].CP.compareTo("{")==0)
         {
             i++;
             System.out.println("CALLING CLASS BODY FROM class_base");
             if(class_body())
             {
                 return true;
             }
         }
         else if(tokn[i].CP.compareTo(":")==0)
         {
             i++;
             if(tokn[i].CP.compareTo("ID")==0)
             {
                 i++;
                 if(class_body())
                 {
                     return true;
                 }
             }
         }
         return false;
    }
     
     private boolean class_body()
    {
         System.out.println("Class_body RECEIVE "+tokn[i].CP);
         System.out.println("CALLING CLASS MEMBER");
          if(tokn[i].CP.compareTo("}")==0)
        {
             i++;
             return true;
        }
         if(class_member())
         {
             if(class_body())
             {
                 return true;
             }
         }
        
         return false;
    }
     
     private boolean class_member()
    {
        System.out.println("CLASS MEMER RECEIVE "+tokn[i].CP);
         if(tokn[i].CP.compareTo("AccessModifier")==0)
         {
             i++;
             System.out.println("CALLING MEMBER LINK");
             if(member_link())
             {
                 System.out.println("---------------------------------------passed member_link in class_member");
                 return true;
             }
         }
         else if(tokn[i].CP.compareTo("}")==0)
        {
             return true;
        }
         System.out.println("CLASS MEMr RETURN FALLSE");
         return false;
    }
     
     private boolean member_link(){
    
         System.out.println("MEMBER LINK REC "+tokn[i].CP);
         //(tokn[i].CP.compareTo("stored")==0)
        
         if(tokn[i].CP.compareTo("stored")==0)
        {
             i++;
             if(SS_A())
            {
                 return true;
            }  
       
        }
        
         
         else if(tokn[i].CP.compareTo("void")==0)
         {
            System.out.println("in void");
             i++;
           //  System.out.println("calling Method_Link3");
             if (Method_Link3()){
                 System.out.println("----------------------------------Methof_link3 passed member_link return true");
                 return true;
             }
             
         }
         else if(tokn[i].CP.compareTo("DT")==0)
         {
           //  System.out.println("IN DT");
             i++;
             //System.out.println("CALLING DT_2");
             if(DT_2())
             {
                 return true;
             }
         }
         else if(tokn[i].CP.compareTo("ID")==0)
         {
            // System.out.println("in ID");
             i++;
            // System.out.println("calling Object_Constructor_DEC");
             if (Object_Constructor_DEC()){
             
                 return true;
             }
         }
        
         return false;
    }
     
     private boolean DT_2()
    {
       System.out.println("IN DT_2 With "+tokn[i].CP);
         if(tokn[i].CP.compareTo("ID")==0)
         {
              i++;
            //  System.out.println("CALLING ID_1");
             if(ID_1())
            {
                   return true;
            }
         }
         else if(tokn[i].CP.compareTo("[")==0)
         {
             if (Array_DEC()){
                 return true;
             }
             
           //check this  
         }
         return false;
    }

     private boolean ID_1()
    {
         System.out.println("ID_1 RECEIVE "+tokn[i].CP);
         if(tokn[i].CP.compareTo("As_Op")==0)
         {
           //  System.out.println("CALLING VARIABLE_LINK2");
             if(Variable_Link2())
             {
                 return true;
             }
         }
         
         else if (Method_Link3()){
             return true;
         }
         //CONDITION FOR METHOD AND DIVERT TO METHOD 
         return false;
    }

    private boolean Variable_Link2() {
      //  System.out.println("IN VARIABLE_LINK2 WITH "+tokn[i].CP);
        if(tokn[i].CP.compareTo("As_Op")==0)
        {
             i++;
            // System.out.println("calling varaiable _value");
             if(Variable_Value())
             {
                     return true;
             }
        }
        else if(tokn[i].CP.compareTo(",")==0 || tokn[i].CP.compareTo(";")==0)
        {
            if(LIST())
            {
                return true;
            }
        }
        return false;
    }

     private boolean Variable_Value()
    {
       // System.out.println("IN VARIABLE VAL WITH "+tokn[i].CP);
       // System.out.println("CALLING EXP");
         if(Exp())
        {
            // System.out.println("EXP RETURNED TRUE CALLING LIST");
            // if(tokn[i].CP.compareTo(",")==0 || tokn[i].CP.compareTo(";")==0)
             if(LIST())
             {
                 return true;
             }
        }
         return false;
    }

     private boolean LIST()
    {
       System.out.println("IN LIST WITH "+tokn[i].CP);
        if(tokn[i].CP.compareTo(",")==0)
        {
           // System.out.println("CODE FOR DOT ");
            if (Variable_Link2()){
            return true;
            }
        }
        else if(tokn[i].CP.compareTo(";")==0)
        {
            i++;
            return true;
        }
        return false;
    }

    private boolean Exp() {
      System.out.println("IN EXP WITH "+tokn[i].CP);
        if(tokn[i].CP.compareTo("integer_constant")==0 || tokn[i].CP.compareTo("ID")==0 || tokn[i].CP.compareTo("!")==0 || tokn[i].CP.compareTo("inc_dec")==0 || tokn[i].CP.compareTo("float constant")==0 || tokn[i].CP.compareTo("char_const")==0 || tokn[i].CP.compareTo("String_const")==0 )
        {
            i++;
             return true;
           
    }
        return false;
    }
    
    private boolean OR_Exp(){
    if(tokn[i].CP.compareTo("integer_constant")==0 || tokn[i].CP.compareTo("ID")==0 || tokn[i].CP.compareTo("!")==0 || tokn[i].CP.compareTo("inc_dec")==0 || tokn[i].CP.compareTo("float constant")==0 || tokn[i].CP.compareTo("char_const")==0 || tokn[i].CP.compareTo("String_const")==0 ){
    
        if (AND_Exp()){
        
            if (OR_Exp2()){
            return true;
            }
        }
    }
        
        
        return false;
    }
    
    private boolean OR_Exp2(){
    
        if (tokn[i].CP.compareTo("||")==0){
        
            if (AND_Exp()){
             if (OR_Exp2()){
             return true;
             }
            }
        }
        
        else {
        return true;
        }
       return false;
    }
    
    private boolean AND_Exp(){
    if(tokn[i].CP.compareTo("integer_constant")==0 || tokn[i].CP.compareTo("ID")==0 || tokn[i].CP.compareTo("!")==0 || tokn[i].CP.compareTo("inc_dec")==0 || tokn[i].CP.compareTo("float constant")==0 || tokn[i].CP.compareTo("char_const")==0 || tokn[i].CP.compareTo("String_const")==0 ){

        if (ROP()){
        if (AND_Exp2()){
        return true;
        }
        }
    }
        
        return false;
    }
    
    private boolean AND_Exp2(){
    if (tokn[i].CP.compareTo("&&")==0){
        i++;
        if (ROP()){
        if (AND_Exp2()){
            return true;
        }
    
    }
    } 
    
    
    else {
    return true;
    }
        return false;
    
    }
    
    private boolean ROP(){
    if(tokn[i].CP.compareTo("integer_constant")==0 || tokn[i].CP.compareTo("ID")==0 || tokn[i].CP.compareTo("!")==0 || tokn[i].CP.compareTo("inc_dec")==0 || tokn[i].CP.compareTo("float constant")==0 || tokn[i].CP.compareTo("char_const")==0 || tokn[i].CP.compareTo("String_const")==0 ){
     if (E()){
     if (ROP2()){
     return true;
     }
     }
    }
        
        return false;
    }
    
    private boolean ROP2(){
    if (tokn[i].CP.compareTo("R_Op")==0){
    
        i++;
        if (E()){
        if (ROP2()){
        return true;
        }
        }
    }
    else {
    return true;
    }
        
        return false;
    }
    
    private boolean E(){
    if(tokn[i].CP.compareTo("integer_constant")==0 || tokn[i].CP.compareTo("ID")==0 || tokn[i].CP.compareTo("!")==0 || tokn[i].CP.compareTo("inc_dec")==0 || tokn[i].CP.compareTo("float constant")==0 || tokn[i].CP.compareTo("char_const")==0 || tokn[i].CP.compareTo("String_const")==0 ){
    
        if (T()){
        if (E2()){
        return true;
        }
        }
    }
        
        return false;
    }
    
    private boolean E2(){
    if (tokn[i].CP.compareTo("P_M")==0 ){
    i++;
     if (T()){
     if (E2()){
     return true;
     }
     }
    }
    else {
    return true;
    }  
        return false;
    
    }
    
    private boolean T(){
    if(tokn[i].CP.compareTo("integer_constant")==0 || tokn[i].CP.compareTo("ID")==0 || tokn[i].CP.compareTo("!")==0 || tokn[i].CP.compareTo("inc_dec")==0 || tokn[i].CP.compareTo("float constant")==0 || tokn[i].CP.compareTo("char_const")==0 || tokn[i].CP.compareTo("String_const")==0 ){
    
        if (F()){
        
            if (T2()){
            return true;
            }
        }
    
    }
        return false;
    }
    

    private boolean T2(){
    
        if (tokn[i].CP.compareTo("M_D_M")==0){
        i++;
        if (F()){
        if (T2()){
        return true;
        }
        }
        }
        
        else{
        return true;
        }
        return false;
    }
    
    private boolean F(){
    
        if (tokn[i].CP.compareTo("ID")==0){
        i++;
        if (id_op()){
        return true;
        }
        
        }
        
        else if (tokn[i].CP.compareTo("integer_constant")==0 || tokn[i].CP.compareTo("float constant")==0 || tokn[i].CP.compareTo("char_const")==0 || tokn[i].CP.compareTo("String_const")==0){
        
            if (CONST()){
            return true;
            }
        }
        
        else if (tokn[i].CP.compareTo("!")==0){
        i++;
        if (F()){
        return true;
        }
        }
        
        else if (tokn[i].CP.compareTo("(")==0){
        i++;
        if (Exp()){
        if (tokn[i].CP.compareTo(")")==0){
        i++;
        return true;
        }
        }
        }
        
        else if (tokn[i].CP.compareTo("inc_dec")==0){
        
            i++;
            if (tokn[i].CP.compareTo("ID")==0){
            i++;
            if (inc_dec_list()){
            return true;
            }
            }
        }
        
        return false;
    }
    
    private boolean inc_dec_list(){
        if (tokn[i].CP.compareTo("[")==0){
            i++;
        if (Exp()){
        if (tokn[i].CP.compareTo("]")==0){
        i++;
        return true;
        }
        }
        }
        
        else if(tokn[i].CP.compareTo(".")==0){
        i++;
        if (tokn[i].CP.compareTo("ID")==0){
        i++;
        if (tokn[i].CP.compareTo("[")==0){
        i++;
        if (Exp()){
        if (tokn[i].CP.compareTo("]")==0){
        i++;
        return true;
        }
        }
        }
        }
        }
        
        else {
        return true;
        }
        
    return false;
    }
    
    private boolean id_op(){
    
        if (tokn[i].CP.compareTo("(")==0){
        if (Method_Call_1()){
        return true;
        }
        
        }
        
        else if (tokn[i].CP.compareTo("(")==0){
        i++;
        if (Exp()){
        if (tokn[i].CP.compareTo(")")==0){
        i++;
        return true;
        }
        }
        }
        
        else if (tokn[i].CP.compareTo("inc_dec")==0){
        i++;
        }
        
        else if(tokn[i].CP.compareTo(".")==0){
        if (member_exp()){
        return true;
        }
        }
        
        else {
        return true;
        }
        return false;
    }
    
    
    private boolean member_exp(){
    
        if (tokn[i].CP.compareTo(".")==0){
        i++;
        if (tokn[i].CP.compareTo("ID")==0){
        i++;
        if (member_exp()){
        return true;
        }
        
        }
        }
        return false;
    }
    
    private boolean SS_A() {
         
        System.out.println("IN SS_A");
        if (tokn[i].CP.compareTo("DT")==0){
            i++;
            //if ((tokn[i].CP.compareTo("ID")==0)){
              //  i++;
            if (DT_1()){
                return true;
            }
            //}
        }
       else if (tokn[i].CP.compareTo("ID")==0){
           
            i++;
            //if ((tokn[i].CP.compareTo("ID")==0)){
              //  i++;
            if (Id_OArray()){
                return true;
            }
            //}
       }
       else if (tokn[i].CP.compareTo("void")==0){
             i++;
            // if (tokn[i].CP.compareTo("(")==0){
              //   i++;
             if (Method_Link3()){
                 return true;
             }
             //}
             
       }
           return false;
    }

    private boolean Method_Link3() {
      
        System.out.println("METHOD_LINK_3 rec" + tokn[i].CP);
        if(tokn[i].CP.compareTo("main")==0)
        {
            i++;
            System.out.println("PASSED MAIn");
        }   
         if (tokn[i].CP.compareTo("(")==0)
        {
             i++;
             System.out.println("PASSED (");
             if (List_Param())
        {
             System.out.println("PASSED PARM LIST");
             if (tokn[i].CP.compareTo(")")==0)
            {
                i++;
                if (tokn[i].CP.compareTo("{")==0)
               {
                   i++;
                   if (M_ST())
                  {
                      System.out.println(" MST PASSED IN MAIN");
                       if (tokn[i].CP.compareTo("}")==0)
                      {
                          
                          System.out.println("PASSED }");
                          i++;
                          System.out.println("NEXT TOKEN IS "+tokn[i].CP);
                       return true;
                      }
                  }System.out.println("MST NOT PASSED IN MAIN");
               }
            }
        }
    }  
      return false;
}
   
    private boolean Object_Constructor_DEC() {
        
        if ((tokn[i].CP.compareTo("ID")==0)){
            i++;
        if(object_link()){
            return true;
        }
        }
        
       
        else if(tokn[i].CP.compareTo("(")==0){
            i++;
         if(Constructor_DEC())
        {
            return true;
        }
        }
        
      return false;  
    }

    private boolean object_link() {
        if (tokn[i].CP.compareTo("ID")==0){
        i++;
        if (Object_Creation_Exp()){
        return true;
        }
        }
        
        else if(tokn[i].CP.compareTo("[")==0){
        i++;
        
        if (tokn[i].CP.compareTo("]")==0){
        i++;
        
        if (tokn[i].CP.compareTo("ID")==0){
        i++;
        
        if (object_array_dec()){
        return true;
        }
        }
        }
        }
        
        return false;
    }

    private boolean Constructor_DEC() {
        if (tokn[i].CP.compareTo("(")==0){
        i++;
        
        if (List_Param()){
            
            if (tokn[i].CP.compareTo(")")==0){
            i++;
            
            if (tokn[i].CP.compareTo("{")==0){
            i++;
            
            if (M_ST()){
            
                if (tokn[i].CP.compareTo("}")==0){
                i++;
                return true;
                }
            }
            }
            }
        }
        }
        
      return false;
    }

    private boolean Array_DEC() {
        if (tokn[i].CP.compareTo("[")==0){
        i++;
        
        if (tokn[i].CP.compareTo("]")==0){
        i++;
            System.out.println("PASEED TILL [");
        
        
        if (INIT_Array()){
            System.out.println("Array_DEC Ret true");
        return true;
        }
        
        }
        }
        
        return false;
    }
    
    private boolean INIT_Array(){
    
        System.out.println("In INT_array with "+tokn[i].CP);
        if (tokn[i].CP.compareTo(";")==0){
        
            i++;
        }
        
        else if (tokn[i].CP.compareTo("As_Op")==0){
        
            i++;
            System.out.println("Checking new");
            if (tokn[i].VP.compareTo("new")==0){
            i++;
                System.out.println("Checking DT");
            if (tokn[i].CP.compareTo("DT")==0){
            i++;
            if (tokn[i].CP.compareTo("[")==0){
            i++;
                System.out.println("PASED TILLLLL [");
            if (ID_Constant()){
                
            if (tokn[i].CP.compareTo("]")==0){
                System.out.println("passed ]");
            i++;
            if (Array_Const()){
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
    
    private boolean Array_Const(){
         System.out.println("IN Array _Const with "+tokn[i].CP);
        if (tokn[i].CP.compareTo("{")==0){
        
            if (Array_C()){
            return true;
            }
        }
        
        else if(tokn[i].CP.compareTo(";")==0){
        i++;
        return true;
        }
    
        
        return false;
    }
    
    private boolean Array_C(){
    
        if (tokn[i].CP.compareTo("{")==0){
        i++;
        
        if (Exp()){
        
           if (Array_C2()) {
           return true;
           }
           
        }
        }
        return false;
    }
    
    private boolean Array_C2(){
    
        if (tokn[i].CP.compareTo(",")==0){
        i++;
        
        if (Exp()){

            if (Array_C2()){
            return true;
            }
        }
        }
        
        else if(tokn[i].CP.compareTo("}")==0){
        i++;
        
        if (tokn[i].CP.compareTo(";")==0){
        i++;
        }
        }
        return false;
    }

    private boolean DT_1() {
        if (tokn[i].CP.compareTo("ID")==0){
            i++;
            if (ID_2()){
                return true;
            }
        }
        
        else if ((tokn[i].CP.compareTo("[")==0)){
            i++;
         if(Array_DEC()){
            return true;
        }
        }
      return false;  
    }

    private boolean Id_OArray() {
         if(tokn[i].CP.compareTo("ID")==0)
         {
              i++;
              //System.out.println("CALLING ID_A");
             if(Id_A())
            {
                   return true;
            }
         }
         else if(tokn[i].CP.compareTo("[")==0)
         {
             if (Array_DEC()){
                 return true;
             }
             
           //check this  
         }
         return false;
    }

    private boolean ID_2() {
        if(tokn[i].CP.compareTo("As_Op")==0)
         {
             i++;
            // System.out.println("CALLING VARIABLE_LINK2");
             if(Variable_Link2())
             {
                 return true;
             }
         }
         
        else if ((tokn[i].CP.compareTo("(")==0)){
            i++;
          if (Method_Link3()){
             return true;
         }
        }
         //CONDITION FOR METHOD AND DIVERT TO METHOD 
         return false;
    }

    private boolean Id_A() {
        if (tokn[i].CP.compareTo("(")==0){ 
            i++;
        if (Method_Link3()){
            return true;
        }
        }
        
        else if ((tokn[i].CP.compareTo("=")==0)){
            i++;
            
         if(Object_Creation_Exp()){
            return true;
        }
        }
        return false;
    }

    private boolean Object_Creation_Exp() {
        if (tokn[i].CP.compareTo("=")==0){
        i++;
        if(tokn[i].CP.compareTo("new")==0){
        i++;
        if (tokn[i].CP.compareTo("ID")==0){
        i++;
        if(tokn[i].CP.compareTo("(")==0){
        i++;
        if (Param()){
        
            if (tokn[i].CP.compareTo(")")==0){
            i++;
            
            if (Object_List()){
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
         
    private boolean Object_List(){
    
        if (tokn[i].CP.compareTo(",")==0){
        i++;
        if (tokn[i].CP.compareTo("ID")==0){
        i++;
        if (Object_Creation_Exp()){
        return true;
        }
        }
        }
        
        else if (tokn[i].CP.compareTo(";")==0){
        i++;
        }
        return false;
    }
    
    private boolean DT_A(){
        if(tokn[i].CP.compareTo("As_Op")==0)
         {
             System.out.println("CALLING VARIABLE_LINK2");
             if(Variable_Link2())
             {
                 return true;
             }
         }
         
        else if(tokn[i].CP.compareTo("(")==0){
          if (Method_Link3()){
             return true;
                }
         }
         //CONDITION FOR METHOD AND DIVERT TO METHOD 
         return false;
    }
    
    private boolean CONST(){
        System.out.println("HERE IN CONST WITH "+tokn[i].CP);
        if(tokn[i].CP.compareTo("integer_constant")==0){
        i++;
        return true;
        }
        
        else if(tokn[i].CP.compareTo("float constant")==0){
       i++;
       return true;
    }
        
        else if(tokn[i].CP.compareTo("char_const")==0){
       i++;
       return true;
        } 
        
        else if(tokn[i].CP.compareTo("String_const")==0){
       i++;
       return true;
        }
     return false;   
     
    }
    private boolean ID_Constant(){
        if(tokn[i].CP.compareTo("ID")==0){
            i++;
        }
        
        else if(tokn[i].CP.compareTo("integer_constant")==0 || tokn[i].CP.compareTo("float constant")==0 ||tokn[i].CP.compareTo("char_const")==0 || tokn[i].CP.compareTo("String_const")==0){
         if (CONST()){
            return true;
        }
        }
        
        
    return false;
    }
    
    private boolean Return_Type(){
        if(tokn[i].CP.compareTo("void")==0){
            i++;
        }
    
        else if(tokn[i].CP.compareTo("DT")==0){
        i++;
        }
    return false;
    }
    
    private boolean Body(){
        if(tokn[i].CP.compareTo(";")==0){
        i++;
        }
        
        
       // else if (tokn[i].CP.compareTo(";")==0){
        if (S_ST()){
        return true;
        }
        //}  check this
        
        else if(tokn[i].CP.compareTo("{")==0){
          i++;
        
        if (M_ST()){
        return true;
        }
        
        if(tokn[i].CP.compareTo("}")==0){
        i++;
        }
    }
    
    return false;
    }

     private boolean S_ST()
    {
         System.out.println("IN SST WITH "+tokn[i].CP);
         if (tokn[i].CP.compareTo("either")==0)
        {
             if (either())
            {
             return true;
            }
        }
        
         else if (tokn[i].CP.compareTo("DT")==0)
        {
             i++;
             if (S_St_DT())
            {
             return true;
            }
        }
        
         else if(tokn[i].VP.compareTo("repeat")==0)
        {
            System.out.println("CALLIUING REPEAT");
             if (repeat())
            {
             return true;
            }
        }
        
        else if(tokn[i].CP.compareTo("let_next")==0){
         if (let_next()){
        return true;
        }
        }
        
        else if (tokn[i].CP.compareTo("return")==0){
         if (Return()){
        return true;
        }
        }
        
        else if(tokn[i].CP.compareTo("stop")==0){
         if (stop()){
        return true;
        }
        }
       
        else if (tokn[i].CP.compareTo("carry")==0){
         if (carry()){
            return true;
        }
        }
        
        
        else if (tokn[i].CP.compareTo("that")==0){ 
       if (that()){
        
            return true;
        }
        }
        
         else if (tokn[i].CP.compareTo("ID")==0)
        {
             System.out.println("CALLING SST _ ID ");
             i++;
             if (S_St_ID())
            {
                 return true;
            }
        }
        
        else if (tokn[i].CP.compareTo("inc_dec")==0){
         i++;
         
          if (tokn[i].CP.compareTo("ID")==0){
          i++;
          
          
          if (inc_dec_list()){
           //return true;
          
          
          if (tokn[i].CP.compareTo(";")==0){
          i++;
          }
          }
        }
        }
           
    return false;    
    }

   
    
     private boolean M_ST()
    {
        System.out.println("IM MST WITH "+tokn[i].CP);
         if(tokn[i].CP.compareTo("repeat")==0 || tokn[i].CP.compareTo("either")==0 || tokn[i].CP.compareTo("DT")==0  || tokn[i].CP.compareTo("let_next")==0  || tokn[i].CP.compareTo("return")==0   || tokn[i].CP.compareTo("inc_dec")==0   || tokn[i].CP.compareTo("ID")==0 || tokn[i].CP.compareTo("stop")==0 || tokn[i].CP.compareTo("carry")==0 || tokn[i].CP.compareTo("that")==0)
        {   
            System.out.println("IN M_ST WITH "+tokn[i].CP);
             if(S_ST())
            {
                 if (M_ST())
                {
                     return true;
                }
            }
             else 
            {
                 return false;
            }
        }
         else if(tokn[i].CP.compareTo("}")==0)
         {
             return true;
         }
        return false;
    }
    
    private boolean Method_Call_1(){
        if(tokn[i].CP.compareTo("(")==0){
        i++;
        
         
        if (Param()){
       // return true;
        
        
        if(tokn[i].CP.compareTo(")")==0){
        i++;
        }
        return true;
        }
    }
    
    return false;
    }

    private boolean Param() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean either() {
        if (tokn[i].CP.compareTo("either")==0){
        i++;
        
        if (tokn[i].CP.compareTo("(")==0){
        i++;
        
        if (Exp()){
        
            if (tokn[i].CP.compareTo(")")==0){
            i++;
            
            if (Body()){
            return true;
            }
            }
        }
        }
        }
        
        
        return false;
    }
    
    

     private boolean S_St_DT()
    {
         if (tokn[i].CP.compareTo("ID")==0)
        {
             i++;
         
             if (S_St_DT2())
            {
                 return true;
            }
             else if (tokn[i].CP.compareTo("void")==0 || tokn[i].CP.compareTo("DT")==0)
            {
                 if(Method_DEC())
                {
                     return true;
                }
            }
             else if (tokn[i].CP.compareTo("[")==0)
            {
                if(Array_DEC())
               {
                 System.out.println("Arrar_DEC return true");
                    return true;
               }
            }
        }
        return false;
    }

    private boolean repeat() {
        if (tokn[i].VP.compareTo("repeat")==0){
        i++;
        if (tokn[i].CP.compareTo("(")==0){
        i++;
            System.out.println("passed repeat till (");
        if (F1()){
            {
            if (F2()){
                System.out.println("F2 passed");
                if (tokn[i].CP.compareTo(";")==0){
                    i++;
                    if (F3()){
                        System.out.println("F3 Passed");
                        System.out.println("Token is "+tokn[i].CP);
                    if (tokn[i].CP.compareTo(")")==0){
                        i++;
                        System.out.println(") PASSED");
                    if (Body()){
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
    
    private boolean F1(){
    
        if (tokn[i].CP.compareTo("DT")==0){
            System.out.println("IN f1 WITH DT");
        
            if (DECL()){
                System.out.println("F1 return true");
            return true;
            }
        }
        
        else if(tokn[i].CP.compareTo("ID")==0){
        i++;
        if (Assign_Op()){
        return true;
        }
        }
        
        else {
            return true;
        }
        
        return false;
    }
    
    private boolean F2(){
     if (tokn[i].CP.compareTo("ID")==0 || tokn[i].CP.compareTo("!")==0 || tokn[i].CP.compareTo("inc_dec")==0 || tokn[i].CP.compareTo("integer_constant")==0 || tokn[i].CP.compareTo("float constant")==0 || tokn[i].CP.compareTo("char_const")==0 || tokn[i].CP.compareTo("String_const")==0){
     
         if (Exp()){
             System.out.println("EXP RETURN TRUE");
          if (X()){
              System.out.println("X return true");
          return true;
          }
         }
         
     }
      
     else  {
     return true;
     }
        return false;
    }
    
    private boolean X(){
    
        if (tokn[i].CP.compareTo(",")==0){
        i++;
        if (Exp()){
        if (X()){
        return true;
        }
        }
        }
        
        else {
        return true;
        }
        return false;
    }

    private boolean F3(){
    
        if (tokn[i].CP.compareTo("inc_dec")==0){
        i++;
        if (tokn[i].CP.compareTo("ID")==0){
        i++;
        }
        }
        
        else if (tokn[i].CP.compareTo("ID")==0){
            i++;
            System.out.println("PassedId In F3");
            if (F4()){
            return true;
            }
        
        }
        else {
        return true;
        }
        
        return false;
    }
    
    private boolean F4(){
    
        if (tokn[i].CP.compareTo("inc_dec")==0){
        i++;
        return true;
        }
        
        else if (tokn[i].CP.compareTo("As_Op")==0){
            i++;
            if (Exp()){
            return true;
            }
            
        }
        return false;
    }
    
    private boolean let_next() {
        System.out.println("IN_LET NEXT WITH "+tokn[i].CP);
        if (tokn[i].CP.compareTo("let_next")==0){
        i++;
        
        if (tokn[i].CP.compareTo("(")==0){
        i++;
        
        if (Exp()){
       // return true;
        
        if (tokn[i].CP.compareTo(")")==0){
        i++;
         
        if (tokn[i].CP.compareTo("{")==0){
        i++;
        
        if (M_ST()){
        
            if (tokn[i].CP.compareTo("}")==0){
            i++;
            
            if (next()){
            return true;
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
    
    

    private boolean Return() {
        if (tokn[i].CP.compareTo("return")==0){
        i++;
        
        if (Return2()){
        return true;
        }
        }
        
        
        return false;
    }

    private boolean Return2(){
        if (tokn[i].CP.compareTo(";")==0){
        i++;
        }
    
        //check exp
        else if (Exp()){
        
            if (tokn[i].CP.compareTo(";")==0){
            i++;
            }
        }
       return false;
    }
    
    private boolean stop() {
        if (tokn[i].CP.compareTo("stop")==0){
        i++;
        
        if (tokn[i].CP.compareTo(";")==0){
        i++;
        }
        }
        return false;
    }

    private boolean carry() {
        if (tokn[i].CP.compareTo("carry")==0){
        i++;
        
        if (tokn[i].CP.compareTo(";")==0){
        i++;
        }
        }
        return false;
    }

    private boolean that() {
        if (tokn[i].CP.compareTo("that")==0){
         i++;
         
         if (LIST1()){
         return true;
         }
        }
        
        
        return false;
    }
    
    private boolean LIST1(){
        if (tokn[i].CP.compareTo(";")==0){
         i++;
        }
        
        else if (tokn[i].CP.compareTo("As_Op")==0){
        i++;
        
        if (LIST2()){
         return true;
        }
        }
        
        return false;
    
    }

    private boolean LIST2(){
    if (tokn[i].CP.compareTo("ID")==0){
     i++;
     
     if (INIT()){
     
         if (tokn[i].CP.compareTo(";")==0){
         i++;
         
         return true;
         }
     }
    }
    
    else if (tokn[i].CP.compareTo("integer_constant")==0 || tokn[i].CP.compareTo("float constant")==0 || tokn[i].CP.compareTo("char_const")==0 || tokn[i].CP.compareTo("String_constant")==0){
     if(CONST()){
    
        if (tokn[i].CP.compareTo(";")==0){
        i++;
        
        return true;
    }
    }
    }
        return false;
    
    }

    
    private boolean INIT(){
        if (tokn[i].CP.compareTo("As_Op")==0){
        i++;
        if (CONST()){
        
        return true;
       
        }
        }
        
        else if(tokn[i].CP.compareTo("=")==0){
        i++;
        if (INIT()){
        if (LIST()){
        return true;
        }
        }
        }
        
        else 
        {
            return true;
        }
    return false;
    }
    
    private boolean S_St_ID() {
        System.out.println("IN S_St_ID WITH "+tokn[i].CP);
        if (tokn[i].CP.compareTo("inc_dec")==0){
         i++;
        
        if (tokn[i].CP.compareTo(";")==0){
         i++;
        }  
        }
        
        else if (tokn[i].CP.compareTo("As_Op")==0){
            System.out.println("As_Op Passed calling Assign_Op");
        if (Assign_Op()){
        return true;
        }
        }
        
        else if (tokn[i].CP.compareTo("ID")==0){
         i++;
         if (Object_Creation_Exp()){
         return true;
         }
         
        }
        
        else if (tokn[i].CP.compareTo("(")==0){
         if (Method_Call_1()){
         //return true;
         
          if (tokn[i].CP.compareTo(";")==0){
         i++;
          return true;
          }
       }
        }
        
        else if (tokn[i].CP.compareTo(".")==0){
            
         if(Object_Call()){
        if (tokn[i].CP.compareTo(";")==0){
         i++;
         }
          return true;
        }
        }
       
      
        
        else if(tokn[i].CP.compareTo("[")==0){
            System.out.println("S_St_ID GOT [");
        i++;
        
         if (Array_Access()){
             return true;
         }
        }
        
        else if (tokn[i].CP.compareTo("ID")==0){
         i++;
         
         if (Object_Creation_Exp()){
         return true;
         }
        }
            
        
        return false;
    }

    

    private boolean Assign_Op() {
        if (tokn[i].CP.compareTo("As_Op")==0){
        i++;
            System.out.println("Calling Assign_Op2");
        if (Assign_Op2()){
         return true;
        }
        }
        
        
        
        return false;
    }

    private boolean Array_Access() {
        //check this
        if (Exp()){
            System.out.println("EXP RETURN TRUE FOR ARRAY EXP");
        
        if (tokn[i].CP.compareTo("]")==0){
        i++;
        
        if (Assign_Op()){
            return true;
        }
        }
        //return true;
        }
        
        else if(tokn[i].CP.compareTo("]")==0){
        i++;
         
        if (tokn[i].CP.compareTo("ID")==0){
        i++;
        
        if (object_array_dec()){
        return true;
        }
        }
        }
        
        return false;
      
    }

    private boolean object_array_dec() {
        if (tokn[i].CP.compareTo("=")==0){
        i++;
        if (tokn[i].CP.compareTo("new")==0){
        i++;
        if (tokn[i].CP.compareTo("ID")==0){
        i++;
        if (tokn[i].CP.compareTo("[")==0){
        i++;
        
        if (Exp()){
         
            if (tokn[i].CP.compareTo("]")==0){
            i++;
            
            if (obj_arr_dec1()){
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

    private boolean obj_arr_dec1(){
    
        if (tokn[i].CP.compareTo(";")==0){
        i++;
        }
        
        else if(tokn[i].CP.compareTo("{")==0){
        i++;
        
        if (obj_arr_dec2()){
         
            return true;
        }
        }
        return false;
    }
    
    private boolean obj_arr_dec2(){
    
        if (tokn[i].CP.compareTo("new")==0){
        i++;
        if(tokn[i].CP.compareTo("ID")==0){
        i++;
        if(tokn[i].CP.compareTo("(")==0){
        i++;
        if (Param()){
        
            if (tokn[i].CP.compareTo(")")==0){
            i++;
            
            if (obj_arr_dec3()){
            return true;
            }
            }
        }
        
        }
        }
        }
        
        return false;
    }
    
   private boolean obj_arr_dec3(){
   
       if (tokn[i].CP.compareTo(",")==0){
       i++;
       if (obj_arr_dec2()){
       return true;
       }
       }
       
       else if (tokn[i].CP.compareTo("}")==0){
       i++;
       if (tokn[i].CP.compareTo(";")==0){
       i++;
       }
       }
       return false;
   }
    
    private boolean Object_Call() {
        if (tokn[i].CP.compareTo(".")==0){
        i++;
        
        if (Exp()){
        return true;
        }
        }
        
        else if (tokn[i].CP.compareTo("[")==0){
        i++;
        if(Exp()){
        
            if (tokn[i].CP.compareTo("]")==0){
            i++;
            
            if (tokn[i].CP.compareTo(".")==0){
                i++;
                if (Exp()){
                return true;
                }
                
            }
        }
        }
        }
        return false;
    }

    private boolean S_St_DT2() {
        if (tokn[i].CP.compareTo("As_Op")==0 || tokn[i].CP.compareTo(",")==0 || tokn[i].CP.compareTo(";")==0){
        if (Variable_Link2()){
        return true;
        }
        }
       return false; 
    }

    private boolean Method_DEC() {
        if (tokn[i].CP.compareTo("void")==0 || tokn[i].CP.compareTo("DT")==0){
        
            if (Return_Type()){
            
                if (tokn[i].CP.compareTo("ID")==0){
                i++;
                
                if (Method_Link3()){
                return true;
                }
                }
            }
        }
        
        return false;
    }

    private boolean DEC(){
     
        if (tokn[i].CP.compareTo("DT")==0){
        i++;
        
        if (Variable_Link()){
        return true;
        }
        }
        return false;
    }

    private boolean Variable_Link() {
        if (tokn[i].CP.compareTo("ID")==0){
        i++;
        
        if (Variable_Link2()){
        return true;
        }
        }
        
        return false;
    }

    private boolean Assign_Op2() {
         System.out.println("Calling Exp()");
         if (Exp())
        {
             System.out.println("EXP PASSED!!!!!!");
             System.out.println("CURRENT TOKEN IS "+tokn[i].CP);
             if (tokn[i].CP.compareTo(";")==0)
            {
                 i++;
                 return true;
            }
             System.out.println("EXP PASSED EXPECTED ;");
        }
        System.out.println("EXP RETURN FALSE ");
        return false;
    }

 private boolean List_Param()
{
    System.out.println("IN LIST_PARAM WITH "+tokn[i].CP +" of "+tokn[i].VP);
     if(tokn[i].CP.compareTo("DT")==0)
    {
         i++;
         if(tokn[i].CP.compareTo("ID")==0)
        {
             i++;
             System.out.println("CALLING LIST_PARAM_1 HERE");
             if(List_Param1())
            {
                 return true;
            }
             else 
            {
                 return true;
            }
        }  
        //return false;
    }
     else if(tokn[i].CP.compareTo(")")==0)
     {
          System.out.println("RETUN TRUE FOR (");
          return true;
         
     }
     return false;
    }
    
    private boolean DECL(){
    
        if (tokn[i].CP.compareTo("DT")==0){
        i++;
            System.out.println("DT PASSED ");
            
         if(tokn[i].CP.compareTo("ID")==0)
         {
             i++;
             System.out.println("PASSED ID CALLING INIT FROM DECL");
         }
         if (INIT()){
         
             if (LIST()){
             return true;
             }
         }
        }
        
        return false;
    }

     private boolean List_Param1()
    {
         System.out.println("IN List_Param1 with "+tokn[i].CP);
         System.out.println("HEREEEEE");
         System.out.println("Next Token is "+tokn[i].CP);
         if(tokn[i].CP.compareTo(",")==0)
        {
             i++;
             System.out.println("PASSED ,");
             if(tokn[i].CP.compareTo("DT")==0)
            {
                 i++;
                 if(tokn[i].CP.compareTo("ID")==0)
                {
                     i++;
                     if(List_Param1())
                    {
                         return true;
                    }
                }
            }
             else
            {
                 return true;
            }
        }
         else if(tokn[i].CP.compareTo(")")==0)
         {
             System.out.println("List retun true");
             return true;
         }
         System.out.println("List_Param return false");
         return false;
    }

    
    private boolean next(){
if(tokn[i].CP.compareTo("next")==0){
i++;
if(tokn[i].CP.compareTo("{")==0){
i++;
if(M_ST()){
return true; }
}
}
else { return true; }

return false ;
}

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
        
    }
   
  
    
    

    
    
    
    

    


    
        



