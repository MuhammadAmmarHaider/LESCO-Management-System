
import Controller.EmployeeOperations;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeOperationsTest {

    private EmployeeOperations employeeOperations;

    @BeforeEach
    void setUp() throws IOException {
        employeeOperations = new EmployeeOperations();

        // Set up test files with dummy data
        setupTestFile(EmployeeOperations.EmployeesData, "user1,password1\nuser2,password2");
        setupTestFile(EmployeeOperations.CustomerInfo, "123,cnicid,Name,Address,phone,Type,1,Phase,date,100,200");
        setupTestFile(EmployeeOperations.BillingInfo, "123,1,Jan-2024,100,50,15-01-2024,500,10,20,530,2024-01-29,UNPAID,yyyy-mm-dd");
        setupTestFile(EmployeeOperations.TariffTaxInfo, "1,2,0.5,1.0,0.05,50\n1,2,0.6,1.2,0.06,60");
        setupTestFile(EmployeeOperations.NadraDB, "CNIC1,2024-12-31\nCNIC2,2025-01-01");
    }

    private void setupTestFile(String filename, String content) throws IOException {
        Files.write(Paths.get(filename), content.getBytes());
    }

    @Test
    void testLogin_Successful() {
        assertTrue(employeeOperations.login("user1", "password1"));
    }

    @Test
    void testLogin_Unsuccessful() {
        assertFalse(employeeOperations.login("user1", "wrongpassword"));
    }

    @Test
    void testChangePassword_Successful() {
        assertTrue(employeeOperations.changePassword("user1", "password1", "newpassword"));
        assertTrue(employeeOperations.login("user1", "newpassword"));
    }

    @Test
    void testChangePassword_Unsuccessful() {
        assertFalse(employeeOperations.changePassword("user1", "wrongpassword", "newpassword"));
    }

    @Test
    void testAddMeterReading_Successful() {
        assertTrue(employeeOperations.addMeterReading("123", "1", "FEBRUARY", 150, 75, "01-02-2024"));
    }

    @Test
    void testAddMeterReading_InvalidDate() {
        assertFalse(employeeOperations.addMeterReading("123", "1", "Feb-2024", 150, 75, "invalid-date"));
    }

    @Test
    void testPayBill_Successful() {
        assertTrue(employeeOperations.payBill("123", "1"));
    }

    @Test
    void testPayBill_Unsuccessful() {
        assertFalse(employeeOperations.payBill("invalidId", "invalidMeterId"));
    }

    @Test
    void testUpdateTaxFile_Successful() {
        assertTrue(employeeOperations.updateTaxFile(1, 2, 0.7, 1.3, 0.07, 70));
    }

    @Test
    void testUpdateTaxFile_Unsuccessful() {
        assertFalse(employeeOperations.updateTaxFile(3, 4, 0.7, 1.3, 0.07, 70));
    }

    @Test
    void testGetBillDetails() {
        String[][] details = employeeOperations.getBillDetails("123", "1");
        assertEquals(1, details.length);
        assertEquals("123", details[0][0]); // Validate first field
        assertEquals("UNPAID", details[0][11]); // Validate status field
    }

    @Test
    void testGetPaidAndUnpaidReport()
    {
        employeeOperations.payBill("123", "1");

        Map<String, Double> report = employeeOperations.getPaidAndUnpaidReport();

        assertNotNull(report);
        assertTrue(report.containsKey("paidCount"));
        assertTrue(report.containsKey("paidAmount"));
        assertTrue(report.containsKey("unpaidCount"));
        assertTrue(report.containsKey("unpaidAmount"));
        assertEquals(1.0, report.get("paidCount"));
        assertEquals(530.0, report.get("paidAmount"));
        assertEquals(0.0, report.get("unpaidCount"));
        assertEquals(0.0, report.get("unpaidAmount"));
    }

    @Test
    void testGetPaidAndUnpaidReport_EmptyFile() throws IOException
    {
        setupTestFile(EmployeeOperations.BillingInfo, "");
        Map<String, Double> report = employeeOperations.getPaidAndUnpaidReport();
        assertNotNull(report);
        assertTrue(report.containsKey("paidCount"));
        assertTrue(report.containsKey("paidAmount"));
        assertTrue(report.containsKey("unpaidCount"));
        assertTrue(report.containsKey("unpaidAmount"));

        assertEquals(0.0, report.get("paidCount"));
        assertEquals(0.0, report.get("paidAmount"));
        assertEquals(0.0, report.get("unpaidCount"));
        assertEquals(0.0, report.get("unpaidAmount"));
    }
}
