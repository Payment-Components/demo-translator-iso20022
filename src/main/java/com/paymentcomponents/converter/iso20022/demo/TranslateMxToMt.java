package com.paymentcomponents.converter.iso20022.demo;

import gr.datamation.converter.common.exceptions.InvalidMtMessageException;
import gr.datamation.converter.common.exceptions.InvalidMxMessageException;
import gr.datamation.converter.common.exceptions.StopTranslationException;
import gr.datamation.converter.common.exceptions.TranslationUnhandledException;
import gr.datamation.converter.common.utils.MtMessageValidationUtils;
import gr.datamation.converter.common.utils.MxMessageValidationUtils;
import gr.datamation.converter.iso20022.Iso20022Translator;
import gr.datamation.converter.iso20022.converters.mx.Fxtr01400104ToMt300;
import gr.datamation.converter.iso20022.interfaces.Iso20022ToMtTranslator;
import gr.datamation.mt.common.SwiftMessage;
import gr.datamation.mt.processor.SwiftMsgProcessor;
import gr.datamation.mx.message.fxtr.ForeignExchangeTradeInstruction04;

public class TranslateMxToMt {

    public static void main(String... args) {
        translateFxtr014ToMt300_Auto();
        translateFxtr014ToMt300_ExplicitText();
        translateFxtr014ToMt300_ExplicitObject();
    }

