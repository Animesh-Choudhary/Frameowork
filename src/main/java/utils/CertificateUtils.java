package utils;

import java.io.FileInputStream;
import java.net.URI;
import java.security.cert.*;
import java.util.Collections;
import javax.naming.directory.*;
import javax.naming.*;
import java.util.Hashtable;

public class CertificateUtils {

    /**
     * Loads an X.509 certificate from the given file path.
     */
    public static X509Certificate loadCertificate(String certPath) throws Exception {
        FileInputStream fis = new FileInputStream(certPath);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        return (X509Certificate) cf.generateCertificate(fis);
    }

    /**
     * Checks if the given certificate is revoked using OCSP and CRL.
     */
    public static boolean isCertificateRevoked(X509Certificate cert) {
        try {
            // Check OCSP (Online Certificate Status Protocol)
            if (isRevokedUsingOCSP(cert)) {
                System.out.println("Certificate is revoked (OCSP check).");
                return true;
            }

            // Check CRL (Certificate Revocation List)
            if (isRevokedUsingCRL(cert)) {
                System.out.println("Certificate is revoked (CRL check).");
                return true;
            }
        } catch (Exception e) {
            System.err.println("Error during revocation check: " + e.getMessage());
        }

        return false; // If both checks fail, assume certificate is not revoked
    }

    /**
     * Checks if the certificate is revoked using OCSP.
     */
    private static boolean isRevokedUsingOCSP(X509Certificate cert) throws Exception {
        try {
            CertPathValidator cpv = CertPathValidator.getInstance("PKIX");
            PKIXParameters params = new PKIXParameters(Collections.singleton(new TrustAnchor(cert, null)));
            params.setRevocationEnabled(true);

            CertPathValidatorResult result = cpv.validate(
                    CertificateFactory.getInstance("X.509").generateCertPath(Collections.singletonList(cert)), params);

            return false; // If validation succeeds, cert is not revoked
        } catch (CertPathValidatorException e) {
            System.out.println("OCSP check failed: Certificate is revoked.");
            return true; // If OCSP check fails, assume certificate is revoked
        }
    }

    /**
     * Checks if the certificate is revoked using CRL.
     */
    private static boolean isRevokedUsingCRL(X509Certificate cert) throws Exception {
        X509CRL crl = getCRL(cert);
        if (crl == null) {
            System.out.println("No CRL found for certificate.");
            return false;
        }

        return crl.isRevoked(cert);
    }

    /**
     * Fetches the CRL for the given certificate.
     */
    private static X509CRL getCRL(X509Certificate cert) throws Exception {
        String crlURL = extractCRLURL(cert);
        if (crlURL == null) {
            System.out.println("No CRL URL found.");
            return null;
        }

        return fetchCRLFromURL(crlURL);
    }

    /**
     * Extracts the CRL distribution point URL from the certificate.
     */
    private static String extractCRLURL(X509Certificate cert) throws Exception {
        byte[] crlDistPoints = cert.getExtensionValue("2.5.29.31");
        if (crlDistPoints == null) return null;

        try {
            String crlURL = new String(crlDistPoints);
            return crlURL;
        } catch (Exception e) {
            System.err.println("Error extracting CRL URL: " + e.getMessage());
            return null;
        }
    }

    /**
     * Fetches the CRL from the given URL.
     */
    private static X509CRL fetchCRLFromURL(String crlURL) throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        return (X509CRL) cf.generateCRL(new URI(crlURL).toURL().openStream());
    }
}
