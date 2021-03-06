package com.tiggerbiggo.prima.play.node.implemented.output;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeHasOutput;
import com.tiggerbiggo.prima.play.node.link.type.NumberArrayOutputLink;
import com.tiggerbiggo.prima.view.sample.components.timeline.PrimaTimeline;
import com.tiggerbiggo.prima.view.sample.components.timeline.TimePoint;
import java.util.List;
import javafx.scene.Node;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class TimelineNode extends NodeHasOutput{
  transient PrimaTimeline timeline;

  NumberArrayOutputLink out;

  @TransferGrid
  List<TimePoint> points;

  public TimelineNode() {
    out = new NumberArrayOutputLink("Out") {
      @Override
      public Double[] get(RenderParams p) {
        Double[] toReturn = new Double[p.frameNum()];
        for (int i = 0; i < p.frameNum(); i++) {
          toReturn[i] = timeline.evaluate((double)i/p.frameNum());
        }
        return toReturn;
      }
      @Override
      public void generateGLSLMethod(StringBuilder s) {
        throw new NotImplementedException();
      }

      @Override
      public String getMethodName() {
        throw new NotImplementedException();
      }
    };
    addOutput(out);
  }

  @Override
  public String getName() {
    return "Timeline Node";
  }

  @Override
  public String getDescription() {
    return "X axis is time, Y axis is output 0 - 1.\n\nDouble click to add and remove points";
  }

  @Override
  public Node getFXNode(ChangeListener listener) {
    timeline = new PrimaTimeline(300, 100, 16, points);
    points = timeline.getPoints();
    return timeline;
  }
}
