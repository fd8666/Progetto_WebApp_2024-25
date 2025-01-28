package org.web24_25.cardswap_backend.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

class GzipCompression {
    public static byte[] compress(String data) throws IOException {
        if (data == null || data.isEmpty()) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(data.getBytes());
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static String decompress(byte[] compressedData) throws IOException {
        if (compressedData == null || compressedData.length == 0) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(compressedData))) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
        }
        return byteArrayOutputStream.toString();
    }

    public static String compressToBase64(String data) throws IOException {
        return Base64.getEncoder().encodeToString(compress(data));
    }

    public static String decompressFromBase64(String base64CompressedData) throws IOException {
        if (base64CompressedData == null || base64CompressedData.isEmpty()) {
            return null;
        }
        byte[] compressedData = Base64.getDecoder().decode(base64CompressedData);
        return decompress(compressedData);
    }
}