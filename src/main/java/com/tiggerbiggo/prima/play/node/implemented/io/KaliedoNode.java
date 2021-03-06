package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorOutputLink;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class KaliedoNode extends NodeInOut {

  @TransferGrid
  Vector2 rotationPoint;

  @TransferGrid
  int rotationNum;

  VectorInputLink map;
  VectorOutputLink out;

  public KaliedoNode(Vector2 _rotationPoint, int _rotationNum) {
    rotationPoint = _rotationPoint;
    rotationNum = _rotationNum;

    map = new VectorInputLink("Position Input");
    addInput(map);

    out = new VectorOutputLink("Out") {
      @Override
      public Vector2 get(RenderParams p) {
        Vector2 point;
        double baseAngle, angle;
        int multiplier;

        point = map.get(p).add(rotationPoint);

        baseAngle = Math.PI * 2;
        baseAngle /= rotationNum;

        angle = Vector2.RIGHT.angleBetween(point);
        multiplier = (int) (angle / baseAngle);

        angle = baseAngle * -multiplier;
        if (multiplier % 2 == 0) {
          //angle = baseAngle - angle;
        }

        point = point.rotateAround(rotationPoint, angle);
        point = point.subtract(rotationPoint);

        return point;
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

  public KaliedoNode(int rotationNum) {
    this(Vector2.ZERO, rotationNum);
  }

  public KaliedoNode(){this(6); }

  @Override
  public String getName() {
    return "Kaliedo Node";
  }

  @Override
  public String getDescription() {
    return "Effectively performs a modulo operation on the rotation value of a given coordinate, creating a kaliedoscope effect.";
  }
}
