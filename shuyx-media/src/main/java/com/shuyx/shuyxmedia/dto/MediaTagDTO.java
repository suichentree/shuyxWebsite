package com.shuyx.shuyxmedia.dto;

import com.shuyx.shuyxmedia.entity.MediaTagEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class MediaTagDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer mediaId;
    private Integer tagId;

    public MediaTagDTO(){}
    public MediaTagDTO(Integer mediaId, Integer tagId) {
        this.mediaId = mediaId;
        this.tagId = tagId;
    }
}
