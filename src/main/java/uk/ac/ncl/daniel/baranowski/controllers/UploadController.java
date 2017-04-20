package uk.ac.ncl.daniel.baranowski.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("upload")
public class UploadController {

    @RequestMapping("/load_images")
    @ResponseBody
    public ArrayList<Object> index() {
        String fileRoute = "./static/images/uploads";
        String folderPath = "/images/uploads/";
        String thumbPath = "/images/uploads/thumbs/";

        ArrayList<Object> responseData = new ArrayList<>();
        java.io.File folder = new java.io.File(fileRoute);

        // Add images.
        for (java.io.File fileEntry : folder.listFiles()) {
            if (fileEntry.isFile()) {

                String filename = fileEntry.getName();

                Map<Object, Object> imageObj = new HashMap<Object, Object>();
                imageObj.put("url", folderPath + filename);
                imageObj.put("thumb", thumbPath + filename);
                imageObj.put("name", filename);
                responseData.add(imageObj);
            }
        }


        return responseData;
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    @ResponseBody
    public ImageUploadResponse uploadFile(
            @RequestParam("file") MultipartFile uploadfile) {

        String filename;
        try {
            // Get the filename and build the local file path
            filename = uploadfile.getOriginalFilename();
            String directory = "./static/images/uploads";

            File uploadDirectory = new File(directory);
            File thumbNailsDirectory = new File(directory + "/thumbs");
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdir();
            }

            if (!thumbNailsDirectory.exists()) {
                thumbNailsDirectory.mkdir();
            }

            BufferedImage thumbnail = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
            thumbnail.createGraphics()
                    .drawImage(ImageIO.read(uploadfile.getInputStream())
                            .getScaledInstance(100, 100, Image.SCALE_SMOOTH), 0, 0, null);

            File thumbnailSavedFile = new File(thumbNailsDirectory + "/" + filename);
            ImageIO.write(thumbnail, "jpg", thumbnailSavedFile);


            String filepath = directory + '/' + filename;

            // Save the file locally
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(uploadfile.getBytes());
            stream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
        }


        return new ImageUploadResponse("/images/uploads/" + filename);
    } // method uploadFile

    private static class ImageUploadResponse {
        private final String link;

        public ImageUploadResponse(String link) {
            this.link = link;
        }

        public String getLink() {
            return link;
        }
    }

}
