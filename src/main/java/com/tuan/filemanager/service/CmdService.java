package com.tuan.filemanager.service;

import com.tuan.filemanager.entity.FolderEntity;
import com.tuan.filemanager.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
public class CmdService {
    @Autowired
    private FolderService folderService;

    public String cmd(String cmdLine, String folderParentIdOrFolderIdRename) throws CustomException {
        String[] cmdSplitArray = null;

        if (StringUtils.isEmpty(cmdLine)) {
            throw new CustomException("syntax");
        }
        cmdSplitArray = cmdLine.split(" ");
        if (StringUtils.isEmpty(cmdSplitArray[1])) {
            throw new CustomException("syntax");
        }

        if (StringUtils.equalsIgnoreCase(cmdSplitArray[0], "home")) {
            return "redirect:/control";
        }
        if (StringUtils.equalsIgnoreCase(cmdSplitArray[0], "mkdir")) {
            return mkdir(cmdSplitArray, folderParentIdOrFolderIdRename);
        }
        if (StringUtils.equalsIgnoreCase(cmdSplitArray[0], "cd") && !StringUtils.isEmpty(cmdSplitArray[1])) {
            return cd(cmdSplitArray, folderParentIdOrFolderIdRename);
        }
        if (StringUtils.equalsIgnoreCase(cmdSplitArray[0], "rn")) {
            try {
                Long a = Long.parseLong(cmdSplitArray[1]);
                return rename(cmdSplitArray);
            } catch (NumberFormatException e) {
                if (folderParentIdOrFolderIdRename != null) {
                    FolderEntity folderEntity = folderService.findChildFolderByParentFolderId(cmdSplitArray[1], Long.parseLong(folderParentIdOrFolderIdRename));
                    if (folderEntity == null) {
                        throw new CustomException("notfound");
                    }
                    cmdSplitArray[1] = folderEntity.getId().toString();
                    return rename(cmdSplitArray);
                }
                FolderEntity folderEntity = folderService.findSingleFolderByFolderName(cmdSplitArray[1]);
                if (folderEntity == null) {
                    throw new CustomException("notfound");
                }
                cmdSplitArray[1] = folderEntity.getId().toString();
                return rename(cmdSplitArray);
            }
        }


        throw new CustomException("access");
    }

    private String rename(String[] cmdSplitArray) throws CustomException {
        int i = 0;
        if (cmdSplitArray.length > 3 || cmdSplitArray.length <= 2) {
            throw new CustomException("syntax");
        }
        if (StringUtils.isEmpty(cmdSplitArray[1]) || StringUtils.isEmpty(cmdSplitArray[2])) {
            throw new CustomException("syntax");
        }
        FolderEntity folderEntity = folderService.getFolderById(Long.parseLong(cmdSplitArray[1]));
        if (folderEntity == null) {
            throw new CustomException("notfound");
        }
        if (folderEntity.getParentFolder() != null) {
            cmdSplitArray[1] = cmdSplitArray[2];
            folderEntity.setFolderName(mkdirHadParentFolder(cmdSplitArray, i, folderEntity.getParentFolder()));
            folderService.createFolder(folderEntity);
            return "redirect:/control/" + folderEntity.getParentFolder().getFolderName() + "?folderId=" + folderEntity.getParentFolder().getId();
        }
        cmdSplitArray[1] = cmdSplitArray[2];
        folderEntity.setFolderName(mkdirNonParentFolder(cmdSplitArray, i));
        folderService.createFolder(folderEntity);
        return "redirect:/control";
    }

