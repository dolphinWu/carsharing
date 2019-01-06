package com.ziroom.dto.request;

import com.ziroom.model.AddressEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressRequest  extends AddressEntity {
    private String uid;
}
