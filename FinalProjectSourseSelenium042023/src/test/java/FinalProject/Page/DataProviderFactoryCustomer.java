package FinalProject.Page;

import helpers.ExcelHelper;
import org.testng.annotations.DataProvider;

public class DataProviderFactoryCustomer {

    @DataProvider(name = "DataAddCustomerSuccessHashtable", parallel = true)
    public Object[][] dataAddCustomerSuccessHashtable(){

        //Gáng từ file excel vào
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] data = excelHelper.getExcelData("src/test/resources/testdata/DataCustomer.xlsx","Sheet1");
                return data;
    }

    @DataProvider(name = "DataLoginSuccessExcelHashtable", parallel = true)
    public Object[][] dataLoginSuccessExcelHashtable(){

        //Gáng từ file excel data HashTable
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] data = excelHelper.getDataHashTable("src/test/resources/testdata/UsersData.xlsx","Sheet2", 2,3);
        System.out.println("Login Data from Excel: " + data);
        return data;
    }

    @DataProvider(name = "DataLoginEmailInvalid", parallel = true)
    public Object[][] dataLoginFail(){
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] data = excelHelper.getExcelData("src/test/resources/testdata/DataLogin.xlsx","Sheet2");
        System.out.println("Login Data from Excel: " + data);
        return data;
    }

    @DataProvider(name = "DataLoginPasswordInvalid", parallel = true)
    public Object[][] dataLoginFailPassword(){
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] data = excelHelper.getExcelData("src/test/resources/testdata/DataLogin.xlsx","Sheet3");
        System.out.println("Login Data from Excel: " + data);
        return data;
    }

    @DataProvider(name = "DataLoginEmailFormat", parallel = true)
    public Object[][] dataLoginEmailFormat(){
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] data = excelHelper.getExcelData("src/test/resources/testdata/DataLogin.xlsx","Sheet4");
        System.out.println("Login Data from Excel: " + data);
        return data;
        };
    }
