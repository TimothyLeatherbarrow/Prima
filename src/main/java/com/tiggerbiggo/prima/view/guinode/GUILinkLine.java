package com.tiggerbiggo.prima.view.guinode;

import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.shape.CubicCurve;

public class GUILinkLine extends CubicCurve {

  GUIInputLink input;
  GUIOutputLink output;

  private final double AMNT = 50;

  public GUILinkLine(GUIInputLink _input, GUIOutputLink _output) {
    input = Objects.requireNonNull(_input);
    output = Objects.requireNonNull(_output);

    output.addLine(this);
    input.setLine(this);


    getStyleClass().add("LinkLine");

    setManaged(false);
    setMouseTransparent(true);

    doBindings();
  }

  private void doBindings(){
    DoubleBinding inputXBinding, inputYBinding, outputXBinding, outputYBinding;
    outputXBinding = Bindings.createDoubleBinding(
        () -> sceneToLocal(
            output.localToScene(
                output.getCenterX(),
                output.getCenterY()
            )
        ).getX() + output.getRadius(),
        output.getOwner().layoutXProperty(),
        input.getOwner().layoutXProperty(),
        output.centerXProperty());

    outputYBinding = Bindings.createDoubleBinding(
        () -> sceneToLocal(
            output.localToScene(
                output.getCenterX(),
                output.getCenterY()
            )
        ).getY(),
        output.getOwner().layoutYProperty(),
        input.getOwner().layoutYProperty(),
        output.centerXProperty());

    inputXBinding = Bindings.createDoubleBinding(
        () -> {
          return sceneToLocal(
              input.localToScene(
                  input.getCenterX(),
                  input.getCenterY()
              )
          ).getX() - input.getRadius();
        },
        input.getOwner().layoutXProperty(),
        output.getOwner().layoutXProperty(),
        output.centerXProperty());

    inputYBinding = Bindings.createDoubleBinding(
        () -> {
          return sceneToLocal(
              input.localToScene(
                  input.getCenterX(),
                  input.getCenterY()
              )
          ).getY();
        },
        input.getOwner().layoutYProperty(),
        output.getOwner().layoutYProperty(),
        output.centerXProperty());

    startXProperty().bind(inputXBinding);
    startYProperty().bind(inputYBinding);
    endXProperty().bind(outputXBinding);
    endYProperty().bind(outputYBinding);

    controlX1Property().bind(startXProperty().subtract(AMNT));
    controlY1Property().bind(startYProperty());
    controlX2Property().bind(endXProperty().add(AMNT));
    controlY2Property().bind(endYProperty());

    toFront();
  }

  //commit sudoku
  public void delete() {
    input.unlink();
    output.forgetLine(this);
  }
}
