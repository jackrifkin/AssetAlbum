package com.dishianerifkinj;

import com.dishianerifkinj.controller.AssetAlbumCommandLineController;

public class CommandLineApplication {
    public static void main(String[] args) {
        var controller = new AssetAlbumCommandLineController();
        controller.run();
    }
}
