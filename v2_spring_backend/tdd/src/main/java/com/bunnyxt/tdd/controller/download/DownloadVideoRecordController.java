package com.bunnyxt.tdd.controller.download;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.VideoRecord;
import com.bunnyxt.tdd.service.VideoRecordService;
import com.bunnyxt.tdd.util.CsvExportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class DownloadVideoRecordController {

    @Autowired
    private VideoRecordService videoRecordService;

    //    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/download/record", method = RequestMethod.GET)
    public void downloadVideoRecords(HttpServletResponse response,
                                     @RequestParam(defaultValue = "0") Integer aid,
                                     @RequestParam(defaultValue = "0") Integer start_ts,
                                     @RequestParam(defaultValue = "0") Integer end_ts)
            throws InvalidRequestParameterException {
        // check params
        if (aid < 0) {
            throw new InvalidRequestParameterException("aid", aid, "aid should be greater than 0");
        }

        // get list
        List<VideoRecord> list = videoRecordService.queryVideoRecords(aid, start_ts, end_ts, false, 0, 0);

        try {
            // 构造导出数据结构
            List<String> titleList = new ArrayList<String>() {{
                add("timestamp");
                add("aid");
                add("view");
                add("danmaku");
                add("reply");
                add("favorite");
                add("coin");
                add("share");
                add("like");
            }};
            List<String> keyList = new ArrayList<String>() {{
                add("added");
                add("aid");
                add("view");
                add("danmaku");
                add("reply");
                add("favorite");
                add("coin");
                add("share");
                add("like");
            }};

            List<Map<String, Object>> dataList = new ArrayList<>();
            Map<String, Object> data = null;
            for (VideoRecord record : list) {
                data = new HashMap<>();
                data.put("added", record.getAdded());
                data.put("aid", record.getAid());
                data.put("view", record.getView());
                data.put("danmaku", record.getDanmaku());
                data.put("reply", record.getReply());
                data.put("favorite", record.getFavorite());
                data.put("coin", record.getCoin());
                data.put("share", record.getShare());
                data.put("like", record.getLike());
                dataList.add(data);
            }

            // 设置导出文件前缀
            String filename = "tdd_video_record_aid=" + aid + "&start_ts=" + start_ts + "&end_ts=" + end_ts;

            // 文件导出
            OutputStream os = response.getOutputStream();
            CsvExportUtil.responseSetProperties(filename, response);
            CsvExportUtil.doExport(dataList, titleList, keyList, os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
