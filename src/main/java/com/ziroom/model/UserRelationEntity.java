package com.ziroom.model;

public class UserRelationEntity extends BaseEntity {
    private Integer id;

    private String uid1;

    private String uid2;

    private Integer friendshipScore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid1() {
        return uid1;
    }

    public void setUid1(String uid1) {
        this.uid1 = uid1 == null ? null : uid1.trim();
    }

    public String getUid2() {
        return uid2;
    }

    public void setUid2(String uid2) {
        this.uid2 = uid2 == null ? null : uid2.trim();
    }

    public Integer getFriendshipScore() {
        return friendshipScore;
    }

    public void setFriendshipScore(Integer friendshipScore) {
        this.friendshipScore = friendshipScore;
    }
}