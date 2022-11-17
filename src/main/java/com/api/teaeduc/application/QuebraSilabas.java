package com.api.teaeduc.application;


import java.util.*;

public class QuebraSilabas {
    private ArrayList<String> letrasArray = new ArrayList<String>();
    private ArrayList<String> silabasArray = new ArrayList<String>();
    private String silaba = new String();
    private int i = 0;

    public boolean verificarVogal(String s) {
            switch(s){
                case "a":
                    return true;
                case "e":
                    return true;
                case "i":
                    return true;
                case "o":
                    return true;
                case "u":
                    return true;
            }
        return false;
    }
    public boolean vogaisUnidas(String s) {
        switch(s){
            case "ai":
                return true;
            case "au":
                return true;
            case "ei":
                return true;
            case "ou":
                return true;
            case "ue":
                return true;
            case "uê":
                return true;
            case "uei":
                return true;
        }
    return false;
    }
    public boolean verificarConsoanteDeUniao(String s) {
        switch(s){
            case "l":
                return true;
            case "h":
                return true;
            case "r":
                return true;
            case "s":
                return true;
        }
    return false;
    }
    public boolean verificarVogalComAcento(String s) {
        switch(s){
            case "á":
                return true;
            case "é":
                return true;
            case "í":
                return true;
            case "ó":
                return true;
            case "ú":
                return true;
            case "â":
                return true;
            case "ê":
                return true;
            case "î":
                return true;
            case "ô":
                return true;
            case "û":
                return true;
            case "à":
                return true;
            case "è":
                return true;
            case "ì":
                return true;
            case "ò":
                return true;
            case "ù":
                return true;
            case "ã":
                return true;
            case "õ":
                return true;
        }
    return false;
    }

    private void SetSilabas(int total){
        switch(total) {
            case 1:
                silabasArray.add(letrasArray.get(i));
                break;
            case 2:
                silaba = letrasArray.get(i) + letrasArray.get(i+1);
                silabasArray.add(silaba);
                i++;
                break;
            case 3:
                silaba = letrasArray.get(i) + letrasArray.get(i+1) + letrasArray.get(i+2);
                silabasArray.add(silaba);
                i=i+2;
                break;
            case 4:
                silaba = letrasArray.get(i) + letrasArray.get(i+1) + letrasArray.get(i+2)+ 
                letrasArray.get(i+3);
                silabasArray.add(silaba);
                i=i+3;
                break;
            case 5:
                silaba = letrasArray.get(i) + letrasArray.get(i+1)+ 
                letrasArray.get(i+2)+ letrasArray.get(i+3)+ letrasArray.get(i+4);
                silabasArray.add(silaba);
                i=i+4;
                break;
        }
    }

