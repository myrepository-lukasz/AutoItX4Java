import com.jacob.com.LibraryLoader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CalculatorTest {
    final String JACOB_DLL_TO_USE = System.getProperty("sun.arch.data.model").contains("32") ?
            "jacob-1.19-x86.dll" : "jacob-1.19-x64.dll";
    final String APPLICATION_TITLE = "Kalkulator";
    final String APPLICATION = "calc.exe";
    final int DELAY = 500;

    private AutoItX control;

    private Map<Integer, String> calcNumPad_ObjectRepository;
    private Map<String, String> calcOperPad_ObjectRepository;
    {
        //Object Repository for Calculator Numbers
        calcNumPad_ObjectRepository = new HashMap<Integer, String>();
        calcNumPad_ObjectRepository.put(0,"130");
        calcNumPad_ObjectRepository.put(1,"131");
        calcNumPad_ObjectRepository.put(2,"132");
        calcNumPad_ObjectRepository.put(3,"133");
        calcNumPad_ObjectRepository.put(4,"134");
        calcNumPad_ObjectRepository.put(5,"135");
        calcNumPad_ObjectRepository.put(6,"136");
        calcNumPad_ObjectRepository.put(7,"137");
        calcNumPad_ObjectRepository.put(8,"138");
        calcNumPad_ObjectRepository.put(9,"139");

        //Object Repository for Calculator Operators
        calcOperPad_ObjectRepository = new HashMap<String, String>();
        calcOperPad_ObjectRepository.put("+", "93");
        calcOperPad_ObjectRepository.put("-", "94");
        calcOperPad_ObjectRepository.put("*", "92");
        calcOperPad_ObjectRepository.put("/", "91");
        calcOperPad_ObjectRepository.put("=", "121");
        calcOperPad_ObjectRepository.put("clear", "81");

        //Load the jacob dll
        File file = new File(System.getProperty("user.dir") + "\\lib", JACOB_DLL_TO_USE);
        System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());

        control = new AutoItX();
    }

    public static void main(String[] args) throws InterruptedException {

        CalculatorTest ct = new CalculatorTest();

        //Launch 'Calculator' application
        ct.control.run("calc.exe");
        ct.control.winActivate("Kalkulator");
        //ct.control.winWaitActive("Kalkulator");

        //Test
        System.out.println("100 + 2 -> Actual Result: " + ct.add(100,2) + ", Ecpected Result: 102");
        System.out.println("100 - 2 -> Actual Result: " + ct.sub(100,2) + ", Ecpected Result: 98");
        System.out.println("100 * 2 -> Actual Result: " + ct.multi(100,2) + ", Ecpected Result: 200");
        System.out.println("100 / 2 -> Actual Result: " + ct.divide(100,2) + ", Ecpected Result: 50");

        System.out.println();

        //Close "Calculator' application
        ct.control.winClose("Calculator");
    }

    //Perform 'Addition'
    public int add(int a, int b) throws InterruptedException {
        performOperation("clear");

        clickNumber(a);
        performOperation("+");
        clickNumber(b);
        performOperation("=");

        return Integer.parseInt(getResult().trim());
    }

    //Perform 'Subtract'
    public int sub(int a, int b) throws InterruptedException {
        performOperation("clear");

        clickNumber(a);
        performOperation("-");
        clickNumber(b);
        performOperation("=");

        return Integer.parseInt(getResult().trim());
    }

    //Perform 'Multiplication'
    public int multi(int a, int b) throws InterruptedException {
        performOperation("clear");

        clickNumber(a);
        performOperation("*");
        clickNumber(b);
        performOperation("=");

        return Integer.parseInt(getResult().trim());
    }

    //Perform 'Division'
    public int divide(int a, int b) throws InterruptedException {
        performOperation("clear");

        clickNumber(a);
        performOperation("/");
        clickNumber(b);
        performOperation("=");

        return Integer.parseInt(getResult().trim());
    }
    //Fetch the result in the calculator application
    private String getResult() throws InterruptedException {
        Thread.sleep(DELAY);
        return control.winGetText(APPLICATION_TITLE);
    }

    //Click the number in the calculator appliction
    private void clickNumber(int number) throws InterruptedException {
        String sNumber = String.valueOf(number);

        for(int i = 0; i < sNumber.length(); i++) {
            control.controlClick(APPLICATION_TITLE, "", calcNumPad_ObjectRepository.get(Character.digit(sNumber.charAt(i), 10)));
            Thread.sleep(DELAY);
        }
    }

    //Perfotm operations
    private void performOperation(String controlID) throws InterruptedException {
        control.controlClick(APPLICATION_TITLE, "", calcOperPad_ObjectRepository.get(controlID));
        Thread.sleep(1000);
    }
}