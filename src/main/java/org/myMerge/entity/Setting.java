package org.myMerge.entity;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.myMerge.services.enums.ServiceSetting;


public class Setting {
    private final Comparator<Node<?>> compareOperation;
    private ServiceSetting typeOfData;
    private final ServiceSetting orderSorting;
    private final List<String> outFile = new ArrayList<>();
    private final List<String> inputFiles = new ArrayList<>();

    public Setting(String[] inputFileArgs) {
        int startIndex = 0;
        String buffer = String.valueOf(inputFileArgs[0].charAt(1));
        if (!buffer.equals("a") && !buffer.equals("d")) {
            orderSorting = ServiceSetting.fromValue("a");
        } else {
            ++startIndex;
            orderSorting = ServiceSetting.fromValue(buffer);
        }

        buffer = String.valueOf(inputFileArgs[startIndex++].charAt(1));
        if (buffer.equals("i") || buffer.equals("s")) {
            typeOfData = ServiceSetting.fromValue(buffer);
        }

        buffer = inputFileArgs[startIndex++];
        outFile.add(buffer);
        outFile.add("Temp" + buffer);

        for(int i = startIndex; i < inputFileArgs.length; ++i) {
            buffer = inputFileArgs[i];
            inputFiles.add(buffer);
        }

        compareOperation = choseCompareOperation();
    }

    private Comparator<Node<?>> choseCompareOperation() {
        return switch (orderSorting) {
            case ORDER_ASC -> Node::compareTo;
            case ORDER_DEC -> Comparator.reverseOrder();
            default -> throw new IllegalStateException("Unexpected value: " + orderSorting);
        };
    }

    public ServiceSetting getTypeOfData() {
        return this.typeOfData;
    }

    public ServiceSetting getOrderSorting() {
        return this.orderSorting;
    }

    public List<String> getOutFile() {
        return this.outFile;
    }

    public List<String> getInputFiles() {
        return this.inputFiles;
    }

    public Comparator getCompareOperation() {
        return this.compareOperation;
    }
}
