package Listeners;

import helpers.ExcelHelper;
import org.testng.annotations.DataProvider;

public class DataProviderFactory {

    @DataProvider(name = "DataLoginSuccess", parallel = true)
    public Object[][] dataLoginSuccess(){

        //Gáng từ file excel vào
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] data = excelHelper.getExcelData("src/test/resources/testdata/DataLogin.xlsx","Sheet3");
                return data;
    }

    @DataProvider(name = "DataLoginSuccessExcelHashtable", parallel = true)
    public Object[][] dataLoginSuccessExcelHashtable(){

        //Gáng từ file excel data HashTable
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] data = excelHelper.getDataHashTable("src/test/resources/testdata/UsersData.xlsx","Sheet1", 1,3);
        System.out.println("Login Data from Excel: " + data);
        return data;
    }

    @DataProvider(name = "DataLoginEmailInvalid", parallel = true)
    public Object[][] dataLoginFail(){
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] data = excelHelper.getExcelData("src/test/resources/testdata/DataLogin.xlsx","Sheet1");
        System.out.println("Login Data from Excel: " + data);
        return data;
    }

    @DataProvider(name = "DataLoginPasswordInvalid", parallel = true)
    public Object[][] dataLoginFailPassword(){
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] data = excelHelper.getExcelData("src/test/resources/testdata/DataLogin.xlsx","Sheet2");
        System.out.println("Login Data from Excel: " + data);
        return data;
    }
}
