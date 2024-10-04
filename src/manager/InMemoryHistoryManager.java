package manager;

import task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager{
    private static class MyLinkedList {
        private final Map<Long, Node> taskHistoryList = new HashMap<>();
        private Node head;
        private Node tail;


        private void linkLast(Task task) {
            Node newNode = new Node(null, task, tail);
            if (tail != null) {
                tail.next = newNode;
            }
            tail = newNode;
            if (head == null) {
                head = newNode;
            }

            taskHistoryList.put(task.getTaskId(), newNode);
        }

        private List<Task> getTasks() {
            List<Task> result = new ArrayList<>();
            Node element = head;
            while (element != null) {
                result.add(element.getTask());
                element = element.getNext();
            }
            return result;
        }

        private void removeNode(Node node) {
            if (node != null) {
                taskHistoryList.remove(node.getTask().getTaskId());
                Node prev = node.getPrev();
                Node next = node.getNext();

                if (head == node) {
                    head = next;
                }
                if (tail == node) {
                    tail = prev;
                }

                if (prev != null) {
                    prev.setNext(next);
                }

                if (next != null) {
                    next.setPrev(prev);
                }
            }
        }

        private Node getNode(int id) {
            return taskHistoryList.get(id);
        }
    }

    private final MyLinkedList spisok = new MyLinkedList();

    @Override
    public void add(Task task) {
        spisok.linkLast(task);
    }

    @Override
    public void remove(int id) {
        spisok.removeNode(spisok.getNode(id));
    }

    @Override
    public List<Task> getHistory() {
        return spisok.getTasks();
    }
}

    class Node {
    public Task task;
    public Node next;
    public Node prev;

    public Node(Node prev, Task task, Node next) {
        this.prev = prev;
        this.task = task;
        this.next = next;
    }

    public Task getTask() {
        return task;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(task, node.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(task);
    }
}