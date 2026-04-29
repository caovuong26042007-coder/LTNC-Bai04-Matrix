package com.practice.maven;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class PathTest {

    @Test
    public void testWindowsPath() {
        // dùng dấu gạch chéo ngược (\) cứng của Windows
        File file = new File("data\\customers.txt");

        // Trên Windows: hệ thống hiểu data là thư mục cha, trả về data. (Test Pass)
        // Trên Linux/Mac: hệ thống coi "\" chỉ là một ký tự trong tên file, trả về null. (Test Fail)
        Assert.assertEquals("data", file.getParent());
    }
}