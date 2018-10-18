package com.tiggerbiggo.prima.primaplay.node.link.type;

import com.tiggerbiggo.prima.primaplay.node.link.Link;
import com.tiggerbiggo.prima.primaplay.node.link.OutputLink;
import java.awt.Color;

public abstract class ColorOutputLink extends OutputLink<Color> {
  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof ColorInputLink;
  }

  @Override
  public String getStyleClass() {
    return "ColorLink";
  }
}