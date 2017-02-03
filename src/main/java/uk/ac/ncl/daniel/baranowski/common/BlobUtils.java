package uk.ac.ncl.daniel.baranowski.common;

import java.util.Base64;

public class BlobUtils {
    private BlobUtils() {
        //Private contructor because all methods are static
    }

    public static byte[] png64StringToByteArray(String blob) {
        return Base64.getMimeDecoder().decode(blob.replace("data:image/png;base64,", ""));
    }
}
