package queue;

public class QueueNode<E> {
	private E data;
	private QueueNode next;
	private QueueNode before;
	
	public QueueNode(E data) {
		super();
		this.data = data;
		this.next = null;
		this.before = null;
	}
	public QueueNode getBefore() {
		return before;
	}
	public void setBefore(QueueNode before) {
		this.before = before;
	}
	public E getData() {
		return data;
	}
	public void setData(E data) {
		this.data = data;
	}
	public QueueNode getNext() {
		return next;
	}
	public void setNext(QueueNode next) {
		this.next = next;
	}
	
	
}
