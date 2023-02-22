Mask ID
-------

![Java 8](https://img.shields.io/badge/Java-8-blue?style=flat-square)

This micro library aims to provide a solution to hide (mask) incremental identifiers
from external observer without additional database expenses.

It provides mathematical/logical solutions to mask and unmask identifiers before
sending them to client.

It works with both `integer` and `long` identifiers, served by `IntMasker` and `LongMasker`
interface implementors respectively.

### TL&DR;

```java
// Creating arithmetical masker that will multiply any value given by 7
// and then add 15'485'863
LongMasker masker = new MultiplicationShiftMasker(15_485_863, 7);

System.out.println(masker.maskLong(1));          // Outputs 15485870
System.out.println(masker.unmaskLong(15485870)); // Outputs 1
```

### Warning

Security through obscurity - is a bad way to build reliable system. Identification masking
made using this library is not cryptography and not 100% bullet-proof.

### Available maskers

|   **Type**   |         **Integer**         |          **Long**           | **Description**                                                                    |
|:------------:|:---------------------------:|:---------------------------:|------------------------------------------------------------------------------------|
| Arithmetical | `MultiplicationShiftMasker` | `MultiplicationShiftMasker` | Generates masked value using multiplication and addition                           |
|   Logical    |       `IntXorMasker`        |       `LongXorMasker`       | Generates masked value using XOR operation over secret value                       |
|   Shuffle    |    `IntBitShuffleMasker`    |   `LongBitShuffleMasker`    | Generates masked value using bitwise shuffling                                     |
|  Dictionary  |       `IntDictionary`       |      `LongDictionary`       | Generates masked value using fixed size dictionary                                 |
|   Combine    |    `IntSequentialMasker`    |   `LongSequentialMasker`    | Generates masked value sequentially invoking other maskers provided in constructor |