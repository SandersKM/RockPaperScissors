package com.example.rockpaperscissors;

import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    // // // // // // // //
    // IP_AddressEditor  //
    // // // // // // // //

    @Test
    public void digitAdding_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("8");
        ip_addressEditor.addDigit("7");
        ip_addressEditor.addDigit("3");
        assertEquals("873", ip_addressEditor.getIP());
    }

    @Test
    public void digitAddingOverLimit_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("8");
        ip_addressEditor.addDigit("7");
        ip_addressEditor.addDigit("3");
        ip_addressEditor.addDigit("9");
        assertEquals("873", ip_addressEditor.getIP());
    }

    @Test
    public void canAddDot1_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        assertEquals(false, ip_addressEditor.canAddDot());
    }

    @Test
    public void canAddDot2_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("3");
        ip_addressEditor.addDot();
        assertEquals(false, ip_addressEditor.canAddDot());
    }

    public void canAddDot3_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("8");
        ip_addressEditor.addDigit("7");
        ip_addressEditor.addDigit("3");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("4");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("1");
        ip_addressEditor.addDigit("2");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("2");
        ip_addressEditor.addDigit("2");
        ip_addressEditor.addDigit("2");
        assertEquals(false, ip_addressEditor.canAddDot());
    }

    @Test
    public void dotAdding1_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("8");
        ip_addressEditor.addDigit("7");
        ip_addressEditor.addDot();
        assertEquals("87.", ip_addressEditor.getIP());
    }

    @Test
    public void dotAdding2_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("8");
        ip_addressEditor.addDigit("7");
        ip_addressEditor.addDigit("3");
        ip_addressEditor.addDot();
        assertEquals("873.", ip_addressEditor.getIP());
    }

    @Test
    public void dotAdding3_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("8");
        ip_addressEditor.addDigit("7");
        ip_addressEditor.addDigit("3");
        ip_addressEditor.addDot();
        ip_addressEditor.addDot();
        assertEquals("873.", ip_addressEditor.getIP());
    }

    @Test
    public void dotAdding4_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("8");
        ip_addressEditor.addDigit("7");
        ip_addressEditor.addDigit("3");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("4");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("1");
        assertEquals("873.4.1", ip_addressEditor.getIP());
    }

    @Test
    public void backspace1_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("8");
        ip_addressEditor.addDigit("7");
        ip_addressEditor.addDigit("3");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("4");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("1");
        ip_addressEditor.backspace();
        ip_addressEditor.backspace();
        assertEquals("873.4", ip_addressEditor.getIP());
    }

    @Test
    public void backspace2_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("8");
        ip_addressEditor.addDigit("7");
        ip_addressEditor.addDigit("3");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("4");
        ip_addressEditor.backspace();
        ip_addressEditor.backspace();
        assertEquals(true, ip_addressEditor.consecutiveDigitLimitReached());
    }
    @Test
    public void backspace3_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("8");
        ip_addressEditor.addDigit("7");
        ip_addressEditor.addDigit("3");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("4");
        ip_addressEditor.backspace();
        assertEquals(0, ip_addressEditor.consecutiveDigits);
    }

    @Test
    public void backspace4_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("8");
        ip_addressEditor.addDigit("7");
        ip_addressEditor.addDigit("3");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("4");
        ip_addressEditor.backspace();
        ip_addressEditor.backspace();
        ip_addressEditor.backspace();
        assertEquals(2, ip_addressEditor.consecutiveDigits);
    }

    @Test
    public void backspace5_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("8");
        ip_addressEditor.backspace();
        ip_addressEditor.backspace();
        assertEquals("", ip_addressEditor.getIP());
    }

    @Test
    public void backspace6_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("8");
        ip_addressEditor.backspace();
        ip_addressEditor.backspace();
        assertEquals(0, ip_addressEditor.consecutiveDigits);
    }

    @Test
    public void backspace7_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("8");
        ip_addressEditor.addDigit("7");
        ip_addressEditor.addDigit("3");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("4");
        ip_addressEditor.addDigit("4");
        ip_addressEditor.addDigit("4");
        ip_addressEditor.addDot();
        ip_addressEditor.backspace();
        ip_addressEditor.backspace();
        assertEquals(false, ip_addressEditor.consecutiveDigitLimitReached());
    }

    public void isFull1_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("8");
        ip_addressEditor.addDigit("7");
        ip_addressEditor.addDigit("3");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("4");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("1");
        ip_addressEditor.addDigit("2");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("2");
        ip_addressEditor.addDigit("2");
        ip_addressEditor.addDigit("2");
        assertEquals(true, ip_addressEditor.isFull());
    }

    public void isFull2_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("8");
        ip_addressEditor.addDigit("7");
        ip_addressEditor.addDigit("3");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("4");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("1");
        ip_addressEditor.addDigit("2");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("2");
        ip_addressEditor.addDigit("2");
        ip_addressEditor.addDigit("2");
        ip_addressEditor.backspace();
        assertEquals(false, ip_addressEditor.isFull());
    }

    public void isFull3_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("8");
        ip_addressEditor.addDigit("7");
        ip_addressEditor.addDigit("3");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("4");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("1");
        ip_addressEditor.addDigit("2");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("2");
        ip_addressEditor.addDigit("2");
        ip_addressEditor.addDigit("2");
        ip_addressEditor.backspace();
        ip_addressEditor.addDigit("1");
        ip_addressEditor.addDigit("2");
        ip_addressEditor.addDot();
        assertEquals("873.4.12.221", ip_addressEditor.getIP());
    }

    public void isValid1_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("8");
        ip_addressEditor.addDigit("7");
        ip_addressEditor.addDigit("3");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("4");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("1");
        ip_addressEditor.addDigit("2");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("2");
        ip_addressEditor.addDigit("2");
        ip_addressEditor.addDigit("2");
        ip_addressEditor.backspace();
        ip_addressEditor.addDigit("1");
        ip_addressEditor.addDigit("2");
        ip_addressEditor.addDot();
        assertEquals( true, ip_addressEditor.isValidIP());
    }

    public void isValid2_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        ip_addressEditor.addDigit("8");
        ip_addressEditor.addDigit("7");
        ip_addressEditor.addDigit("3");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("4");
        ip_addressEditor.addDot();
        ip_addressEditor.addDigit("1");
        ip_addressEditor.addDigit("2");
        assertEquals( false, ip_addressEditor.isValidIP());
    }

    public void isValid3_isCorrect(){
        IP_AddressEditor ip_addressEditor = new IP_AddressEditor();
        assertEquals( false, ip_addressEditor.isValidIP());
    }


/*    @Test
    public void test_send_receive() throws Exception {
        final String testMsg = "This is a test.\nThis is only a test.\n";

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("test1");
                    Server.get().listenOnce().start();
                    System.out.println("test2");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println("test3");
        Socket test = new Socket("localhost", Server.APP_PORT);
        System.out.println("test4");
        Communication.sendOver(test, testMsg);
        System.out.println("test5"+testMsg);
        String result = Communication.receive(test);
        System.out.println("test6"+result);
        assertEquals(testMsg, result);
        System.out.println("test7");
    }*/

}