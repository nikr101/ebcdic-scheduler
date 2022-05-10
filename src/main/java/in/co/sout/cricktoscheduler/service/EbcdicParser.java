/*
 * EBCDIC to ASCII converter
 * Copyright (C) 2012 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package in.co.sout.cricktoscheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public final class EbcdicParser {


    private static final int FIXED_LENGTH_DEFAULT = -1;

    private int fixedLength = FIXED_LENGTH_DEFAULT;

    @Value("${source.dir}")
    private File source;

    @Value("${destination.dir}")
    private File destination;

    @Autowired
    private FileConverter converter;

    public void convert() {
        try {
            converter.setFixedLength(fixedLength);

            List<String> files = listFiles(source);
            for (String s : files) {
                File sourceFile = new File(source, s);
                File destFile = new File(destination, s);
                log("Converting " + sourceFile + " into " + destFile);
                destFile.getParentFile().mkdirs();
                converter.convert(sourceFile, destFile);
            }
            log("SUCCESS");
        } catch (EbcdicToAsciiConverterException e) {
            log("Unable to convert files", e);
            log("FAILURE");
        }
    }

    private static List<String> listFiles(File dir) {
        List<String> files = new ArrayList<String>();
        recursivelyListFiles(dir, "", files);
        return files;
    }

    private static void recursivelyListFiles(File dir, String relativePath, List<String> files) {
        for (String s : dir.list()) {
            String path = relativePath + File.separator + s;
            File file = new File(dir, s);
            if (file.isFile() && !file.isHidden()) {
                files.add(path);
            } else if (file.isDirectory() && !file.isHidden()) {
                recursivelyListFiles(file, path, files);
            }
        }
    }

    private static void log(String message) {
        System.out.println(message);
    }

    private static void log(String message, Throwable e) {
        System.out.println(message);
        e.printStackTrace();
    }


}
