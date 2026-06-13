# shortest-path-using-sp

Shortest path through a required intermediate vertex using Dijkstra's algorithm in Java.

---

## About

Written in Java, this project finds the shortest path from a source vertex to a destination vertex that passes through a specified intermediate vertex. The graph is loaded from `NYC.txt` and uses an edge-weighted undirected graph. Dijkstra's undirected shortest path algorithm is run twice - once from the source to the middle vertex and once from the middle vertex to the destination. The implementation is based on reference code from "Algorithms, 4th Edition" by Sedgewick and Wayne.

## Usage

Run the program and enter a source vertex, a destination vertex, and a middle vertex when prompted. The resulting path is printed to stdout as a sequence of edges, or a message is shown if no such path exists.

## Getting Started

### Prerequisites

- Java 11 or later
- `NYC.txt` in the working directory

### Building

**Unix**
```
javac -d out src/se/kth/*.java
```

**Windows**
```
javac -d out src\se\kth\*.java
```

### Running

**Unix**
```
java -cp out se.kth.Main
```

**Windows**
```
java -cp out se.kth.Main
```

---

MIT License - see [LICENSE](LICENSE)
