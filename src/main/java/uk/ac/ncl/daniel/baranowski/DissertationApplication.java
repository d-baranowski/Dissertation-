package uk.ac.ncl.daniel.baranowski;

import uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DissertationApplication { //NOSONAR We want a visible public constructor
    public static void main(String[] args) {
        writeConstantsToJsonFile();
        SpringApplication.run(DissertationApplication.class, args); //NOSONAR The context will be closed manually later
    }

    private static void writeConstantsToJsonFile() {
        File file = new File("src/main/resources/static/js/controllerEndpoints.js");
        String content = ControllerEndpoints.toJson();
        content = "var ENDPOINTS = " + content;

        try (FileOutputStream fop = new FileOutputStream(file)) {

            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = content.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
