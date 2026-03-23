// Time Complexity: seat() - O(n), leave() - O(log n) where n is number of students
// Space Complexity: O(n) - set stores all seated students
class ExamRoom {
public:
    int N;
    set<int> students;

    ExamRoom(int n) { N = n; }

    int seat() {
        int student = 0;

        if (!students.empty()) {
            int dist =
                *students.begin(); // distance from seat 0 to first student
            int prev = -1;

            for (int s : students) {
                if (prev != -1) {
                    int d = (s - prev) / 2;
                    if (d > dist) {
                        dist = d;
                        student = prev + d;
                    }
                }
                prev = s;
            }

            // Check right-most seat
            if (N - 1 - *students.rbegin() > dist) {
                student = N - 1;
            }
        }

        students.insert(student);
        return student;
    }

    void leave(int p) { students.erase(p); }
};

/**
 * Your ExamRoom object will be instantiated and called as such:
 * ExamRoom* obj = new ExamRoom(n);
 * int param_1 = obj->seat();
 * obj->leave(p);
 */