    public ArrayList<String> ContarSilabas(String s) {
        
        s = s.toLowerCase();
        if(s.length() == 1){
            letrasArray.add(s);
            return letrasArray;
        }
        for (i = 0; i < s.length(); i++) {
            letrasArray.add(String.valueOf(s.charAt(i)));
        }
        for (i = 0; i < s.length(); i++) {
            
            if(s.length() == i+5){
                if(verificarConsoanteDeUniao(letrasArray.get(i+1))&& verificarVogalComAcento(letrasArray.get(i+2))){
                    if((letrasArray.get(i+2).charAt(0) == 'ã') || (letrasArray.get(i+2).charAt(0) == 'õ')){

                        if(s.length() == i+5){
                            if(letrasArray.get(i+4).charAt(0)== 's'){
                                SetSilabas(5);
                                return silabasArray;
                            }else{
                                SetSilabas(4);
                                return silabasArray;
                            }
                        }  
                    }
                    
                }
            }
            if(s.length() > i){
                if(verificarVogal(letrasArray.get(i))){   
                    silaba = letrasArray.get(i) + letrasArray.get(i+1);
                    if(vogaisUnidas(silaba)){
                        SetSilabas(1);
                    }
                    else if(verificarVogal(letrasArray.get(i+1))==false){   
                        
                        if(s.length() >= i+4){
                            if(verificarConsoanteDeUniao(letrasArray.get(i+2))){
                                if(letrasArray.get(i+1).equals("s")){
                                    SetSilabas(2);
                                }
                                else if(verificarVogal(letrasArray.get(i+3))
                                ||verificarVogalComAcento(letrasArray.get(i+3))){
                                    SetSilabas(1);
                                }
                                else
                                {
                                    SetSilabas(2);
                                }
                            }else if(verificarVogal(letrasArray.get(i+1))==false && verificarVogal(letrasArray.get(i+2))==false && verificarConsoanteDeUniao(letrasArray.get(i+2))==false){
                                SetSilabas(2);
                            }else{
                                SetSilabas(1);
                            }
                        }
                        else if(s.length() >= i+3){
                            if(letrasArray.get(i).equals("-")){
                                SetSilabas(1);
                                SetSilabas(2);
                            }
                            else if(verificarVogal(letrasArray.get(i+1))==false 
                            && verificarVogal(letrasArray.get(i+2))
                            || verificarVogalComAcento(letrasArray.get(i+2))){
                                SetSilabas(1);
                            }else{
                                SetSilabas(1);
                            }
                        }else if(s.length() >= i+2){
                            SetSilabas(2);
                        }else{    
                            SetSilabas(1);
                        }                
                    }
                    
                }else{//consoante regra
                    if(s.length() >= i+4){
                        if(s.length() >= i+5){
                            if((letrasArray.get(i+3).charAt(0)== 's' || letrasArray.get(i+3).charAt(0)== 'r')&& verificarConsoanteDeUniao(letrasArray.get(i+1)) &&letrasArray.get(i+3).equals(letrasArray.get(i+4))){
                                SetSilabas(4);
                                continue;
                            }
                        }
                        if(verificarVogal(letrasArray.get(i))==false && verificarVogal(letrasArray.get(i+1)) && verificarVogal(letrasArray.get(i+2)) && verificarVogal(letrasArray.get(i+3))){
                            silaba = letrasArray.get(i+1) + letrasArray.get(i+2)+ letrasArray.get(i+3);
                            if(vogaisUnidas(silaba)){
                                SetSilabas(4);
                            }
                        }else if(verificarVogal(letrasArray.get(i+1)) && (verificarVogal(letrasArray.get(i+2))||verificarVogalComAcento(letrasArray.get(i+2))) && verificarVogal(letrasArray.get(i+3))==false){
                            silaba = letrasArray.get(i+1) + letrasArray.get(i+2);
                            if(vogaisUnidas(silaba) && letrasArray.get(i+3).equals("s")){
                                SetSilabas(4);
                            }else if(vogaisUnidas(silaba)){
                                SetSilabas(3);
                            }
                            else{
                                SetSilabas(2);
                            }
                        }else if(verificarVogal(letrasArray.get(i+1)) 
                         && verificarVogal(letrasArray.get(i+2))){
                            if(vogaisUnidas(letrasArray.get(i+1)+letrasArray.get(i+2))){
                                SetSilabas(3);
                            }
                            else{
                                SetSilabas(2);
                            }
                        }else if((verificarVogal(letrasArray.get(i+1))||verificarVogalComAcento(letrasArray.get(i+1))) 
                        && (letrasArray.get(i+2).equals("s") || letrasArray.get(i+2).equals("r"))&& verificarVogal(letrasArray.get(i+3))==false){
                            SetSilabas(3);
                        }else if((verificarVogal(letrasArray.get(i+1))||verificarVogalComAcento(letrasArray.get(i+1))) 
                        && verificarVogal(letrasArray.get(i+2))==false && verificarConsoanteDeUniao(letrasArray.get(i+3))){
                            if(s.length() <= i+5){
                                SetSilabas(4);
                            }else{
                                SetSilabas(2);
                            }
                        }else if((verificarVogal(letrasArray.get(i+1))||verificarVogalComAcento(letrasArray.get(i+1))) 
                        && verificarVogal(letrasArray.get(i+2))==false && verificarVogal(letrasArray.get(i+3))||verificarVogalComAcento(letrasArray.get(i+3))){
                            SetSilabas(2);
                        }else if((verificarVogal(letrasArray.get(i+1)) ||verificarVogalComAcento(letrasArray.get(i+1))) 
                        && verificarVogal(letrasArray.get(i+2))==false && verificarConsoanteDeUniao(letrasArray.get(i+3))){
                            SetSilabas(2);
                        }else if((verificarVogal(letrasArray.get(i+1)) ||verificarVogalComAcento(letrasArray.get(i+1))) 
                        && verificarVogal(letrasArray.get(i+2))==false && verificarVogal(letrasArray.get(i+3))||verificarVogalComAcento(letrasArray.get(i+1)) ){
                            SetSilabas(2);
                        }else if((verificarVogal(letrasArray.get(i+1)) ||verificarVogalComAcento(letrasArray.get(i+1))) 
                        && verificarVogal(letrasArray.get(i+2))==false 
                        && verificarVogal(letrasArray.get(i+3)) ==false){
                            SetSilabas(3);
                        }else if(s.length() == i+4){
                            if(verificarConsoanteDeUniao(letrasArray.get(i+1))&& verificarVogal(letrasArray.get(i+2))||verificarVogalComAcento(letrasArray.get(i+1)) ){
                                SetSilabas(4);
                            }
                        }else if(verificarConsoanteDeUniao(letrasArray.get(i+1))&& verificarVogal(letrasArray.get(i+2))||verificarVogalComAcento(letrasArray.get(i+1)) ){
                            SetSilabas(3);
                        }else{
                            SetSilabas(3);
                        }
                        
                    } else if(s.length() >= i+3){
                        silaba = letrasArray.get(i) + letrasArray.get(i+1)+ letrasArray.get(i+2);
                        if(vogaisUnidas(silaba)){
                            SetSilabas(3);
                        }else if(verificarVogal(letrasArray.get(i+2))==false){
                            SetSilabas(3);
                        }else if(letrasArray.get(i+1).charAt(0) == 'ã' ||letrasArray.get(i+1).charAt(0) == 'õ'){
                            SetSilabas(3);
                        }else if((verificarVogal(letrasArray.get(i+1))||verificarVogalComAcento(letrasArray.get(i+1)))
                        && (verificarVogal(letrasArray.get(i+2))||verificarVogalComAcento(letrasArray.get(i+2)))){
                            SetSilabas(3);
                        }else if(verificarVogal(letrasArray.get(i+1))|| verificarConsoanteDeUniao(letrasArray.get(i+1))){
                            SetSilabas(3);
                        }else{
                            SetSilabas(1);
                        }
                    }
                    else{
                        SetSilabas(2);
                    }
                }
            }
            else{
                SetSilabas(1);
            }
        }
        return silabasArray;

    }
}