package com.shuyx.shuyxmedia.dto;

import lombok.Data;

@Data
public class MediaTagDTO {
    private Integer id;
    private Integer mediaId;
    private Integer tagId;

    public MediaTagDTO(Integer mediaId, Integer tagId) {
        this.mediaId = mediaId;
        this.tagId = tagId;
    }
}
