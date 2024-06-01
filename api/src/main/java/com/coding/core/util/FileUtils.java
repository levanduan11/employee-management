package com.coding.core.util;

import com.google.common.base.Preconditions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

public final class FileUtils {

    private FileUtils() {
    }

    public static String constructPath(String... paths) {
        Preconditions.checkArgument(paths != null, "paths must not be null");
        StringJoiner joiner = new StringJoiner("/");
        for (String path : paths) {
            joiner.add(path);
        }
        return joiner.toString();
    }

    public static String getExtension(String path) {
        Preconditions.checkArgument(path != null, "path must not be null");
        return path.substring(path.lastIndexOf("."));
    }

    public static String createFileNameFromExtension(String path, String extension) {
        Preconditions.checkArgument(path != null, "path must not be null");
        Preconditions.checkArgument(extension != null, "extension must not be null");
        return path + extension;
    }

    public static String getFileNameFromOriginalFileName(String originalFileName) {
        Preconditions.checkArgument(originalFileName != null, "originalFileName must not be null");
        return originalFileName.substring(0, originalFileName.lastIndexOf("."));
    }

    public static String generateFileNameWithDateTime(final String fileName) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        return format + "_" + fileName;
    }

    public static void main(String[] args) {
        System.out.println(generateFileNameWithDateTime("test.png"));
    }
}
