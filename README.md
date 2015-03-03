# Backend Mobile Payment Verifiers

Java library to verify mobile payments in backend.

## Usage

Create library jar file from source, gradle is required
```
gradle build
```

```java
// import class
import com.alisrc.android.payment.AndroidVerifier;
import com.alisrc.ios.payment.IosVerifier;
import com.alisrc.wp.payment.WindowsVerifier;

// check android payment verification
boolean androidPaymentVerified = AndroidVerifier.verifySignature(signedData, signature, pubkey);

// check ios payment verification
boolean iosPaymentVerified = IosVerifier.checkIosPayment(orderId, isSandbox);

// check windows phone store verification
boolean WindowsPhonePaymentVerified = WindowsVerifier.validateWindowsPhoneStore(xmlString);

// check windows store verification
boolean WindowsStorePaymentVerified = WindowsVerifier.validateWindowsStore(xmlString);

```

## TODO

- Write functional tests
- Gradle wrapper can be added to the project

## License

Copyright © 2015 Ali Sarac

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
