package com.conceptcoding.uberlld;

import java.util.*;

public class FantasyLeaderboard {

    static class User{
        String userId;
        List<String> playerIds;
        int totalScore;

        public User(String userId, List<String> playerIds){
            this.userId = userId;
            this.playerIds = playerIds;
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
    public FantasyLeaderboard(){
        leaderBoard = new TreeSet<>(userComparator);
        playIdtoUsers = new HashMap<>();
        playerScores = new HashMap<>();
    }

    /**
     * Add a new user with their team of players
     * Time Complexity: O(P * log(U)) where P = number of players in team, U = total users
     *   - Calculating initial score: O(P) for iterating through playerIds
     *   - Adding to TreeSet: O(log(U)) for inserting into sorted tree
     *   - Adding to playIdtoUsers map: O(P) for each player
     * Space Complexity: O(P) for storing the user's player list and map entries
     */
    public void addUser(String userId, List<String> playerIds) {
        User user = new User(userId, playerIds);
        // Calculate initial score based on current player scores
        for(String playerId : playerIds) {
            user.totalScore += playerScores.getOrDefault(playerId, 0);
        }
        users.put(userId, user);
        leaderBoard.add(user);
        for(String playerId:playerIds){
            playIdtoUsers.putIfAbsent(playerId, new HashSet<User>());
            playIdtoUsers.get(playerId).add(user);
        }
    }

    /**
     * Update a player's score and recalculate affected users' scores
     * Time Complexity: O(A * log(U)) where A = affected users (users with this player), U = total users
     *   - HashMap operations: O(1) average
     *   - For each affected user: O(log(U)) to remove + O(log(U)) to re-add = O(2*log(U))
     *   - Total: O(A * log(U))
     * Space Complexity: O(1) - only updating existing structures
     */
    public void addScore(String playerId, int score) {
        // Update the player's score
        playerScores.put(playerId, playerScores.getOrDefault(playerId, 0) + score);

        Set<User> affectedUsers = playIdtoUsers.get(playerId);
        if(affectedUsers!=null){
            for(User user:affectedUsers){
                // Remove user from TreeSet before updating score
                leaderBoard.remove(user);
                user.totalScore+=score;
                // Re-add user to TreeSet with updated score
                leaderBoard.add(user);
            }
        }
    }

    /**
     * Get top K users sorted by score (descending) and userId (ascending for ties)
     * Time Complexity: O(K) where K = requested top users
     *   - TreeSet iteration is already sorted, so we just iterate up to K elements
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