    private String cd(String[] cmdSplitArray, String folderParentIdOrFolderIdRename) throws CustomException {
        if (cmdSplitArray.length > 2 || cmdSplitArray.length <= 1) {
            throw new CustomException("syntax");
        }
        FolderEntity folderSingleEntityByCd = folderService.findSingleFolderByFolderName(cmdSplitArray[1]);
        if (StringUtils.isEmpty(folderParentIdOrFolderIdRename) && folderSingleEntityByCd != null) {
            return "redirect:/control/" + cmdSplitArray[1] + "?folderId=" + folderSingleEntityByCd.getId();
        }
        if (!StringUtils.isEmpty(folderParentIdOrFolderIdRename)) {
            FolderEntity folderEntityByParent = folderService.getFolderById(Long.parseLong(folderParentIdOrFolderIdRename));
            FolderEntity folderEntityByCd = folderService.findChildFolderByParentFolderId(cmdSplitArray[1], folderEntityByParent.getId());
            if (folderSingleEntityByCd != null) {
                throw new CustomException("parent");
            }
            if (folderEntityByParent != null) {
                List<FolderEntity> folderEntityByParentList = folderService.findByFolderParentId(folderEntityByParent.getId());
                if (folderEntityByParentList.contains(folderEntityByCd)) {
                    return "redirect:/control/" + cmdSplitArray[1] + "?folderId=" + folderEntityByCd.getId();
                } else {
                    throw new CustomException("notfound");
                }
            }
        }
        throw new CustomException("access");
    }

    private String mkdir(String[] cmdSplitArray, String folderParentIdOrFolderIdRename) throws CustomException {
        if (cmdSplitArray.length > 2 || cmdSplitArray.length <= 1) {
            throw new CustomException("syntax");
        }
        int i = 0;
        if (!StringUtils.isEmpty(folderParentIdOrFolderIdRename)) {
            FolderEntity folderEntityByParent = folderService.getFolderById(Long.parseLong(folderParentIdOrFolderIdRename));
            if (folderEntityByParent != null) {
                FolderEntity folderEntity = new FolderEntity();
                folderEntity.setFolderName(mkdirHadParentFolder(cmdSplitArray, i, folderEntityByParent));
                folderEntity.setParentFolder(folderEntityByParent);
                folderService.createFolder(folderEntity);
                return "redirect:/control/" + folderEntityByParent.getFolderName() + "?folderId=" + folderEntityByParent.getId();
            }
        }
        FolderEntity folderEntity = new FolderEntity();
        folderEntity.setFolderName(mkdirNonParentFolder(cmdSplitArray, i));
        folderService.createFolder(folderEntity);
        return "redirect:/control";
    }

    public String mkdirNonParentFolder(String[] cmdSplitArray, int i) {
        while (folderService.findSingleFolderByFolderName(cmdSplitArray[1]) != null) {
            i++;
            if (i > 1) {
                char oldChar = (char) (i - 1 + '0');
                char newChar = (char) ((i) + '0');
                cmdSplitArray[1] = cmdSplitArray[1].replace(oldChar, newChar);

            } else
                cmdSplitArray[1] = cmdSplitArray[1] + "(" + i + ")";
        }

        return cmdSplitArray[1];
    }

    public String mkdirHadParentFolder(String[] cmdSplitArray, int i, FolderEntity folderEntity) {
        boolean action = true;
        while (action) {
            FolderEntity entity = folderService.findChildFolderByParentFolderId(cmdSplitArray[1], folderEntity.getId());
//            List<FolderEntity> folderEntityByParent = folderService.findByfolderParentIdOrFolderIdRename(folderEntity.getId());
            if (entity == null) {
                i++;
                action = false;
            } else {
                i++;
                if (i > 1) {
                    char oldChar = (char) (i - 1 + '0');
                    char newChar = (char) ((i) + '0');
                    cmdSplitArray[1] = cmdSplitArray[1].replace(oldChar, newChar);

                } else {
                    cmdSplitArray[1] = cmdSplitArray[1] + "(" + i + ")";
                }
            }
        }

        return cmdSplitArray[1];
    }

    public boolean checkFogJump(String folderParentName) {
        if (StringUtils.isEmpty(folderParentName)) {
            return false;
        }
        return true;
    }

}
