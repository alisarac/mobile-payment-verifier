# Backend Mobile Payment Verifiers

Java library to verify mobile payments in backend.

## Usage

Create library jar file from source, gradle is required
```
gradle build
```

```java
// import class
import com.alisrc.android.payment.Verifier;

// check verification
boolean isVerified = Verifier.verifySignature(signedData, signature, pubkey);
```

## TODO

- Write functional tests
- Add ios verifier
- Add windows phone verifier
- Gradle wrapper can be added to the project

## License

Copyright © 2015 Ali Sarac

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
