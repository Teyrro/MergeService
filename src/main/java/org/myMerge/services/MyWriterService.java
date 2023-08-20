package org.myMerge.services;


import org.myMerge.entity.Setting;

import java.util.Queue;

public interface MyWriterService {
    void renameTempFileToOutFile(Setting var1, int var2);

    <T> void writeAllListElements(Queue<T> var1, String var2, boolean var3);
}