package com.tiggerbiggo.prima.primaplay.node.implemented.output;

import com.tiggerbiggo.prima.primaplay.core.RenderParams;
import com.tiggerbiggo.prima.primaplay.node.core.NodeHasOutput;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorOutputLink;
import com.tiggerbiggo.utils.calculation.Vector2;

public class PixelMapNode extends NodeHasOutput {

  VectorOutputLink out;
  PixelMap map;

  public PixelMapNode(PixelMap _map) {
    this.map = _map;
    out = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        return map.get(p.x(), p.y());
      }
    };
    addOutput(out);
  }

  public PixelMapNode() {
    this(square(100, 100));
  }

  public static PixelMap square(int x, int y) {
    PixelMap map = new PixelMap(x, y);
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        double b = Math.max(i, j);
        map.set(i, j, new Vector2(b, b));
      }
    }
    return map;
  }

  @Override
  public String getName() {
    return "Pixel Map Node";
  }

  @Override
  public String getDescription() {
    return "Uses a Pixel Map to sample from, effectively a pre-set image.";
  }
}