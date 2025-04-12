package org.loop.troop.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    /**
     * Uploads an image to MinIO.
     * 
     * @param uploadLocation the folder in MinIO where the image will be stored
     * @param image          the image file to be uploaded
     * @param username       the username to ensure unique image name
     * @return the file name of the uploaded image
     */
    String save(String uploadLocation, MultipartFile image, String username);

    /**
     * Gets the URL of an image from MinIO.
     * 
     * @param fileName the file name of the image
     * @return the URL of the image
     */
    String getImageUrl(String fileName);
}
