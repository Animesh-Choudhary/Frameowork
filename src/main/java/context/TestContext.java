package context;
import java.security.cert.X509Certificate;
import utils.CertificateUtils;

public class TestContext {
    private X509Certificate certificate;

    public void loadCertificate(String certPath) throws Exception {
        this.certificate = CertificateUtils.loadCertificate(certPath); // Using the correct method
    }

    public X509Certificate getCertificate() {
        return this.certificate;
    }
}