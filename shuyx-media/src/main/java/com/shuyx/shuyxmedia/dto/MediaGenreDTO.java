package com.shuyx.shuyxmedia.dto;

import lombok.Data;

@Data
public class MediaGenreDTO {
    private Integer id;
    private Integer mediaId;
    private Integer genreId;

    public MediaGenreDTO(Integer mediaId, Integer genreId) {
        this.mediaId = mediaId;
        this.genreId = genreId;
    }
}
