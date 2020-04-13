package utility;

public class iPolyElement {
    iPoint2D top;
    iEdge2D edge;
    boolean left;

    public iPolyElement() {
    }

    public iPolyElement(iPoint2D _top) {
        top = _top;
        edge = new iEdge2D();
    }

    public iPolyElement(iPoint2D _top, iEdge2D _edge, boolean _left) {
        top = _top;
        edge = _edge;
        left = _left;
    }

    public iPolyElement copy() {
        iPoint2D _top = new iPoint2D(top.x, top.y);
        iEdge2D _edge = new iEdge2D(edge.a, edge.b, edge.c);
        return new iPolyElement(_top, _edge, left);
    }

    public iPoint2D getTop() {
        return top;
    }

    public void setTop(iPoint2D top) {
        this.top = top;
    }

    public iEdge2D getEdge() {
        return edge;
    }

    public void setEdge(iEdge2D edge) {
        this.edge = edge;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    @Override
    public String toString() {
        return "" + top + " " + edge + " " + left;
    }
}