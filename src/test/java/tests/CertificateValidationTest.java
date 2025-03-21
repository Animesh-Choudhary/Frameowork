package tests;
import org.testng.Assert;
import org.testng.annotations.Test;
import testbase.BaseTest;
import context.TestContext;
import io.qameta.allure.*;

import java.security.cert.CertificateExpiredException;
import java.security.cert.X509Certificate;

public class CertificateValidationTest extends BaseTest {

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify a valid certificate can be loaded and validated")
    @Story("Valid Certificate Validation")
    public void testValidCertificate() throws Exception {
        TestContext context = new TestContext();
        context.loadCertificate("certs/cert.pem");
        X509Certificate cert = context.getCertificate();

        // Check if certificate is loaded
        Assert.assertNotNull(cert, "Certificate should not be null");

        // Validate Common Name (CN)
        String subjectDN = cert.getSubjectDN().getName();
        Assert.assertTrue(subjectDN.contains("CN=mydomain.com"), "CN does not match");

        // Validate Issuer (Replace 'O=MyCompany' with the expected issuer)
        String issuerDN = cert.getIssuerDN().getName();
        Assert.assertTrue(issuerDN.contains("O=MyCompany"), "Issuer does not match");

        // Validate expiration (should not be expired)
        cert.checkValidity();

        // Validate Signature Algorithm
        String sigAlg = cert.getSigAlgName();
        Assert.assertTrue(sigAlg.equalsIgnoreCase("SHA256withRSA"), "Signature Algorithm is not SHA-256 with RSA");

        System.out.println("Certificate validation passed!");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify an expired certificate is detected and validation fails")
    @Story("Expired Certificate Validation")
    public void testExpiredCertificate() throws Exception {
        TestContext context = new TestContext();
        context.loadCertificate("certs/expired_cert.pem");
        X509Certificate cert = context.getCertificate();

        // Check if certificate is loaded
        Assert.assertNotNull(cert, "Certificate should not be null");

        // Validate Common Name (CN)
        String subjectDN = cert.getSubjectDN().getName();
        Assert.assertTrue(subjectDN.contains("CN=expired.mydomain.com"), "CN does not match");

        // Validate Issuer (Replace 'O=MyCompany' with the expected issuer)
        String issuerDN = cert.getIssuerDN().getName();
        Assert.assertTrue(issuerDN.contains("O=MyCompany"), "Issuer does not match");

        try {
            cert.checkValidity();
            Assert.fail("Expected CertificateExpiredException, but no exception was thrown");
        } catch (CertificateExpiredException e) {
            System.out.println("Certificate is correctly detected as expired!");
        }
        // Validate Signature Algorithm
        String sigAlg = cert.getSigAlgName();
        Assert.assertTrue(sigAlg.equalsIgnoreCase("SHA256withRSA"), "Signature Algorithm is not SHA-256 with RSA");

        System.out.println("Expired certificate validation passed!");
    }
}
