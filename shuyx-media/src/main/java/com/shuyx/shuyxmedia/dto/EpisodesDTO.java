package com.shuyx.shuyxmedia.dto;

import com.shuyx.shuyxmedia.entity.EpisodesEntity;
import com.shuyx.shuyxmedia.entity.MediaEntity;
import lombok.Data;
import lombok.ToString;

/**
 * @author 86182
 * @version 1.0
 * @description: TODO
 * @date 2024/3/21 9:39
 */
@Data
@ToString
public class EpisodesDTO extends EpisodesEntity {
    private static final long serialVersionUID = 1L;
    private MediaEntity media;
}
