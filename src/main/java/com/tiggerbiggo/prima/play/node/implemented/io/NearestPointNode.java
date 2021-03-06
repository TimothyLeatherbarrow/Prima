package com.tiggerbiggo.prima.play.node.implemented.io;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.PointInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.defaults.MapGenDefaultLink;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class NearestPointNode extends NodeInOut {

  private VectorInputLink mapIn;
  private PointInputLink points;

  private VectorOutputLink out;

  public NearestPointNode(){
    mapIn = new MapGenDefaultLink("Position");
    points = new PointInputLink("Points");
    addInput(mapIn, points);

    out = new VectorOutputLink("Out") {
      @Override
      public Vector2 get(RenderParams p) {
        Vector2 map = mapIn.get(p);
        Vector2[] ps = points.get(p);

        Vector2 nearest = ps[0];
        double dist = nearest.distanceBetween(map);

        for(int i=1; i<ps.length; i++){
          double d = ps[i].distanceBetween(map);
          if(d < dist){
            dist = d;
            nearest = ps[i];
          }
        }
        return nearest;//.add(map);
      }

      @Override
      public void generateGLSLMethod(StringBuilder s) {
        //TODO
        throw new NotImplementedException();
      }

      @Override
      public String getMethodName() {
        //TODO
        throw new NotImplementedException();
      }
    };
    addOutput(out);
  }

  @Override
  public String getName() {
    return "Nearest Point Node";
  }

  @Override
  public String getDescription() {
    return "";
  }
}
