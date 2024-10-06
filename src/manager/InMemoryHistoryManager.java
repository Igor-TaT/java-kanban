package manager;

import task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final MyLinkedList taskHistoryList = new MyLinkedList();

    @Override
    public void add(Task task) {
        if (task != null) {
            taskHistoryList.linkLast(task);
        }
    }

    @Override
    public void remove(long id) {
        taskHistoryList.removeNode(taskHistoryList.getNode(id));
    }

    @Override
    public List<Task> getHistory() {
        return taskHistoryList.getTasks();
    }

    private static class MyLinkedList {
        private final Map<Long, Node> nodeMap = new HashMap<>();
        private Node head;
        private Node tail;

        private void linkLast(Task task) {
            Node element = new Node();
            element.setTask(task);
            if (nodeMap.containsKey(task.getTaskId())) {
                removeNode(nodeMap.get(task.getTaskId()));
            }
            if (head == null) {
                head = element;
            } else {
                element.setPrev(tail);
                tail.setNext(element);

            }
            tail = element;
            nodeMap.put(task.getTaskId(), element);
        }

        private List<Task> getTasks() {
            List<Task> taskList = new ArrayList<>();
            Node element = head;
            while (element != null) {
                taskList.add(element.getTask());
                element = element.getNext();
            }
            return taskList;
        }

        private void removeNode(Node node) {
            if (node != null) {
                nodeMap.remove(node.getTask().getTaskId());
                Node prev = node.getPrev();
                Node next = node.getNext();

                if (head == node && tail == node) {
                    head = null;
                    tail = null;
                } else if (head == node && tail != node) {
                    head = next;
                    head.setPrev(null);
                } else if (head != node && tail == node) {
                    tail = prev;
                    tail.setNext(null);
                } else {
                    prev.setNext(next);
                    next.setPrev(prev);
                }

            }
        }


        private Node getNode(long id) {
            return nodeMap.get(id);
        }

    }

}
