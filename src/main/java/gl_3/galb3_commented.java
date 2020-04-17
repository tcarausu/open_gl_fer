package gl_3;

public class galb3_commented {

//    private void checkIfPolygonIsConvex(ArrayList<iPolyElement> polyElements,
//                                        int n, GL2 gl2) {
//        int i, i0, r;
//        int above, below, at;
//        below = above = at = 0;
//        i0 = n - 2;
//        for (i = 0; i < n; i++, i0++) {
//            if (i0 >= n) i0 = 0;
//            r = (int) (polyElements.get(i0).getEdge().getA() * polyElements.get(i).getTop().getX()
//                    + polyElements.get(i0).getEdge().getB() * polyElements.get(i).getTop().getY()
//                    + polyElements.get(i0).getEdge().getC());
//            if (r == 0) at++;
//            else if (r > 0) above++;
//            else below++;
//        }
//
//        isConvex = false;
//        if (below == 0) {
//            isConvex = true;
//            isClockwise = false;
//        } else if (above == 0) {
//            isConvex = true;
//            isClockwise = true;
//        }
//        correctOrientation();
//    }
//
//    public void correctOrientation() {
//        if (iPolyElements.size() < 2)
//            return;
//        int i0 = iPolyElements.size() - 1;
//        for (int i = 0; i < iPolyElements.size(); i++) {
//            iPolyElement elemi0 = iPolyElements.get(i0);
//            iPolyElement poleli = iPolyElements.get(i);
//
//            if (isClockwise)
//                elemi0.setLeft(elemi0.getTop().getY() < poleli.getTop().getY());
//            else
//                elemi0.setLeft(elemi0.getTop().getY() > poleli.getTop().getY());
//            i0 = i;
//        }
//    }

//    private void dynamicCalcCoef(ArrayList<iPolyElement> polyElements,GL2 gl2){
//        if (polyElements.size() < 1)
//            return;
//
//        checkIfPolygonIsConvex(polyElements,polyElements.size(),gl2);
//
//        iPolyElement first = polyElements.get(0);
//        dynamicpolel.clear();
//        for (int i=0; i < polyElements.size(); i++)
//            dynamicpolel.add(polyElements.get(i).copy());
//        dynamicmouse = new iPolyElement(new iPoint2D((int) mouseX, (int) mouseY));
//        dynamiclast = dynamicpolel.get(dynamicpolel.size()-1);
//        dynamicpolel.add(dynamicmouse);
//
//
//        dynamiclast.brid.a = dynamiclast.vrh.y - dynamicmouse.vrh.y;
//        dynamiclast.brid.b = -(dynamiclast.vrh.x - dynamicmouse.vrh.x);
//        dynamiclast.brid.c = dynamiclast.vrh.x * dynamicmouse.vrh.y
//                - dynamiclast.vrh.y * dynamicmouse.vrh.x;
//        if (isClockwise)
//            dynamiclast.lijevi = dynamiclast.vrh.y < dynamicmouse.vrh.y;
//        else
//            dynamiclast.lijevi = dynamiclast.vrh.y > dynamicmouse.vrh.y;
//
//        dynamicmouse.brid.a = dynamicmouse.vrh.y - first.vrh.y;
//        dynamicmouse.brid.b = -(dynamicmouse.vrh.x - first.vrh.x);
//        dynamicmouse.brid.c = dynamicmouse.vrh.x * first.vrh.y
//                - dynamicmouse.vrh.y * first.vrh.x;
//        if (isClockwise)
//            dynamicmouse.lijevi = dynamicmouse.vrh.y < first.vrh.y;
//        else
//            dynamicmouse.lijevi = dynamicmouse.vrh.y > first.vrh.y;    }

}
