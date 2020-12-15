package com.zhifa.easypoidemo.controller;

import com.zhifa.easypoidemo.entity.UserEntity;
import com.zhifa.easypoidemo.util.EasyPoiUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @date
 * @effect :
 * Excel 前端控制器
 */
@RestController
public class ExcelController {

    /**
     * 从表格插入数据
     * 接收并返回前台
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping("/uploadExcel")
    public Map<String,Object> uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
        List<UserEntity> checkingIns = EasyPoiUtils.importExcel(file, UserEntity.class);
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg","ok");
        map.put("data",checkingIns);
        return map;
    }

    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse httpServletResponse) {
        List<UserEntity> checkingIns = new ArrayList<>();
        checkingIns.add(new UserEntity(1, "涨啊的", "男", 23));
        EasyPoiUtils.exportExcel(checkingIns, "titile啊", "sheetName啊",UserEntity.class,"wenjianming.xls", httpServletResponse);

    }

    @GetMapping("/templateDownload")
    public void templateDownload(HttpServletResponse response) {
        try {
            // 获取资源中的模板文件
            ClassPathResource resource = new ClassPathResource("static\\easy.xlsx");
            InputStream inputStream = resource.getInputStream();
            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet sheet = wb.getSheetAt(0);
            Row row = sheet.getRow(1);
            row.getCell(1).setCellValue("yyyyyyyy");

            String fileName="数据导入模板";
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            //response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            if (wb.getClass().getSimpleName().equals("HSSFWorkbook")) {
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
            } else {
                response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
            }
            wb.write(response.getOutputStream());

        }catch (IOException | InvalidFormatException e){

        }
    }

}