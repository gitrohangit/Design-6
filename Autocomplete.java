// Time Complexity : O(N), going over all the words from map to search relevant words based on prefix/input
// Space Complexity : O(N)
// Did this code successfully run on Leetcode : yes
// Three line explanation of solution in plain english  : search over all the words from map based on prefix/input.

class AutocompleteSystem {
    HashMap<String,Integer> map;
    String input;

    public AutocompleteSystem(String[] sentences, int[] times) {
        this.map = new HashMap<>();
        int n = sentences.length;
        this.input = "";

        for(int i = 0; i < n ; i++){
            map.put(sentences[i], map.getOrDefault(sentences[i], 0) + times[i]);
        }
        
    }
    
    public List<String> input(char c) {
        if(c == '#'){
            map.put(input, map.getOrDefault(input, 0) + 1);
            input = ""; //reset
            return new ArrayList<>();
        }

        PriorityQueue<String> pq = new PriorityQueue<>((a,b) -> {
            //same freq
            if(map.get(a) == map.get(b)){
                return b.compareTo(a); // as we want bigger string to be at the top
            }

            return map.get(a) - map.get(b);
        });

        input += c;
        // System.out.println(input);
        //search based on prefix
        for(String str : map.keySet()){
            if(str.startsWith(input)){
                pq.add(str);
                if(pq.size() > 3){
                    pq.poll();
                }
            }
        }

        List<String> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0,pq.poll());
        }

        return result;
    }
}

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List<String> param_1 = obj.input(c);
 */

 //Approach 2:
// Time Complexity : O(n), n = number of words in list corresponding to the prefix
// Space Complexity : O(N)
// Did this code successfully run on Leetcode : yes
// Three line explanation of solution in plain english  : search over all the words from map based on prefix/input.

 class AutocompleteSystem {
    class TrieNode{
        TrieNode[] children;
        List<String> startsWith;

        public TrieNode(){
            this.children = new TrieNode[256];
            this.startsWith = new ArrayList<>();
        }
    }

    private void insert(TrieNode root, String word){
        TrieNode curr = root;
        for(char c : word.toCharArray()){
            if(curr.children[c - ' '] == null){
                curr.children[c - ' '] = new TrieNode();
            }
            
            curr = curr.children[c - ' '];
            curr.startsWith.add(word);
        }
    }

    private List<String> search(TrieNode root, String prefix){
        TrieNode curr = root;
        for(char c : prefix.toCharArray()){
            if(curr.children[c - ' '] == null){ // do not have the prefix
                return new ArrayList<String>();
            }
            curr = curr.children[c - ' '];
        }
        return curr.startsWith;
    }
    

    HashMap<String,Integer> map;
    String input;
    TrieNode root;

    public AutocompleteSystem(String[] sentences, int[] times) {
        this.map = new HashMap<>();
        int n = sentences.length;
        this.input = "";
        this.root = new TrieNode();

        for(int i = 0; i < n ; i++){
            map.put(sentences[i], map.getOrDefault(sentences[i], 0) + times[i]);
            insert(root, sentences[i]);
        }
        
    }
    
    public List<String> input(char c) {
        if(c == '#'){
            if(!map.containsKey(input)){
                insert(root,input);
            }
            map.put(input, map.getOrDefault(input, 0) + 1);
            input = ""; //reset
            return new ArrayList<>();
        }

        PriorityQueue<String> pq = new PriorityQueue<>((a,b) -> {
            //same freq
            if(map.get(a) == map.get(b)){
                return b.compareTo(a); // as we want bigger string to be at the top
            }

            return map.get(a) - map.get(b);
        });

        input += c;
        // System.out.println(input);
        //search based on prefix
        for(String str : search(root,input)){
            pq.add(str);
            if(pq.size() > 3){
                pq.poll();
            }
        }

        List<String> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0,pq.poll());
        }

        return result;
    }
}

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List<String> param_1 = obj.input(c);
 */

//Approach 3

// Time Complexity : search is going towards constant in length of prefix we will be able to get the words. 
// Space Complexity :
// Did this code successfully run on Leetcode : yes
// Three line explanation of solution in plain english  : Maintain top k words at each node

 class AutocompleteSystem {
    class TrieNode{
        TrieNode[] children;
        List<String> startsWith;

        public TrieNode(){
            this.children = new TrieNode[256];
            this.startsWith = new ArrayList<>();
        }
    }

    private void insert(TrieNode root, String word){
        TrieNode curr = root;
        for(char c : word.toCharArray()){
            if(curr.children[c - ' '] == null){
                curr.children[c - ' '] = new TrieNode();
            }
            
            curr = curr.children[c - ' '];

            List<String> li = curr.startsWith;

            if(!li.contains(word)){
                li.add(word);
            }
            // hottest trendning should be at the front of list.
            Collections.sort(li,(a,b) -> {
                if(map.get(a) == map.get(b)){
                    return a.compareTo(b);
                }
                return map.get(b) - map.get(a); 
            });


            if(li.size() > 3){
                li.remove(li.size()-1);
            }

        }
    }

    private List<String> search(TrieNode root, String prefix){
        TrieNode curr = root;
        for(char c : prefix.toCharArray()){
            if(curr.children[c - ' '] == null){ // do not have the prefix
                return new ArrayList<String>();
            }
            curr = curr.children[c - ' '];
        }
        return curr.startsWith;
    }
    

    HashMap<String,Integer> map;
    String input;
    TrieNode root;

    public AutocompleteSystem(String[] sentences, int[] times) {
        this.map = new HashMap<>();
        int n = sentences.length;
        this.input = "";
        this.root = new TrieNode();

        for(int i = 0; i < n ; i++){
            map.put(sentences[i], map.getOrDefault(sentences[i], 0) + times[i]);
            insert(root, sentences[i]);
        }
        
    }
    
    public List<String> input(char c) {
        if(c == '#'){ 
            map.put(input, map.getOrDefault(input, 0) + 1);
            insert(root,input); // insert everytime to adjust the search based on frequency
            input = ""; //reset
            return new ArrayList<>();
        }
        input += c;
        return search(root,input);
    }
}

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List<String> param_1 = obj.input(c);
 */