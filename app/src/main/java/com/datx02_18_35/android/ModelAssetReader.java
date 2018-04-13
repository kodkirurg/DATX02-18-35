package com.datx02_18_35.android;

import android.content.res.AssetManager;

import com.datx02_18_35.model.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by robin on 2018-04-13.
 */

public class ModelAssetReader {


    private final AssetManager assetManager;

    public ModelAssetReader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Map<String, String> read() throws IOException {
        return read(Config.ASSET_PATH_MODEL);
    }

    private Map<String, String> read(String path) throws IOException {
        String[] assets = assetManager.list(path);
        if (assets.length == 0) {
            // File
            InputStream in;
            try {
                in = assetManager.open(path);
            } catch (IOException e){
                // Not a file, but possibly empty directory, ignore
                return new HashMap<>();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder builder = new StringBuilder();
            String line;
            while (null != (line = reader.readLine())) {
                builder.append(line).append("\n");
            }
            Map<String, String> fileContent = new HashMap<>();
            fileContent.put(path, builder.toString());
            return fileContent;
        }
        else {
            //Directory
            Map<String, String> files = new HashMap<>();
            for (String asset : assets) {
                try {
                    files.putAll(read(path + "/" + asset));
                } catch (IOException e) {
                    // Print error and ignore asset
                    e.printStackTrace();
                }
            }
            return files;
        }
    }


}
