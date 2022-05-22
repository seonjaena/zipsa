package com.project.zipsa.config;

import lombok.Getter;

@Getter
public enum LOG_FLAG {

    GET_NOTICE("notice.get"),
    CREATE_NOTICE("notice.create"),
    DELETE_NOTICE("notice.delete"),
    MODIFY_NOTICE("notice.modify"),
    LOGIN("login"),
    GET_BOARD("board.get"),
    CREATE_BOARD("board.create"),
    MODIFY_BOARD("board.modify"),
    DELETE_BOARD("board.delete"),
    JOIN("join"),
    DOWNLOAD_FILE("file.download"),
    UPLOAD_FILE("file.upload"),
    DELETE_FILE("file.delete")
    ;
    
    private final String value;

    LOG_FLAG(String value) {
        this.value = value;
    }

}
