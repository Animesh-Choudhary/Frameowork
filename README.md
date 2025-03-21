# Certificate Validation Framework

## Overview
This is a **Certificate Validation Framework** built using Java, TestNG, and Allure for reporting. It validates X.509 certificates for various properties such as Common Name (CN), issuer details, expiration status, and signature algorithms.

## Folder Structure
```
Framework
│── allure-results/          # Stores Allure report results
│── certs/                   # Stores certificate files for validation
│── exportToHTML/            # HTML reports for test execution
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── context/      # TestContext class (Loads certificates)
│   │   │   ├── testbase/     # BaseTest class (Test setup & teardown)
│   │   │   ├── utils/        # CertificateUtils (Helper functions for certificates)
│   ├── test/
│   │   ├── java/
│   │   │   ├── listeners/    # TestListener (TestNG Listeners for reporting)
│   │   │   ├── tests/        # CertificateValidationTest (Test cases)
│── target/                  # Compiled classes & test results
│── pom.xml                   # Maven dependencies
│── testng.xml                # TestNG suite configuration
│── generate_cert.sh          # Shell script to generate test certificates
```

## Class Interactions
- **TestContext** (Loads certificates) → Used in **CertificateValidationTest** to fetch X.509 certificates.
- **BaseTest** (Test setup & teardown) → Extended by **CertificateValidationTest**.
- **CertificateUtils** (Utility methods) → Provides helper functions for certificate validation.
- **TestListener** (TestNG Listener) → Captures test execution events and logs them for reporting.
- **CertificateValidationTest** (Main Test Class) → Contains test methods to validate certificates.

## Technologies & Dependencies
- **Java** (JDK 11+)
- **TestNG** (Unit testing framework)
- **Maven** (Build tool)
- **Allure** (Test reporting framework)

### Maven Dependencies (from `pom.xml`)
```xml
 <project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>x509.certificate</groupId>
    <artifactId>certificate-validation</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>7.7.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.bouncycastle</groupId>
            <artifactId>bcprov-jdk15on</artifactId>
            <version>1.68</version>
        </dependency>
        <dependency>
            <groupId>io.qameta.allure</groupId>
            <artifactId>allure-testng</artifactId>
            <version>2.23.0</version>
        </dependency>
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>7.7.0</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Compiler Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>

            <!-- TestNG Runner -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.22.2</version>
                <configuration>
                    <suiteXmlFiles>
                        <suiteXmlFile>testng.xml</suiteXmlFile>
                    </suiteXmlFiles>
                </configuration>
            </plugin>

            <!-- Allure Report Plugin -->
            <plugin>
                <groupId>io.qameta.allure</groupId>
                <artifactId>allure-maven</artifactId>
                <version>2.11.2</version>
                <executions>
                    <execution>
                        <phase>verify</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

## Installation Guide
### Prerequisites
- Install **Java JDK 11+**
- Install **Maven**
- Install **Allure** for reporting (`brew install allure` on Mac/Linux or manually for Windows)

### Setup
1. **Clone the Repository**:
   ```sh
   git clone <[repository-url](https://github.com/Animesh-Choudhary/Frameowork)>
   cd certificate-validation-framework
   ```
2. **Install Dependencies**:
   ```sh
   mvn clean install
   ```

## Running the Tests
1. Run tests using TestNG:
   ```sh
   mvn test
   ```
2. Generate Allure Reports:
   ```sh
   allure serve allure-results
   ```

## How It Works
- The framework loads certificates from `certs/`.
- Test cases validate the certificate properties (CN, issuer, expiry, signature algorithm).
- All results are logged and stored in Allure reports.

## Contributing
- Fork the repository.
- Create a new branch.
- Make necessary changes and raise a Pull Request.

## License
This project is open-source and available for modification and distribution.

