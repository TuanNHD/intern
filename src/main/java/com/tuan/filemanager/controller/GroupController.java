package com.tuan.filemanager.controller;

import com.tuan.filemanager.entity.FileEntity;
import com.tuan.filemanager.entity.FolderEntity;
import com.tuan.filemanager.exception.CustomException;
import com.tuan.filemanager.repository.ControlCenterRepository;
import com.tuan.filemanager.service.CmdService;
import com.tuan.filemanager.service.FileService;
import com.tuan.filemanager.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/control")
public class GroupController {
    @Autowired
    CmdService cmdService;
    @Autowired
    ControlCenterRepository controlCenterRepository;
    @Autowired
    FolderService folderService;
    @Autowired
    private FileService fileService;

//    @GetMapping
//    public String index(Model model) {
//        model.addAttribute("folders", folderService.findAllSingleFolder());
//        model.addAttribute("files", fileService.getAllFileNotInFolder());
//        FileEntity fileEntity = new FileEntity();
//        model.addAttribute("file", fileEntity);
//        return "group";
//    }

    @GetMapping
    public String indexErr(@RequestParam(name ="error",defaultValue = "" ) String error  , Model model) {
        model.addAttribute("error", error);
        model.addAttribute("folders", folderService.findAllSingleFolder());
        model.addAttribute("files", fileService.getAllFileNotInFolder());
        FileEntity fileEntity = new FileEntity();
        model.addAttribute("file", fileEntity);
        model.addAttribute("folder", new FolderEntity());
        return "group";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("data") MultipartFile file, @RequestParam(name = "folderId",defaultValue ="0" ) Long id) {
        if(file==null) {
            return "redirect:/control";
        }
        FileEntity fileEntity = new FileEntity();
        FolderEntity entity = folderService.getFolderById(id);
        if (entity != null) {
            fileEntity.setFolderEntity(entity);
        }
        try {
            fileEntity.setData(file.getBytes());
            fileEntity.setFileName(file.getOriginalFilename());
            fileEntity.setFileType(file.getContentType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileService.saveFile(fileEntity);
        return "redirect:/control";
    }

    @PostMapping("/cmd")
    public String confirmCmd(@RequestParam("cmd") String cmdLine, Model model) throws CustomException {
        String folderParentName = "";
        return cmdService.cmd(cmdLine, folderParentName);
    }

    @PostMapping("/cmdCd")
    public String confirmCmdCd(@RequestParam("cmd") String cmdLine, Model model
            , @RequestParam("folderParentId") String folderParentId) throws CustomException {
        return cmdService.cmd(cmdLine, folderParentId);
    }

    @GetMapping
    @RequestMapping("/{folderName}")
    public String indexByFolderName(@PathVariable("folderName") String folderName,@RequestParam(name ="folderId",defaultValue = "" ) Long folderId  , Model model) {
        FolderEntity folderEntity = folderService.getFolderById(folderId);
        List<FolderEntity> folderEntityByParent = new ArrayList<>();
        List<FileEntity> files = new ArrayList<>();
        if (folderEntity != null) {
            folderEntityByParent = folderService.findByFolderParentId(folderEntity.getId());
             files = fileService.findByFolderEntity(folderEntity);
        }
        else {
            folderEntity= new FolderEntity();
        }

        model.addAttribute("files", files);
        model.addAttribute("folder", folderEntity);
        model.addAttribute("folders", folderEntityByParent);
        model.addAttribute("file", new FileEntity());
        return "viewFolder";
    }
    @GetMapping("/delete/{id}")
    public String deleteFolder(@PathVariable("id") Long id) throws CustomException {
        folderService.deleteFolder(id);
        return "redirect:/control";
    } @GetMapping("/deleteFile/{id}")
    public String deleteFile(@PathVariable("id") Long id) throws CustomException {
        FileEntity fileEntity = fileService.getFile(id);
        fileService.deleteFile(fileEntity);
        return "redirect:/control";
    }
}
