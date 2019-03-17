/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operator;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author NadiaGhafoor
 */
public class Arithmetic {

    private List<Character> P_M = Arrays.asList('+', '-');
    private List<Character> M_D_M = Arrays.asList('*', '/', '%');

    public List<Character> getP_M(){
        return P_M;
    }
    public List<Character> getM_D_M(){
        return M_D_M;
    }
}
