package com.pszymczyk.embedded.consul.infrstructure

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.PosixFilePermission

public class AntUnzip {

    @Override
    public void unzip(File zip, File destDir) {
        new AntBuilder().unzip(src: zip.absolutePath, dest: destDir.absolutePath, overwrite: 'false')
        Files.setPosixFilePermissions(Paths.get(destDir.absolutePath, 'consul'), [PosixFilePermission.OWNER_EXECUTE] as Set)
    }
}