    public static void translateFxtr014ToMt300_Auto() {
        // You have the option to provide the ISO20022 message in text format and get back the MT message in text format.
        // Translator auto detects the translation mapping.
        // In order to handle MT and ISO20022 messages, advice README.md
        String mtMessage = null;
        try {
            mtMessage = Iso20022Translator.translateMxToMt(validMXMessage);
        } catch (InvalidMxMessageException e) {
            System.out.println("ISO20022 message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (StopTranslationException e) {
            System.out.println("Translation errors occurred");
            e.getTranslationErrorList().forEach(System.out::println);
        } catch (TranslationUnhandledException e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
            return;
        }

        //Validate the Translated message
        try {
            MtMessageValidationUtils.parseAndValidateMtMessage(mtMessage);
            System.out.println("Translated Message is: \n" + mtMessage);
        } catch (InvalidMtMessageException e) {
            System.out.println("MT message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        }
    }

    public static void translateFxtr014ToMt300_ExplicitText() {
        // If you do not want to use the auto-translation functionality, you have the option to provide the ISO20022 message
        // in text format and get back the MT message in text format. In this case you need to know the exact translation mapping.
        // In order to handle MT and ISO20022 messages, advice README.md
        String mtMessage = null;
        try {
            Iso20022ToMtTranslator<?> mxToMtTranslator = new Fxtr01400104ToMt300();
            mtMessage = mxToMtTranslator.translate(validMXMessage);
        } catch (InvalidMxMessageException e) {
            System.out.println("ISO20022 message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (StopTranslationException e) {
            System.out.println("Translation errors occurred");
            e.getTranslationErrorList().forEach(System.out::println);
        } catch (TranslationUnhandledException e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
            return;
        }

        //Validate the Translated message
        try {
            MtMessageValidationUtils.parseAndValidateMtMessage(mtMessage);
            System.out.println("Translated Message is: \n" + mtMessage);
        } catch (InvalidMtMessageException e) {
            System.out.println("MT message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        }
    }

    public static void translateFxtr014ToMt300_ExplicitObject() {
        // If you do not want to use the auto-translation functionality, you have the option to provide the ISO20022 message
        // in Object format and get back the MT message in Object format. In this case you need to know the exact translation mapping.
        // In order to handle MT and ISO20022 messages, advice README.md
        SwiftMessage mtMessage = null;
        try {
            ForeignExchangeTradeInstruction04 coreMessage = MxMessageValidationUtils.parseAndValidateMxMessage(validMXMessage, ForeignExchangeTradeInstruction04.class);
            //or MxMessageValidationUtils.autoParseAndValidateMxMessage(validMXMessage);
            Iso20022ToMtTranslator<ForeignExchangeTradeInstruction04> mxToMtTranslator = new Fxtr01400104ToMt300();
            mtMessage = mxToMtTranslator.translate(coreMessage);
        } catch (InvalidMxMessageException e) {
            System.out.println("ISO20022 message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (StopTranslationException e) {
            System.out.println("Translation errors occurred");
            e.getTranslationErrorList().forEach(System.out::println);
        } catch (TranslationUnhandledException e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
        }

        //Validate the Translated message
        try {
            MtMessageValidationUtils.validateMtMessage(mtMessage);
            System.out.println("Translated Message is: \n" + new SwiftMsgProcessor().BuildMsgStringFromObject(mtMessage));
        } catch (InvalidMtMessageException e) {
            System.out.println("MT message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("MT message is invalid");
            e.printStackTrace();
        }
    }

    private static final String validMXMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:fxtr.014.001.04\">\n" +
            "    <FXTradInstr>\n" +
            "        <TradInf>\n" +
            "            <TradDt>2022-08-05</TradDt>\n" +
            "            <OrgtrRef>309656</OrgtrRef>\n" +
            "            <CmonRef>NAMELA1462PRDTLA</CmonRef>\n" +
            "            <OprTp>NEWT</OprTp>\n" +
            "        </TradInf>\n" +
            "        <TradgSdId>\n" +
            "            <SubmitgPty>\n" +
            "                <PtyId>\n" +
            "                    <AnyBIC>\n" +
            "                        <AnyBIC>PRDTNGLA</AnyBIC>\n" +
            "                    </AnyBIC>\n" +
            "                    <AcctNb>12345</AcctNb>\n" +
            "                </PtyId>\n" +
            "            </SubmitgPty>\n" +
            "        </TradgSdId>\n" +
            "        <CtrPtySdId>\n" +
            "            <SubmitgPty>\n" +
            "                <PtyId>\n" +
            "                    <AnyBIC>\n" +
            "                        <AnyBIC>NAMENGLA</AnyBIC>\n" +
            "                    </AnyBIC>\n" +
            "                    <AcctNb>54321</AcctNb>\n" +
            "                </PtyId>\n" +
            "            </SubmitgPty>\n" +
            "        </CtrPtySdId>\n" +
            "        <TradAmts>\n" +
            "            <TradgSdBuyAmt Ccy=\"USD\">5000</TradgSdBuyAmt>\n" +
            "            <TradgSdSellAmt Ccy=\"NGN\">2073100</TradgSdSellAmt>\n" +
            "            <SttlmDt>2022-08-09</SttlmDt>\n" +
            "        </TradAmts>\n" +
            "        <AgrdRate>\n" +
            "            <XchgRate>414.62</XchgRate>\n" +
            "        </AgrdRate>\n" +
            "        <TradgSdSttlmInstrs>\n" +
            "            <RcvgAgt>\n" +
            "                <PtyId>\n" +
            "                    <AnyBIC>\n" +
            "                        <AnyBIC>CITIUS33</AnyBIC>\n" +
            "                    </AnyBIC>\n" +
            "                    <AcctNb>36204777</AcctNb>\n" +
            "                </PtyId>\n" +
            "            </RcvgAgt>\n" +
            "        </TradgSdSttlmInstrs>\n" +
            "        <CtrPtySdSttlmInstrs>\n" +
            "            <RcvgAgt>\n" +
            "                <PtyId>\n" +
            "                    <AnyBIC>\n" +
            "                        <AnyBIC>CBNINGLA</AnyBIC>\n" +
            "                    </AnyBIC>\n" +
            "                    <AcctNb>3000025911</AcctNb>\n" +
            "                </PtyId>\n" +
            "            </RcvgAgt>\n" +
            "            <BnfcryInstn>\n" +
            "                <PtyId>\n" +
            "                    <AnyBIC>\n" +
            "                        <AnyBIC>NAMENGLA</AnyBIC>\n" +
            "                    </AnyBIC>\n" +
            "                    <AcctNb>541291</AcctNb>\n" +
            "                </PtyId>\n" +
            "            </BnfcryInstn>\n" +
            "        </CtrPtySdSttlmInstrs>\n" +
            "        <OptnlGnlInf>\n" +
            "            <RltdTradRef>REF</RltdTradRef>\n" +
            "        </OptnlGnlInf>\n" +
            "    </FXTradInstr>\n" +
            "</Document>";

}
