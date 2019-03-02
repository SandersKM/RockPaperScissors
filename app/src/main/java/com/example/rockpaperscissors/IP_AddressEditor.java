package com.example.rockpaperscissors;

public class IP_AddressEditor {

    String IP;
    int consecutiveDigits, dots, length;

    public IP_AddressEditor(){
        this.IP = "";
        consecutiveDigits = 0;
        dots = 0;
        length = 0;
    }

    public String getIP(){
        return IP;
    }

    public boolean isValidIP(){
        if(!isEmpty() && !canAddDot() && IP.charAt(length - 1) != '.'){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isEmpty(){
        if(length == 0){
            return true;
        }
        return false;
    }

    // This is based on IPv4
    public boolean isFull(){
        if(dotLimitReached() && consecutiveDigitLimitReached()){
            return true;
        }
        return false;
    }

    public boolean canAddDot(){
        if(!dotLimitReached() && consecutiveDigits > 0){
            return true;
        }
        return false;
    }

    public boolean dotLimitReached(){
        if(dots < 3){
            return false;
        }
        return true;
    }

    public boolean consecutiveDigitLimitReached(){
        if (consecutiveDigits < 3){
            return false;
        }
        return true;
    }

    public void addDigit(String digit){
        if(!consecutiveDigitLimitReached()){
            IP = IP + digit;
            consecutiveDigits++;
            length++;
        }
    }

    public void addDot(){
        if(canAddDot()){
            IP = IP + ".";
            consecutiveDigits = 0;
            dots++;
            length++;
        }
    }

    public void backspace(){
        if(!isEmpty()){
            char toDelete = IP.charAt(IP.length() - 1);
            IP = IP.substring(0, IP.length() - 1);
            if(toDelete == '.'){
                dots--;
                if(IP.lastIndexOf('.') == -1){
                    consecutiveDigits = IP.length();
                }
                else{ consecutiveDigits = IP.length() - IP.lastIndexOf('.') - 1;}
            }
            else{
                consecutiveDigits--;
            }
            length--;
        }
    }

}
