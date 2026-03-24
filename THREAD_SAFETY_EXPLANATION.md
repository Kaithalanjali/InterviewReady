# Thread Safety in BookMyShow - How to Prevent Double Booking

## Problem: Race Condition
When multiple users try to book the same seat simultaneously, without proper synchronization:

```
Time  | User A                    | User B
------|---------------------------|---------------------------
T1    | Check seat [0,0] is free  |
T2    |                           | Check seat [0,0] is free
T3    | Mark seat [0,0] as booked |
T4    |                           | Mark seat [0,0] as booked ❌
```
**Result**: Both users book the same seat! 💥

## Solution: Synchronized Blocks

### 1. **bookTicket Method** (Lines 125-183)
```java
synchronized(show) {
    // 1. Check availability
    if(show.freeSeats < ticketsCount) return;
    
    // 2. Find seats
    // 3. Mark seats as booked
    // 4. Update free seat count
    // 5. Create ticket
}
```

**What it does:**
- Only ONE thread can execute booking for a specific show at a time
- Other threads wait until the first thread completes
- Ensures atomic operation: all steps happen together or not at all

### 2. **cancelTicket Method** (Lines 185-201)
```java
synchronized(show) {
    // 1. Unmark seats
    // 2. Increment free seat count
    // 3. Remove ticket
}
```

**Why needed:**
- Prevents cancellation while booking is in progress
- Ensures consistent free seat count

### 3. **getFreeSeatsCount Method** (Lines 203-212)
```java
synchronized(show) {
    return show.freeSeats;
}
```

**Why needed:**
- Reading `freeSeats` during concurrent bookings/cancellations
- Ensures you get a consistent, up-to-date value

## How It Works in Practice

### Scenario 1: Two Users, Same Show
```
User A wants 2 seats for Show 1
User B wants 3 seats for Show 1

Timeline:
1. User A acquires lock on Show 1
2. User B tries to acquire lock → BLOCKED (must wait)
3. User A books seats [0,0], [0,1]
4. User A releases lock
5. User B acquires lock on Show 1
6. User B books seats [0,2], [0,3], [0,4]
7. User B releases lock

✅ No conflict!
```

### Scenario 2: Two Users, Different Shows
```
User A wants seats for Show 1
User B wants seats for Show 2

Timeline:
1. User A acquires lock on Show 1
2. User B acquires lock on Show 2 (different lock!)
3. Both execute simultaneously
4. Both release locks

✅ Parallel execution - no waiting!
```

## Key Design Decisions

### ✅ Fine-Grained Locking (Per Show)
```java
synchronized(show)  // Lock specific show object
```
**Benefit**: Users booking different shows don't block each other

### ❌ What We DIDN'T Do (Coarse-Grained Locking)
```java
synchronized(this)  // Lock entire BookMyShow instance
```
**Problem**: Would serialize ALL bookings system-wide (too slow!)

## Performance Characteristics

| Operation | Without Sync | With Sync (Our Approach) |
|-----------|--------------|--------------------------|
| Different shows | Parallel | Parallel ✅ |
| Same show | Race condition ❌ | Serialized ✅ |
| Throughput | High but broken | High and correct |

## Thread Safety Guarantees

1. ✅ **No double booking**: Impossible for two users to book the same seat
2. ✅ **Atomic operations**: Booking either completes fully or not at all
3. ✅ **Consistent state**: Free seat count always accurate
4. ✅ **Deadlock-free**: Single lock per show, no circular dependencies
5. ✅ **High concurrency**: Only same-show bookings are serialized

## Visual Example

```
Cinema Layout:
[0,0] [0,1] [0,2] [0,3] [0,4]
[1,0] [1,1] [1,2] [1,3] [1,4]

Concurrent Requests:
- User A: Book 3 continuous seats
- User B: Book 2 continuous seats

WITHOUT Synchronization (❌ Broken):
T1: A checks row 0, sees [0,0][0,1][0,2] free
T2: B checks row 0, sees [0,0][0,1] free (A hasn't marked yet!)
T3: A books [0,0][0,1][0,2]
T4: B books [0,0][0,1] ❌ CONFLICT!

WITH Synchronization (✅ Correct):
T1: A acquires lock
T2: A checks row 0, sees [0,0][0,1][0,2] free
T3: A marks [0,0][0,1][0,2] as booked
T4: A releases lock
T5: B acquires lock
T6: B checks row 0, sees [0,3][0,4] free
T7: B books [0,3][0,4] ✅ SUCCESS!
```

## Code Flow with Synchronization

```
bookTicket("ticket1", showId=1, count=2)
    ↓
Get Show object for showId=1
    ↓
synchronized(show) { ← ACQUIRE LOCK
    ↓
    Check if 2 seats available
    ↓
    Find best 2 seats
    ↓
    Mark seats as booked in show.booked[][]
    ↓
    Decrease show.freeSeats by 2
    ↓
    Create Ticket object
    ↓
    Return seat list
    ↓
} ← RELEASE LOCK
```

## Testing Thread Safety

To test if synchronization works:

```java
// Simulate concurrent bookings
ExecutorService executor = Executors.newFixedThreadPool(10);
for(int i = 0; i < 100; i++) {
    executor.submit(() -> {
        bookTicket("ticket-" + UUID.randomUUID(), showId, 1);
    });
}
executor.shutdown();
executor.awaitTermination(1, TimeUnit.MINUTES);

// Verify: freeSeats + bookedTickets = totalSeats
// With proper sync: This should always be true ✅
// Without sync: This would fail ❌
```

## Summary

Your implementation uses **Java's intrinsic locks** (synchronized keyword) to ensure:
- **Mutual Exclusion**: Only one thread at a time can book seats for a specific show
- **Visibility**: All threads see consistent seat availability
- **Atomicity**: Operations complete fully or not at all

This prevents the race condition where two users could book the same seat simultaneously! 🎯
