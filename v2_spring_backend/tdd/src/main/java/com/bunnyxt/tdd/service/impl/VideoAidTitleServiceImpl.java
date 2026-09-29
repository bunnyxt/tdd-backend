package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.VideoAidTitleDao;
import com.bunnyxt.tdd.model.AidRange;
import com.bunnyxt.tdd.model.VideoAidTitle;
import com.bunnyxt.tdd.service.VideoAidTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoAidTitleServiceImpl implements VideoAidTitleService {

    @Autowired
    VideoAidTitleDao videoAidTitleDao;

    @Override
    public List<VideoAidTitle> queryVideoAidTitle(Long aid) {
        return videoAidTitleDao.queryVideoAidTitle(prefixRanges(aid));
    }

    // aids whose decimal form starts with prefix p are exactly the union of
    // [p * 10^k, p * 10^k + 10^k - 1] for k = 0, 1, 2, ..., so the query can use
    // index range scans instead of comparing every aid as a string
    static List<AidRange> prefixRanges(long prefix) {
        List<AidRange> ranges = new ArrayList<>();
        for (long scale = 1; prefix <= Long.MAX_VALUE / scale; scale *= 10) {
            long from = prefix * scale;
            // cap the last range at Long.MAX_VALUE instead of overflowing
            long to = from > Long.MAX_VALUE - (scale - 1) ? Long.MAX_VALUE : from + (scale - 1);
            ranges.add(new AidRange(from, to));
            if (scale > Long.MAX_VALUE / 10) {
                break;
            }
        }
        return ranges;
    }
}
