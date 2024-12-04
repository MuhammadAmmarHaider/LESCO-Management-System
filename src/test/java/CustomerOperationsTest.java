import Controller.CustomerOperations;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import model.CustomerType;
import model.MeterType;

public class CustomerOperationsTest
{
    private CustomerOperations customerOperations;
    private static final String CUSTOMER_INFO_FILE = "CustomerInfo.txt";
    private static final String TARIFF_TAX_INFO_FILE = "TariffTaxInfo.txt";
    private static final String NADRA_DB_FILE = "NadraDB.txt";

    @BeforeEach
    public void setUp()
    {
        customerOperations = new CustomerOperations();
    }

    @Test
    public void testIsCustomerIdValid_Found()
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_INFO_FILE))) {
            writer.write("1234,1234567890123,John Doe,123 Main St,12345678901,DOMESTIC,12,SINGLE_PHASE,0,0\n");
        } catch (IOException e) {
            fail("Error setting up test data: " + e.getMessage());
        }

        assertTrue(customerOperations.isCustomerIdValid("1234"));
    }

    @Test
    public void testIsCustomerIdValid_NotFound()
    {
        assertFalse(customerOperations.isCustomerIdValid("9999"));
    }

    @Test
    public void testIsCnicMatchingCustomerId_Matching()
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_INFO_FILE))) {
            writer.write("1234,1234567890123,John Doe,123 Main St,1234567890,DOMESTIC,SINGLE_PHASE\n");
        }
        catch (IOException e) {
            fail("Error setting up test data: " + e.getMessage());
        }

        assertTrue(customerOperations.isCnicMatchingCustomerId("1234", "1234567890123"));
    }

    @Test
    public void testIsCnicMatchingCustomerId_NotMatching()
    {
        assertFalse(customerOperations.isCnicMatchingCustomerId("1234", "0000000000000"));
    }

    @Test
    public void testCalculateBill_Success()
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_INFO_FILE)))
        {
            writer.write("1234,1234567890123,John Doe,123 Main St,12345678901,DOMESTIC,12,SINGLE_PHASE,01-01-2022,1,1\n");
        }
        catch (IOException e)
        {
            fail("Error setting up test data: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TARIFF_TAX_INFO_FILE))) {
            writer.write("Phase1,5.00,0.00,10.0,10.0\n");
        }
        catch (IOException e) {
            fail("Error setting up tariff data: " + e.getMessage());
        }

        String[] bill = customerOperations.calculateBill("1234", 1, 100, 50);
        assertNotNull(bill);
        assertEquals("5.000000", bill[0]);
        assertEquals("0.000000", bill[1]);
        assertEquals("10.000000", bill[2]);
        assertEquals("10.000000", bill[3]);
        assertEquals("500.000000", bill[4]);
        assertEquals("560.000000", bill[5]);
    }

    @Test
    public void testIsValidCNIC_Valid()
    {
        assertTrue(customerOperations.isValidCNIC(1234567890123L));
    }

    @Test
    public void testIsValidCNIC_Invalid()
    {
        assertFalse(customerOperations.isValidCNIC(123456789L));
    }

    @Test
    public void testUpdateExpiryDate_Success()
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NADRA_DB_FILE)))
        {
            writer.write("1234567890123,2025-12-31\n");
        }
        catch (IOException e) {
            fail("Error setting up test data: " + e.getMessage());
        }

        boolean result = customerOperations.updateExpiryDate(1234567890123L, "2026-12-31");
        assertTrue(result);

        try (BufferedReader reader = new BufferedReader(new FileReader(NADRA_DB_FILE)))
        {
            String line = reader.readLine();
            assertNotNull(line);
            assertTrue(line.contains("2026-12-31"));
        }
        catch (IOException e)
        {
            fail("Error reading updated file: " + e.getMessage());
        }
    }

    @Test
    public void testAddCustomer_Success()
    {
        boolean result = customerOperations.addCustomer(1234567890123L, "John Doe", "123 Main St", 12345678901L, CustomerType.DOMESTIC, MeterType.SINGLE_PHASE);
        assertTrue(result);

        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_INFO_FILE)))
        {
            String line = reader.readLine();
            assertNotNull(line);
            assertTrue(line.contains("John Doe"));
        }
        catch (IOException e) {
            fail("Error reading customer data: " + e.getMessage());
        }
    }

    @Test
    public void testAddCustomer_AlreadyExists()
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_INFO_FILE)))
        {
            writer.write("1234,1234567890123,John Doe,123 Main St,1234567890,DOMESTIC,SINGLE_PHASE\n");
        } catch (IOException e) {
            fail("Error setting up test data: " + e.getMessage());
        }

        boolean result = customerOperations.addCustomer(1234567890123L, "John Doe", "123 Main St", 1234567890L, CustomerType.DOMESTIC, MeterType.SINGLE_PHASE);
        assertFalse(result);
    }
}
