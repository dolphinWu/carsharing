package com.ziroom.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressEntity extends BaseEntity {
    private Integer id;

    private String uid;

    private String nameShort;

    private String nameFull;

    private String longitude;

    private String latitude;

}