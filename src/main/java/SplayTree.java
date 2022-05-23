import java.util.*;

public class SplayTree<T extends Comparable<T>> implements Set<T> {

    private Node root;

    private class Node {
        private T value;
        private Node left, right;

        public Node (T value) {

            this.value = value;
        }
    }

    public void put( T value) {
        if (root == null) {
            root = new Node(value);
            return;
        }

        root = splay(root, value);

        int cmp = value.compareTo(root.value);

        if (cmp < 0) {
            Node n = new Node( value);
            n.left = root.left;
            n.right = root;
            root.left = null;
            root = n;
        }

        else if (cmp > 0) {
            Node n = new Node( value);
            n.right = root.right;
            n.left = root;
            root.right = null;
            root = n;
        }

        else {
            root.value = value;
        }

    }


    public void removeNode(T value) {
        if (root == null) return;

        root = splay(root, value);

        int cmp = value.compareTo(root.value);

        if (cmp == 0) {
            if (root.left == null) {
                root = root.right;
            }
            else {
                Node x = root.right;
                root = root.left;
                splay(root, value);
                root.right = x;
            }
        }

    }

    boolean search( T value) {
        root = splay(root, value);
        return value == root.value;
    }



    private Node splay(Node h, T value) {
        if (h == null) return null;

        int cmp1 = value.compareTo(h.value);

        if (cmp1 < 0) {
            if (h.left == null) {
                return h;
            }
            int cmp2 = value.compareTo(h.left.value);
            if (cmp2 < 0) {
                h.left.left = splay(h.left.left, value);
                h = rotateRight(h);
            }
            else if (cmp2 > 0) {
                h.left.right = splay(h.left.right, value);
                if (h.left.right != null)
                    h.left = rotateLeft(h.left);
            }

            if (h.left == null) return h;
            else                return rotateRight(h);
        }

        else if (cmp1 > 0) {
            if (h.right == null) {
                return h;
            }

            int cmp2 = value.compareTo(h.right.value);
            if (cmp2 < 0) {
                h.right.left  = splay(h.right.left, value);
                if (h.right.left != null)
                    h.right = rotateRight(h.right);
            }
            else if (cmp2 > 0) {
                h.right.right = splay(h.right.right, value);
                h = rotateLeft(h);
            }

            if (h.right == null) return h;
            else                 return rotateLeft(h);
        }

        else return h;
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return 1 + size(x.left) + size(x.right);
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        return x;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        return x;
    }

    @Override
    public int size() {
        return size(root);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object o) {
        return preOrder(root,(T)o);
    }

    boolean preOrder(Node root,T o)
    {
        if (root != null)
        {
            if(root.value == o) {
                return true;
            }
            if (preOrder(root.left,o)) return true;
            return preOrder(root.right, o);
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new SplayTree.SplayTreeIterator(this,root);
    }


    private class SplayTreeIterator implements Iterator<T> {

        private final SplayTree<T> tree;
        private final List<Node> elements;
        private Node currentNode;


        public SplayTreeIterator (SplayTree<T> tree, Node root){
            this.tree = tree;
            elements = new ArrayList<>();
            addToList(root);
            if (!elements.isEmpty()) currentNode = elements.get(0);
        }

        private void addToList(Node node){
            if (node != null){
                addToList(node.left);
                elements.add(node);
                addToList(node.right);
            }
        }
        @Override
        public boolean hasNext() {
            return currentNode!=null;
        }

        @Override
        public T next() {
            if (currentNode == null) return null;
            T result = currentNode.value;
            int index = elements.indexOf(currentNode) + 1;
            currentNode = index == elements.size() ? null : elements.get(index);
            return result;
        }


        @Override
        public void remove() {
            int index = currentNode == null ? elements.size() : elements.indexOf(currentNode);
            if (index < 1 )return;
            tree.remove(elements.get(index- 1).value);
        }

    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        Iterator<T> iterator = iterator();
        for (int i = 0; i < size(); i++)
            if (iterator.hasNext()) {
                array[i] = iterator.next();
            }
        return array;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T1> T1[] toArray(T1[] a) {
        Object[] array = toArray();
        if (a.length < size())
            return (T1[]) Arrays.copyOf(array, size(), a.getClass());
        System.arraycopy(array, 0, a, 0, size());
        if (a.length > size())
            a[size()] = null;
        return a;
    }

    @Override
    public boolean add(T t) {
        put(t);
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object o) {
        removeNode((T) o);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if(c.isEmpty()) throw new NullPointerException();
        for (Object node : c)
            if (!contains(node))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T node : c)
            this.add(node);
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if(c.isEmpty()) throw new NullPointerException();
        Iterator<T> iterator = iterator();
        while (iterator.hasNext())
            if (!c.contains(iterator.next())) {
                iterator.remove();
            }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c.isEmpty()) throw new NullPointerException();
        for(Object node: c){
            this.remove(node);
        }
        return true;
    }

    @Override
    public void clear() {
        root = null;
    }
}