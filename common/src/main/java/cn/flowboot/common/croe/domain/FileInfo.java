package cn.flowboot.common.croe.domain;

import lombok.Builder;
import lombok.Data;

import java.nio.file.attribute.FileTime;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/12/28
 */
@Data
@Builder
public class FileInfo {

    private String fileName;

    private String fileTime;
}
