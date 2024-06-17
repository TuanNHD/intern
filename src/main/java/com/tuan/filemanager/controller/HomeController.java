package com.tuan.filemanager.controller;

import com.tuan.filemanager.entity.FileEntity;
import com.tuan.filemanager.entity.FolderEntity;
import com.tuan.filemanager.service.FileService;
import com.tuan.filemanager.service.FolderService;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    FileService fileService;
    @Autowired
    private FolderService folderService;

    @GetMapping()
    public String index(Model model) {

        return "windowUI";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("data") MultipartFile file) {
        FileEntity fileEntity = new FileEntity();
        try {
            fileEntity.setData(file.getBytes());
            fileEntity.setFileName(file.getOriginalFilename());
            fileEntity.setFileType(file.getContentType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileService.saveFile(fileEntity);
        return "redirect:/home";
    }
//    @GetMapping("/{id}/download")
//    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
//        FileEntity fileEntity = fileService.getFile(id);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(fileEntity.getFileType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFileName() + "\"")
//                .body(new ByteArrayResource(fileEntity.getData()));
//    }

    @GetMapping("/{id}/content")
    public ResponseEntity<String> viewFileContent(@PathVariable Long id) {
        FileEntity fileEntity = fileService.getFile(id);
        StringBuilder fileContent = new StringBuilder();
        fileContent.append("<table border='1' style='border-collapse: collapse'>");
        if (fileEntity.getFileType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            try (InputStream inputStream = new ByteArrayInputStream(fileEntity.getData())) {
                Workbook workbook = new XSSFWorkbook(inputStream);
                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    int i = 0;
                    fileContent.append("<tr>");
                    for (Cell cell : row) {
                        fileContent.append("<td>");
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_STRING:
                                fileContent.append(cell.getStringCellValue());
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    fileContent.append(cell.getDateCellValue());
                                } else {
                                    fileContent.append(cell.getNumericCellValue());
                                }
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                fileContent.append(cell.getBooleanCellValue());
                                break;
                            case Cell.CELL_TYPE_FORMULA:
                                fileContent.append(cell.getCellFormula());
                                break;
                            default:
                                fileContent.delete(fileContent.length() - 5, fileContent.length() - 1);
                        }
                        fileContent.append("</td>");
                    }
                    fileContent.append("</tr>");
                }
            } catch (IOException | EncryptedDocumentException e) {
                e.printStackTrace();
            }
            fileContent.append("</table>");
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(fileContent.toString());
        }
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML)
                .body(new String(fileEntity.getData()));
    }

    @GetMapping("/{id}")
    public String viewFile(@PathVariable("id") Long id, Model model) {
        FileEntity fileEntity = fileService.getFile(id);
        model.addAttribute("file", fileEntity);
        return "view";
    }
}
