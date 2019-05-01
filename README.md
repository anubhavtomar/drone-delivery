# Drone Delivery Challenge

Language : Java

Compilation command
```
javac -d bin/ src/delivery/interafces/*.java src/delivery/main/*.java src/delivery/store/*.java src/delivery/threads/*.java src/delivery/utility/*.java src/delivery/wrappers/*.java
```

Execution command
```
java -cp bin delivery.main.Main "/Users/anubhav/repo/drone-delivery/files/input.txt"
```

Assumptions
* Promoters are considered to be with time to delivery from 0 to <2 hours.
* Neutrals are considered to be with time to delivery from 2 to <4 hours.
* Detractors are considered to be with time to delivery >=4 hours.
* Orders in the input file matches the sample given in the problem. I am using a RegEx match on the full string. RegEx: "^WM\\d+\\s([NSEW]\\d+){1,2}\\s([0-9]{2}+[:]){2}[0-9]{2}$". If there is no match, I am skipping the order.
* OrderId should start with WM followed by any number of digits i.e WM####
* Direction should have NSEW followed by any number of digits. There could be at most 2 times the same sequence can occur. And there should not be any complementary directino combination e.f N2S1 or E4W5. These cases should be handled in the part of code which is handling the order generation.
* Timestamp should follow HH:mm:SS.

Design
* 6 packages delivery.interfaces, delivery.main, delivery.store, delivery.threads, delivery.utility, delivery.wrappers.
* delivery.interface has 2 interface: Order_Item.java, Order_Warehouse.java
* delivery.main has the Main.java class.
* delivery.store has 2 classes: Order_Item.java, Order_Warehouse.java
* delivery.threads has 4 thread classes: Drone_Thread.java, Mater_Thread.java, Warehouse_Thread.java, Writer_Thread.java
* delivery.utility has 3 classes: File_Parser.java, File_Writer.java, Order_Category_Comaparator.java; 1 enum: Order_Category.java
* delivery.wrappers has 1 class: Drone_Wrapper.java

Execution Flow
* Main class calls the start_delivery function of the Drone_Wrapper class.
* start_delivery function starts the Master_Thread.
* Master_Thread starts 3 separate thread Warehouse_Thread, Drone_Thread, and Writer_Thread.
* Warehouse_Thread: For fetching new orders every 30 minutes starting from 06:00:00 and pass it to the Order_Warehouse object to store it in the priority_queue.
* Drone_Thread: For fetching the most priority order from the queue and write it to an output buffer.
* Writer_Thread: For reading the output buffer and writing the content to an output file.
* Master_Thread stops all the threads and itself using a shared STOP property in all the 3 thread.
