/*
 * To change this template, choose Tools | Templates 
 * and open the template in the editor.
 */
package org.scauhci.enumvalue;

/**
 *
 * @author willing
 */
public enum RoleNameEnum {

    root(0),                 //代表订单已处理
    station_manager(1);      //代表订单未处理
    public final int value;

    RoleNameEnum(int value) {
        this.value = value;
    }
}
