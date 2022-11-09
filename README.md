# Message Translator ISO20022 Demo

The project is here to demonstrate how our [SDK](https://www.paymentcomponents.com/messaging-libraries/) for ISO20022
Message Translator works.

This documentation describes how to incorporate the ISO20022 Translator Library into your project. The SDK is written in Java.  
By following this guide you will be able to translate MT(ISO 15022) messages to ISO20022 messages 
and vice versa according to ISO20022 guidelines.

It's a simple maven project, you can download it and run it, with Java 1.8 or above.

## SDK setup
Incorporate the SDK [jar](https://nexus.paymentcomponents.com/repository/[CLIENTS_REPO]/gr/datamation/translator-iso20022/4.0.0/translator-iso20022-4.0.0-full.jar)
into your project by the regular IDE means.  
This process will vary depending upon your specific IDE and you should consult your documentation on how to deploy a bean.  
For example in Intellij all that needs to be done is to import the jar files into a project. Alternatively, you can import it as a Maven or Gradle dependency.

### Maven

Define repository in the repositories section
```xml
<repository>
    <id>[CLIENTS_REPO]</id>
    <url>https://nexus.paymentcomponents.com/repository/[CLIENTS_REPO]</url>
</repository>
```

Import the SDK
```xml
<dependency>
    <groupId>gr.datamation</groupId>
    <artifactId>translator-iso20022</artifactId>
    <version>4.0.0</version>
    <classifier>full</classifier>
</dependency>
```
Import additional dependencies if not included in your project
```xml
<dependency>
    <groupId>org.codehaus.groovy</groupId>
    <artifactId>groovy-all</artifactId>
    <version>2.5.11</version>
    <scope>compile</scope>
    <type>pom</type>
</dependency>

<dependency>
    <groupId>javax.xml.bind</groupId>
    <artifactId>jaxb-api</artifactId>
    <version>2.3.1</version>
</dependency>

<dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
    <version>2.3.1</version>
</dependency>
```

### Gradle 

Define repository in the repositories section
```groovy
repositories {
    maven {
        url "https://nexus.paymentcomponents.com/repository/[CLIENTS_REPO]"
    }
}
```

Import the SDK
```groovy
implementation 'gr.datamation:translator-iso20022:4.0.0:[CLIENTS_REPO]@jar'
```

Import additional dependencies if not included in your project
```groovy
implementation group: 'org.codehaus.groovy', name: 'groovy-all', version: '2.5.11', ext: 'pom'
implementation group: 'javax.xml.bind', name: 'jaxb-api', version: '2.3.1'
implementation group: 'org.glassfish.jaxb', name: 'jaxb-runtime', version: '2.3.1'
```

## Supported MT > MX Translations

| MT message | MX message      | Translator Class    | Multiple MTX support |
|------------|-----------------|---------------------|:--------------------:|
| MT300      | fxtr.014.001.04 | Mt300ToFxtr01400104 |       &cross;        |

_* Multiple MX are splitted in text by an empty line_

## Supported MX > MT Translations

| MT message      | MX message     | Translator Class    | Multiple MT support |
|-----------------|----------------|---------------------|:-------------------:|
| fxtr.014.001.04 | MT940          | Fxtr01400104ToMt300 |       &cross;       |

## Instructions

### Auto Translation

You have the option to provide the MT or ISO20022 message and the library auto translates it to its equivalent.  
Both input and output are in text format.  
You need to call the following static methods of `Iso20022Translator` class.  
In case of no error of the input message, you will get the formatted translated message.  
When MT message is used as input it is not validated.  
Translated message is not validated.
```java
public static String translateMtToMx(String mtMessage) throws InvalidMtMessageException, StopTranslationException, TranslationUnhandledException
```
```java
public static String translateMxToMt(String mxMessage) throws InvalidMxMessageException, StopTranslationException, TranslationUnhandledException
```

### Explicit Translation

If you do not want to use the auto-translation functionality, you can call directly the Translator you want.  
In this case you need to know the exact translation mapping.  
Translator classes implement the `MtToIso20022Translator` or `Iso20022ToMtTranslator` interface.  
The `translate(Object)`, does not validate the message.  
The `translate(String)`, validates the message only in case of MX as input.  
Translated message is not validated.

`MtToIso20022Translator` interface provides the following methods for both text and object format translations.
```java
String translate(String swiftMtMessageText) throws InvalidMtMessageException, StopTranslationException, TranslationUnhandledException;
CoreMessage translate(SwiftMessage swiftMtMessage) throws StopTranslationException, TranslationUnhandledException;
CoreMessage[] translateMultipleMx(SwiftMessage swiftMtMessage) throws StopTranslationException, TranslationUnhandledException;
```
The method `translateMultipleMx` translates an MT message to multiple ISO20022 messages.

`Iso20022ToMtTranslator` interface provides the following methods.
```java
String translate(String mxMessageText) throws InvalidMxMessageException, StopTranslationException, TranslationUnhandledException;
SwiftMessage translate(CoreMessage coreMessage) throws StopTranslationException, TranslationUnhandledException;
SwiftMessage[] translateMultipleMt(CoreMessage coreMessage) throws StopTranslationException, TranslationUnhandledException;
```
The method `translateMultipleMt` translates an ISO20022 message to multiple MT messages.

You can see in [table below](#supported-mx--mt-translations) which translations support this.  
In case that a translation uses this logic, the translation in text format will return the MT messages splitted with `$`.  
For example:
```
:56A:INTERBIC
-}${1:F01TESTBICAXXXX1111111111}
```

### Message Validation

In order to validate the translated MT message, you can use `MtMessageValidationUtils.validateMtMessage(SwiftMessage mtMessage)`,
`MtMessageValidationUtils.parseAndValidateMtMessage(String message)` or any other way you prefer.  
In order to validate the translated ISO20022 message, you can use `MxMessageValidationUtils.validateMxMessage(T message)`,
`MxMessageValidationUtils.autoParseAndValidateMxMessage(String messageText)` or any other way you prefer.


### Error Handling
#### InvalidMtMessageException & InvalidMxMessageException
When you translate a message, input message is validated. For example, in a MT→MX translation, the
first step is to validate the MT message and we proceed to translation only if the message is valid.  
This is the reason why this direction throws `InvalidMtMessageException`.  
The other direction throws `InvalidMxMessageException`.  
Both Exceptions contain a `validationErrorList` attribute which contains a description of the errors occurred.

#### StopTranslationException
When there is a condition in input message that obstructs the translation, a `StopTranslationException` is thrown which
contains a `translationErrorList`. The `TranslationError` has the structure:
- errorCode
- errorCategory
- errorDescription

#### TranslationUnhandledException
When there is an exception that is not known, like `NullPointerException`, an `TranslationUnhandledException` is thrown the
actual exception is attached as the cause.


### Modify the generated message

Once you have the translated message as text, you can use our other Financial Messaging
Libraries ([Other Resources](#other-resources)) in order to create a Java Object and make any changes you want.

In order to create an ISO20022 Java Object use the below code. The object class `ForeignExchangeTradeInstruction04` may vary depending on the ISO20022 Message Type.   
```java
ForeignExchangeTradeInstruction04 foreignExchangeTradeInstruction04 = new ForeignExchangeTradeInstruction04();
foreignExchangeTradeInstruction04.parseXML(xml);
```

In order to create an MT Java Object use the below code.
```java
SwiftMessage swiftMessage = new SwiftMsgProcessor().ParseMsgStringToObject(translatedMessage);
```

### Code Samples

In this project you can see code for all the basic manipulation of an MX message, like:
- [Translate MT to MX](src/main/java/com/paymentcomponents/converter/iso20022/demo/TranslateMtToMx.java)
- [Translate MX to MT](src/main/java/com/paymentcomponents/converter/iso20022/demo/TranslateMxToMt.java)

### Other resources

- More information about our implementation of **MT library** can be found in our demo on [PaymentComponents GitHub](https://github.com/Payment-Components/demo-swift-mt).
- More information about our implementation of **ISO20022 library** can be found in our demo on [PaymentComponents GitHub](https://github.com/Payment-Components/demo-iso20022).