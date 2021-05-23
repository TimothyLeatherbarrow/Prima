package com.tiggerbiggo.prima.play.graphics;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.calculation.Calculation;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * A wrapper for a BufferedImage object, which allows safe calls to get and set color methods.
 */
public class SafeImage implements Serializable {

  private transient BufferedImage img;
  @TransferGrid
  private int width, height;

  /**
   * Constructs a new SafeImage with an existing BufferedImage object
   *
   * @param img The image to import
   * @throws IllegalArgumentException if the <b>img</b> parameter is null
   */
  public SafeImage(BufferedImage img) throws IllegalArgumentException {
    if (img == null) {
      throw new IllegalArgumentException();
    }

    this.img = img;
    this.width = img.getWidth();
    this.height = img.getHeight();
  }

  /**
   * Constructs a new blank SafeImage with the width and height provided
   *
   * @param width The width of the image
   * @param height The height of the image
   * @throws IllegalArgumentException if width or height &lt;= 0
   */
  public SafeImage(int width, int height) throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException();
    }

    img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    this.width = width;
    this.height = height;
  }

  public Vector2 sizeAsVector() {
    return new Vector2(width, height);
  }

  public Vector2 denormVector(Vector2 in) {
    return in.multiply(sizeAsVector());
  }

  public Vector2 denormVector(float x, float y){
    return denormVector(new Vector2(x, y));
  }

  public Vector2 denormVectorAndLoop(Vector2 in, boolean xLoop, boolean yLoop){
    in = new Vector2(
        Calculation.modLoop(in.X(), xLoop),
        Calculation.modLoop(in.Y(), yLoop)
    );
    return denormVector(in);
  }

  public Color getColorInterp(Vector2 in){
    Vector2 floor = new Vector2(in.iX(), in.iY()); //intentionally truncate
    Vector2 remainder = in.subtract(floor);
    Color[] cols = new Color[4];
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        cols[j+(2*i)] = getColor(floor.add(new Vector2(j, i)));
      }
    }
    return null;
  }

  /**
   * Gets the color of the pixel at the position given by the passed Vector
   *
   * @param in the vector representing the position to com.tiggerbiggo.prima.view.sample Color from the image
   * @return The Color sampled from (in.X, in.Y)
   */
  public Color getColor(Vector2 in) {
    return getColor(in.iX(), in.iY());
  }

  /**
   * Gets the color of the pixel at the position given by the X and Y coordinates specified
   *
   * @param x The X position to com.tiggerbiggo.prima.view.sample
   * @param y The Y position to com.tiggerbiggo.prima.view.sample
   * @return The Color sampled from (X, Y)
   */
  public Color getColor(int x, int y) {
    return new Color(getRGB(x, y));
  }

  /**
   * Gets the RGB Value of the pixel at the position given by the passed Vector
   *
   * @param in the vector representing the position to com.tiggerbiggo.prima.view.sample RGB from the image
   * @return The RGB Value sampled from (in.X, in.Y)
   */
  public int getRGB(Vector2 in) {
    return getRGB(in.iX(), in.iY());
  }

  /**
   * Gets the RGB Value of the pixel at the position given by the X and Y coordinates specified
   *
   * @param x The X position to com.tiggerbiggo.prima.view.sample
   * @param y The Y position to com.tiggerbiggo.prima.view.sample
   * @return The RGB Value sampled from (X, Y)
   */
  public int getRGB(int x, int y) {
    x = safeX(x);
    y = safeY(y);

    return img.getRGB(x, y);
  }

  public void setColor(int x, int y, Color c){
    x = safeX(x);
    y = safeY(y);

    img.setRGB(x, y, c.getRGB());
  }

  public void fillColor(Color c){
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        setColor(i,j,c);
      }
    }
  }

  public int safeX(int x){
    x = x % width;
    x = Math.abs(x);
    return x;
  }

  public int safeY(int y){
    y = y % height;
    y = Math.abs(y);
    return y;
  }

  public int getWidth() {
    return img.getWidth();
  }

  public int getHeight() {
    return img.getHeight();
  }

  public BufferedImage getImg() {
    return img;
  }

  public Vector2 getDimensions() {
    return new Vector2(getWidth(), getHeight());
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();
    int[] outArray = new int[width * height];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        outArray[i + (j * width)] = img.getRGB(i, j);
      }
    }
    out.writeObject(outArray);
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();

    if(width <=0 || height <= 0){
      width = 1;
      height = 1;
    }

    img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    try {
      int[] inArray = (int[]) in.readObject();

      for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
          img.setRGB(i, j, inArray[i + (j * width)]);
        }
      }
    } catch (IOException | ClassNotFoundException e) {
      img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }
  }

  public SafeImage clone(){
    SafeImage toReturn = new SafeImage(width, height);
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        toReturn.img.setRGB(i,j,img.getRGB(i,j));
      }
    }
    return toReturn;
  }
}
