package com.zl.springbootexcelimport.controller;

import com.zl.springbootexcelimport.entity.UserData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
public class HelloController {


    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
    @ResponseBody
    @RequestMapping("/accountImport")
    public String accountImport(@RequestParam("file") MultipartFile file){
        ArrayList<UserData> users = new ArrayList<>();
        LinkedHashMap<String, UserData> maps = new LinkedHashMap<>();
        if ( file == null){
            return "文件不能为空";
        }
        String filename = file.getOriginalFilename();
        Workbook workbook = null;
        try {
            if (filename.endsWith(".xls")){
                workbook = new HSSFWorkbook(file.getInputStream());
            }else if(filename.endsWith(".xlsx")){
                workbook = new XSSFWorkbook(file.getInputStream());
            }
        } catch (IOException e) {
            return "文件后缀不是.xls或者.xlsx";
        }
        if (workbook == null){
            return ("创建workbook失败");
        }else{
            int number = workbook.getNumberOfSheets();
            for(int i = 0;i < number ;i++){
                Sheet sheet = workbook.getSheetAt(i);
                int lastRowNum = sheet.getLastRowNum();
                int j =0 ;
                int usernameId=-1;
                int passwordId = -1;
                if(j<lastRowNum){
                    Row row = sheet.getRow(j);
                    short column = row.getLastCellNum();
                    for(int z = 0 ;z<column;z++) {
                        if (row.getCell(z) != null) {
                            String value = row.getCell(z).getStringCellValue();
                            System.out.print(value+" ");

                            if(value.equalsIgnoreCase("username")){
                                usernameId=z;
                            }else if(value.equalsIgnoreCase("PASSWORD")){
                                passwordId = z;
                            }
                        }
                    }
                    j++;
                    while(j<= lastRowNum){
                        UserData hwData = new UserData();
                        row = sheet.getRow(j);

                        if(row == null){
                            j++;
                            continue;
                        }
                        if(usernameId==-1&&passwordId==-1){
                            return "字段错误";
                        }
                        if(row.getCell(usernameId) != null){
                            row.getCell(usernameId).setCellType(Cell.CELL_TYPE_STRING);
                            String value = row.getCell(usernameId).getStringCellValue();
                            hwData.setName(value);
                            System.out.println(value);
                        }
                        if(row.getCell(passwordId) != null){
                            row.getCell(passwordId).setCellType(Cell.CELL_TYPE_STRING);
                            String value = row.getCell(passwordId).getStringCellValue();
                            hwData.setPassword(value);
                            System.out.println(value);
                        }
                        j++;
                        maps.put(hwData.getName(),hwData);
                    }
                }
            }
        }
        Collection<UserData> keys = maps.values();
        return "success";
    }
}
