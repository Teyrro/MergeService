package org.myMerge;

import org.myMerge.services.Impl.AppServiceImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {

        new AppServiceImpl(args, 10000, 5);
    }
}
