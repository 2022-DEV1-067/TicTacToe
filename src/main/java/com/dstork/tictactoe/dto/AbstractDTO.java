package com.dstork.tictactoe.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class AbstractDTO {

    private Long id;
    private Long version;
    private Date createDate;
    private Date lastUpdateDate;

}
