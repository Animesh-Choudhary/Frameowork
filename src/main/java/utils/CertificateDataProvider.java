package utils;

import org.testng.annotations.DataProvider;
import java.io.*;
import java.util.*;

public class CertificateDataProvider {
    private static final String CSV_FILE = "certs/cert_data.csv"; // Updated path

    @DataProvider(name = "certificateData")
    public static Object[][] getCertificateData() {
        List<Object[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 4) {
                    data.add(new Object[]{values[0].trim(), values[1].trim(), values[2].trim(), values[3].trim()});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Debugging: Print loaded data
        System.out.println("✅ Loaded " + data.size() + " certificate entries from CSV.");
        return data.toArray(new Object[0][]);
    }
}
