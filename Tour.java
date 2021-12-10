public class Tour {

    private Node start;

    private class Node {
        private Point p;
        private Node next;

        public Node(Point p) {
            this.p = p;
            this.next = null;
        }
    }

    public Tour() {
    }

    // creates the 4-point tour a->b->c->d->a (for debugging)
    public Tour(Point a, Point b, Point c, Point d) {
        Node node1 = new Node(a);
        Node node2 = new Node(b);
        Node node3 = new Node(c);
        Node node4 = new Node(d);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node1;
        start = node1;
    }

    // returns the number of points in this tour
    public int size() {
        int count = 0;
        if (start != null) {
            Node current = start;
            do {
                count += 1;
                current = current.next;
            } while (current != start);
        }
        return count;
    }

    // returns the length of this tour
    public double length() {
        double length = 0.0;
        if (start != null) {
            Node current = start;
            do {
                length += current.p.distanceTo(current.next.p);
                current = current.next;
            } while (current != start);
        }
        return length;
    }

    // returns a string representation of this tour
    public String toString() {
        Node current = start;
        String s = "";
        for (int i = 0; i < size(); i++) {
            s = s + current.p.toString();
            if (current.next != null) {
                s = s + "\n";
            }
            current = current.next;
        }
        return s;
    }

    // draws this tour to standard drawing
    public void draw() {
        Node current = start;
        do {
            current = current.next;
            current.p.drawTo(current.next.p);
        }
        while (current != start);
    }

    // inserts p using the nearest neighbor heuristic
    public void insertNearest(Point p) {
        if (start == null) {
            start = new Node(p);
            start.next = start;
            return;
        }
        Node current = start;
        Node newNode = new Node(p);
        double min = Double.MAX_VALUE;
        Node minNode = start;
        do {
            double distance = p.distanceTo(current.p);
            if (distance < min) {
                min = distance;
                minNode = current;
            }
            current = current.next;
        }
        while (current != start);
        newNode.next = minNode.next;
        minNode.next = newNode;
    }

    // inserts p using the smallest increase heuristic
    public void insertSmallest(Point p) {
        if (start == null) {
            start = new Node(p);
            start.next = start;
            return;
        }
        Node current = start;
        Node newNode = new Node(p);
        double min = Double.MAX_VALUE;
        Node minNode = start;

        do {
            double a = current.p.distanceTo(p);
            double b = p.distanceTo(current.next.p);
            double c = current.p.distanceTo(current.next.p);
            double delta = a + b - c;
            if (delta < min) {
                min = delta;
                minNode = current;
            }
            current = current.next;
        }
        while (current != start);
        newNode.next = minNode.next;
        minNode.next = newNode;
    }

    // tests this class by calling all constructors and instance methods
    public static void main(String[] args) {

        // define 4 points that are the corners of a square
        Point a = new Point(1.0, 1.0);
        Point b = new Point(1.0, 4.0);
        Point c = new Point(4.0, 4.0);
        Point d = new Point(4.0, 1.0);

        // create the tour a -> b -> c -> d -> a
        Tour squareTour = new Tour(a, b, c, d);

        // print the size to standard output
        int size = squareTour.size();
        StdOut.println("Number of points = " + size);

        // print the tour length to standard output
        double length = squareTour.length();
        StdOut.println("Tour length = " + length);

        // print the tour to standard output
        StdOut.println(squareTour);
        squareTour.insertNearest(d);
        squareTour.insertSmallest(d);
        StdDraw.setXscale(0, 6);
        StdDraw.setYscale(0, 6);

        Point e = new Point(5.0, 6.0);
        squareTour.insertSmallest(e);
        squareTour.draw();
    }
}
