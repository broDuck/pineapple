package com.broduck.controller;

import com.broduck.domain.MusicVO;
import com.broduck.domain.PageMaker;
import com.broduck.domain.SearchCriteria;
import com.broduck.service.MusicService;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by broDuck on 2016-01-26.
 */

@Controller
@RequestMapping("/music/*")
public class MusicController {
    
    @Resource
    private String musicPath;

    private static final Logger logger = LoggerFactory.getLogger(MusicController.class);

    @Inject
    private MusicService service;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public void registGET() throws Exception {

    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public void listPage(@ModelAttribute("cri")SearchCriteria cri, Model model) throws Exception {
        logger.info(cri.toString());

        model.addAttribute("list", service.listPage(cri));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);

        pageMaker.setTotalCount(service.listSearchCount(cri));

        model.addAttribute("pageMaker", pageMaker);
    }

    @RequestMapping(value = "/fileUpload", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String upload(MultipartHttpServletRequest multipartRequest) throws Exception {

        Iterator<String> itr = multipartRequest.getFileNames();
        String fileName = "";

        MusicVO musics;

        while (itr.hasNext()) {
            MultipartFile mpf = multipartRequest.getFile(itr.next());

            musics = uploadFile(mpf.getOriginalFilename(), mpf.getBytes());
            service.create(musics);
        }

        return fileName;
    }

    @RequestMapping(value = "/player", method = RequestMethod.GET)
    public void playMusicGET() throws Exception {

    }

    @RequestMapping(value = "/player", method = RequestMethod.POST)
    public void playMusicPOST(@RequestBody String data) throws Exception {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(data);

        logger.info(jsonObject.toString());

        Integer mno = Integer.parseInt(jsonObject.get("mno").toString());
        String session = (String) jsonObject.get("session");

        logger.info("mno : " + mno);
        logger.info("session : " + session);

        MusicVO vo = service.read(mno);

        if (session.length() != 0) {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            JSONObject object = new JSONObject();
            object.put("title", vo.getTitle());
            object.put("artist", vo.getArtist());
            object.put("mp3", musicPath + "/" + vo.getFilepath());

            channel.queueDeclare("test", false, false, false, null);
            channel.basicPublish("", "test", null, object.toJSONString().getBytes("UTF-8"));

            logger.info("SENT : " + object.toJSONString());

            channel.close();
            connection.close();
        }
    }

    private MusicVO uploadFile(String originalName, byte[] fileData) throws Exception {
        UUID uid = UUID.randomUUID();
        MusicVO music = new MusicVO();

        String addPath = calcPath(musicPath);
        String savedName = uid.toString() + "_" + originalName;

        music.setFilepath(addPath + "/" + savedName);

        File target = new File(musicPath + addPath, savedName);

        FileCopyUtils.copy(fileData, target);

        Mp3File mp3file = new Mp3File(target);
        if (mp3file.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();

            music.setArtist(id3v2Tag.getArtist());
            music.setTitle(id3v2Tag.getTitle());
        }

        return music;
    }

    public static String calcPath(String musicPath) {
        Calendar cal = Calendar.getInstance();
        String yearPath = File.separator + cal.get(Calendar.YEAR);
        String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
        String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

        makeDir(musicPath, yearPath, monthPath, datePath);

        return datePath;
    }

    private static void makeDir(String musicPath, String... paths) {
        if (new File(paths[paths.length - 1]).exists()) {
            return;
        }

        for (String path : paths) {
            File dirPath = new File(musicPath + path);

            if (!dirPath.exists()) {
                dirPath.mkdir();
            }
        }
    }
}
