package com.paymentcomponents.converter.iso20022.demo;

import gr.datamation.converter.common.exceptions.InvalidMtMessageException;
import gr.datamation.converter.common.exceptions.InvalidMxMessageException;
import gr.datamation.converter.common.exceptions.StopTranslationException;
import gr.datamation.converter.common.exceptions.TranslationUnhandledException;
import gr.datamation.converter.common.utils.MtMessageValidationUtils;
import gr.datamation.converter.common.utils.MxMessageValidationUtils;
import gr.datamation.converter.iso20022.Iso20022Translator;
import gr.datamation.converter.iso20022.converters.mt.Mt300ToFxtr01400104;
import gr.datamation.converter.iso20022.interfaces.MtToIso20022Translator;
import gr.datamation.mt.common.SwiftMessage;
import gr.datamation.mx.message.fxtr.ForeignExchangeTradeInstruction04;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;

public class TranslateMtToMx {

    public static void main(String... args) {
        translateMt300ToFxtr014_Auto();
        translateMt300ToFxtr014_ExplicitText();
        translateMt300ToFxtr014_ExplicitObject();
    }

    public static void translateMt300ToFxtr014_Auto() {
        String mxMessage = null;
        // You have the option to provide the MT message in text format and get back the ISO20022 message in text format.
        // Translator auto detects the translation mapping.
        // In order to handle MT and ISO20022 messages, advice README.md
        try {
            mxMessage = Iso20022Translator.translateMtToMx(validMtMessage);
        } catch (InvalidMtMessageException e) {
            System.out.println("MT message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (StopTranslationException e) {
            System.out.println("Translation errors occurred");
            e.getTranslationErrorList().forEach(System.out::println);
            return;
        } catch (TranslationUnhandledException e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
            return;
        }

        //Validate the Translated message
        try {
            MxMessageValidationUtils.autoParseAndValidateMxMessage(mxMessage);
            System.out.println("Translated Message is: \n" + mxMessage);
        } catch (InvalidMxMessageException e) {
            System.out.println("ISO20022 message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        }
    }

    public static void translateMt300ToFxtr014_ExplicitText() {
        String mxMessage = null;
        try {
            // If you do not want to use the auto-translation functionality, you have the option to provide the MT message
            // in text format and get back the ISO20022 message in text format. In this case you need to know the exact translation mapping.
            // In order to handle MT and ISO20022 messages, advice README.md
            MtToIso20022Translator<?> mtToMxTranslator = new Mt300ToFxtr01400104();
            mxMessage = mtToMxTranslator.translate(validMtMessage);
        } catch (InvalidMtMessageException e) {
            System.out.println("MT message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (StopTranslationException e) {
            System.out.println("Translation errors occurred");
            e.getTranslationErrorList().forEach(System.out::println);
            return;
        } catch (TranslationUnhandledException e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
            return;
        }

        //Validate the Translated message
        try {
            MxMessageValidationUtils.autoParseAndValidateMxMessage(mxMessage);
            System.out.println("Translated Message is: \n" + mxMessage);
        } catch (InvalidMxMessageException e) {
            System.out.println("ISO20022 message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        }
    }

    public static void translateMt300ToFxtr014_ExplicitObject() {
        ForeignExchangeTradeInstruction04 mxMessage = null;
        // If you do not want to use the auto-translation functionality, you have the option to provide the MT message
        // in Object format and get back the ISO20022 message in Object format. In this case you need to know the exact translation mapping.
        // In order to handle MT and ISO20022 messages, advice README.md
        try {
            SwiftMessage swiftMessage = MtMessageValidationUtils.parseMtMessage(validMtMessage);
            MtToIso20022Translator<ForeignExchangeTradeInstruction04> mtToMxTranslator = new Mt300ToFxtr01400104();
            mxMessage = mtToMxTranslator.translate(swiftMessage);
        } catch (InvalidMtMessageException e) {
            System.out.println("MT message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (StopTranslationException e) {
            System.out.println("Translation errors occurred");
            e.getTranslationErrorList().forEach(System.out::println);
            return;
        } catch (TranslationUnhandledException e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
            return;
        }

        //Validate the Translated message
        try {
            MxMessageValidationUtils.validateMxMessage(mxMessage);
            System.out.println("Translated Message is: \n" + mxMessage.convertToXML());
        } catch (InvalidMxMessageException e) {
            System.out.println("ISO20022 message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (JAXBException | UnsupportedEncodingException e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
        }
    }

    private static final String validMtMessage = "{1:F01PRDTNGLAAXXX0000000000}{2:I300NAMENGLAXXXXN2020}{3:{108:MT300}}{4:\n" +
            ":15A:\n" +
            ":20:309656\n" +
            ":21:REF\n" +
            ":22A:NEWT\n" +
            ":22C:NAMELA1462PRDTLA\n" +
            ":82A:/12345\n" +
            "PRDTNGLA\n" +
            ":87A:/54321\n" +
            "NAMENGLA\n" +
            ":15B:\n" +
            ":30T:20220805\n" +
            ":30V:20220809\n" +
            ":36:414,62\n" +
            ":32B:USD5000,\n" +
            ":57A:/36204777\n" +
            "CITIUS33\n" +
            ":33B:NGN2073100,\n" +
            ":57A:/3000025911\n" +
            "CBNINGLA\n" +
            ":58A:/541291\n" +
            "NAMENGLA\n" +
            "-}{5:}";

}
