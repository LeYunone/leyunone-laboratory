package com.leyunone.laboratory.core.bug;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-10-30
 */
public class 泛型擦除 {

    public static void main(String[] args) {
        TagDO tagDO = new TagDO();
        tagDO.setTags(Arrays.asList("tag1", "tag2"));
        TagDTO tagDTO = new TagDTO();
        BeanUtils.copyProperties(tagDO, tagDTO);
        System.out.println(tagDTO.getTags());
        System.out.println(tagDTO.getTags().get(0));
        System.out.println(tagDTO.getTags().get(0).equals(1));
    }
}

@Data
class TagDO {
    private List<String> tags;
}

@Data
class TagDTO {
    private List<Integer> tags;
}
