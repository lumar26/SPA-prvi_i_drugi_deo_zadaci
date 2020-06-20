package queue;

public interface Queue<E> {
	public boolean isEmpty();
	public void enqueue(E e);
	public E dequeue();
	public E peek();
}
