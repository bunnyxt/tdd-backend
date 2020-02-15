package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.MemberExDao;
import com.bunnyxt.tdd.dao.VideoExDao;
import com.bunnyxt.tdd.dao.VideoStaffExDao;
import com.bunnyxt.tdd.model.MemberEx;
import com.bunnyxt.tdd.model.fragment.VideoFragment;
import com.bunnyxt.tdd.model.fragment.VideoRecordFragment;
import com.bunnyxt.tdd.service.MemberExService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberExServiceImpl implements MemberExService {

    @Autowired
    MemberExDao memberExDao;

    @Autowired
    VideoExDao videoExDao;

    @Autowired
    VideoStaffExDao videoStaffExDao;

    private void extendMember(MemberEx memberEx) {
        // get videoExList
        List<VideoFragment> videoFragmentList = videoExDao.queryVideosByMidSimplified(
                memberEx.getMid(), "pubdate", 1, -1, -1, true);


        // videoCount
        memberEx.setVideoCount(videoFragmentList.size());

        // videoViewCount mostViewedVideo
        Integer videoViewCount = 0;
        Integer mostViewedVideoView = 0;
        VideoFragment mostViewedVideo = null;
        for (VideoFragment videoFragment: videoFragmentList) {
            VideoRecordFragment laststat = videoFragment.getLaststat();
            if (laststat != null) {
                Integer view = laststat.getView();
                if (view > mostViewedVideoView) {
                    mostViewedVideo = videoFragment;
                    mostViewedVideoView = view;
                }
                videoViewCount += view;
            }
        }
        memberEx.setVideoViewCount(videoViewCount);
        memberEx.setMostViewedVideo(mostViewedVideo);

        // latestIssuedVideo
        if (videoFragmentList.size() > 0) {
            memberEx.setLatestIssuedVideo(videoFragmentList.get(0));
        }
    }

    @Override
    public MemberEx queryMemberByMid(Integer mid) {

        MemberEx memberEx = memberExDao.queryMemberByMid(mid);

        extendMember(memberEx);

        return memberEx;
    }

    @Override
    public List<MemberEx> queryMembers(String sex, String name, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 20);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        List<MemberEx> memberExList = memberExDao.queryMembers(sex, name, offset, ps);

        for (MemberEx memberEx: memberExList) {
            extendMember(memberEx);
//            System.out.println(memberEx.getMid() + " extended!");
        }

        return memberExList;
    }

    @Override
    public Integer queryMembersCount(String sex, String name) {
        return memberExDao.queryMembersCount(sex, name);
    }
}
