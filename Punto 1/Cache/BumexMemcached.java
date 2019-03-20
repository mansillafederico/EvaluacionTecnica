package Cache;

public class BumexMemcached {

	
		private static final BumexMemcached instance = new BumexMemcached();
		
		private BumexMemcached() {
			
		}
		
		public static BumexMemcached getInstance() {
			return instance;
		}
		
		public void set(String key, Object value) {
				
		}
		
		public Object get(String key) {
		
			return null;
		}
		
		public void delete(String key) {
			
	}
}
