package contants;


import helpers.PropertiesHelper;

public class ConfigData {
    static {
        PropertiesHelper.loadAllFiles();
}

    public static String URLCRM = PropertiesHelper.getValue("url");
    public static String EMAIL1 = PropertiesHelper.getValue("email1");
    public static String PASSWORD1 = PropertiesHelper.getValue("password1");

    public static String EMAIL2 = PropertiesHelper.getValue("email2");
    public static String PASSWORD2 = PropertiesHelper.getValue("password2");


    public static String EMAIL3 = PropertiesHelper.getValue("email3");
    public static String PASSWORD3 = PropertiesHelper.getValue("password3");

    public static String EMAIL4 = PropertiesHelper.getValue("email4");
    public static String PASSWORD4 = PropertiesHelper.getValue("password4");

    public static String EMAIL5 = PropertiesHelper.getValue("email5");
    public static String PASSWORD5 = PropertiesHelper.getValue("password5");
}
