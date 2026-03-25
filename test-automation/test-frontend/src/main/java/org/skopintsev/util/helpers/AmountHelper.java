package org.skopintsev.util.helpers;

import java.math.BigInteger;
import java.text.NumberFormat;

public class AmountHelper {

    public static String formatAmount(BigInteger amount) {
        NumberFormat formatter = NumberFormat.getInstance();
        return formatter.format(amount);
    }
}
