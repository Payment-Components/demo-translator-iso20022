package com.paymentcomponents.converter.iso20022.demo;

public class Main {

    public static void main(String[] args) {
        TranslateMtToMx.translateMt300ToFxtr014_Auto();
        TranslateMtToMx.translateMt300ToFxtr014_ExplicitText();
        TranslateMtToMx.translateMt300ToFxtr014_ExplicitObject();

        TranslateMxToMt.translateFxtr014ToMt300_Auto();
        TranslateMxToMt.translateFxtr014ToMt300_ExplicitText();
        TranslateMxToMt.translateFxtr014ToMt300_ExplicitObject();
    }

}
