package com.ziroom.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserRelationEntity extends BaseEntity {
    private Integer id;

    private String uid1;

    private String uid2;

    private Integer friendshipScore;
    
}