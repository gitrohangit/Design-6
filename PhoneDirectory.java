// TC: O(1) for all operations. To achieve O(1) for get operation use a linear data structure and for check and realease use hashing based data structure.

class PhoneDirectory {
    private HashSet<Integer> set; // available numbers
    private Stack<Integer> st;

    public PhoneDirectory(int maxNumbers) {
        this.set = new HashSet<>();
        this.st = new Stack<>();
        for(int i = 0 ; i < maxNumbers ; i++){
            set.add(i);
            st.add(i);
        }
    }
    
    public int get() {
        if(st.isEmpty()) return -1;
        int n = st.pop();
        set.remove(n);
        return n;
    }
    
    public boolean check(int number) {
        return set.contains(number);
    }
    
    public void release(int number) {
        if(!set.contains(number)){
            set.add(number);
            st.add(number);
        }
    }
}

/**
 * Your PhoneDirectory object will be instantiated and called as such:
 * PhoneDirectory obj = new PhoneDirectory(maxNumbers);
 * int param_1 = obj.get();
 * boolean param_2 = obj.check(number);
 * obj.release(number);
 */