package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.link.InputLink;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import java.awt.Color;

public class ColorInputLink extends InputLink<Color> {

  public ColorInputLink(String desc){
    this.desc = desc;
  }

  @Override
  public boolean link(OutputLink<?> toLink) {
    if (canLink(toLink)) {
      currentLink = (ColorOutputLink) toLink;
      return true;
    }
    return false;
  }

  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof ColorOutputLink;
  }

  @Override
  public Color defaultValue(RenderParams p) {return Color.BLACK;}

  @Override
  public String getStyleClass() {
    return "ColorLink";
  }
}
