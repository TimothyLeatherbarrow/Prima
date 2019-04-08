package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.link.InputLink;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import java.awt.Color;

public class UniversalInputLink extends InputLink<Object> {
  OutputLink<?> link;

  @Override
  public boolean link(OutputLink<?> toLink) {
    link = toLink;
    return true;
  }

  @Override
  public Object defaultValue(RenderParams p) {
    return null;
  }

  @Override
  public boolean canLink(Link other) {
    return other instanceof OutputLink;
  }

  @Override
  public String getStyleClass() {
    return "UniversalLink";
  }

  public Color getColor(RenderParams p){return link.getColor(p);}

  public String getDetail(RenderParams p){
    return link.describeValue(p, 0);
  }
}