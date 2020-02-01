package com.bunnyxt.tdd.util;

import org.apache.commons.collections.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

// ref: https://blog.csdn.net/fengzyf/article/details/86554106
public class CsvExportUtil {

    private static final String CSV_COLUMN_SEPARATOR = ",";
    private static final String CSV_ROW_SEPARATOR = System.lineSeparator();

    public static void doExport(List<Map<String, Object>> dataList,
                                List<String> titleList,
                                List<String> keyList,
                                OutputStream os) throws Exception {
        // 保证线程安全
        StringBuffer stringBuffer = new StringBuffer();

        // 组装表头
        for (String title : titleList) {
            stringBuffer.append(title).append(CSV_COLUMN_SEPARATOR);
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1); // delete extra comma
        stringBuffer.append(CSV_ROW_SEPARATOR);

        // 组装数据
        if (CollectionUtils.isNotEmpty(dataList)) {
            for (Map<String, Object> data : dataList) {
                for (String key : keyList) {
                    stringBuffer.append(data.get(key)).append(CSV_COLUMN_SEPARATOR);
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1); // delete extra comma
                stringBuffer.append(CSV_ROW_SEPARATOR);
            }
        }

        // 写出响应
        os.write(stringBuffer.toString().getBytes("GBK")); // why GBK?
        os.flush();
    }


    public static void responseSetProperties(String filename, HttpServletResponse response) throws UnsupportedEncodingException {
        // 设置文件后缀
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fn = filename + "_" + sdf.format(new Date()) + ".csv";
        // 读取字符编码
        String enc = "UTF-8";

        // 设置响应
        response.setContentType("application/ms-txt.numberformat:@");
        response.setCharacterEncoding(enc);
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "max-age=30");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fn, enc));
    }

}