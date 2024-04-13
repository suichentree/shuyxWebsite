package com.shuyx.shuyxmedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuyx.shuyxmedia.dto.EpisodesDTO;
import com.shuyx.shuyxmedia.entity.EpisodesEntity;
import com.shuyx.shuyxmedia.entity.MediaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodesMapper extends BaseMapper<EpisodesEntity> {

    List<EpisodesDTO> pageFindEpisodesByMediaName(String mediaName);

}
