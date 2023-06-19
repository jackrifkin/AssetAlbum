package com.dishianerifkinj.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public abstract class Asset {
    protected String fileName;
    protected String artistUsername;
    protected MultipartFile file;
}
