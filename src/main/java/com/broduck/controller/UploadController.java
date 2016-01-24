package com.broduck.controller;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by broduck on 16. 1. 22.
 */
@Controller
public class UploadController {

    @Resource(name = "uploadPath")
    private String uploadPath;

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @RequestMapping(value = "/fileUpload/post", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String upload(MultipartHttpServletRequest multipartRequest) throws Exception {

        Iterator<String> itr = multipartRequest.getFileNames();
        String fileName = "";

        while (itr.hasNext()) {
            MultipartFile mpf = multipartRequest.getFile(itr.next());

            fileName = uploadFile(mpf.getOriginalFilename(), mpf.getBytes());
            logger.info("file info : " + fileName);
        }

        return fileName;
    }

    @ResponseBody
    @RequestMapping(value = "/displayFile")
    public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {
        InputStream in = null;
        ResponseEntity<byte[]> entity = null;

        logger.info("File name : " + fileName);

        try {
            HttpHeaders headers = new HttpHeaders();

            in = new FileInputStream(uploadPath + fileName);

            fileName = fileName.substring(fileName.indexOf("_") + 1);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.add("Content-Disposition", "attachment; filename=\"" +
                    new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");

            entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
        } finally {
            in.close();
        }

        return entity;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public ResponseEntity<String> deleteFile(String fileName) {
        logger.info("delete file: " + fileName);

        new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();

        return new ResponseEntity<String>("deleted", HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAllFiles", method = RequestMethod.POST)
    public ResponseEntity<String> deleteFile(@RequestParam("files[]") String[] files) {
        if (files == null || files.length == 0) {
            logger.info("files is null");
            return new ResponseEntity<String>("deleted", HttpStatus.OK);
        }

        for (String fileName : files) {
            logger.info("delete  file : " + fileName);

            try {
                new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<String>("deleted", HttpStatus.OK);
    }

    private String uploadFile(String originalName, byte[] fileData) throws Exception {
        UUID uid = UUID.randomUUID();

        String addPath = calcPath(uploadPath);

        String savedName = uid.toString() + "_" + originalName;
        File target = new File(uploadPath + addPath, savedName);

        FileCopyUtils.copy(fileData, target);

        return addPath + "/" + savedName;
    }

    public static String calcPath(String uploadPath) {
        Calendar cal = Calendar.getInstance();
        String yearPath = File.separator + cal.get(Calendar.YEAR);
        String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
        String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

        makeDir(uploadPath, yearPath, monthPath, datePath);

        return datePath;
    }

    private static void makeDir(String uploadPath, String... paths) {
        if (new File(paths[paths.length - 1]).exists()) {
            return;
        }

        for (String path : paths) {
            File dirPath = new File(uploadPath + path);

            if (!dirPath.exists()) {
                dirPath.mkdir();
            }
        }
    }
}
