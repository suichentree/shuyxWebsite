package com.shuyx.shuyxmedia.dto;

import com.shuyx.shuyxmedia.entity.EpisodesEntity;
import com.shuyx.shuyxmedia.entity.MediaTagEntity;
import com.shuyx.shuyxmedia.entity.TagEntity;
import com.shuyx.shuyxmedia.entity.MediaEntity;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class MediaDTO extends MediaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    //当前页数
    private Integer pageNum;
    //每页的记录数
    private Integer pageSize;
    //媒体标签数组
    private Integer[] tagIds;
    //媒体对应的类型
    private List<TagEntity> tagList;
    //媒体对应的剧集列表
    private List<EpisodesEntity> episodesList;

}
