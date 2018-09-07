package gnode;

import com.tiggerbiggo.utils.calculation.Vector2;
import com.tiggerbiggo.prima.primaplay.node.link.OutputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.NumberArrayOutputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorArrayOutputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorOutputLink;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GOutputLink extends GLink {

  OutputLink<?> link;

  List<GLinkLine> lineList;

  private Vector2 position;
  private GUINode owner;
  private int index;

  private final Pane parent;

  public GOutputLink(OutputLink<?> in, GUINode owner, int index, Vector2 position, Pane parent) {
    link = in;
    setRelativeOffset(position);
    lineList = new ArrayList<>();

    this.parent = parent;
    this.owner = owner;
    this.index = index;

    if (link instanceof ColorArrayOutputLink) {
      setFill(Color.YELLOW);
    } else if (link instanceof NumberArrayOutputLink) {
      setFill(Color.GREY);
    } else if (link instanceof VectorArrayOutputLink) {
      setFill(Color.BLUE);
    } else if (link instanceof VectorOutputLink) {
      setFill(Color.AQUA);
    }

    setOnDragDropped(event -> {
      GInputLink inputLink;
      if (event.getGestureSource() instanceof GInputLink) {
        inputLink = (GInputLink) event.getGestureSource();
        if (inputLink.link(this)) {
          //success, create line
          this.parent.getChildren().add(new GLinkLine(inputLink, this, this.parent));
        }
      }
      updatePosition();
      updateLinePos();
    });
  }

  @Override
  public void unlink() {
    while (lineList.size() >= 1) {
      lineList.get(0).delete();
    }
  }

  @Override
  public void updatePosition(Vector2 offset) {
    super.updatePosition(offset);

  }

  public void updateLinePos() {
    lineList.forEach(GLinkLine::updatePositions);
  }

  public OutputLink<?> getLink() {
    return link;
  }

  public void addLine(GLinkLine line) {
    lineList.add(line);
  }

  public void forgetLine(GLinkLine toForget) {
    toForget = Objects.requireNonNull(toForget);
    lineList.remove(toForget);
  }

  public void deleteAllLines() {
    while (lineList.size() > 0) {
      GLinkLine line = lineList.get(0);
      line.delete();
    }
  }

  public GUINode getOwner() {
    return owner;
  }

  public int getIndex() {
    return index;
  }
}
