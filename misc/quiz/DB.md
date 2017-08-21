# Database Quiz

## Isolation levels

* Read uncommitted
* Read committed 
* Repeatable read
* Serializable

## Optimistic vs Pessimistic locking 

* **Optimistic Locking** is a strategy where you read a record, take note of a version number (other methods to do this involve dates, timestamps or checksums/hashes) and check that the version hasn't changed before you write the record back
* **Pessimistic Locking** is when you lock the record for your exclusive use until you have finished with it.
Requires you to be careful with your application design to avoid **Deadlocks**

## ACID

* Atomicity
* Consistency
* Isolation
* Durability

## BASE

* Basically available
* Soft state
* Eventually consistent