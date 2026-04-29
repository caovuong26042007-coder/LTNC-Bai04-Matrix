package com.practice.maven;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class PathTest {

    @Test
    public void testMultiPlatformPath() {
       // dùng file seperator
        File file = new File("data" + File.separator + "customers.txt");
        Assert.assertEquals("data", file.getParent());
    }
}