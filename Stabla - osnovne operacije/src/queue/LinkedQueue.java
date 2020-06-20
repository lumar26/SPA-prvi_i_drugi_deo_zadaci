package queue;

public class LinkedQueue<E> implements Queue {
	private QueueNode<E> front;
	private QueueNode<E> rear;
	private int count;

	@Override
	public boolean isEmpty() {
		if (count == 0)
			return true;
		return false;
	}

	@Override
	public void enqueue(Object e) {
		QueueNode<E> node = new QueueNode<E>((E) e);
		if (front == null) {
			front = rear = node;
			count++;
		}

		else {
			rear.setNext(node);
			node.setBefore(rear);
			rear = node;
		}

	}

	@Override
	public Object dequeue() {
		if (count == 0)
			return null;
		E temp = front.getData();
		front = front.getNext();
		try {
			front.setBefore(null);
		} catch (NullPointerException e) {
			//kad je ostao samo jedan element u redu
			count = 0;
		}
		return temp;
	}

	@Override
	public Object peek() {
		if(front != null) return front.getData();
		return null;
	}

}
