package com.tiggerbiggo.primaplay.node.implemented.io.iterative;

import com.tiggerbiggo.primaplay.calculation.ComplexNumber;
import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;

public class MandelNode extends IterativeNode {

  double multiplier;

  public MandelNode(int iter, double multiplier) {
    super(iter, Vector2.ONE);
    this.multiplier = multiplier;
  }

  @Override
  public Vector2 initZ(RenderParams p) {
    return Vector2.ZERO;
  }

  @Override
  public Vector2 initC(RenderParams p) {
    return in.get(p);
  }

  @Override
  public Vector2 transform(Vector2 _z, Vector2 _c) {
    ComplexNumber z, c;
    z = _z.asComplex();
    c = _c.asComplex();

    //z=z^2
    z = z.multiply(z);
    //z=z+c
    z = z.add(c);

    return z.asVector();
  }

  @Override
  public Vector2 onEscape(Vector2 in, int currentIteration) {
    System.out.println("Escape");
    double smooth = (currentIteration + 1.0) - Math.log(Math.log(in.magnitude())) / Math.log(2);

    return new Vector2(smooth * multiplier);
  }

  @Override
  public Vector2 onBound(Vector2 in, int currentIteration) {
    //System.out.println("Bound");
    return in;
  }

  @Override
  public boolean escapeCheck(Vector2 z) {
    return z.sqMagnitude() > (1 << 16);
  }


  @Override
  public String getName() {
    return "Mandel Node";
  }

  @Override
  public String getDescription() {
    return "Performs Mandelbrot iterations on given input.";
  }
}
