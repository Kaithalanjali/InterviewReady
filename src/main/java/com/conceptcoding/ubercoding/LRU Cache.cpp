struct Node{
    int key;
    int value;
    Node* next;
    Node* prev;

    Node(){
        key=-1;
        value=-1;
        next=NULL;
        prev=NULL;
    }

    Node(int key, int value){
        this->key = key;
        this->value = value;
        next = nullptr;
        prev = nullptr;
    }
};
// Time Complexity: O(1) for both get() and put() operations
// Space Complexity: O(capacity) - hashmap and doubly linked list store at most capacity elements
class LRUCache {
public:
    int capacity;
    unordered_map<int, Node*> mapp;
    Node* head;
    Node* tail;
    LRUCache(int capacity) {
        this->capacity = capacity;
        mapp.clear();
        head = new Node();
        tail = new Node();

        head->next = tail;
        tail->next = head;
    }

    int get(int key) {
        if(!mapp.count(key)){
            return -1;
        }

        Node* node = mapp[key];
        deleteNode(node);
        insertAfterHead(node);
        return node->value;
    }

    void put(int key, int value) {
        if(mapp.count(key)){
            Node* node = mapp[key];
            node->value = value;
            deleteNode(node);
            insertAfterHead(node);
        }else{
            if(mapp.size()==capacity){
                Node* delNode = tail->prev;
                mapp.erase(delNode->key);
                deleteNode(delNode);
            }
            Node* node = new Node(key, value);
            mapp[key] = node;
            insertAfterHead(node);
        }
    }

    void deleteNode(Node* node){
        Node* prevNode = node->prev;
        Node* nextNode = node->next;

        prevNode->next = nextNode;
        nextNode->prev = prevNode;
    }

    void insertAfterHead(Node* node){
        Node* headNext = head->next;

        head->next = node;
        node->prev = head;

        node->next = headNext;
        headNext->prev = node;

    }
};

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache* obj = new LRUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */