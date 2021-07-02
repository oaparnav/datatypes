package datatypes;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FallbackMap<K, V> implements Map<K, V> {
	private Map<K, V> values;
	private Map<K, V> fallback;

	public FallbackMap(Map<K, V> values) {
		this.values = values;
		this.fallback = Map.of();
	}
	
	public FallbackMap(Map<K, V> values, Map<K, V> fallback) {
		this.values = values;
		this.fallback = fallback;
	}
	
	@Override
	public int size() {
		return keySet().size();
	}

	@Override
	public boolean isEmpty() {
		return values.isEmpty() && fallback.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return values.containsKey(key) || fallback.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return values.containsValue(value) || fallback.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return Optional.ofNullable(this.values.get(key))
				.orElseGet(() -> fallback.get(key));
	}

	@Override
	public V put(K key, V value) {
		throw new UnsupportedOperationException("FallbackMap is immutable");
	}

	@Override
	public V remove(Object key) {
		throw new UnsupportedOperationException("FallbackMap is immutable");
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {		
		throw new UnsupportedOperationException("FallbackMap is immutable");
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("FallbackMap is immutable");
	}

	@Override
	public Set<K> keySet() {
		Set<K> keySet = new HashSet<>(values.keySet());
		keySet.addAll(fallback.keySet());
		return keySet;
	}

	@Override
	public Collection<V> values() {
		return keySet().stream().map(key -> get(key))
				.collect(Collectors.toSet());
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return keySet().stream().map(key -> Map.entry(key, get(key)))
				.collect(Collectors.toSet());
	}
	
	public String toString() {
		return entrySet().stream()
				.map(entry -> entry.getKey() + "=" + entry.getValue())
				.collect(Collectors.joining(", ", "{", "}"));
	}
}