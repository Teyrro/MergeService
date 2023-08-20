package org.myMerge.services.Impl;

import org.myMerge.entity.Setting;
import org.myMerge.services.AppService;
import org.myMerge.services.MergeService;
import org.myMerge.services.MyReaderService;
import org.myMerge.services.MyWriterService;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AppServiceImpl implements AppService {
    MergeService mergeService;
    Setting setting;

    public AppServiceImpl(String[] args, int countListElements, int countBoundWithApp) {
        this.setting = new Setting(args);
        MyReaderService reader = new MyReaderServiceImpl(setting.getTypeOfData(), countBoundWithApp,
                setting.getInputFiles(), countListElements);
        MyWriterService writer = new MyWriterServiceImpl(setting.getOutFile().get(0));
        this.mergeService = new MergeServiceImpl(writer, reader, setting);
        this.run();
    }

    public void run() {
        try {
            this.choseTypeDataMerge();
        } catch (FileNotFoundException | NumberFormatException exp) {
            System.out.println(exp.getMessage());
        }

    }

    private void choseTypeDataMerge() throws FileNotFoundException {
        List<?> data = new ArrayList<>();
        mergeService.mergesort(data, this.setting.getOrderSorting());
    }
}
