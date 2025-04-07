package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.dao.VideoAidDao;
import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.VideoEx;
import com.bunnyxt.tdd.service.VideoExService;
import com.bunnyxt.tdd.util.TddParamCheckUtil;
import com.bunnyxt.tdd.util.TddResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@CrossOrigin
@RestController
public class VideoExRestController {

    @Autowired
    private VideoExService videoExService;

    @Autowired
    private VideoAidDao videoAidDao;

    @RequestMapping(value = "/video/{aid}", method = RequestMethod.GET)
    public VideoEx queryVideoByAid(
            @PathVariable Long aid
    ) {
        return videoExService.queryVideoByAid(aid);
    }

    @RequestMapping(value = "/video", method = RequestMethod.GET)
    public ResponseEntity<List<VideoEx>> queryVideos(
            @RequestParam(defaultValue = "0") Integer vc,
            @RequestParam(defaultValue = "0") Integer start_ts,
            @RequestParam(defaultValue = "0") Integer end_ts,
            @RequestParam(defaultValue = "-1") Integer activity,
            @RequestParam(defaultValue = "-1") Integer recent,
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "") String up,
            @RequestParam(defaultValue = "pubdate") String order_by,
            @RequestParam(defaultValue = "1") Integer desc,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "20") Integer ps
    ) throws InvalidRequestParameterException {
        // check params
        if (vc != 0 && vc != 1) {
            throw new InvalidRequestParameterException("vc", vc, "vc should be 0 or 1");
        }
        if (activity < -1 || activity > 2) {
            throw new InvalidRequestParameterException("activity", activity, "activity should be 0 or 1 or 2");
        }
        if (recent < -1 || recent > 2) {
            throw new InvalidRequestParameterException("recent", recent, "recent should be 0 or 1 or 2");
        }
        TddParamCheckUtil.desc(desc);
        List<String> allowedOrderBy = new ArrayList<String>(){{
            add("pubdate");
            add("view");
            add("danmaku");
            add("reply");
            add("favorite");
            add("coin");
            add("share");
            add("like");
        }};
        if (!allowedOrderBy.contains(order_by)) {
            throw new InvalidRequestParameterException("order_by", order_by,
                    "only support order by " + allowedOrderBy.toString());
        }
        TddParamCheckUtil.pn(pn);
        TddParamCheckUtil.ps(ps, 20);

        return TddResponseUtil.AssembleList(
                videoExService.queryVideos(vc, start_ts, end_ts, activity, recent, title, up, order_by, desc, pn, ps),
                videoExService.queryVideosCount(vc, start_ts, end_ts, activity, recent, title, up)
        );
    }

    @RequestMapping(value = "/video/random", method = RequestMethod.GET)
    public List<VideoEx> queryVideosRandom(
            @RequestParam(defaultValue = "1") Long count
    ) throws InvalidRequestParameterException {
        // check params
        if (count <= 0 || count > 20) {
            throw new InvalidRequestParameterException("count", count, "count should between 1 and 20");
        }

        // get max id
        Long maxId = videoAidDao.queryVideoAidMaxId();

        // generate random ids
        if (count > maxId) {
            count = maxId;
        }
        List<Long> randomIds = new ArrayList<>();
        Random random = new Random();
        for (long i = 0; i < count; i++) {
            Long randomId = random.nextLong() % maxId + 1;
            if (randomId > 0 && !randomIds.contains(randomId)) {
                randomIds.add(randomId);
            } else {
                i--;
            }
        }

        // get random aids
        List<Long> randomAids = videoAidDao.queryVideoAidsByIds(randomIds);

        // get video ex list
        List<VideoEx> videoExList = new ArrayList<>();
        for (Long aid : randomAids) {
            videoExList.add(videoExService.queryVideoByAid(aid));
        }

        return videoExList;
    }

    @RequestMapping(value = "/member/{mid}/video", method = RequestMethod.GET)
    public ResponseEntity<List<VideoEx>> queryVideosByMid(
            @PathVariable Long mid,
            @RequestParam(defaultValue = "pubdate") String order_by,
            @RequestParam(defaultValue = "1") Integer desc,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "20") Integer ps
    ) throws InvalidRequestParameterException {
        // check params
        if (mid <= 0) {
            throw new InvalidRequestParameterException("mid", mid, "mid should be greater than 0");
        }
        TddParamCheckUtil.desc(desc);
        List<String> allowedOrderBy = new ArrayList<String>() {{
            add("pubdate");
            add("view");
            add("danmaku");
            add("reply");
            add("favorite");
            add("coin");
            add("share");
            add("like");
        }};
        if (!allowedOrderBy.contains(order_by)) {
            throw new InvalidRequestParameterException("order_by", order_by,
                    "only support order by " + allowedOrderBy.toString());
        }
        TddParamCheckUtil.pn(pn);
        TddParamCheckUtil.ps(ps, 20);

        return TddResponseUtil.AssembleList(
                videoExService.queryVideosByMid(mid, order_by, desc, pn, ps),
                videoExService.queryVideosByMidCount(mid)
        );
    }

}
