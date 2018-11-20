/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.display;

import java.awt.image.BufferedImage;
import gioco.prova.gfx.ImageLoader;

/**
 *
 * @author Vincenzo
 */
public class Background {
    private int x;
    private BufferedImage image;

    public Background(int x, String path) {
        image = ImageLoader.loadImage(path);
        this.x = x * image.getWidth();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public BufferedImage getImage() {
        return image;
    }
}
