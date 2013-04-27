/*
 * To change this template, choose Tools | Templates 
 * and open the template in the editor.
 */
package org.scauhci.enumvalue;

/**
 *
 * @author Administrator
 */
public enum IsFinishEnum {
    isFinish(0),     //代表订单已处理
    noFinish(1);      //代表订单未处理
    
    public final int value;

    IsFinishEnum(int value) {
        this.value = value;
    }
}
