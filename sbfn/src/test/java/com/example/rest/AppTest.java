package com.example.rest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    private Connection conn = null;
    private String message = null;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {

        try {

            String dbUser = System.getenv().getOrDefault("DB_USER", "fnusername");
            System.err.println("DB User " + dbUser);

            String dbPasswd = System.getenv().getOrDefault("DB_PASSWORD", "xxx");

            String dbServiceName = System.getenv().getOrDefault("DB_SERVICE_NAME", "sr081400adw_medium");
            System.err.println("DB Service name " + dbServiceName);

            String tnsAdminLocation = "/function/wallet";
            tnsAdminLocation = "/Users/kyle/workspace/java/sbfn/wallet";

            System.err.println("TNS Admin location " + tnsAdminLocation);

            String dbUrl = "jdbc:oracle:thin:@" + dbServiceName + "?TNS_ADMIN=" + tnsAdminLocation;
            System.err.println("DB URL " + dbUrl);

            Properties prop = new Properties();

            prop.setProperty("user", dbUser);
            prop.setProperty("password", dbPasswd);

            System.err.println("Connecting to Oracle ATP DB......");

            conn = DriverManager.getConnection(dbUrl, prop);
            if (conn != null) {
                System.err.println("Connected to Oracle ATP DB successfully");
            }

        } catch (Throwable e) {
            System.err.println("DB connectivity failed due - " + e.getMessage());
            message = e.getMessage();
        }
        assertTrue( conn != null );
    }
}
