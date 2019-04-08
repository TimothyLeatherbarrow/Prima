package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.calculation.SimplexNoise;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.defaults.MapGenDefaultLink;

public class NoiseNode extends NodeInOut{

  @TransferGrid
  double z;

  @TransferGrid
  NoiseType type;

  VectorInputLink mapIn;
  VectorOutputLink out;

  public NoiseNode() {
    z = 0;
    type = NoiseType.SIMPLEX;

    mapIn = new MapGenDefaultLink();
    addInput(mapIn);

    out = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        switch(type){
          case WHITE:
            return new Vector2(Math.random(), Math.random());
          case SIMPLEX:
            double n = SimplexNoise.noise(mapIn.get(p), z);
            return new Vector2(n);
        }
        return Vector2.ZERO;
      }
    };
    addOutput(out);
  }

  @Override
  public String getName() {
    return "Noise Node";
  }

  @Override
  public String getDescription() {
    return "Generates different kinds of random noise";
  }
}
enum NoiseType{
  WHITE,
  SIMPLEX
}