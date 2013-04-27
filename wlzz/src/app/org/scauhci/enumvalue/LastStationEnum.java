/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scauhci.enumvalue;

/**
 *
 * @author Administrator
 */
public enum LastStationEnum {
    isLastStation(-1);   //代表最后一个工作站
    
    public final int value;

    LastStationEnum(int value) {
        this.value = value;
    }
    
}
