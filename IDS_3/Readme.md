Internet Data Streaming
----

---
by Raghuveer sharma (ufid: 5094-6752)

Folder Structure

This zip contains 4 java files and 1 input and 3 output files

- Main.java: The main driver file which parses input file and trigger the execution of other 3 classes
- CountMin.java - this class has 3 methods, insert which puts data into the flow table, 
getMin which return min of all the values in flow 
and execute which call the before 2 methods, calc to error rate and print output to file.
- CounterSketch.java - this class has 3 methods, insert, getMedian which get the median of 3 values
and execute which finds out error rate and prints output to file.
- ActiveCounter.java - This file computes the 32 bit active counter and returns the value of the counter after the computation.
- project3input.txt - input file containing ip address and flow values
- 3 outputs - CountMinOutput.txt and CounterSketch.txt (contains absolute error rate, list of entries) and ActiveCounter.txt (contains count of active counters)
