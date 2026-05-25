package com.conceptcoding.codezymLLD;

import java.util.*;
/*
31. Design a Leaderboard for Fantasy Teams

Build an in-memory leaderboard for a fantasy-sports style app. Each user creates exactly one team made up of one or more players. As a live match progresses, players receive positive or negative points. A user’s score is the sum of the current scores of all players on that user’s team. You must support querying the Top-K users ranked by score.

Rules
Every user has exactly one team; teams contain one or more player IDs.
A player may belong to multiple users’ teams (many-to-many relation).
Each user’s initial score is 0 (before any player points are applied).
Player score updates are deltas and may be negative or positive.
User score = sum of scores of all players currently in that user’s team.
Leaderboard ordering:
Primary: user score in descending order (higher is better).
Tie-break: userId in lexicographically ascending order.
Methods
void addUser(String userId, List<String> playerIds)
userId will always be globally unique and non-blank.
playerIds will contain at least one element; each playerId is non-blank and valid.
Effect: registers a new user with their team and an initial score of 0.
Players may already have accrued points from prior updates; the user’s score should immediately reflect the current player scores after registration.
void addScore(String playerId, int score)
Updates the specified player’s cumulative score by the given delta.
score is an integer in the inclusive range [-1000, 1000].
All users whose team includes playerId must have their team scores updated accordingly.
Leaderboard ordering must remain consistent with the updated scores.
List<String> getTopK(int k)
k >= 1. If k exceeds the total number of users, return all users.
Return the list of userId values sorted by:
Score descending, then
userId lexicographically ascending to break ties.
 */
public class FantasyLeaderboard {

    static class User {
        String userId;
        int totalScore;

        public User(String userId) {
            this.userId = userId;
            this.totalScore = 0;
        }
    }

    Map<String, Set<User>> playIdtoUsers;
    Map<String, Integer> playerScores; // Track individual player scores
    TreeSet<User> leaderBoard;
    Map<String, User> users; // For quick access to user objects by userId
    Comparator<User> userComparator = (a, b) -> {
        if (a.totalScore != b.totalScore) return b.totalScore - a.totalScore;
        return a.userId.compareTo(b.userId);
    };

    /**
     * Initialize empty leaderboard
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public FantasyLeaderboard() {
        leaderBoard = new TreeSet<>(userComparator);
        playIdtoUsers = new HashMap<>();
        playerScores = new HashMap<>();
        users = new HashMap<>();
    }

    /**
     * Add a new user with their team of players
     * Time Complexity: O(P * log(U)) where P = number of players in team, U = total users
     * - Calculating initial score: O(P) for iterating through playerIds
     * - Adding to TreeSet: O(log(U)) for inserting into sorted tree
     * - Adding to playIdtoUsers map: O(P) for each player
     * Space Complexity: O(P) for storing the user's player list and map entries
     */
    public void addUser(String userId, List<String> playerIds) {
        User user = new User(userId);
        // Calculate initial score based on current player scores
        for (String playerId : playerIds) {
            user.totalScore += playerScores.getOrDefault(playerId, 0);
        }
        users.put(userId, user);
        leaderBoard.add(user);
        for (String playerId : playerIds) {
            playIdtoUsers.putIfAbsent(playerId, new HashSet<>());
            playIdtoUsers.get(playerId).add(user);
        }
    }

    /**
     * Update a player's score and recalculate affected users' scores
     * Time Complexity: O(A * log(U)) where A = affected users (users with this player), U = total users
     * - HashMap operations: O(1) average
     * - For each affected user: O(log(U)) to remove + O(log(U)) to re-add = O(2*log(U))
     * - Total: O(A * log(U))
     * Space Complexity: O(1) - only updating existing structures
     */
    public void addScore(String playerId, int score) {
        // Update the player's score
        playerScores.put(playerId, playerScores.getOrDefault(playerId, 0) + score);

        Set<User> affectedUsers = playIdtoUsers.get(playerId);
        if (affectedUsers != null) {
            for (User user : affectedUsers) {
                // Remove user from TreeSet before updating score
                leaderBoard.remove(user);
                user.totalScore += score;
                // Re-add user to TreeSet with updated score
                leaderBoard.add(user);
            }
        }
    }

    /**
     * Get top K users sorted by score (descending) and userId (ascending for ties)
     * Time Complexity: O(K) where K = requested top users
     * - TreeSet iteration is already sorted, so we just iterate up to K elements
     * Space Complexity: O(K) for storing the result list
     */
    public List<String> getTopK(int k) {
        List<String> res = new ArrayList<>();

        int count = 0;

        for (User u : leaderBoard) {

            res.add(u.userId);

            count++;

            if (count == k) break;
        }

        return res;
    }
}

/*
addUser("uA", ["p1", "p2"])
addUser("uB", ["p2"])
getTopK(2)              // ["uA", "uB"]  // both 0; "uA" < "uB"

addScore("p2", 10)      // p2 = 10; uA=10 (p1=0 + p2=10), uB=10
getTopK(2)              // ["uA", "uB"]  // tie by score, lex by userId

addScore("p1", 3)       // p1 = 3; uA=13, uB=10
getTopK(1)              // ["uA"]

addScore("p2", -5)      // p2 = 5; uA=8 (3+5), uB=5
getTopK(5)              // return all users: ["uA", "uB"]

 */
