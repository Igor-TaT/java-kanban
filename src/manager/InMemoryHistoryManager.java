package manager;

import task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final MyLinkedList taskHistoryList = new MyLinkedList();

    @Override
    public void add(Task task) {
        taskHistoryList.linkLast(task);
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
                tail = element;
                head = element;
                element.setNext(null);
                element.setPrev(null);
            } else {
                element.setPrev(tail);
                element.setNext(null);
                tail.setNext(element);
                tail = element;
            }
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

                if (head == node) {
                    head = node.getNext();
                }
                if (tail == node) {
                    tail = node.getPrev();
                }
                if (prev != null) {
                    prev.setNext(next);
                }
                if (next != null) {
                    next.setPrev(prev);
                }
            }
        }

        private Node getNode(long id) {
            return nodeMap.get(id);
        }

    }


}