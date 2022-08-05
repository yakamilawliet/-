package packages;

public class Thelinkedlist<E> {
    private int size;
    private Node<E> begin;
    private Node<E> end;

    public class Node<E> {
        private Node<E> pre;
        private Node<E> next;
        private E data;

        private Node() {
        }

        public Node(E data) {
            this.data = data;
        }
    }

    //在链表中插入元素
    public void insert(int index, E data) {
        Node<E> element = new Node<>(data);
        if (empty()) {
            begin = element;
            end = element;
        } else {
            if (index > size) {
                throw new RuntimeException("插入索引超出界限");
            } else {
                Node<E> check = getelements(index);
                Node<E> temp = check.pre;
                temp.next = element;
                element.pre = temp;
                element.next = check;
                check.pre = element;
            }
        }
        size++;
    }

    //删除指定索引元素
    public void delete(int index) {
        if (index > size - 1 || index < 0) {
            throw new RuntimeException("超过索引界限");
        }
        if (index == 0) {
            begin = begin.next;
            begin.pre = null;
        } else if (index == size - 1) {
            end = end.pre;
            end.next = null;
        } else {
            Node<E> node = getelements(index);
            Node<E> prev = node.pre;
            Node<E> next = node.next;
            prev.next = next;
            next.pre = prev;
            node.pre = null;
            node.next = null;
        }
        size--;
    }

    //删除对应内容的节点
    public void redelete(E data) {
        if (search(data)) {
            for (int i = 0; i < size; i++) {
                if (data.equals(getelements(i).data)) {
                    if (i == 0) {
                        begin = begin.next;
                        begin.pre = null;
                    } else if (i == size - 1) {
                        end = end.pre;
                        end.next = null;
                    } else {
                        Node<E> node = getelements(i);
                        Node<E> prev = node.pre;
                        Node<E> next = node.next;
                        prev.next = next;
                        next.pre = prev;
                        node.pre = null;
                        node.next = null;
                    }
                    size--;
                }
            }
        } else {
            throw new RuntimeException("无对应元素");
        }
    }

    //获取指定位置的节点元素
    public Node<E> getelements(int index) {
        Node<E> result = begin;
        int count = 0;
        while (count < index) {
            result = result.next;
            count++;
        }
        return result;
    }

    //检验链表是否为空
    public boolean empty() {
        return size == 0;
    }

    //按照元素内容进行查找
    public boolean search(E thedata) {
        int flag = 1;
        for (int i = 0; i < size; i++) {
            if (getelements(i).data.equals(thedata)) {
                return true;
            }
        }
        return false;
    }

    //按照索引进行查找
    public void find(int index) {
        System.out.println(getelements(index).data);
    }

    //对链表元素进行遍历输出
    public void traverse() {
        for (int i = 0; i < size; i++) {
            System.out.println(getelements(i).data);
        }
    }

    //对链表元素进行翻转
    public void flip() {
        Node<E> pre = null;
        Node<E> next = null;
        while (begin != null) {
            next = begin.next;
            begin.next = pre;
            begin.pre = next;
            pre = begin;
            begin = next;
        }
        Node<E> temp = begin;
        begin = end;
        end = temp;
    }

    //获取size
    public int getSize() {
        return size;
    }

}
